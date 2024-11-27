package pri.yqx.common.groups;

import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsPhoneValidation implements ConstraintValidator<Isphone, String> {
    public IsPhoneValidation() {
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        } else {
            Pattern compile = Pattern.compile("^1[345678]\\d{9}$");
            return compile.matcher(value).matches();
        }
    }
}