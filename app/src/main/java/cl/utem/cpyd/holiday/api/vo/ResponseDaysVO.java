package cl.utem.cpyd.holiday.api.vo;

public class ResponseDaysVO extends QueryDaysVO {

    private Integer days = null;

    public ResponseDaysVO() {
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }
}
