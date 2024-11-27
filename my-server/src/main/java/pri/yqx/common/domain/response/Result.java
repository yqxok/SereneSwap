

package pri.yqx.common.domain.response;

import lombok.Data;

@Data
public class Result<T> {
    private int code;
    private String message;
    private T data;


    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }



    public static <T> Result<T> success(T data) {
        return new Result(200, "Success", data);
    }

    public static <T> Result<T> success(T data, String message) {
        return new Result(200, message, data);
    }

    public static <T> Result<T> error(int code, String message) {
        return new Result(code, message);
    }

    public static <T> Result<T> error(int code, String message, T data) {
        return new Result(code, message, data);
    }
}
