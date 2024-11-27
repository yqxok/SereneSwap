
package pri.yqx.user.domain.vo;

import java.time.LocalDate;

import lombok.Data;
import pri.yqx.common.enums.AccountType;
import pri.yqx.common.enums.Gender;
@Data
public class UserVo {
    private Long userId;
    private String userName;
    private String phoneNumber;
    private LocalDate birthday;
    private Gender gender;
    private String bio;
    private String profilePicUrl;
    private AccountType accountType;


}
