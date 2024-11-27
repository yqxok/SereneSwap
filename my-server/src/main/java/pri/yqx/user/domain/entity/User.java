//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.user.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.experimental.Accessors;
import pri.yqx.common.enums.Gender;
@Data
@Accessors(chain = true)
public class User {
    @TableId
    private Long userId;
    private String openId;
    private String sessionKey;
    private String userName;
    private String password;
    private String phoneNumber;
    private LocalDate birthday;
    private Gender gender;
    private String bio;
    private String profilePicUrl;
    private LocalDateTime lastLogin;
    private Integer noReadNum;
    @TableField(
        fill = FieldFill.INSERT
    )
    private LocalDateTime createTime;
    @TableField(
        fill = FieldFill.INSERT_UPDATE
    )
    private LocalDateTime updateTime;
    private Integer version;
    @TableLogic
    private Boolean isDeleted;


}
