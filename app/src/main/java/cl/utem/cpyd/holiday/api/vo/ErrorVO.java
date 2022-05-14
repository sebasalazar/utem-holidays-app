package cl.utem.cpyd.holiday.api.vo;

import java.time.LocalDateTime;

public class ErrorVO {

    public boolean ok = false;
    public String message = null;
    public LocalDateTime created = LocalDateTime.now();

    public ErrorVO() {
    }

    public ErrorVO(String message) {
        this.ok = false;
        this.message = message;
        this.created = LocalDateTime.now();
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

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
