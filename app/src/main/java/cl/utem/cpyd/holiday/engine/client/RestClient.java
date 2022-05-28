package cl.utem.cpyd.holiday.engine.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import okhttp3.CacheControl;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author seba
 */
@Service
public class RestClient implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Timeout de conexión
     */
    private static final Integer CONNECTION_TIMEOUT = 10;
    /**
     * Timeout de lectura
     */
    private static final Integer READ_TIMEOUT = 20;
    /**
     * Agenta de usuario
     */
    private static final String USER_AGENT = "CPYD/1.0.0 (Ejemplo universitario utem.cl)";
    /**
     * Tipo mime para Json
     */
    private static final String JSON_MIME = "application/json";
    /**
     * Media Type para Json (usado en POST)
     */
    private static final MediaType JSON = MediaType.parse("application/json");
    /**
     * Cantidad de peticiones concurrentes
     */
    private static final Integer MAX_REQUEST = 128;
    /**
     * Mapeador Json
     */
    public static final ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper();
        MAPPER.registerModule(new JavaTimeModule());
        MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    /**
     * Cliente HTTP/HTTPS
     */
    private OkHttpClient client = null;

    /**
     * Validador de url con soporte para url locales
     */
    private static final UrlValidator URL_VALIDATOR = new UrlValidator(UrlValidator.ALLOW_LOCAL_URLS);

    /**
     * LOG de eventos
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RestClient.class);

    /**
     * Instancia los objetos usados para la comunicación http/https
     */
    @PostConstruct
    public void initClient() {
        try {
            ConnectionPool connectionPool = new ConnectionPool(MAX_REQUEST, READ_TIMEOUT, TimeUnit.SECONDS);
            Dispatcher dispatcher = new Dispatcher(Executors.newFixedThreadPool(MAX_REQUEST));
            dispatcher.setMaxRequests(MAX_REQUEST);
            dispatcher.setMaxRequestsPerHost(MAX_REQUEST);

            this.client = new OkHttpClient.Builder()
                    .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    .connectionPool(connectionPool)
                    .dispatcher(dispatcher)
                    .retryOnConnectionFailure(true)
                    .build();
        } catch (Exception e) {
            LOGGER.error("Error al iniciar cliente rest: {}", e.getMessage());
            LOGGER.debug("Error al iniciar cliente rest: {}", e.getMessage(), e);
        }
    }

    /**
     * Termina las conexiones abiertas antes de matar el objeto
     */
    @PreDestroy
    public void destroyClient() {
        try {
            if (client != null) {
                client.connectionPool().evictAll();
                client.dispatcher().cancelAll();
                client.dispatcher().executorService().shutdown();
            }
        } catch (Exception e) {
            LOGGER.error("Error al terminar cliente: {}", e.getMessage());
            LOGGER.debug("Error al terminar cliente: {}", e.getMessage(), e);
        }
    }

    /**
     * Esta función realiza una operación GET sobre la URL, no conserva el
     * estado HTTP
     *
     * @param url URL a la que solicitar la información
     * @return La representación JSON de la respuesta en caso exitoso, vacío en
     * caso de error o una excepción.
     * @throws IOException
     */
    public String get(final String url) throws IOException {
        String json = StringUtils.EMPTY;
        if (URL_VALIDATOR.isValid(url)) {
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("User-Agent", USER_AGENT)
                    .addHeader("Accept", JSON_MIME)
                    .addHeader("Content-Type", JSON_MIME)
                    .cacheControl(CacheControl.FORCE_NETWORK)
                    .get()
                    .build();

            try ( Response response = client.newCall(request).execute();  ResponseBody body = response.body()) {
                if (response.isSuccessful()) {
                    if (body != null) {
                        json = StringUtils.trimToEmpty(body.string());
                    } else {
                        LOGGER.error("[{}] Respuesta sin datos de salida", response.code());
                    }
                } else {
                    LOGGER.error("[GET] url       : '{}'", url);
                    LOGGER.error("[GET] Código    : '{}'", response.code());
                    LOGGER.error("[GET] respuesta : '{}'", body.string());
                }
            }
        }
        return json;
    }
}
