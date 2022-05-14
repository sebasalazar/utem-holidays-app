package cl.utem.cpyd.holiday.api.vo;

import cl.utem.cpyd.holiday.persistence.model.BaseJson;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LeyFLVO extends BaseJson {

    @JsonProperty("nombre")
    private String nombre = null;

    @JsonProperty("url")
    private String url = null;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
