package pri.yqx.sensitive;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GrepFileLoader {
    public static List<String>  loadGrepFile()  {
        ArrayList<String> list = new ArrayList<>();
        try {
            ClassLoader classLoader = GrepFileLoader.class.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("grep.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            // 逐行读取文件内容
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }

        } catch (IOException e) {
            System.out.println("过滤文件有误");
        }
        return list;
    }
}
