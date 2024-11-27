package pri.yqx.good.domain.enums;

public enum GoodStatusEnums {
    UNSELL(0),
    SELLED(1);

    private final Integer status;

    private GoodStatusEnums(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return this.status;
    }
}