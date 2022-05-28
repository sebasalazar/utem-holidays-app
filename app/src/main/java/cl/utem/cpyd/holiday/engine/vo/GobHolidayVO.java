package cl.utem.cpyd.holiday.engine.vo;

import cl.utem.cpyd.holiday.persistence.model.Utem;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

/**
 *
 * @author seba
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GobHolidayVO extends Utem {

    @JsonProperty("nombre")
    private String name = null;

    @JsonProperty("comentarios")
    private String comments = null;

    @JsonProperty("fecha")
    private LocalDate date = null;

    @JsonProperty("irrenunciable")
    private String inalienable = null;

    @JsonProperty("tipo")
    private String type = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getInalienable() {
        return inalienable;
    }

    public void setInalienable(String inalienable) {
        this.inalienable = inalienable;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
