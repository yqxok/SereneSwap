create table wx_address
(
    address_id   bigint               not null comment '地址id'
        primary key,
    user_id      bigint               not null comment '用户id,联合id',
    dormitory_id bigint               not null comment '宿舍id，联合id',
    dormi_num    int                  not null comment '宿舍号',
    phone_number char(15)             not null comment '手机号',
    receiver     varchar(255)         not null comment '收货人',
    create_time  datetime             not null comment '创建时间',
    update_time  datetime             null comment '更新时间',
    version      int        default 0 null comment '版本号',
    is_deleted   int        default 0 null,
    is_default   tinyint(1) default 0 not null comment '是否为默认地址'
)
    comment '地址表' charset = utf8mb4;

create table wx_ai_chat
(
    chat_id     bigint     not null
        primary key,
    type        tinyint(1) not null comment '0ai消息，1用户消息',
    room_id     bigint     null comment '逻辑外键，与aide',
    create_time datetime   not null comment '消息发送时间',
    content     text       not null comment '消息发送的内容',
    goods       text       not null comment 'json字符串'
);

create table wx_ai_room
(
    room_id     bigint                                not null comment '会话id'
        primary key,
    user_id     bigint                                not null comment '用户id',
    create_time datetime                              not null,
    is_deleted  tinyint(1)   default 0                null,
    room_name   varchar(255) default '没有命名的会话' not null comment '会话名'
)
    comment '与ai的一次会话';

create table wx_category
(
    category_name varchar(255)             not null comment '分类名称,唯一且不为空'
        primary key,
    pk_id         varchar(255) default ' ' not null comment '自关联的key,默认为一个空格',
    create_user   bigint                   not null comment '创建人',
    create_time   datetime                 not null comment '创建时间',
    is_deleted    tinyint(1)   default 0   null comment '逻辑删除,0未删除，1删除',
    level         int                      not null comment '分类层次',
    constraint category_name
        unique (category_name)
)
    comment '分类表' charset = utf8mb4;

create table wx_chat_content
(
    chat_id         bigint        not null comment '消息id'
        primary key,
    send_user_id    bigint        not null comment '发送人id',
    receive_user_id bigint        not null comment '接收人id',
    content         varchar(255)  not null comment '聊天内容',
    create_time     datetime      null,
    update_time     datetime      null,
    version         int default 0 null,
    is_deleted      int default 0 null,
    good_id         bigint        not null,
    room_key        bigint        not null comment 'send_user_id+receive_user_id+good_id，组成key',
   
)
comment ' 聊天记录 ' charset=utf8mb4;

create index wx_chat_content_create_time_index
    on wx_chat_content (create_time);

create index wx_chat_content_room_key_index
    on wx_chat_content (room_key);

create table wx_collect
(
    collect_id  bigint        not null comment '收藏id'
        primary key,
    user_id     bigint        not null comment '用户id',
    good_id     bigint        not null comment '商品id',
    create_time datetime      not null comment '创建时间',
    update_time datetime      null,
    version     int default 0 null,
    is_deleted  int default 0 null
)
    comment '收藏表' charset = utf8mb4;

create table wx_comment
(
    comment_id   bigint        not null comment 'id'
        primary key,
    good_id      bigint        not null comment '商品id',
    user_id      bigint        not null comment '留言人id',
    content      text          not null comment '留言内容',
    father_id    bigint        null comment '父评论',
    create_time  datetime      not null comment '创建时间',
    reply_id     bigint        null comment '回复某人评论的id',
    update_time  datetime      null,
    version      int default 0 null,
    is_deleted   int default 0 null,
    good_job_num int default 0 not null comment '点赞数量',
    constraint chk_good_job_num
        check (`good_job_num` >= 0)
)
    comment '商品留言表' charset = utf8mb4;

create table wx_comment_msg
(
    cm_msg_id   bigint               not null comment '主键'
        primary key,
    comment_id  bigint               not null,
    content     varchar(255)         not null comment '评论内容',
    is_read     tinyint(1) default 0 not null comment '被回复人是否已读',
    sender_id   bigint               null comment '评论人id',
    receiver_id bigint               null comment '收到回复的人id',
    create_time datetime             not null,
    good_id     bigint               not null,
    is_deleted  tinyint(1) default 0 not null,
    type        tinyint              null comment '1留言，0回复',
    constraint wx_comment_msg_pk
        unique (comment_id)
)
    comment '评论消息回复表' charset = utf8mb4;

create table wx_comment_son
(
    son_comment_id bigint        not null comment '主键'
        primary key,
    comment_id     bigint        not null comment '关联comment表',
    create_time    datetime      not null comment '创建时间',
    content        char(255)     not null comment '评论内容',
    good_job_num   int default 0 null comment '被点赞数',
    reply_id       bigint        not null comment '回复的用户id',
    user_id        bigint        not null comment '评论人id',
    constraint chk_son_good_job_num
        check (`good_job_num` >= 0)
);

create index wx_comment_son_comment_id_index
    on wx_comment_son (comment_id);

create table wx_contact
(
    contact_id    bigint               not null
        primary key,
    user_id       bigint               not null,
    other_id      bigint               not null comment '联系人id',
    create_time   datetime             not null,
    update_time   datetime             not null,
    earlist_time  datetime             not null comment '能查看的最早消息时间',
    is_deleted    tinyint(1) default 0 null,
    no_read_num   int        default 0 null,
    version       int        default 0 null comment '乐观锁',
    good_id       bigint               not null comment '商品id',
    latest_msg    varchar(255)         not null comment '最新消息',
    room_key      bigint               null,
    up_time_stamp bigint               not null comment '更新时间',
    constraint wx_contact_pk
        unique (user_id, room_key)
)
    comment '会话表';

create table wx_dormitory
(
    dormitory_id bigint        not null comment '宿舍楼id'
        primary key,
    dormi_name   varchar(255)  not null comment '宿舍楼名字',
    school       char(255)     null comment '学校',
    zone         varchar(255)  null comment '校区',
    create_time  datetime      null,
    update_time  datetime      null,
    version      int default 0 null,
    is_deleted   int default 0 null
)
    comment '宿舍表' charset = utf8mb4;

create table wx_good
(
    good_id       bigint               not null comment '商品id'
        primary key,
    html          text                 not null comment '商品描述,h5',
    pic_urls      text                 not null comment '商品图片链接,url数组',
    price         decimal(10, 2)       not null comment '商品价格',
    status        tinyint    default 0 null comment '商品状态，0未出售，1已出售',
    browser_times int        default 1 null comment '被浏览次数,每个用户对应一次',
    user_id       bigint               not null comment '关联user_id',
    create_time   datetime             not null comment '创建时间',
    update_time   datetime             not null comment '更新时间',
    is_deleted    tinyint(1) default 0 null comment '逻辑删除,0未删除，1删除',
    version       int        default 0 null,
    good_num      int        default 1 not null comment '商品数量',
    collect_num   int        default 0 not null comment '被收藏数量'
)
    comment '商品表' charset = utf8mb4;

create table wx_good_category
(
    good_category_id bigint        not null comment '主键'
        primary key,
    good_id          bigint        not null comment '关联good_id',
    category_name    varchar(255)  null comment '冗余字段',
    create_time      datetime      null,
    update_time      datetime      null,
    version          int default 0 null,
    is_deleted       int default 0 null,
    level            int           not null
)
    comment '商品分类联合表' charset = utf8mb4;

create index good_id_index
    on wx_good_category (good_id);

create table wx_good_job
(
    gj_id       bigint   not null comment '点赞记录id'
        primary key,
    comment_id  bigint   not null comment '关联字段',
    user_id     bigint   not null comment '关联字段，点赞的用户id',
    create_time datetime not null
)
    charset = utf8mb4;

create table wx_msg_room
(
    msg_room_id bigint        not null
        primary key,
    no_read_num int           null,
    type        tinyint       null comment '0评论消息，1订单消息',
    update_time datetime      null,
    user_id     bigint        null,
    create_time datetime      null,
    version     int default 0 null,
    constraint wx_msg_room_pk
        unique (user_id, type)
);

create table wx_no_read
(
    no_read_id  bigint   not null
        primary key,
    user_id     bigint   null,
    no_read_num int      null,
    create_time datetime null,
    update_time datetime null,
    constraint wx_no_read_pk
        unique (user_id)
);

create table wx_order
(
    order_id     bigint        not null comment '买家订单id'
        primary key,
    good_id      bigint        not null comment '商品id',
    create_time  datetime      not null,
    total_price  decimal       not null comment '总价',
    buy_num      int default 1 not null comment '购买商品数量',
    status       int default 0 not null comment '0正在交易,1交易完成,2取消交易',
    deal_time    datetime      null comment '交易成功时间',
    address      varchar(255)  not null comment '地址信息',
    receiver     varchar(255)  not null comment '收货人昵称',
    phone_number varchar(255)  null
)
    comment '订单表';

create index wx_order_good_id_index
    on wx_order (good_id);

create index wx_order_status_index
    on wx_order (status);

create table wx_order_info
(
    order_info_id bigint               not null comment '主键'
        primary key,
    user_id       bigint               not null comment '订单用户id',
    deal_user_id  bigint               null comment '交易对象用户id',
    is_deleted    tinyint(1) default 0 null comment '逻辑删除',
    is_buyer      tinyint(1)           not null comment 'false表示卖家订单，true表示买家订单',
    order_id      bigint               null comment '关联orderbiao'
);

create index wx_order_info_user_id_index
    on wx_order_info (user_id);

create table wx_order_msg
(
    order_msg_id bigint               not null comment '订单消息id'
        primary key,
    sender_id    bigint               not null comment '发送人id',
    receiver_id  bigint               not null comment '接收方id',
    order_id     bigint               not null comment '关联的订单id',
    is_read      tinyint(1) default 0 not null comment '消息是否已读',
    create_time  datetime             not null comment '创建时间',
    status       int                  not null comment '用户状态',
    good_id      bigint               not null,
    constraint wx_order_msg_pk
        unique (order_id, status)
)
    charset = utf8mb4;

create table wx_user
(
    user_id         bigint                        not null comment '用户id,雪花算法'
        primary key,
    user_name       varchar(255) default '莞工er' null comment '用户名',
    password        varchar(255)                  null comment '密码，不可为空',
    phone_number    varchar(20)                   null comment '电话号码，唯一',
    birthday        date                          null comment '出生日期',
    gender          tinyint      default 1        null comment '性别，0女1男',
    bio             text                          null comment '个人简介',
    profile_pic_url varchar(255)                  null comment '个人头像链接',
    create_time     datetime                      not null comment '注册日期',
    last_login      datetime                      null comment '最后登录时间',
    is_deleted      tinyint(1)   default 0        not null comment '逻辑删除',
    open_id         varchar(255)                  null,
    session_key     varchar(255)                  null,
    update_time     datetime                      null,
    version         int          default 0        null,
    no_read_num     int          default 0        not null comment '未读消息数',
    constraint chk_password
        check (length(`password`) >= 8),
    constraint chk_username
        check (length(`user_name`) >= 3)
)
    comment '用户表' charset = utf8mb4;

