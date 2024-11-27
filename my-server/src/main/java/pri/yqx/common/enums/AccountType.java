package pri.yqx.common.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum AccountType implements IEnum<String> {
    REGULAR("REGULAR"),
    SELLER("SELLER"),
    ADMIN("ADMIN");

    private final String sort;

    private AccountType(String sort) {
        this.sort = sort;
    }

    public String getValue() {
        return this.sort;
    }

    public String getSort() {
        return this.sort;
    }
}