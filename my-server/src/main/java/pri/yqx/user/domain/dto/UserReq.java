//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.user.domain.dto;

import java.time.LocalDate;

import lombok.Data;
import pri.yqx.common.enums.Gender;
@Data
public class UserReq {
    private String userName;
    private String password;
    private String phoneNumber;
    private LocalDate birthday;
    private Gender gender;
    private String bio;
    private String profilePicUrl;


}
