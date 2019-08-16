package com.jbb.server.common;

import java.nio.charset.Charset;
import java.sql.Timestamp;

/**
 * @Type Constants.java
 * @Desc
 * @author VincentTang
 * @date 2017年10月30日 下午12:03:36
 * @version
 */
public class Constants {

    public static final String UTF8 = "UTF-8";
    public static final Charset UTF8_CHARSET = Charset.forName(UTF8);
    public static final String API_KEY = "API_KEY";

    // Last system date 2030-01-01 00:00:00
    public static final long LAST_SYSTEM_MILLIS = 1893427200000L;
    public static final Timestamp LAST_SYSTEM_TIMESTAMP = new Timestamp(LAST_SYSTEM_MILLIS);

    // Seconds in one day
    public static final int ONE_DAY_SECONDS = 24 * 3600;
    // Milliseconds in one day
    public static final long ONE_DAY_MILLIS = ONE_DAY_SECONDS * 1000L;
    public static final long ONE_HOUR_MILLIS = 3600000L;

    // Standard value delimiter
    public static final char DELIMITER = ':';

    // Event Log Name
    public static String Event_LOG_EVENT_DEVICE_NAME = "device";
    public static String Event_LOG_EVENT_DEVICE_ACTION_ACTIVATE = "activate";

    // Event Log User
    public static String Event_LOG_EVENT_USER_EVENT = "user";
    public static String Event_LOG_EVENT_USER_ACTION_LOGIN = "login";
    public static String Event_LOG_EVENT_USER_ACTION_REGISTER = "register";
    public static String Event_LOG_EVENT_USER_ACTION_UPDATE = "update";
    public static String Event_LOG_EVENT_USER_LABEL_PALTFORM = "platform";

    // Event Log Name
    public static String Event_LOG_EVENT_RECOMMANDS = "recommand";
    public static String Event_LOG_EVENT_RECOMMAND_GET = "get";

    // 推送给平安赠险
    public static String Event_LOG_EVENT_USER_ACTION_POST_TO_PA = "post2pa";

    // Iou Type
    public static final int IOU_CATEGORY_HALL = 1; // 大厅
    public static final int IOU_CATEGORY_FOLLOW = 2; // 用户关注的
    public static final int IOU_CATEGORY_PUBLISY = 3; // 用户发布的
    public static final int IOU_CATEGORY_LEND = 4; // 用户出借的
    public static final int IOU_CATEGORY_LENDER_FILL = 5; // 出借人补借条
    public static final int IOU_CATEGORY_BORROWER_FILL = 6; // 借款人补借条
    public static final int IOU_CATEGORY_IOUS_LENDER = 7; // 出借人借出的借条
    public static final int IOU_CATEGORY_IOUS_BORROWER = 8; // 借款人借入的借条
    public static final int IOU_CATEGORY_IOUS_PUBLISH = 9; // 借款人发布的借条

    // Iou Intend status
    public static final int IOU_INTEND_YES = 0; // 有意向
    public static final int IOU_INTEND_REJECT = 1; // 借款人拒绝
    public static final int IOU_INTEND_NO = 2; // 取消意向
    public static final int IOU_INTEND_APPROVE = 3; // 借款人同意
    public static final int IOU_INTEND_MODIFY_APPROVE = 4; // 意向人同意修改
    public static final int IOU_INTEND_MODIFY_REJECT = 5; // 意向人拒绝修改

    // User property name
    public static final String SESAME_CREDIT_SCORE = "sesameCreditScore";
    public static final String CHANNEL_SOURCE_ID = "channel_source_id";
    public static final String FILTER_EXPRESSION = "filterExpression";
    public static final String SIGNIN_PLATFROM = "signin-platform";

    // User Priv name
    public static final String USER_PRIV_QQ = "qq";
    public static final String USER_PRIV_WECHAT = "wechat";
    public static final String USER_PRIV_INFO = "info";
    public static final String USER_PRIV_PHONE = "phone";

    // User property ReadAt value
    public static final String USER_P_READAT = "readAt_";
    public static final String USER_P_READAT_HALL = "readAt_hall";
    public static final String USER_P_READAT_PUBLISH = "readAt_publish";
    public static final String USER_P_READAT_FOLLOW = "readAt_follow";
    public static final String USER_P_READAT_LEND = "readAt_lend";

    // iou
    public static final int IOU_STATISTIC_EFFECT = 1;
    public static final int IOU_STATISTIC_EFFECT_WITHOUT_COMPLETED = 2;
    public static final int[] IOU_STATUS_EFFECT = {1, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
    public static final int[] IOU_STATUS_EFFECT_WITHOUT_COMPLETED = {1, 5, 6, 7, 8, 10, 11, 12, 13};
    public static final int[] IOU_STATUS_INEFFECT = {0, 2, 3, 4, 20, 30};
    public static final int[] IOU_STATUS_INEFFECT_EXCLUDE_FILL = {0, 2, 3, 4};
    public static final int IOU_STATUS_LENDER_FILL = 30;
    public static final int IOU_STATUS_BORROWER_FILL = 20;

    // JBB MGT
    public static final String OSS_BUCKET_TD_PRELOAN = "jbb-td-preloan";
    public static final String OSS_BUCKET_CLUB_PRELOAN = "jbb-club-preloan";
    public static final String OSS_BUCKET_YX_PRELOAN = "jbb-yx-report";

    // JBB MGT PERMISSONS
    public static final int MGT_P_SYSADMIN = 1;
    public static final int MGT_P_ORGADMIN = 2;
    public static final int MGT_P_MGTRPORT = 3;
    public static final int MGT_P_USERREPORT = 4;
    public static final int MGT_P_RECHARGE = 5;
    public static final int MGT_P_ACCSETTING = 6;
    public static final int MGT_P_CHANNEL = 7;
    public static final int MGT_P_MGTCHANNEL = 8;
    public static final int MGT_P_DATAFLOW = 9;
    public static final int MGT_P_ASSIGN = 10;
    public static final int MGT_P_TELEMARKETING = 11;
    public static final int MGT_P_PRE_CHECK = 12;
    public static final int MGT_P_FIN_CHECK = 13;
    public static final int MGT_P_IOU = 14;
    public static final int MGT_P_BILL = 15;
    public static final int MGT_P_LOAN_MGT = 16;
    public static final int MGT_P_COLLECTION = 17;
    // 社群贷超管理员
    public static final int MGT_P_COMMUNITY = 139;

    // 运营子系统 - 系统管理员 101
    public static final int MGT_P1_101 = 101;
    // 运营子系统 - 组织管理员 102
    public static final int MGT_P1_102 = 102;
    // 渠道推广 103
    public static final int MGT_P1_103 = 103;
    // H5商家管理 104
    public static final int MGT_P1_104 = 104;
    // 组织统计数据列表 105
    public static final int MGT_P1_105 = 105;
    // 添加组织 106
    public static final int MGT_P1_106 = 106;
    // 进件 - 其他 107
    public static final int MGT_P1_107 = 107;
    // 进件 - 推荐 108
    public static final int MGT_P1_108 = 108;
    // 非纯进件 109
    public static final int MGT_P1_109 = 109;
    // 渠道扣量 110
    public static final int MGT_P1_110 = 110;
    // 总报表 111
    public static final int MGT_P1_111 = 111;
    // 注册导流报表 112
    public static final int MGT_P1_112 = 112;
    // 进件导流报表 113
    public static final int MGT_P1_113 = 113;
    // 代理导流报表 114
    public static final int MGT_P1_114 = 114;
    // 注册导流充消报表 115
    public static final int MGT_P1_115 = 115;
    // 进件导流充消报表 116
    public static final int MGT_P1_116 = 116;
    // 代理充消报表 117
    public static final int MGT_P1_117 = 117;
    // H5客户充消报表 118
    public static final int MGT_P1_118 = 118;
    // 贷超充消报表 119
    public static final int MGT_P1_119 = 119;
    // 渠道充消报表 120
    public static final int MGT_P1_120 = 120;
    // 注册模式漏斗报表 121
    public static final int MGT_P1_121 = 121;
    // 进件模式漏斗报表 122
    public static final int MGT_P1_122 = 122;
    // H5商家业务数据表 123
    public static final int MGT_P1_123 = 123;
    // 贷超业务数据表 124
    public static final int MGT_P1_124 = 124;
    // 贷超渠道绩效表 125
    public static final int MGT_P1_125 = 125;
    // 渠道总体情况表 126
    public static final int MGT_P1_126 = 126;
    // 注册模式渠道质量反馈表 127
    public static final int MGT_P1_127 = 127;
    // 进件模式渠道质量反馈表 128
    public static final int MGT_P1_128 = 128;
    // 平台链接 129
    public static final int MGT_P1_129 = 129;
    // app区域配置 130
    public static final int MGT_P1_130 = 130;
    // 平台配置 131
    public static final int MGT_P1_131 = 131;

    // 销售 132
    public static final int MGT_P1_132 = 132;
    // 综合运营 133
    public static final int MGT_P1_133 = 133;
    // 用户运营 134
    public static final int MGT_P1_134 = 134;
    // 商务主管 135
    public static final int MGT_P1_135 = 135;
    // 结算 136
    public static final int MGT_P1_136 = 136;
    // 财务 137
    public static final int MGT_P1_137 = 137;

    // 贷超产品UV数 140
    public static final int MGT_P1_140 = 140;
    // 贷超导流漏斗表 141
    public static final int MGT_P1_141 = 141;

    // 贷超产品数据上报金额 142
    public static final int MGT_P1_142 = 142;

    // 贷超IP异常分析 143
    public static final int MGT_P1_143 = 143;
    // ip查询
    public static final int MGT_P1_144 = 144;

    // 高亮配置 145
    public static final int MGT_P1_145 = 145;

    public static final int[] MGT_STATISTIC_SALE_CHECK = {101, 102, 104, 107, 108};

    public static final int[] MGT_CHANNELS_SALE_CHECK = {101, 102, 104, 105, 107};

    // 小金条-系统管理员 201
    public static final int XJL_MGT_P2_201 = 201;
    // 小金条-组织管理员 202
    public static final int XJL_MGT_P2_202 = 202;
    // 小金条-充值 203
    public static final int XJL_MGT_P2_203 = 203;
    // 小金条-账户设置 204
    public static final int XJL_MGT_P2_204 = 204;
    // 小金条-渠道管理 205
    public static final int XJL_MGT_P2_205 = 205;
    // 小金条-待领取 206
    public static final int XJL_MGT_P2_206 = 206;
    // 小金条-审核待领取 207
    public static final int XJL_MGT_P2_207 = 207;
    // 小金条-催收待领取 208
    public static final int XJL_MGT_P2_208 = 208;
    // 小金条-订单管理 209
    public static final int XJL_MGT_P2_209 = 209;
    // 小金条-已申请借款 210
    public static final int XJL_MGT_P2_210 = 210;
    // 小金条-未申请借款 211
    public static final int XJL_MGT_P2_211 = 211;
    // 小金条-业务报表 212
    public static final int XJL_MGT_P2_212 = 212;

    // 借帮帮销售
    public static final int MGT_P_JBB_SALES = 18;
    // 借帮帮结算
    public static final int MGT_P_JBB_SETTLE = 19;

    // JBB CLUB notify
    public static final String NOTIFY_TYPE_ACQUIRE = "ACQUIRE";
    public static final String NOTIFY_TYPE_REPORT = "REPORT";

    public static final String NOTIFY_EVENT_CREATED = "CREATED";
    public static final String NOTIFY_EVENT_SUCCESS = "SUCCESS";
    public static final String NOTIFY_EVENT_FAILURE = "FAILURE";
    public static final String NOTIFY_EVENT_TIMEOUT = "TIMEOUT";

    // LOAN STATUS
    public static final int STATUS_FINISH_7 = 7;
    public static final int STATUS_FINISH_8 = 8;

    // JBB Organization
    public static final int JBB_ORG = 1;

    // PLATFOMRS
    public static final int PLATFORM_JBB = 1;

    // Channel Delegate Name
    public static final String CHANNEL_DELEGATE_NAME = "借帮帮";

    // JBB DEFAULT CHANNEL
    public static final String JBB_DEFAULT_CHANNEL = "jbbd";

    // JBB XJL CHANNEL
    public static final String JBB_XJL_CHANNEL = "xjl";

    // User Property Name
    public static final String USER_PROPERTY_DECREMENT_RESULT_VALUE = "1";

    // iou
    public static final String PRODUCT_AUTH = "auth";
    public static final String PRODUCT_IOU = "iou";

    // 用户类型
    // 进件
    public static final int USER_TYPE_ENTRY = 1;
    // 导流
    public static final int USER_TYPE_REGISTER = 2;

    // 广告代理
    public static final int USER_TYPE_DELEGATE = 3;

    // User Verify Type
    public static final String VERIFY_TYPE_REALNAME = "realName";
    public static final String VERIFY_TYPE_VIDEO = "video";
    public static final String VERIFY_TYPE_MOBILE = "mobile";

    // ChuangLan
    public static final String STATUS_REAL_PHONE_NUMBER = "1";
    public static final String STATUS_WOOL_CHECK_B1 = "B1";
    public static final String STATUS_WOOL_CHECK_B2 = "B2";

    // 组织自动设置
    public static final int TYPE_USER_AUTO_SETTING_SELF = 1;
    public static final int TYPE_USER_AUTO_SETTING_ENTRY = 2;
    public static final int TYPE_USER_AUTO_SETTING_DFLOW = 3;

    // MGT ROLE
    public static final int MGT_INIT_ID = 6;
    public static final int MGT_LOAN_ID = 9;
    public static final int XJL_MGT_MARKET_ID = 203;
    public static final int XJL_MGT_CHECK_ID = 204;
    public static final int XJL_MGT_AD_ID = 205;
    public static final int XJL_MGT_SERVICE_ID = 206;
    public static final int XJL_MGT_REMIND_ID = 207;

    // ORG TYPE
    public static final int SYS_ORG_TYPE = 1;
    public static final int FLOW_ORG_TYPE = 2;
    public static final int XJL_ORG_TYPE = 3;
}
