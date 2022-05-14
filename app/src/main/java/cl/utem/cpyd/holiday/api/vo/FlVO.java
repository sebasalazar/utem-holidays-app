package cl.utem.cpyd.holiday.api.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class FlVO extends BaseJson {

    @JsonProperty("nombre")
    private String nombre = null;
    @JsonProperty("comentarios")
    private String comentarios = null;
    @JsonProperty("fecha")
    private String fecha = null;
    @JsonProperty("irrenunciable")
    private String irrenunciable = null;
    @JsonProperty("tipo")
    private String tipo = null;
    @JsonProperty("leyes")
    private List<LeyFLVO> leyes = null;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getIrrenunciable() {
        return irrenunciable;
    }

    public void setIrrenunciable(String irrenunciable) {
        this.irrenunciable = irrenunciable;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<LeyFLVO> getLeyes() {
        return leyes;
    }

    public void setLeyes(List<LeyFLVO> leyes) {
        this.leyes = leyes;
    }
}
