drop database if exists test;
create database test default character set utf8mb4 collate utf8mb4_unicode_ci;

drop table if exists test.product_category;
create table test.product_category (
                                       category_id int not null auto_increment,
                                       category_name varchar(64) not null comment '类目名字',
                                       category_type int not null comment '类目编号',
                                       create_time timestamp not null default current_timestamp comment '创建时间',
                                       update_time timestamp not null default current_timestamp on update current_timestamp comment '更新时间',
                                       PRIMARY KEY (`category_id`),
                                       unique key uqe_category_type (category_type)
) comment '类目表';

insert into test.product_category value (null, '粥类' ,1,null,null);
insert into test.product_category value (null, '饭食' ,2,null,null);
insert into test.product_category value (null, '汤类' ,3,null,null);
insert into test.product_category value (null, '饮料' ,4,null,null);
insert into test.product_category value (null, '猪食' ,5,null,null);









drop table if exists test.product_info;
create table test.product_info (
                              product_id varchar(32) not null,
                              product_name varchar(64) not null comment '名称',
                              product_price DECIMAL(8,2) not null comment '单价',
                              product_stock int not null comment '库存',
                              product_description varchar(64) comment '描述',
                              product_icon varchar(512) comment '小图',
                              product_status tinyint(3) default '0' comment '商品状态,0下架1正常',
                              category_type int not null comment '类目编号',
                              create_time timestamp not null default current_timestamp comment '创建时间',
                              update_time timestamp not null default current_timestamp on update current_timestamp comment '更新时间',
                              primary key (`product_id`)
) comment '商品表';

insert into test.product_info value (1,'皮蛋瘦肉粥',6.1,100,'nice food','icon',1,1,null,null);
insert into test.product_info value (2,'广西周某',7.1,100,'明天你的电瓶就不见','icon',1,1,null,null);
insert into test.product_info value (3,'老干妈送饭',5.1,100,'贵州特产老干妈送饭','icon',1,2,null,null);
insert into test.product_info value (4,'好吃的饭',2.1,100,'你问我好不好次，我当然说好次了','icon',1,2,null,null);
insert into test.product_info value (5,'皮蛋瘦肉粥',6.1,100,'nice food','icon',1,3,null,null);
insert into test.product_info value (6,'广西周某',7.1,100,'明天你的电瓶就不见','icon',1,3,null,null);
insert into test.product_info value (7,'老干妈送饭',5.1,100,'贵州特产老干妈送饭','icon',1,4,null,null);
insert into test.product_info value (8,'皮蛋瘦肉粥',6.1,100,'nice food','icon',1,4,null,null);
insert into test.product_info value (9,'剩饭',6.1,100,'便宜就只有剩饭咯','icon',1,5,null,null);
insert into test.product_info value (10,'剩菜',7.1,100,'爱买不买','icon',1,5,null,null);









drop table if exists test.order_master;
create table test.order_master(
                             order_id varchar (32) not null,
                             buyer_name varchar(32) not null comment '买家名字',
                             buyer_phone varchar(32) not null comment '买家电话',
                             buyer_address varchar(32) not null comment '买家地址',
                             buyer_openid varchar(32) not null comment '买家微信openid',
                             order_amount decimal(8,2) not null comment '订单总金额',
                             order_status tinyint(3) not null default '0' comment '订单状态,下单0',
                             pay_status tinyint(3) not null default '0' comment '支付状态',
                             create_time timestamp not null default current_timestamp comment '创建时间',
                             update_time timestamp not null default current_timestamp on update current_timestamp comment '更新时间',
                             PRIMARY KEY (`order_id`),
                             key idx_buyer_openid (buyer_openid)
) comment '订单主表';


drop table if exists test.order_detail;
create table test.order_detail (
                              detail_id varchar(32) not null,
                              order_id varchar(32) not null,
                              product_id varchar(32) not null,
                              product_name varchar(32) not null comment '商品名称',
                              product_price DECIMAL(8,2) not null comment '商品价格',
                              product_quantity varchar(32) not null comment '商品数量',
                              product_icon varchar(32) not null comment '商品小图',
                              create_time timestamp not null default current_timestamp comment '创建时间',
                              update_time timestamp not null default current_timestamp on update current_timestamp comment '更新时间',
                              primary key (detail_id),
                              key indx_order_id (order_id)
) comment '订单详情';




