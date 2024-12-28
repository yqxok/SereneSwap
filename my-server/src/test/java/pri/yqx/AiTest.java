package pri.yqx;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import pri.yqx.common.util.MyBeanUtils;
import pri.yqx.good.domain.dto.GoodCursorReq;
import pri.yqx.good.domain.entity.Good;
import pri.yqx.good.domain.json.PicUrl;
import pri.yqx.good.domain.json.PicUrlJsonHandler;
import pri.yqx.good.domain.vo.GoodCursorPageVo;
import pri.yqx.good.service.GoodService;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class AiTest {
    @Autowired
    private ChatClient.Builder chatClientBuilder;
    @Autowired
    private GoodService goodService;
    @Data
    static public class AiGood{
        private Long goodId;
        private String html;
        private String url;
        private BigDecimal price;
    }
    @Test
    public void test() throws IOException {
        GoodCursorReq req = new GoodCursorReq();
        req.setCursor(0L);
        req.setPageSize(100);
        req.setDeviceWidth(375);
        req.setMaxHeight(500);
        GoodCursorPageVo pageVo = goodService.pageGoodVo(req);
        pageVo.getList().addAll(pageVo.getAnotherList());
        List<AiGood> collect = pageVo.getList().stream().map(i -> {
            AiGood aiGood = MyBeanUtils.copyProperties(i, new AiGood());
            aiGood.setUrl(i.getPicUrl().getUrl());
            return aiGood;
        }).collect(Collectors.toList());
        String s = JSON.toJSONString(collect);
        BufferedWriter writer=new BufferedWriter(new FileWriter("D:\\code\\java\\xian-yu\\my-server\\src\\main\\resources\\data.json"));
        writer.write(s);
        writer.flush();
    }
}
