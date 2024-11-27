//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.websocket.service;

import lombok.Data;

@Data
public class WsMsgDto {
    private Integer type;
    private Object data;


    public WsMsgDto(final Integer type, final Object data) {
        this.type = type;
        this.data = data;
    }
    public WsMsgDto(){};
}
