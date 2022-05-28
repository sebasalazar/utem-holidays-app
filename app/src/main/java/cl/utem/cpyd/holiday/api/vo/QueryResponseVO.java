package cl.utem.cpyd.holiday.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 *
 * @author seba
 */
@Schema(name = "QueryResponse")
public class QueryResponseVO extends QueryRequestVO {

    @Schema(description = "Conteo de días")
    private Long count = null;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

}
