//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.comment.domain.serializer;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;

public class CreateTimeSerializer implements ObjectSerializer {


    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        LocalDateTime createTime = (LocalDateTime)object;
        LocalDate localDate = createTime.toLocalDate();
        LocalTime localTime = createTime.toLocalTime();
        LocalDate now = LocalDate.now();
        LocalTime now1 = LocalTime.now();
        Period between = Period.between(localDate, now);
        Duration between1 = Duration.between(localTime, now1);
        if (between.getYears() > 0) {
            serializer.write(between.getYears() + "年前");
        } else if (between.getMonths() > 0) {
            serializer.write(between.getMonths() + "个月前");
        } else if (between.getDays() > 0) {
            serializer.write(between.getDays() + "天前");
        } else if (between1.toHours() > 0L) {
            serializer.write(between1.toHours() + "小时前");
        } else if (between1.toMinutes() > 0L) {
            serializer.write(between1.toMinutes() + "分钟前");
        } else {
            serializer.write("刚刚");
        }

    }
}
