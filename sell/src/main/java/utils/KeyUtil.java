package utils;

import java.util.Random;

/**
 * 生成唯一的主键随机数
 * 格式： 时间+随机数
 */
public class KeyUtil {
    public static synchronized String genUniqueKey(){
        Random random = new Random();
        Integer num = random.nextInt(900000) + 100000;

        return System.currentTimeMillis() + String.valueOf(num);
    }
}
