package pri.yqx.common.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum Gender implements IEnum<Short> {
    MALE(Short.valueOf((short)1)),
    FEMALE(Short.valueOf((short)0));

    private Short gender;

    private Gender(Short gender) {
        this.gender = gender;
    }

    public Short getValue() {
        return this.gender;
    }

    public Short getGender() {
        return this.gender;
    }
}
