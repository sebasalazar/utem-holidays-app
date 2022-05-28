package cl.utem.cpyd.holiday.api.vo;

import cl.utem.cpyd.holiday.persistence.model.Utem;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 *
 * @author seba
 */
@Schema(name = "Error")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorVO extends Utem {

    @Schema(description = "Flag que indica el resultado de la operación, por defecto en false", example = "false", required = true)
    private boolean ok = false;

    @Schema(description = "Glosa descriptiva del error", example = "No fue posible conectarse al motor de base de datos", required = true)
    private String message = null;

    @Schema(description = "Fecha de creación del objeto")
    private OffsetDateTime created = OffsetDateTime.now(ZoneOffset.UTC);

    public ErrorVO() {
    }

    public ErrorVO(String message) {
        this.ok = false;
        this.message = message;
        this.created = OffsetDateTime.now(ZoneOffset.UTC);
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public OffsetDateTime getCreated() {
        return created;
    }

    public void setCreated(OffsetDateTime created) {
        this.created = created;
    }
}
