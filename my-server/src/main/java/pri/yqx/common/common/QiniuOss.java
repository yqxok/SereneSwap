
package pri.yqx.common.common;

import com.alibaba.fastjson.JSON;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.Configuration.ResumableUploadAPIVersion;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

@Component
@ConfigurationProperties(
    prefix = "oss"
)
@Data
public class QiniuOss {
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String host;

    public String uploadQiniu(File file, String prefix) throws IOException {
        Configuration cfg = new Configuration(Region.huanan());
        cfg.resumableUploadAPIVersion = ResumableUploadAPIVersion.V2;
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(this.accessKey, this.secretKey);
        String upToken = auth.uploadToken(this.bucket);
        int i = ((String)Objects.requireNonNull(file.getName())).lastIndexOf(".");
        UUID uuid = UUID.randomUUID();
        String uri = prefix + "/" + uuid.toString() + file.getName().substring(i);

        try {
            Response response = uploadManager.put(Files.newInputStream(file.toPath()), uri, upToken, (StringMap)null, (String)null);
            DefaultPutRet putRet = (DefaultPutRet)JSON.parseObject(response.bodyString(), DefaultPutRet.class);
            return this.host + putRet.key;
        } catch (IOException var12) {
            throw var12;
        }
    }

    public String uploadLocal(File file, String prefix) throws IOException {
        String filename = file.getName();
        String suffix = filename.substring(filename.lastIndexOf("."));
        filename = UUID.randomUUID() + suffix;
        String fileUrl = "http://10.61.117.240:7001/api/download/" + prefix + "/" + filename;
        String filePath = "D:\\img\\" + prefix + "\\" + filename;
        FileCopyUtils.copy(file, new File(filePath));
        return fileUrl;
    }

    public boolean deleteLocalByUrl(String prefix, String... imgNames) {
        List<String> collect = (List)Arrays.stream(imgNames).map((item) -> {
            return item.substring(item.lastIndexOf(47) + 1);
        }).collect(Collectors.toList());
        String basePath = "D:\\img\\" + prefix + "\\";
        collect.forEach((item) -> {
            File file = new File(basePath + item);
            if (file.exists()) {
                file.delete();
            }

        });
        return true;
    }


}
