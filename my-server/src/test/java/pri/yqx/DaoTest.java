//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pri.yqx;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.Rollback;
import pri.yqx.chat.dao.ChatContentDao;
import pri.yqx.chat.dao.ContactDao;
import pri.yqx.comment.dao.CommentDao;
import pri.yqx.comment.dao.SonCommentDao;
import pri.yqx.good.dao.GoodDao;
import pri.yqx.good.mapper.GoodMapper;
import pri.yqx.msg.dao.MsgRoomDao;
import pri.yqx.msg.dao.OrderMsgDao;
import pri.yqx.msg.domain.entity.MsgRoom;
import pri.yqx.order.dao.OrderDao;
import pri.yqx.order.dao.OrderInfoDao;
import pri.yqx.user.dao.UserDao;
import pri.yqx.user.domain.entity.User;

@SpringBootTest(
    webEnvironment = WebEnvironment.RANDOM_PORT
)
@Rollback(true)
public class DaoTest {
    private static final Logger log = LoggerFactory.getLogger(DaoTest.class);
    @Autowired
    private GoodDao goodDao;

    @Autowired
    private ChatContentDao chatContentDao;
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private SonCommentDao sonCommentDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderInfoDao orderInfoDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private OrderMsgDao orderMsgDao;
    @Autowired
    private MsgRoomDao msgRoomDao;
    @Autowired
    private ContactDao contactDao;
    @Autowired
    private GoodMapper goodMapper;

    public DaoTest() {
    }

    @Test
    public void testGoodDao() {
        List<User> list = this.userDao.list();
        List<MsgRoom> collect = list.stream().map((i) -> {
            return (new MsgRoom()).setNoReadNum(0).setUserId(i.getUserId()).setType(2);
        }).collect(Collectors.toList());
        this.msgRoomDao.saveBatch(collect);
    }
}
