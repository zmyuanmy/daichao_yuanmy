--2019-01-18 新建数据表

CREATE TABLE mall_banners (
  banner_id int(11) NOT NULL AUTO_INCREMENT,
  index_no int(11) DEFAULT NULL COMMENT '顺序，排序用',
  adImg varchar(200) DEFAULT NULL COMMENT '图片地址',
  creation_date timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  product_type varchar(20) DEFAULT NULL COMMENT '商城类型 flower为卖花，后续支持其他的',
  product_id int(11) DEFAULT NULL COMMENT '关联的商品ID',
  PRIMARY KEY (banner_id)
) ENGINE=InnoDB;

CREATE TABLE mall_categories (
  category_id int(11) NOT NULL AUTO_INCREMENT,
  classification varchar(10) DEFAULT NULL COMMENT '分类方法(卖花分类：色彩、花材、对象、场合、首页中间情景、首页列表场景)',
  index_no int(11) DEFAULT NULL COMMENT '顺序，排序用',
  title varchar(10) DEFAULT NULL COMMENT '分类标题',
  `desc` varchar(100) DEFAULT NULL COMMENT '分类描述',
  creation_date timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  product_type varchar(20) DEFAULT NULL COMMENT '商城类型 flower为卖花，后续支持其他的',
  adImg varchar(200) DEFAULT NULL COMMENT '图片地址',
  PRIMARY KEY (category_id)
) ENGINE=InnoDB;

CREATE TABLE mall_category_products (
  product_id int(11) NOT NULL COMMENT '商品id',
  category_id int(11) NOT NULL COMMENT '分类ID',
	primary key (product_id,category_id)
) ENGINE=InnoDB;

CREATE TABLE mall_classifications (
  classification varchar(10) NOT NULL COMMENT '分类方法(卖花分类：色彩、花材、对象、场合、首页中间情景、首页列表场景)',
  `desc` varchar(20) DEFAULT NULL COMMENT '描述',
  img_url varchar(200) DEFAULT NULL COMMENT '图片地址',
  PRIMARY KEY (classification)
) ENGINE=InnoDB;

CREATE TABLE mall_orders (
  order_id int(11) NOT NULL AUTO_INCREMENT COMMENT '商品订单表',
  user_id int(11) DEFAULT NULL COMMENT '用户id',
  product_id int(11) DEFAULT NULL COMMENT '产品id',
  price int(11) DEFAULT NULL COMMENT '价格',
  creation_date timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  update_date timestamp NULL DEFAULT NULL COMMENT '更新日期，状态变动时更新',
  delivery_date timestamp NULL DEFAULT NULL COMMENT '配送时间',
  `status` int(4) DEFAULT NULL COMMENT '订单状态。1 - 待付款(订单创建)，2 待配送(付款完成) 3 配送完成  0 用户取消。 默认为1',
  address varchar(200) DEFAULT NULL COMMENT '配送地址',
  custome_name varchar(20) DEFAULT NULL COMMENT '收货人姓名',
  card_msg varchar(100) DEFAULT NULL COMMENT '卡片留言',
  comment varchar(100) DEFAULT NULL COMMENT '备注',
  is_deleted tinyint(4) DEFAULT 0 COMMENT '是否删除。 1 用户删除订单',
  PRIMARY KEY (order_id)
) ENGINE=InnoDB;

CREATE TABLE mall_product_imgs (
  product_id int(11) DEFAULT NULL COMMENT '商品ID',
  img_url varchar(200) DEFAULT NULL COMMENT '商品图片',
  index_no int(11) DEFAULT NULL COMMENT '顺序',
  creation_date timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间'
) ENGINE=InnoDB;

CREATE TABLE mall_product_sale_count (
  product_id int(11) NOT NULL COMMENT '商品id',
  sale_count int(11) DEFAULT NULL COMMENT '商品销量',
  PRIMARY KEY (product_id)
) ENGINE=InnoDB;

CREATE TABLE mall_products (
  product_id int(11) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  product_type varchar(20) DEFAULT NULL COMMENT '商城类型 flower为卖花，后续支持其他的',
  title varchar(20) DEFAULT NULL COMMENT '标题',
  creation_date timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  price int(11) DEFAULT NULL COMMENT '价格',
  discount_price int(11) DEFAULT NULL COMMENT '折扣后的价格',
  `desc` varchar(100) DEFAULT NULL COMMENT '描述文案',
  content varchar(1000) DEFAULT NULL COMMENT '详情页面的描述文字。可存储json串，前端解析。',
  PRIMARY KEY (product_id)
) ENGINE=InnoDB;

CREATE TABLE mall_user_favorites (
  user_id int(11) NOT NULL,
  product_id int(11) NOT NULL COMMENT '产品id',
  creation_date timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '添加收藏时间',
  `status` int(4) DEFAULT NULL COMMENT '1 收藏 0 移除收藏',
  remove_date timestamp NULL DEFAULT NULL COMMENT '移除收藏时间',
  PRIMARY KEY (user_id,product_id)
) ENGINE=InnoDB;

-- 2019-01-24
alter table mall_orders alter column status set default 1