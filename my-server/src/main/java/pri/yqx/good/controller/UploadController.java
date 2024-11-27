//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx.good.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pri.yqx.common.common.QiniuOss;
import pri.yqx.common.domain.response.Result;
import pri.yqx.common.exception.BusinessException;
import pri.yqx.common.util.ImgZipUtil;
import pri.yqx.good.domain.json.PicUrl;

@RestController
@RequestMapping({"/upload"})
public class UploadController {
    private static final Logger log = LoggerFactory.getLogger(UploadController.class);
    @Resource
    private QiniuOss qiniuOss;
    @Value("${oss.tmp-file}")
    private String tmpFile;
    @Value("${oss.tmp-file1}")
    private String tmpFile1;

    public UploadController() {
    }

    @PostMapping({"/{name}"})
    public Result<PicUrl> uploadFile(@RequestParam("pic") MultipartFile pic, @PathVariable String name) {
        try {
            File file = new File(this.tmpFile);
            pic.transferTo(file);
            File file1 = this.zipGoodShowImage(file);
            String url = "";
            if (name.equals("user")) {
                url = this.qiniuOss.uploadQiniu(file1, "user");
            } else if (name.equals("good")) {
                url = this.qiniuOss.uploadQiniu(file1, "good");
            }

            BufferedImage image = ImageIO.read(file1);
            PicUrl picUrl = new PicUrl().setUrl(url).setHeight(image.getHeight()).setWidth(image.getWidth());
            return Result.success(picUrl, "upload success");
        } catch (Exception var8) {
            log.error("uploadFile出错", var8);
            throw new BusinessException("系统出错,图片上传失败");
        }
    }

    private File zipGoodShowImage(File file) throws IOException {
        BufferedImage image = ImageIO.read(file);
        float width = (float)image.getWidth();
        float height = (float)image.getHeight();
        if (width > 500.0F) {
            float tmp = width / 500.0F;
            width = 500.0F;
            height /= tmp;
        }

        return ImgZipUtil.zipImageCustom(file, (int)width, (int)height, new File(this.tmpFile1));
    }

    private File zipProfileImage(File file) throws IOException {
        BufferedImage image = ImageIO.read(file);
        float width = (float)image.getWidth();
        float height = (float)image.getHeight();
        if (width > 100.0F) {
            float tmp = width / 100.0F;
            width = 100.0F;
            height /= tmp;
        }

        return ImgZipUtil.zipImageCustom(file, (int)width, (int)height, new File(this.tmpFile1));
    }
}
