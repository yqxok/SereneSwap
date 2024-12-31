package pri.yqx.common.util;

import pri.yqx.chat.domain.vo.ChatContentVo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 时间转化器
 */
public class TimeConverterUtil {
    /**
     *
     * @param collect 元素带有createTime属性,类型是LocalDateTime
     * @param fieldName 要设置的字段名,首字母要大写
     * @param clazz 元素字节码
     * @return
     * @param <T>
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static<T> void convertTime(List<T> collect, String fieldName, Class<T> clazz) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("MM月dd日 HH:mm");
        try {
            //反射获取setSendTime方法
            Method method = clazz.getMethod("set"+fieldName, String.class);
            method.setAccessible(true);
            method.invoke(collect.get(collect.size()-1),"");
            //反射获取getCreateTime()方法
            Method method1 = clazz.getMethod("getCreateTime");
            method1.setAccessible(true);
            //反射获取getSendTime方法
            Method method2 = clazz.getMethod("get"+fieldName);
            method.setAccessible(true);
            for (int i = 0; i < collect.size() - 1; i++) {
                T current = collect.get(i);

                T next = collect.get(i + 1);

                LocalDateTime currentTime =(LocalDateTime)method1.invoke(current);
                LocalDateTime nextTime =(LocalDateTime) method1.invoke(next);
                long minutesDifference = Duration.between(nextTime, currentTime).toMinutes();

                if (minutesDifference > 3) {
                    method.invoke(current,"");
                }
            }
            LocalDateTime now = LocalDateTime.now();

            for (int i = 0; i < collect.size(); i++) {
                T cur = collect.get(i);
                if(method2.invoke(cur)==null)
                    continue;
                long daysDifference = Duration.between((LocalDateTime)method1.invoke(cur), now).toDays();
                if (daysDifference < 1) {
                    method.invoke(cur,((LocalDateTime)method1.invoke(cur)).format(timeFormatter));

                } else {
                    method.invoke(cur,((LocalDateTime)method1.invoke(cur)).format(dayFormatter));
                }

            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

    }
}
