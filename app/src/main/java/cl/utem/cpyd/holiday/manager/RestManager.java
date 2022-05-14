package cl.utem.cpyd.holiday.manager;

import cl.utem.cpyd.holiday.api.vo.FlVO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("restManager")
public class RestManager implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(RestManager.class);

    private OkHttpClient client = null;

    @PostConstruct
    public void initService() {
        try {
            ConnectionPool connectionPool = new ConnectionPool(128, 5, TimeUnit.SECONDS);
            Dispatcher dispatcher = new Dispatcher(Executors.newFixedThreadPool(16));

            this.client = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .connectionPool(connectionPool)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .dispatcher(dispatcher)
                    .build();

        } catch (Exception e) {
            LOGGER.error("Error al iniciar administrador rest: {}", e.getMessage());
            LOGGER.debug("Error al iniciar administrador rest: {}", e.getMessage(), e);
        }
    }

    public boolean isHoliday(LocalDate date) {
        boolean ok = false;
        try {

            if (date != null) {
                final Request request = new Request.Builder()
                        .url(String.format("https://apis.digital.gob.cl/fl/feriados/%04d/%02d/%02d",
                                date.getYear(), date.getMonthValue(), date.getDayOfMonth()))
                        .addHeader("Accept", "application/json; charset=utf-8")
                        .addHeader("Content-Type", "application/json; charset=utf-8")
                        .get()
                        .build();
                try ( Response response = client.newCall(request).execute()) {
                    if (response != null) {
                        ResponseBody body = response.body();
                        if (body != null) {
                            final String json = body.string();

                            ObjectMapper mapper = new ObjectMapper();
                            List<FlVO> fls = mapper.readValue(json, new TypeReference<List<FlVO>>() {
                            });

                            if (fls != null && !fls.isEmpty()) {
                                ok = true;
                            }

                            body.close();
                        }
                    }
                }
            }

        } catch (Exception e) {
            ok = false;
            LOGGER.error("Error al consultar administrador rest: {}", e.getMessage());
            LOGGER.debug("Error al consultar administrador rest: {}", e.getMessage(), e);
        }
        return ok;
    }
}
