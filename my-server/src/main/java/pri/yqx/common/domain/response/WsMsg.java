
package pri.yqx.common.domain.response;

import lombok.Data;
import pri.yqx.common.enums.WsMsgType;
@Data
public class WsMsg<T> {
    private WsMsgType type;
    private T data;


}
