//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.user.domain.dto;



import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import pri.yqx.common.groups.Isphone;
@Data
public class LoginReq {
    private String code;
    @Isphone
    private @NotEmpty String phoneNumber;
    private @NotEmpty @Range(
    min = 8L,
    max = 13L
) String password;


}
