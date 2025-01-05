package pri.yqx.ai.service.adapter;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import pri.yqx.ai.domain.entity.AiChat;
import pri.yqx.ai.domain.vo.AiChatVo;
import pri.yqx.common.util.MyBeanUtils;
import pri.yqx.common.util.TimeConverterUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

public class AiChatAdapter {
    public static List<AiChatVo> build(List<AiChat> aiChats) {
        if(CollectionUtil.isEmpty(aiChats))
            return List.of();
        List<AiChatVo> collect = aiChats.stream().map(i -> {
            AiChatVo aiChatVo = MyBeanUtils.copyProperties(i, new AiChatVo());
            aiChatVo.setGoods(JSON.parseArray(i.getGoods(), AiChatVo.Goods.class));
            return aiChatVo;
        }).collect(Collectors.toList());

//        TimeConverterUtil.convertTime(collect,"SendTime",AiChatVo.class);

        return collect;
    }
}
