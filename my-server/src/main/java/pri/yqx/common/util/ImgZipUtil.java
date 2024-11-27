package pri.yqx.common.util;

import java.io.File;
import java.io.IOException;
import net.coobird.thumbnailator.Thumbnails;

public class ImgZipUtil {
    public ImgZipUtil() {
    }

    public static File zipImage800x300(File file, File tmp) throws IOException {
        return zipImageCustom(file, 800, 300, tmp);
    }

    public static File zipImageCustom(File file, int i, int y, File tmpFile) throws IOException {
        try {
            Thumbnails.of(new File[]{file}).size(i, y).outputQuality(0.5F).toFile(tmpFile);
            return tmpFile;
        } catch (IOException var5) {
            throw new IOException("图片压缩失败");
        }
    }
}