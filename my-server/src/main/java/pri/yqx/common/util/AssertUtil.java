//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.common.util;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Objects;
import pri.yqx.common.exception.BusinessException;
import pri.yqx.common.exception.enums.BussinessErrorEnum;
import pri.yqx.common.exception.enums.ErrorEnum;

public class AssertUtil {
    public AssertUtil() {
    }

    public static void isEmpty(Object o1, String msg) {
        if (Objects.isNull(o1)) {
            throwException(msg);
        }

    }

    public static void isNotEmpty(Object o1, String msg) {
        if (!Objects.isNull(o1)) {
            throwException(msg);
        }

    }

    public static void ltSize(Collection collection, int num, String msg) {
        isEmpty(collection, msg);
        if (num < collection.size()) {
            throwException(msg);
        }

    }

    public static void isZero(Long o1, String msg) {
        if (o1 == 0L) {
            throwException(msg);
        }

    }

    public static void isTrue(Boolean check, String msg) {
        if (check) {
            throwException(msg);
        }

    }

    private static void throwException(Object... msg) {
        throwException((ErrorEnum)null, msg);
    }

    private static void throwException(ErrorEnum errorEnum, Object... msg) {
        if (Objects.isNull(errorEnum)) {
            errorEnum = BussinessErrorEnum.BUSINESS_ERROR;
        }

        throw new BusinessException(((ErrorEnum)errorEnum).getErrorCode(), MessageFormat.format(((ErrorEnum)errorEnum).getErrormsg(), msg));
    }
}
