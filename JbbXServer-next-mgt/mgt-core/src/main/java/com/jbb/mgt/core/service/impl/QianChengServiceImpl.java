package com.jbb.mgt.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.GenericRequest;
import com.jbb.mgt.core.dao.UserAddressDao;
import com.jbb.mgt.core.dao.UserCardDao;
import com.jbb.mgt.core.dao.UserJobDao;
import com.jbb.mgt.core.dao.XjlApplyRecordDao;
import com.jbb.mgt.core.domain.*;
import com.jbb.mgt.core.domain.jsonformat.gjj.Bill_record;
import com.jbb.mgt.core.domain.jsonformat.gjj.GjjRootBean;
import com.jbb.mgt.core.domain.jsonformat.gjj.Pay_info;
import com.jbb.mgt.core.domain.jsonformat.shebao.*;
import com.jbb.mgt.core.domain.jsonformat.yys.Call_info;
import com.jbb.mgt.core.domain.jsonformat.yys.Call_record;
import com.jbb.mgt.core.domain.jsonformat.yys.Sms_info;
import com.jbb.mgt.core.domain.jsonformat.yys.YysRootBean;
import com.jbb.mgt.core.domain.jsonformat.yys.report.*;
import com.jbb.mgt.core.service.ClubService;
import com.jbb.mgt.core.service.QianChengService;
import com.jbb.mgt.core.service.UserContantService;
import com.jbb.mgt.core.service.UserService;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.AliyunException;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.HttpUtil;
import com.jbb.server.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 现金白卡/前程数据风控审核service
 */

@Service("QianChengService")
public class QianChengServiceImpl implements QianChengService {

    private static Logger logger = LoggerFactory.getLogger(QianChengServiceImpl.class);

    private static String TOKEN = "";

    private static int REAEAT_COUNT = 0;

    @Autowired
    private UserContantService userContantService;

    @Autowired
    private UserService userService;

    @Autowired
    private ClubService clubService;

    @Autowired
    private XjlApplyRecordDao xjlApplyRecordDao;

    @Autowired
    private UserJobDao userJobDao;

    @Autowired
    private UserAddressDao userAddressDao;

    @Autowired
    private UserCardDao userCardDao;

    @Override
    public String getToken() {
        String userName = PropertyManager.getProperty("xjl.qiancheng.userName");
        String password = PropertyManager.getProperty("xjl.qiancheng.password");
        String url = PropertyManager.getProperty("xjl.qiancheng.url") + "/client/authorize/token";
        String dataParam = "{\"username\":\"" + userName + "\",\"password\":\"" + password + "\",\"hours\":" + 24 + "}";
        try {
            HttpUtil.HttpResponse response
                = HttpUtil.sendDataViaHTTP(HttpUtil.HTTP_POST, url, HttpUtil.CONTENT_TYPE_JSON, dataParam, null);
            if (response.getResponseCode() == HttpUtil.STATUS_OK) {
                byte[] byteArray = response.getResponseBody();
                if (byteArray == null || byteArray.length < 1) {
                    throw new IllegalArgumentException("this byteArray must not be null or empty");
                } else {
                    String strRead = new String(byteArray, "utf-8");
                    logger.debug("content = " + strRead);
                    JSONObject json = JSONObject.parseObject(strRead);
                    String code = json.getString("code");
                    if (code.equals("0")) {
                        String data = json.getString("data");
                        JSONObject jsonData = JSONObject.parseObject(data);
                        String token = jsonData.getString("token");
                        return token;
                    } else {
                        String msg = json.getString("msg");
                        // 抛出异常信息
                        throw new WrongParameterValueException(msg);
                    }
                }
            } else {
                logger.info("response code = " + response.getResponseCode());
            }

        } catch (IOException e) {
            logger.error("response with error, " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getVerifyResult(User user, String applyId) {

        /**
         * token string 是 前程数据分配的token name string 是 借款人姓名 id_card string 是 借款人身份证号 mobile string 是 借款人手机号 tag int 否
         * 订单类型，默认 0未知 1联营订单 2自身app订单 data object 是 用户推送过来的数据 encrypt int 是 数据是否加密，默认1加密 product int 否 产品 默认0
         * 多个产品时顺延，也可自定义字符串，需要告知到我
         */
        if (StringUtil.isEmpty(TOKEN)) {
            initToken();
        }
        String token = TOKEN;
        String name = user.getUserName();
        String idCard = user.getIdCard();
        String mobile = user.getPhoneNumber();
        int tag = 2;
        int encrypt = 0;
        com.alibaba.fastjson.JSONObject verifyData = new com.alibaba.fastjson.JSONObject();
        verifyData.put("token", token);
        verifyData.put("name", name);
        verifyData.put("id_card", idCard);
        verifyData.put("mobile", mobile);
        verifyData.put("tag", tag);
        verifyData.put("encrypt", encrypt);
        verifyData.put("product", 0);
        XjlApplyRecord applyRecord = xjlApplyRecordDao.selectXjlApplyRecordByApplyId(applyId, null, false);
        long delayTime = PropertyManager.getLongProperty("mgt.xjl.checkapply.delayTime", 300000L);
        Timestamp startDate = applyRecord.getCreationDate();
        Timestamp endDate = DateUtil.calTimestamp(startDate.getTime(), delayTime);
        verifyData.put("data", getVerifyData(user, startDate, endDate).toString());
        String url = PropertyManager.getProperty("xjl.qiancheng.url") + "/risk-model/galaxy/open-id";
        try {
            logger.info("QianChengDate Request User BaseInfo : userId = " + user.getUserId() + " userName = "
                + user.getUserName() + " phoneNumber = " + user.getPhoneNumber() + " idCard = " + user.getIdCard());
            HttpUtil.HttpResponse response = HttpUtil.sendDataViaHTTP(HttpUtil.HTTP_POST, url,
                HttpUtil.CONTENT_TYPE_JSON, verifyData.toString(), null);
            if (response.getResponseCode() == HttpUtil.STATUS_OK) {
                byte[] byteArray = response.getResponseBody();
                if (byteArray == null || byteArray.length < 1) {
                    throw new IllegalArgumentException("this byteArray must not be null or empty");
                } else {
                    String strRead = new String(byteArray, "utf-8");
                    logger.info("content = " + strRead);
                    JSONObject json = JSONObject.parseObject(strRead);
                    String data = json.getString("data");
                    String msg = json.getString("msg");
                    String code = json.getString("code");
                    if (msg.equals("令牌验证失败")) {
                        REAEAT_COUNT += 1;
                        if (REAEAT_COUNT > 3) {
                            logger.error("QianCheng GetToken Out Of Three Times");
                            try {
                                // 休眠一分钟
                                Thread.sleep(60000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            REAEAT_COUNT = 0;
                            return null;
                        }
                        initToken();
                        this.getVerifyResult(user, applyId);
                        return null;
                    }

                    if (!code.equals("0")) {
                        logger.error("QianCheng verify error response = " + strRead);
                        return null;
                    }
                    if (data.equals("[]")) {
                        logger.info("QianCheng GetOpenId Date IS Null");
                        return null;
                    }
                    JSONObject jsonData = JSONObject.parseObject(data);
                    String openId = jsonData.getString("open_id");
                    // 获取open_id成功，保存信息到applyrecord表
                    applyRecord.setApplyStatus("1");
                    applyRecord.setOpenId(openId);
                    xjlApplyRecordDao.updateXjlApplyRecord(applyRecord);
                }
            } else {
                logger.info("response code = " + response.getResponseCode());
            }

        } catch (IOException e) {
            logger.error("response with error, " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public JSONObject getVerifyData(User user, Timestamp startDate, Timestamp endDate) {

        /* data
        basic_info	object	是	借款人基本信息
        user_mobile_contacts	array	是	通讯录信息
        user_proof_materia	array	是	照片资料信息
        user_login_upload_logs	array	否	登录启动日志
        user_loan_orders	array	是	订单信息
        card_info	array	否	银行卡信息
        zm_data	object	否	芝麻信用信息
        telecom_data / operator_report_verify	object	否	运营商报告信息(聚信立格式)
        ebank_data / ibank_verify	object	否	网银原始信息
        fund_data / fund_verify	object	否	公积金信息
        soin_data / insure_verify	object	否	社保信息
        alipay_data / zfb_verify	object	否	支付宝原始信息*/

        JSONObject jsonData = new JSONObject();

        // ** --basic_info start
        JSONObject basicInfo = new JSONObject();
        /*degree	tinyint	否	学历 1中专/高中以下 2大专 3本科 4硕士 5博士及以上
        register_time	string	是	注册时间 日期格式（2018-08-01 12:00:00）
        profession_type	tinyint	否	职业 (1、上班族 2、企业主 3、个体户 4、学生 5、自由职业)
        sex	tinyint	是	性别 0:男,1:女
        race	string	是	民族
        address	string	否	身份证地址
        start_time	string	否	身份证有效期开始时间
        end_time	tinyint	否	身份证有效期结束时间
        visa_agency	string	否	签证机关
        company_name	string	否	公司名称
        company_areas	string	否	公司所在区(格式:福建省,宁德市,蕉城区)
        company_address	string	否	公司地址
        company_position	tinyint	否	职位 1普通员工 2部门主管 3部门总监 4公司高管
        company_email	string	否	公司邮箱
        company_tel	string	否	公司电话
        company_entry_date	int	否	入职时间
        qq	string	否	QQ号
        wechat	string	否	微信号
        email	string	否	邮箱
        loan_use	tinyint	否	贷款用途：1装修2婚庆3旅游4教育5租房6汽车周边7电子数码产品8医疗9其他10家用电器11家具家居
        work_age	tinyint	否	当前单位工龄 1：0-5个月， 2：6-11个月 3：1-3年， 4：3-7年， 5：7年以上
        home_areas	string	否	住宅地址所在地区
        home_address	string	否	住宅地址
        home_phone	string	否	住宅电话
        household_type	tinyint	否	户口性质 ;1.农村;2.城市
        live_time	tinyint	否	现地址居住时长 1.0-5个月;2.6-11个月;3.1-3年;4.3-7年;5.7年以上
        permanent_areas	string	否	户籍地址所在地区
        permanent_address	string	否	户籍地址
        free_income	int	否	自由职业的收入(单位分)
        pay_day	tinyint	否	工资发放日1-30日
        marriage	tinyint	否	婚姻状况 1未婚 2已婚已育 3离异 4已婚未育 5丧偶 6其他
        user_contact	array	是	紧急联系人*/

        basicInfo.put("degree", getEducation(user.getEducation()));// 1中专/高中以下 2大专 3本科 4硕士 5博士及以上
        basicInfo.put("register_time", DateUtil.getTimeString(user.getCreationDate()));// 注册时间是什么时候？
        basicInfo.put("profession_type", getProfessionType(user));// 1、上班族 2、企业主 3、个体户 4、学生 5、自由职业
        basicInfo.put("sex", getSex(user.getSex()));// user.getSex();
        basicInfo.put("race", user.getRace());
        basicInfo.put("address", user.getIdcardAddress());
        // basicInfo.put("start_time", "");
        // basicInfo.put("end_time", "");
        basicInfo.put("visa_agency", "");

        UserJob userJob = userJobDao.selectJobInfoByAddressId(user.getUserId());
        if (userJob != null) {
            UserAddresses userAddresses = userJob.getJobAddress();
            basicInfo.put("company_name", userJob.getCompany());
            if (userAddresses != null) {
                basicInfo.put("company_areas",
                    userAddresses.getProvince() + "," + userAddresses.getCity() + "," + userAddresses.getArea());
                basicInfo.put("company_address", userAddresses.getAddress());
                basicInfo.put("company_position", "");// 职位 信息完全不匹配，无法枚举 先传空
                basicInfo.put("company_email", "");
                basicInfo.put("company_tel", "");
                // basicInfo.put("company_entry_date", "");
                long workAge
                    = (DateUtil.getCurrentTime() - userJob.getStartDate().getTime()) / (365 * 24 * 60 * 60 * 1000);
                basicInfo.put("work_age", workAge);
            }
        }

        basicInfo.put("qq", user.getQq());
        basicInfo.put("wechat", user.getWechat());
        basicInfo.put("email", "");
        // basicInfo.put("loan_use", 0);//贷款用途 数据维度不同 暂时不传
        UserAddresses userAddresses = userAddressDao.selectUserAddressesUserIdAndType(user.getUserId(), 0);
        if (userAddresses != null) {
            basicInfo.put("home_areas",
                userAddresses.getProvince() + "," + userAddresses.getCity() + "," + userAddresses.getArea());
        }
        basicInfo.put("home_address", user.getLiveAddress());
        basicInfo.put("home_phone", "");
        // basicInfo.put("household_type", 2);//户口性质 ;1.农村;2.城市
        // basicInfo.put("live_time", 10);
        basicInfo.put("permanent_areas", "");
        basicInfo.put("permanent_address", user.getIdcardAddress());
        // basicInfo.put("free_income", 50000000);//自由职业收入
        // basicInfo.put("pay_day", 5);
        basicInfo.put("marriage", user.getMaritalStatus());
        basicInfo.put("user_contact", getContactArray(user));
        // --basicInfo end

        // ** -- user_mobile_contacts start
        net.sf.json.JSONArray userMobileContactsArray = new net.sf.json.JSONArray();
        if (user.isHasContacts()) {
            List<UserContant> userContants = userContantService.selectUserContantByJbbUserId(user.getJbbUserId());
            if (userContants != null && userContants.size() != 0) {
                for (UserContant userContant : userContants) {
                    JSONObject userMobileContact = new JSONObject();
                    userMobileContact.put("mobile", userContant.getPhoneNumber());
                    userMobileContact.put("name", userContant.getUserName());
                    userMobileContactsArray.add(userMobileContact);
                }
            }
        } else {
            // 将系统存的联系人信息拼入结果
            JSONObject userMobileContact = new JSONObject();
            userMobileContact.put("mobile", user.getContract1XjlPhonenumber());
            userMobileContact.put("name", user.getContract1XjlUsername());

            JSONObject userMobileContact1 = new JSONObject();
            userMobileContact1.put("mobile", user.getContract2XjlPhonenumber());
            userMobileContact1.put("name", user.getContract2XjlUsername());
            userMobileContactsArray.add(userMobileContact);
            userMobileContactsArray.add(userMobileContact1);
        }

        // -- user_mobile_contacts end

        // ** -- user_proof_materia start
        net.sf.json.JSONArray userProofMateriaArray = new net.sf.json.JSONArray();
        /* url	string	是	图片地址
        type	int	是	图片类型:1.身份证2.学历证明3.工作证明4.收入证明5.财产证明6.工作照7.个人名片8.银行卡9.房产证明10.人脸识别11.身份证正面12.身份证反面100.其他*/
        JSONObject userProofMateria1 = new JSONObject();
        JSONObject userProofMateria2 = new JSONObject();
        JSONObject userProofMateria3 = new JSONObject();
        String idcardRear = user.getIdcardRear();
        userProofMateria1.put("url", getIdCardUrl(idcardRear, 0));
        userProofMateria1.put("type", 11);
        String idcardBack = user.getIdcardBack();
        userProofMateria2.put("url", getIdCardUrl(idcardBack, 0));
        userProofMateria2.put("type", 12);
        String videoScreenShot = user.getVidoeScreenShot();
        userProofMateria3.put("url", getIdCardUrl(videoScreenShot, 1));
        userProofMateria3.put("type", 10);
        if (!getIdCardUrl(idcardRear, 0).equals("")) {
            userProofMateriaArray.add(userProofMateria1);
        }
        if (!getIdCardUrl(idcardBack, 0).equals("")) {
            userProofMateriaArray.add(userProofMateria2);
        }
        if (!getIdCardUrl(videoScreenShot, 1).equals("")) {
            userProofMateriaArray.add(userProofMateria3);
        }

        // -- user_login_upload_logs start
        net.sf.json.JSONArray userLoginUploadLogs = new net.sf.json.JSONArray();
        /*carrier	string	否	运营商：中国移动
        tele_num	string	否	运营商号：46002
        longitude	string	否	经度
        latitude	string	否	纬度
        address	string	否	具体地址
        ip	string	否	IP地址
        dns	string	否	dns
        imsi	string	否	imsi
        idfv	string	否	idfv
        idfa	string	否	idfa
        mac	string	否	mac地址
        installed_time	string	否	安装app时间
        android_id	string	否	手机 AndroidId
        memory	string	否	手机内存:989.0 MB
        udid	string	否	设备唯一标识
        uuid	string	否	通用唯一识别码
        wifi	string	否	是否使用wifi:1是 0否
        wifi_name	string	否	Wifi名
        wifi_bssid	string	否	wifi路由器mac
        storage	float	否	存储总空间：29.8 GB
        unuse_storage	float	否	可用的总储存空间：29.8 GB
        sdcard	float	否	SD卡总存储空间：29.8 GB
        unuse_sdcard	float	否	SD卡可用存储空间：29.8 GB
        bettary	int	否	电量：97（百分比数值）
        is_charging	int	否	是否在充电 :1是 0否
        pic_count	int	否	手机图片数量
        is_root	int	否	is_root:1是 0否
        is_simulator	int	否	is_simulator:1是 0否
        gyroscope	string	否	陀螺仪
        net_type	string	否	网络类型
        create_time	string	否	上报记录时间
        app_market	string	否	应用市场
        app_version	string	否	app版本
        client_type	string	否	客户端类型：ios
        device_id	string	否	设备码
        device_name	string	否	设备名称
        os_version	string	否	APP版本*/
        // -- user_login_upload_logs end

        // ** -- user_loan_orders start
        net.sf.json.JSONArray userLoanOrdersArray = new net.sf.json.JSONArray();
        /*    order_id	string	是	订单id
        money_amount	integer	是	金额,单位分
        true_amount	integer	是	实发金额,单位分
        loan_method	integer	是	0:按天，1,：按月，2：按年
        loan_term	integer	是	根据loan_method确定，几天、几月、几年
        loan_interests	integer	是	总共利息，单位分
        loan_purpose	integer	是	借款用途(1.装修2.婚庆3.旅游4.教育5.租房6.汽车周边7.电子数码产品8.医疗9.其他10.家用电器11.家具家居)
        order_time	integer	是	下单时间(时间戳，单位秒)
        loan_time	integer	是	放款时间，用于计算利息的起止时间(时间戳，单位秒)
        counter_fee	integer	是	手续费,单位分
        status	integer	是	状态：0、审核中1、借款失败 2、待还款3、部分已还款 4、已还款
        repayment	array	是	还款计划，只有status为2，3，4时才会有，没有时传空数组
        repayment 还款计划信息（放款之后订单才需传）
        参数	类型	是否必选	描述
        late_fee	integer	是	滞纳金，单位分
        plan_fee_time	integer	是	到期时间/开始计算滞纳金时间，时间戳，单位秒
        total_money	integer	是	还款总额，单位分
        true_total_money	integer	是	实际还款总额，单位分
        true_repayment_time	string	是	实际还款时间，时间戳，单位秒
        is_overdue	integer	是	是否是逾期：0，不是；1，是
        overdue_day	integer	是	逾期天数
        status	integer	是	状态：1、待还款 2、已还款*/
        // 数据暂时没有，手动拼接两条
        // 查询用户的订单
        List<XjlApplyRecord> applyRecords
            = xjlApplyRecordDao.getXjlApplyRecordByUserId(user.getUserId(), null, true, null);
        if (applyRecords != null && applyRecords.size() != 0) {
            for (XjlApplyRecord xjlApplyRecord : applyRecords) {
                if (xjlApplyRecord.getStatus() == 3 || xjlApplyRecord.getStatus() == 4
                    || xjlApplyRecord.getStatus() == 5 || xjlApplyRecord.getStatus() == 99) {
                    JSONObject userLoanOrder = new JSONObject();
                    userLoanOrder.put("order_id", xjlApplyRecord.getApplyId());
                    userLoanOrder.put("money_amount", xjlApplyRecord.getAmount());
                    userLoanOrder.put("true_amount", xjlApplyRecord.getAmount());
                    userLoanOrder.put("loan_method", 0);
                    userLoanOrder.put("loan_term", 7);
                    userLoanOrder.put("loan_interests", 0);
                    userLoanOrder.put("loan_purpose", xjlApplyRecord.getPurpose());
                    userLoanOrder.put("order_time", xjlApplyRecord.getCreationDate().getTime() / 1000);
                    userLoanOrder.put("loan_time", xjlApplyRecord.getLoanDate().getTime() / 1000);
                    userLoanOrder.put("counter_fee", xjlApplyRecord.getServiceFee());
                    // 0、审核中1、借款失败 2、待还款3、部分已还款 4、已还款
                    int status = 0;
                    if (xjlApplyRecord.getStatus() == 3) {
                        status = 2;
                    }
                    if (xjlApplyRecord.getStatus() == 4) {
                        status = 2;
                    }
                    if (xjlApplyRecord.getStatus() == 5) {
                        status = 2;
                    }
                    if (xjlApplyRecord.getStatus() == 99) {
                        status = 4;
                    }
                    userLoanOrder.put("status", status);
                    net.sf.json.JSONArray repaymentArray = new net.sf.json.JSONArray();
                    JSONObject repayment = new JSONObject();
                    repayment.put("late_fee", 0);
                    long repaymentDate = 0;
                    if (xjlApplyRecord.getRepaymentDate() != null) {
                        repaymentDate = xjlApplyRecord.getRepaymentDate().getTime();
                    }
                    repayment.put("plan_fee_time", repaymentDate / 1000);

                    repayment.put("total_money", xjlApplyRecord.getAmount() + xjlApplyRecord.getServiceFee());
                    repayment.put("true_total_money", xjlApplyRecord.getAmount() + xjlApplyRecord.getServiceFee());
                    if (xjlApplyRecord.getActualRepaymentDate() != null) {
                        repayment.put("true_repayment_time", xjlApplyRecord.getActualRepaymentDate().getTime() / 1000);
                        long days = (xjlApplyRecord.getActualRepaymentDate().getTime() - repaymentDate)
                            / (1000 * 60 * 60 * 24);
                        repayment.put("overdue_day", days);
                    } else {
                        repayment.put("true_repayment_time", repaymentDate);
                        repayment.put("overdue_day", 0);
                    }
                    if (xjlApplyRecord.getStatus() == 4) {
                        repayment.put("is_overdue", 1);
                    } else {
                        repayment.put("is_overdue", 0);
                    }
                    if (xjlApplyRecord.getStatus() == 99) {
                        repayment.put("status", 2);
                    } else {
                        repayment.put("status", 1);
                    }
                    repaymentArray.add(repayment);
                    userLoanOrder.put("repayment", repaymentArray);
                    userLoanOrdersArray.add(userLoanOrder);
                }
            }
        }

        // -- user_login_upload_logs end

        // -- card_info start
        net.sf.json.JSONArray cardInfoArray = new net.sf.json.JSONArray();
        /* bank_id	int	是	1、工商银行,2、农业银行,3、光大银行,4、邮政储蓄银行,5、兴业银行,6、深圳发展银行,7、建设银行,8、招商银行,9、中国银行,10、浦发银行,11、平安银行,12、华夏银行,13、中信银行,14、交通银行,15、民生银行,16、广发银行17、北京银行,18、上海银行,19、上海农商银行,20、成都银行,21、渤海银行,22、南京银行,23、宁波银行,24、东亚银行,30、江西银行
        card_no	string	是	银行卡号
        valid_period	int	否	信用卡有效期
        credit_amount	int	否	信用卡额度，单位分
        type	int	是	银行卡类型1:信用卡 2:借记卡,3:对公账号
        main_card	int	否	是否是主卡:1是 0
        create_time	string	否	绑卡时间
        bank_address	string	否	开户行地址*/
        String payProductType = PropertyManager.getProperty("jbb.mgt.pay.product.type");
        String payProductId = "";
        if (payProductType.equals("1")) {
            payProductId = PropertyManager.getProperty("jbb.xjl.payProduct.helibao.id");
        } else {
            payProductId = PropertyManager.getProperty("jbb.xjl.payProduct.changjie.id");
        }
        List<UserCard> userCards = userCardDao.selectUserCards(user.getUserId(), payProductId, false);
        if (userCards != null && userCards.size() != 0) {
            for (UserCard userCard : userCards) {
                JSONObject card = new JSONObject();
                card.put("bank_id", getBankId(userCard.getBankCode()));
                card.put("card_no", userCard.getCardNo());
                // card.put("valid_period", );//信用卡有效期
                // card.put("credit_amount", );//信用卡额度，单位分
                card.put("type", 2);// 银行卡类型1:信用卡 2:借记卡,3:对公账号
                // card.put("main_card", );
                card.put("create_time", DateUtil.getTimeString(userCard.getCreationDate()));
                // card.put("bank_address", );
                cardInfoArray.add(card);
            }
        }

        // -- card_info end

        // -- zm_data start
        JSONObject zmData = new JSONObject();
        /*zm_score	string	是	芝麻信用分:688	zm_data
        watch_matched	string	是	是否在关注名单 1. 未命中 2. 命中	zm_data
        watch_info	array	否	行业关注名单; 未命中则为空	zm_data
        biz_code	string	是	风险信息行业编码	watch_info
        code	string	是	风险编码	watch_info
        level	string	是	风险等级	watch_info
        refresh_time	string	是	数据刷新时间	watch_info
        settlement	string	是	结清状态	watch_info
        type	string	是	行业名单风险类型	watch_info
        extend_info	array	是	扩展信息	watch_info
        description	string	是	补充信息字段描述	extend_info
        key	string	是	补充信息字段编码	extend_info
        value	string	是	补充信息字段内容	extend_info*/
        // -- zm_data end

        // -- telecom_data start
        JSONObject telecomData = getTelecomDataJson(user, startDate, endDate);
        // -- telecom_data end

        // -- ebank_data start
        JSONObject ebankData = new JSONObject();
        /*bank_name	string	银行名称:浦发储蓄	ibank_verify.银行名称
        account_info	object	账户信息	ibank_verify.银行名称
        bill_info	object	账单数据	ibank_verify.银行名称
        bill_record	object	资金明细	ibank_verify.银行名称
                account_info参数
        参数	类型	字段说明	父节点字段
        user_name	string	姓名	account_info
        id_card	string	身份证号	account_info
        mobile	string	手机号	account_info
        is_match_user_name	string	用户名与网银名是否匹配	account_info
        login_name	string	网银登录名	account_info
        card_list	list	银行卡集合	account_info
        card_list参数
        参数	类型	字段说明	父节点字段
        card_no	string	银行卡末四位	card_list
        full_card_no	string	完整银行卡号	card_list
        card_type	int	银行卡卡类型：1：信用卡 2：储蓄卡	card_list
        salary_card	string	是否是工资卡：true/false	card_list
                bill_info参数
        参数	类型	字段说明	父节点字段
        card_no	array	银行卡集合（如多张信用卡）	bill_info
        bill_data	string	账单日	bill_info
        bill_month	string	账单月	bill_info
        payment_data	string	还款日	bill_info
        interest	string	利息（逾期、取现）	bill_info
        credit_limit	string	信用额度	bill_info
        cash_limit	string	取现额度	bill_info
        last_balance	string	上期应还款金额	bill_info
        last_payment	string	上期还款金额	bill_info
        new_balance	string	本期应还款金额	bill_info
        new_charges	string	本期账单金额	bill_info
        min_payment	string	最低还款金额	bill_info
        usd_credit_limit	string	美元信用额度	bill_info
        usd_cash_limit	string	美元取现额度	bill_info
        usd_interest	string	美元利息	bill_info
        usd_last_balance	string	美元上期应还款金额	bill_info
        usd_last_payment	string	美元上期还款金额	bill_info
        usd_new_balance	string	美元本期账单金额	bill_info
        usd_min_payment	string	美元最低还款金额	bill_info
        bill_record参数
        参数	类型	字段说明	父节点字段
        card_no	string	银行卡末四位	bill_record
        trade_money	string	交易金额（支出- 收入+）	bill_record
        description	string	交易描述	bill_record
        currency_type	string	币种0：不区分	bill_record
        trade_time	string	交易时间	bill_record
        balance	string	余额	bill_record
        银行卡集合格式如下：
        
        "[\"8571\",\"8572\",\"8573\"]"
        
        参数	类型	字段说明	父节点字段
        card_no	string	银行卡末四位	bill_record
        balance	string	余额	bill_record
        trade_money	string	交易金额（支出- 收入+）	bill_record
        description	string	交易描述	bill_record
        currency_type	string	币种	bill_record
        trade_time	string	交易时间	bill_record*/
        // -- ebank_data end

        // -- fund_data start
        JSONObject fundData = new JSONObject();
        /* is_name_match / isNameMatch	int	输入接口中姓名匹配状态(1=成功 2=模糊成 功 3=失败)	fund_data
        province_name / provinceName	string	省名称	fund_data
        city_name / cityName	string	城市名称	fund_data
        fund_udid / fundUdid	string	身份证或公积金账号	fund_data
        nation	string	民族	fund_data
        mobile	string	手机号 或 座机号	fund_data
        name	string	姓名	fund_data
        idCard	string	身份证	fund_data
        email	string	邮箱	fund_data
        liveAddr	string	居住地址	fund_data
        accountAddr	string	户口地址	fund_data
        accountProp	int	户口性质(1=城镇，2=农村，3=未知)	fund_data
        workerProp	string	职工性质（直接抓取，常见：职员、办事员职员、其他等）	fund_data
        birthday	string	生日(1990-01-01)	fund_data
        livePostcode	string	居住地邮编	fund_data
        degree	string	学历	fund_data
        marriageStatus	string	婚姻状态（直接抓取，常见：否、未婚、是、已婚等）	fund_data
        start_work_date / startWorkDate	string	开始工作时间	fund_data
        funds	array	公积金账号流水组	fund_data
        payment	object	缴费情况	fund_data
        funds参数
        参数	类型	字段说明	父节点字段
        lastTime	string	更新时间	funds
        unitName	string	公司名称	funds
        unitCode	float	公司机构码	funds
        endPayDay	string	缴纳结束日期	funds
        personalRatio	string	个人缴纳比例	funds
        companyRatio	float	公司缴纳比例	funds
        accountOpenTime	string	开卡日期	funds
        freezeAmount	string	冻结金额	funds
        fundNo	float	公积金账户	funds
        balance	string	余额	funds
        bankName	string	开卡银行	funds
        beginPayDay	float	缴纳起始日期	funds
        premiumBaseCalculationType	int	缴存基数计算类型 0：直接抓取 1：直接计算2：通过月缴额和单位或个人缴纳比例计算 3：通过月缴额和该城市缴纳比例计算4：无法获取月缴额	funds
        premiumMonth	float	月缴纳金额	funds
        premiumBase	float	缴纳基数	funds
        status	string	缴纳状态	funds
        records	array	流水数组	funds
        records参数
        参数	类型	字段说明	父节点字段
        personalPremium	float	个人缴纳	records
        income	float	收入	records
        summary	float	摘要	records
        transTime	string	交易时间	records
        balance	float	账户余额	records
        unitName	string	所属单位	records
        unitPremium	float	单位缴纳	records
        outcome	string	支出	records
        premiumBase	string	缴纳基数	records
        payment参数
        参数	类型	字段说明	父节点字段
        preCompanyLastPay	string	上司末次月缴额	payment
        is6Successive	string	是否连续6个月缴存	payment
        currCompanyPayMoneyCount	string	本司总缴存金额	payment
        currCompanyFirstPayDate	string	本司首次缴存日期	payment
        currCompanyPayMonthCount	string	本司总缴存月数（需要再对流水结合进行去重）	payment
        past2YearBackCount	string	近2年补缴次数	payment
        currCompanyFirstPay	string	本司首次月缴额	payment
        lastOutcome	string	末次月支出额	payment
        applyIntervalMonthCount	string	申请间隔月数	payment
        past2YearOutcomeCount	string	近2年支出总额	payment
        totalOutcome	string	累计支出金额	payment
        currCompanyLastBackDate	string	本司末次补缴日期	payment
        jobHopDateDiff	string	近期跳槽日期差（月数）	payment
        isBalance10TimesFundMonthMoney	string	是否公积金余额是个人月缴额的10倍	payment
        count6m	string	近6月缴存月数	payment
        recentAdjustDate	string	本司最近一次调额月	payment
        adjustInterval	string	本司调额间隔	payment
        currCompanyPayDiff	string	本司首尾缴额差值	payment
        totalPay	string	累计缴纳金额	payment
        totalExtract	string	累计提取金额	payment
        isCurrCompanyHasBack	string	本司是否存在补缴0否，1是	payment
        currCompanyLastPayDate	string	本司末次缴存日期	payment
        past2YearCompanyCount	string	近二年缴交单位数	payment
        past1YearBackCount	string	近1年补缴次数	payment
        lastMonthPayDate	string	上月缴存日期	payment
        currCompanyLastPay	string	本司末次月缴额	payment
        currCompanyBackTimes	string	本司补缴次数	payment
        past2YearPay	string	近两年缴纳金额	payment
        past1YearOutcomeRate	string	近1年支出频率	payment
        companyCount	string	缴存至今变更单位数	payment
        isCompanyHasSupplement	string	曾缴补补充公积金0否，1是	payment
        past1YearCompanyCount	string	近一年缴交单位数	payment
        past1YearPay	string	近一年缴纳金额	payment
        currCompanyBackMonthCount	string	本司补缴月数	payment
        count24m	string	近24月缴存月数	payment
        count12m	string	近12月缴存月数	payment
        intervalMonthCount	string	本司入职前间隔月数	payment
        currCompanySupplementPay	string	本司补充公积金末次月缴存额	payment
        preCompanyLastPayDate	string	上家末次缴存日期	payment
        jobHopIncomeDiff	string	近期跳槽缴额差值 -1表示没有上一个账号	payment
        past1YearOutcomeCount	string	近1年支出总额	payment
        uniqueMonthNum	string	总缴存月数(已进行去重)	payment
        isCurrCompanyHasSupplement	string	本司缴纳补充公积金，0否，1是	payment
        aging	string	账龄	payment
        successiveMonthCount	string	最大连续缴存数	payment
        isCompanyEverPay	string	本司曾有缴交 1为存在，0为不存在	payment*/
        // -- fund_data end

        // -- soin_data start
        JSONObject soinData = new JSONObject();
        /*data_source	string	数据来源	soin_data
        update_time	string	数据获取时间	soin_data
        user_company	string	用户所属单位	soin_data
        soins_area	string	社保开户区县	soin_data
        soins_prov	string	社保开户省份	soin_data
        soins_city	string	社保开户城市	soin_data
        user_idcard	string	身份证号	soin_data
        soins_org	string	社保开户机构	soin_data
        soins_num	string	社保账号	soin_data
        open_time	string	开户日期	soin_data
        soins_status	string	社保状态	soin_data
        details	list	交易记录	soin_data
        details参数
        参数	类型	字段说明	父节点字段
        total_pay	float	缴费总计	details
        pay_cardi	float	缴费基数	details
        pson_pay	float	缴纳基数	details
        start_time	datetime	缴费开始时间	details
        end_time	datetime	缴费结束时间	details
        soins_type	string	社保种类	details
        soins_nature	string	缴费性质	details
        comp_pay	string	单位缴费	details
        pay_months	int	缴费月数	details
        soins_company	string	单位名称	details*/
        // -- soin_data end

        // -- alipay_data start
        JSONObject alipayData = new JSONObject();
        /*  taobao_name	string	淘宝ID	alipay_data
        phone	string	手机号码	alipay_data
        email	string	email地址	alipay_data
        real_name	string	用户姓名	alipay_data
        real_name_status	bool	是否实名	alipay_data
        register_time	string	注册时间	alipay_data
        wealth	int	总资产（已舍弃）	alipay_data
        fund	int	基金	alipay_data
        balance	int	余额	alipay_data
        balance_bao	int	余额宝	alipay_data
        fortune_bao	int	招财宝	alipay_data
        deposit_bao	int	存金宝	alipay_data
        taobao_financial	int	淘宝理财	alipay_data
        ants_lines	int	花呗消费额度	alipay_data
        bank_cards	int	银行卡信息（已舍弃）	alipay_data
        friends_contact	int	联系人信息（已舍弃）	alipay_data
        deal_record	array	数据详情	alipay_data
        create_time	date	创建时间	alipay_data
        update_time	date	修改时间	alipay_data
        deal_record参数
        参数	类型	字段说明	父节点字段
        name	string	产品名	deal_record
        order_no	string	交易号	deal_record
        deal_time	string	交易时间	deal_record
        deal_amount	float	交易额	deal_record
        detail_amount	float	详细金额	deal_record
        status	string	交易状态	deal_record
        other_party	string	交易对方	deal_record
        trade_location	string	交易来源地	deal_record*/
        // -- alipay_data end

        jsonData.put("basic_info", basicInfo);
        jsonData.put("user_mobile_contacts", userMobileContactsArray);
        jsonData.put("user_proof_materia", userProofMateriaArray);
        jsonData.put("user_loan_orders", userLoanOrdersArray);
        jsonData.put("card_info", cardInfoArray);
        jsonData.put("telecom_data", telecomData);
        return jsonData;
    }

    /**
     * 跑批审核用户是否通过的方法处理逻辑为 1.根据时间正序获取 status为0，数量为X条的申请记录 2.判断申请时间与当前时间的差是否大于x分钟，如果小于则排除掉，不做后续check 2.check
     * 指定用户user的基本信息是否完善。其中realname 需要在video 视频认证的时候加上认证标识 3.查询异步数据魔盒，是否有魔盒数据的反馈值，如果没有则为信息不全，建议审核失败
     * 4.如果通过了魔盒数据存在性的校验，那么再执行现金白卡的接口。并保存验证结果。
     */
    @Override
    public void checkUserApplyRecord() {
        int num = PropertyManager.getIntProperty("mgt.xjl.checkapply.size", 10);
        long delayTime = PropertyManager.getLongProperty("mgt.xjl.checkapply.delayTime", 300000L);
        List<XjlApplyRecord> xjlApplyRecords = xjlApplyRecordDao.selectXjlApplyRecordForCheck(num);
        if (xjlApplyRecords != null && xjlApplyRecords.size() != 0) {
            for (XjlApplyRecord xjlApplyRecord : xjlApplyRecords) {
                if (DateUtil.getCurrentTime() - xjlApplyRecord.getCreationDate().getTime() >= delayTime) {
                    try {
                        // 表示该记录已经达到了等待时间，开始执行检测
                        User user = userService.selectUserById(xjlApplyRecord.getUserId());
                        // 检测基本信息是否存在

                        String checkBaseInfoMsg = checkUserBaseInfo(user);
                        if (!checkBaseInfoMsg.equals("success")) {
                            xjlApplyRecord.setStatus(2);
                            xjlApplyRecord.setApplyMsg(checkBaseInfoMsg);
                            xjlApplyRecordDao.updateXjlApplyRecord(xjlApplyRecord);
                            continue;
                        }

                        // 检测三大魔盒数据是否存在
                        String checkMoHeMsg = checkMoHeReport(user, xjlApplyRecord.getCreationDate(),
                            DateUtil.calTimestamp(xjlApplyRecord.getCreationDate().getTime(), delayTime));
                        if (!checkMoHeMsg.equals("success")) {
                            xjlApplyRecord.setStatus(2);
                            xjlApplyRecord.setApplyMsg(checkMoHeMsg);
                            xjlApplyRecordDao.updateXjlApplyRecord(xjlApplyRecord);
                            continue;
                        }

                        // 通过了验证，再调用现金白卡接口
                        getVerifyResult(user, xjlApplyRecord.getApplyId());
                    } catch (Exception e) {
                        logger.error("Qiancheng Check User Error msg = ", e);
                    }
                }
            }
        }
    }

    private String getIdCardUrl(String photoKey, int type) {
        if (StringUtil.isEmpty(photoKey)) {
            return "";
        }

        String endpoint = PropertyManager.getProperty("jbb.aliyu.oss.bucket.endpoint");
        String accessId = PropertyManager.getProperty("jbb.aliyun.oss.accessKeyId");
        String accessKey = PropertyManager.getProperty("jbb.aliyun.oss.accessKeySecret");
        String bucket = "";
        if (type == 0) {
            bucket = PropertyManager.getProperty("jbb.aliyu.oss.bucket.jbb.mgt.user.photos");
        } else {
            bucket = PropertyManager.getProperty("jbb.aliyu.oss.bucket.jbb.video.and.pic");
        }
        String style = PropertyManager.getProperty("jbb.aliyu.oss.style");
        OSSClient client = new OSSClient(endpoint, accessId, accessKey);
        String urlString = "";
        try {
            client.getObjectMetadata(new GenericRequest(bucket, photoKey));
            GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucket, photoKey);
            request.setExpiration(new Date(DateUtil.getCurrentTime() + DateUtil.HOUR_MILLSECONDES * 5));
            // request.setProcess("style/resize");
            URL url = client.generatePresignedUrl(request);
            urlString = url.toString();
        } catch (Exception e) {
            throw new AliyunException("jbb.error.exception.aliyun.error");
        }
        return urlString;
    }

    private JSONObject getFundDataJson() {

        GjjRootBean gjjRootBean = new GjjRootBean();// 转换后的数据源
        /* is_name_match / isNameMatch	int	输入接口中姓名匹配状态(1=成功 2=模糊成 功 3=失败)	fund_data
        province_name / provinceName	string	省名称	fund_data
        city_name / cityName	string	城市名称	fund_data
        fund_udid / fundUdid	string	身份证或公积金账号	fund_data
        nation	string	民族	fund_data
        mobile	string	手机号 或 座机号	fund_data
        name	string	姓名	fund_data
        idCard	string	身份证	fund_data
        email	string	邮箱	fund_data
        liveAddr	string	居住地址	fund_data
        accountAddr	string	户口地址	fund_data
        accountProp	int	户口性质(1=城镇，2=农村，3=未知)	fund_data
        workerProp	string	职工性质（直接抓取，常见：职员、办事员职员、其他等）	fund_data
        birthday	string	生日(1990-01-01)	fund_data
        livePostcode	string	居住地邮编	fund_data
        degree	string	学历	fund_data
        marriageStatus	string	婚姻状态（直接抓取，常见：否、未婚、是、已婚等）	fund_data
        start_work_date / startWorkDate	string	开始工作时间	fund_data
        funds	array	公积金账号流水组	fund_data
        payment	object	缴费情况	fund_data
        funds参数
        参数	类型	字段说明	父节点字段
        lastTime	string	更新时间	funds
        unitName	string	公司名称	funds
        unitCode	float	公司机构码	funds
        endPayDay	string	缴纳结束日期	funds
        personalRatio	string	个人缴纳比例	funds
        companyRatio	float	公司缴纳比例	funds
        accountOpenTime	string	开卡日期	funds
        freezeAmount	string	冻结金额	funds
        fundNo	float	公积金账户	funds
        balance	string	余额	funds
        bankName	string	开卡银行	funds
        beginPayDay	float	缴纳起始日期	funds
        premiumBaseCalculationType	int	缴存基数计算类型 0：直接抓取 1：直接计算2：通过月缴额和单位或个人缴纳比例计算 3：通过月缴额和该城市缴纳比例计算4：无法获取月缴额	funds
        premiumMonth	float	月缴纳金额	funds
        premiumBase	float	缴纳基数	funds
        status	string	缴纳状态	funds
        records	array	流水数组	funds
        records参数
        参数	类型	字段说明	父节点字段
        personalPremium	float	个人缴纳	records
        income	float	收入	records
        summary	float	摘要	records
        transTime	string	交易时间	records
        balance	float	账户余额	records
        unitName	string	所属单位	records
        unitPremium	float	单位缴纳	records
        outcome	string	支出	records
        premiumBase	string	缴纳基数	records
        payment参数
        参数	类型	字段说明	父节点字段
        preCompanyLastPay	string	上司末次月缴额	payment
        is6Successive	string	是否连续6个月缴存	payment
        currCompanyPayMoneyCount	string	本司总缴存金额	payment
        currCompanyFirstPayDate	string	本司首次缴存日期	payment
        currCompanyPayMonthCount	string	本司总缴存月数（需要再对流水结合进行去重）	payment
        past2YearBackCount	string	近2年补缴次数	payment
        currCompanyFirstPay	string	本司首次月缴额	payment
        lastOutcome	string	末次月支出额	payment
        applyIntervalMonthCount	string	申请间隔月数	payment
        past2YearOutcomeCount	string	近2年支出总额	payment
        totalOutcome	string	累计支出金额	payment
        currCompanyLastBackDate	string	本司末次补缴日期	payment
        jobHopDateDiff	string	近期跳槽日期差（月数）	payment
        isBalance10TimesFundMonthMoney	string	是否公积金余额是个人月缴额的10倍	payment
        count6m	string	近6月缴存月数	payment
        recentAdjustDate	string	本司最近一次调额月	payment
        adjustInterval	string	本司调额间隔	payment
        currCompanyPayDiff	string	本司首尾缴额差值	payment
        totalPay	string	累计缴纳金额	payment
        totalExtract	string	累计提取金额	payment
        isCurrCompanyHasBack	string	本司是否存在补缴0否，1是	payment
        currCompanyLastPayDate	string	本司末次缴存日期	payment
        past2YearCompanyCount	string	近二年缴交单位数	payment
        past1YearBackCount	string	近1年补缴次数	payment
        lastMonthPayDate	string	上月缴存日期	payment
        currCompanyLastPay	string	本司末次月缴额	payment
        currCompanyBackTimes	string	本司补缴次数	payment
        past2YearPay	string	近两年缴纳金额	payment
        past1YearOutcomeRate	string	近1年支出频率	payment
        companyCount	string	缴存至今变更单位数	payment
        isCompanyHasSupplement	string	曾缴补补充公积金0否，1是	payment
        past1YearCompanyCount	string	近一年缴交单位数	payment
        past1YearPay	string	近一年缴纳金额	payment
        currCompanyBackMonthCount	string	本司补缴月数	payment
        count24m	string	近24月缴存月数	payment
        count12m	string	近12月缴存月数	payment
        intervalMonthCount	string	本司入职前间隔月数	payment
        currCompanySupplementPay	string	本司补充公积金末次月缴存额	payment
        preCompanyLastPayDate	string	上家末次缴存日期	payment
        jobHopIncomeDiff	string	近期跳槽缴额差值 -1表示没有上一个账号	payment
        past1YearOutcomeCount	string	近1年支出总额	payment
        uniqueMonthNum	string	总缴存月数(已进行去重)	payment
        isCurrCompanyHasSupplement	string	本司缴纳补充公积金，0否，1是	payment
        aging	string	账龄	payment
        successiveMonthCount	string	最大连续缴存数	payment
        isCompanyEverPay	string	本司曾有缴交 1为存在，0为不存在	payment*/
        // -- fund_data end
        JSONObject gjjJsonObject = new JSONObject();
        if (gjjRootBean != null && gjjRootBean.getCode() == 0) {
            gjjJsonObject.put("is_name_match", 1);// 无数据 设为自定义值
            gjjJsonObject.put("province_name", "");
            gjjJsonObject.put("city_name", "");
            String fund_uuid = "";
            gjjJsonObject.put("fund_uuid", gjjRootBean.getData().getTask_data().getBase_info().getCert_no());// 魔盒中事数组的形式
            gjjJsonObject.put("nation", "");
            gjjJsonObject.put("mobile", gjjRootBean.getData().getTask_data().getBase_info().getMobile());
            gjjJsonObject.put("name", gjjRootBean.getData().getTask_data().getBase_info().getName());
            gjjJsonObject.put("idCard", gjjRootBean.getData().getTask_data().getBase_info().getCert_no());
            gjjJsonObject.put("email", gjjRootBean.getData().getTask_data().getBase_info().getEmail());
            gjjJsonObject.put("liveAddr", gjjRootBean.getData().getTask_data().getBase_info().getHome_address());
            gjjJsonObject.put("accountAddr", gjjRootBean.getData().getTask_data().getBase_info().getRegisted());
            gjjJsonObject.put("accountProp", 3);// 户口性质 无数据
            gjjJsonObject.put("workerProp", "");// 职工性质
            gjjJsonObject.put("birthday", "");
            gjjJsonObject.put("livePostcode", gjjRootBean.getData().getTask_data().getBase_info().getPost_no());
            gjjJsonObject.put("degree", gjjRootBean.getData().getTask_data().getBase_info().getEducation());
            gjjJsonObject.put("marriageStatus",
                gjjRootBean.getData().getTask_data().getBase_info().getMarital_status());
            gjjJsonObject.put("start_work_date ", "");

            net.sf.json.JSONArray fundsArray = new net.sf.json.JSONArray();
            List<Pay_info> pay_infos = gjjRootBean.getData().getTask_data().getPay_info();
            List<Bill_record> bill_records = gjjRootBean.getData().getTask_data().getBill_record();
            if (pay_infos != null && pay_infos.size() != 0) {
                for (Pay_info pay_info : pay_infos) {
                    JSONObject fundsObject = new JSONObject();
                    fundsObject.put("lastTime", DateUtil.getSystemTimeString());
                    fundsObject.put("unitName", pay_info.getCorp_name());
                    /*fundsObject.put("unitCode", "");
                    fundsObject.put("endPayDay", "");
                    fundsObject.put("personalRatio", "");
                    fundsObject.put("companyRatio", "");
                    fundsObject.put("accountOpenTime", "");
                    fundsObject.put("freezeAmount", "");*/
                    fundsObject.put("fundNo", pay_info.getGjj_no());
                    /*fundsObject.put("balance", "");
                    fundsObject.put("bankName", "");
                    fundsObject.put("beginPayDay", "");
                    fundsObject.put("premiumBaseCalculationType", "");*/
                    if (pay_info.getBalance() != null && !pay_info.getBalance().equals("")) {
                        fundsObject.put("premiumMonth", Float.parseFloat(pay_info.getBalance()));
                    }
                    // fundsObject.put("premiumBase", pay_info.getPay_status_desc());
                    fundsObject.put("status", pay_info.getPay_status_desc());
                    net.sf.json.JSONArray recordsArray = new net.sf.json.JSONArray();
                    if (bill_records != null && bill_records.size() != 0) {
                        for (Bill_record bill_record : bill_records) {
                            if (bill_record.getCorp_name().equals(pay_info.getCorp_name())) {
                                JSONObject recordsObject = new JSONObject();
                                // 设计到金额的 要从分转换为元
                                recordsObject.put("personalPremium", bill_record.getCust_income());
                                recordsObject.put("income", bill_record.getIncome() / 100);
                                recordsObject.put("summary", bill_record.getDesc());
                                recordsObject.put("transTime", bill_record.getDeal_time());
                                recordsObject.put("balance", bill_record.getBalance() / 100);
                                recordsObject.put("unitName", bill_record.getCorp_name());
                                recordsObject.put("unitPremium", bill_record.getCorp_income() / 100);
                                recordsObject.put("outcome", bill_record.getOutcome() / 100);
                                if (gjjRootBean.getData().getTask_data().getBase_info().getCorp_name()
                                    .equals(bill_record.getCorp_name())) {
                                    recordsObject.put("premiumBase",
                                        gjjRootBean.getData().getTask_data().getBase_info());// 缴纳基数，魔盒中将此基数绑定在baseinfo中，此处为一个公积金账号数组
                                                                                             // 比较魔盒中的名字再传入
                                }
                                recordsArray.add(recordsObject);
                            }
                        }
                    }
                    fundsObject.put("records", recordsArray);
                }
            }
            gjjJsonObject.put("funds ", fundsArray);
        }
        return gjjJsonObject;
    }

    private JSONObject getSoinData() {

        SheBaoRootBean sheBaoRootBean = new SheBaoRootBean();
        /*data_source	string	数据来源	soin_data
        update_time	string	数据获取时间	soin_data
        user_company	string	用户所属单位	soin_data
        soins_area	string	社保开户区县	soin_data
        soins_prov	string	社保开户省份	soin_data
        soins_city	string	社保开户城市	soin_data
        user_idcard	string	身份证号	soin_data
        soins_org	string	社保开户机构	soin_data
        soins_num	string	社保账号	soin_data
        open_time	string	开户日期	soin_data
        soins_status	string	社保状态	soin_data
        details	list	交易记录	soin_data
        details参数
        参数	类型	字段说明	父节点字段
        total_pay	float	缴费总计	details
        pay_cardi	float	缴费基数	details
        pson_pay	float	缴纳基数	details
        start_time	datetime	缴费开始时间	details
        end_time	datetime	缴费结束时间	details
        soins_type	string	社保种类	details
        soins_nature	string	缴费性质	details
        comp_pay	string	单位缴费	details
        pay_months	int	缴费月数	details
        soins_company	string	单位名称	details*/
        JSONObject soinDataJsonObject = new JSONObject();
        soinDataJsonObject.put("data_source", "");// 数据来源 无数据
        soinDataJsonObject.put("update_time", DateUtil.getSystemTimeString());// 数据获取时间
        soinDataJsonObject.put("user_company",
            sheBaoRootBean.getData().getTask_data().getUser_info().getCompany_name());// 用户所属单位
        soinDataJsonObject.put("soins_area", "");// 社保开户区县
        soinDataJsonObject.put("soins_prov", "");// 社保开户省份
        soinDataJsonObject.put("soins_city", "");// 社保开户城市
        soinDataJsonObject.put("user_idcard",
            sheBaoRootBean.getData().getTask_data().getUser_info().getCertificate_number());// 身份证号
        soinDataJsonObject.put("soins_org", "");// 社保开户机构
        soinDataJsonObject.put("soins_num", "");// 社保账号
        soinDataJsonObject.put("open_time",
            sheBaoRootBean.getData().getTask_data().getEndowment_overview().getBegin_date());// 开户日期
        soinDataJsonObject.put("soins_status", sheBaoRootBean.getData().getTask_data().getUser_info().getType());// 社保状态

        net.sf.json.JSONArray detailsArray = new net.sf.json.JSONArray();
        // 拼接养老 失业 工伤 生育 医疗保险的数据

        // 养老
        List<Endowment_insurance> endowment_insurances
            = sheBaoRootBean.getData().getTask_data().getEndowment_insurance();
        // 医疗
        List<Medical_insurance> medical_insurances = sheBaoRootBean.getData().getTask_data().getMedical_insurance();
        // 失业
        List<Unemployment_insurance> unemployment_insurances
            = sheBaoRootBean.getData().getTask_data().getUnemployment_insurance();
        // 工伤保险缴费记录
        List<Accident_insurance> accident_insurances = sheBaoRootBean.getData().getTask_data().getAccident_insurance();
        // 生育
        List<Maternity_insurance> maternity_insurances
            = sheBaoRootBean.getData().getTask_data().getMaternity_insurance();

        if (endowment_insurances != null && endowment_insurances.size() != 0) {
            for (Endowment_insurance endowment_insurance : endowment_insurances) {
                JSONObject detailsJsonObject = new JSONObject();
                detailsJsonObject.put("total_pay", ((double)(endowment_insurance.getMonthly_company_income()
                    + endowment_insurance.getMonthly_personal_income())) / 100);// 缴费总计
                detailsJsonObject.put("pay_cardi", endowment_insurance.getBase_number());// 缴费基数
                detailsJsonObject.put("pson_pay", ((double)endowment_insurance.getMonthly_personal_income()) / 100);// 缴纳基数
                detailsJsonObject.put("start_time", endowment_insurance.getLast_pay_date());// 缴费开始时间
                detailsJsonObject.put("end_time", endowment_insurance.getLast_pay_date());// 缴费结束时间
                detailsJsonObject.put("soins_type", "养老保险");// 社保种类
                detailsJsonObject.put("soins_nature", "");// 缴费性质
                detailsJsonObject.put("comp_pay", ((double)endowment_insurance.getMonthly_company_income()) / 100);// 单位缴费
                detailsJsonObject.put("pay_months", endowment_insurance.getMonth_count());// 缴费月数
                detailsJsonObject.put("soins_company", endowment_insurance.getCompany_name());// 单位名称
                detailsArray.add(detailsJsonObject);
            }
        }

        if (medical_insurances != null && medical_insurances.size() != 0) {
            for (Medical_insurance medical_insurance : medical_insurances) {
                JSONObject detailsJsonObject = new JSONObject();
                detailsJsonObject.put("total_pay", ((double)(medical_insurance.getMonthly_company_income()
                    + medical_insurance.getMonthly_personal_income())) / 100);// 缴费总计
                detailsJsonObject.put("pay_cardi", medical_insurance.getBase_number());// 缴费基数
                detailsJsonObject.put("pson_pay", ((double)medical_insurance.getMonthly_personal_income()) / 100);// 缴纳基数
                detailsJsonObject.put("start_time", medical_insurance.getLast_pay_date());// 缴费开始时间
                detailsJsonObject.put("end_time", medical_insurance.getLast_pay_date());// 缴费结束时间
                detailsJsonObject.put("soins_type", "医疗保险");// 社保种类
                detailsJsonObject.put("soins_nature", "");// 缴费性质
                detailsJsonObject.put("comp_pay", ((double)medical_insurance.getMonthly_company_income()) / 100);// 单位缴费
                detailsJsonObject.put("pay_months", medical_insurance.getMonth_count());// 缴费月数
                detailsJsonObject.put("soins_company", medical_insurance.getCompany_name());// 单位名称
                detailsArray.add(detailsJsonObject);
            }
        }

        if (unemployment_insurances != null && unemployment_insurances.size() != 0) {
            for (Unemployment_insurance unemployment_insurance : unemployment_insurances) {
                JSONObject detailsJsonObject = new JSONObject();
                detailsJsonObject.put("total_pay", ((double)(unemployment_insurance.getMonthly_company_income()
                    + unemployment_insurance.getMonthly_personal_income())) / 100);// 缴费总计
                detailsJsonObject.put("pay_cardi", unemployment_insurance.getBase_number());// 缴费基数
                detailsJsonObject.put("pson_pay", ((double)unemployment_insurance.getMonthly_personal_income()) / 100);// 缴纳基数
                detailsJsonObject.put("start_time", unemployment_insurance.getLast_pay_date());// 缴费开始时间
                detailsJsonObject.put("end_time", unemployment_insurance.getLast_pay_date());// 缴费结束时间
                detailsJsonObject.put("soins_type", "失业保险");// 社保种类
                detailsJsonObject.put("soins_nature", "");// 缴费性质
                detailsJsonObject.put("comp_pay", ((double)unemployment_insurance.getMonthly_company_income()) / 100);// 单位缴费
                detailsJsonObject.put("pay_months", unemployment_insurance.getMonth_count());// 缴费月数
                detailsJsonObject.put("soins_company", unemployment_insurance.getCompany_name());// 单位名称
                detailsArray.add(detailsJsonObject);
            }
        }

        if (accident_insurances != null && accident_insurances.size() != 0) {
            for (Accident_insurance accident_insurance : accident_insurances) {
                JSONObject detailsJsonObject = new JSONObject();
                detailsJsonObject.put("total_pay", ((double)(accident_insurance.getMonthly_company_income()
                    + accident_insurance.getMonthly_personal_income())) / 100);// 缴费总计
                detailsJsonObject.put("pay_cardi", accident_insurance.getBase_number());// 缴费基数
                detailsJsonObject.put("pson_pay", ((double)accident_insurance.getMonthly_personal_income()) / 100);// 缴纳基数
                detailsJsonObject.put("start_time", accident_insurance.getLast_pay_date());// 缴费开始时间
                detailsJsonObject.put("end_time", accident_insurance.getLast_pay_date());// 缴费结束时间
                detailsJsonObject.put("soins_type", "工伤保险");// 社保种类
                detailsJsonObject.put("soins_nature", "");// 缴费性质
                detailsJsonObject.put("comp_pay", ((double)accident_insurance.getMonthly_company_income()) / 100);// 单位缴费
                detailsJsonObject.put("pay_months", accident_insurance.getMonth_count());// 缴费月数
                detailsJsonObject.put("soins_company", accident_insurance.getCompany_name());// 单位名称
                detailsArray.add(detailsJsonObject);
            }
        }

        if (maternity_insurances != null && maternity_insurances.size() != 0) {
            for (Maternity_insurance maternity_insurance : maternity_insurances) {
                JSONObject detailsJsonObject = new JSONObject();
                detailsJsonObject.put("total_pay", ((double)(maternity_insurance.getMonthly_company_income()
                    + maternity_insurance.getMonthly_personal_income())) / 100);// 缴费总计
                detailsJsonObject.put("pay_cardi", maternity_insurance.getBase_number());// 缴费基数
                detailsJsonObject.put("pson_pay", ((double)maternity_insurance.getMonthly_personal_income()) / 100);// 缴纳基数
                detailsJsonObject.put("start_time", maternity_insurance.getLast_pay_date());// 缴费开始时间
                detailsJsonObject.put("end_time", maternity_insurance.getLast_pay_date());// 缴费结束时间
                detailsJsonObject.put("soins_type", "生育保险");// 社保种类
                detailsJsonObject.put("soins_nature", "");// 缴费性质
                detailsJsonObject.put("comp_pay", ((double)maternity_insurance.getMonthly_company_income()) / 100);// 单位缴费
                detailsJsonObject.put("pay_months", maternity_insurance.getMonth_count());// 缴费月数
                detailsJsonObject.put("soins_company", maternity_insurance.getCompany_name());// 单位名称
                detailsArray.add(detailsJsonObject);
            }
        }
        soinDataJsonObject.put("details", detailsArray);// 交易记录 包括五种社保
        return soinDataJsonObject;
    }

    private ClubReport getOssReport(int userId, String channelType, String channelCode, Timestamp startDate,
        Timestamp endDate) {
        ClubReport report = clubService.getReport(userId, channelType, channelCode, startDate, endDate);
        return report;
    }

    /*private boolean checkUserExistInOrg(int userId) {
        return organizationUserService.checkExist(this.account.getOrgId(), userId);
    }*/

    /**
     * 通过魔方报告的json 拼接聚信力格式的json
     * 
     * @return
     */
    private JSONObject getTelecomDataJson(User user, Timestamp startDate, Timestamp endDate) {

        ClubReport clubReport = clubService.getReport(user.getUserId(), "YYS", "100000", startDate, endDate);
        if (clubReport == null) {
            return new JSONObject();
        }

        String data = clubReport.getData();
        String report = clubReport.getReport();

        YysRootBean yysRootBean = JSON.parseObject(data, YysRootBean.class);// 数据基类
        YysReportRootBean yysReportRootBean = JSON.parseObject(report, YysReportRootBean.class);// 报表基类

        if (yysRootBean == null || yysReportRootBean == null) {
            return new JSONObject();
        }

        JSONObject telecomDataJson = new JSONObject();// telecomData公

        // application start
        JSONArray application_check = new JSONArray();

        JSONObject applicationChecJsonkIdCard = new JSONObject();
        JSONObject applicationCheckPointIdCard = new JSONObject();
        JSONObject applicationCheckUserName = new JSONObject();
        if(yysReportRootBean.getUser_info()!=null){
            applicationCheckPointIdCard.put("gender", yysReportRootBean.getUser_info().getGender());// 性别
            applicationCheckPointIdCard.put("age", yysReportRootBean.getUser_info().getAge());// 年龄
            applicationCheckPointIdCard.put("province", yysReportRootBean.getUser_info().getHometown());// 省份
            applicationCheckPointIdCard.put("city", yysReportRootBean.getUser_info().getHometown());// 城市
            applicationCheckPointIdCard.put("region", yysReportRootBean.getUser_info().getHometown());// 区县
            applicationCheckPointIdCard.put("key_value", yysReportRootBean.getUser_info().getIdentity_code());// 身份证号
            applicationChecJsonkIdCard.put("app_point", "id_card");
            applicationChecJsonkIdCard.put("check_points", applicationCheckPointIdCard);


            JSONObject applicationCheckPointUserName = new JSONObject();
            applicationCheckPointUserName.put("key_value", yysReportRootBean.getUser_info().getReal_name());
            applicationCheckUserName.put("app_point", "user_name");
            applicationCheckUserName.put("check_points", applicationCheckPointUserName);
        }


        JSONObject applicationCheckCellPhone = new JSONObject();
        if(yysReportRootBean.getMobile_info()!=null&&yysReportRootBean.getInfo_match()!=null){
            JSONObject applicationCheckPointCellPhone = new JSONObject();
            applicationCheckPointCellPhone.put("key_value", yysReportRootBean.getMobile_info().getUser_mobile());// 电话号码
            applicationCheckPointCellPhone.put("website", yysReportRootBean.getMobile_info().getMobile_carrier());// 移动运营商
            if (yysReportRootBean.getInfo_check().getIs_identity_code_reliable() != null
                && yysReportRootBean.getInfo_check().getIs_identity_code_reliable().equals("是")) {
                applicationCheckPointCellPhone.put("reliability", "实名认证");// 实名认证
            } else {
                applicationCheckPointCellPhone.put("reliability", "非实名认证");// 非实名认证
            }

            applicationCheckPointCellPhone.put("reg_time", yysReportRootBean.getMobile_info().getMobile_net_time());// 注册时间
            if (yysReportRootBean.getInfo_match().getReal_name_check_yys() != null
                && yysReportRootBean.getInfo_match().getReal_name_check_yys().equals("完全匹配")) {
                applicationCheckPointCellPhone.put("check_name", "用户姓名与运营商提供的姓名[*]匹配成功");// 姓名检查
            } else {
                applicationCheckPointCellPhone.put("check_name", "用户姓名与运营商提供的姓名[*]匹配失败");// 姓名检查
            }

            if (yysReportRootBean.getInfo_match().getIdentity_code_check_yys() != null
                && yysReportRootBean.getInfo_match().getIdentity_code_check_yys().equals("完全匹配")) {
                applicationCheckPointCellPhone.put("check_idcard", "用户身份证号与运营商提供的身份证号码[*]匹配成功");// 身份证号检查
            } else {
                applicationCheckPointCellPhone.put("check_idcard", "用户身份证号与运营商提供的身份证号码[*]匹配失败");// 身份证号检查
            }

            applicationCheckPointCellPhone.put("check_ebusiness", "");// 电商使用号码检查
            applicationCheckCellPhone.put("app_point", "cell_phone");
            applicationCheckCellPhone.put("check_points", applicationCheckPointCellPhone);
        }


        /*
        JSONObject applicationCheckHomeAddr = new JSONObject();
        JSONObject applicationCheckPointHomeAddr = new JSONObject();
        applicationCheckPointHomeAddr.put("key_value", "");
        applicationCheckPointHomeAddr.put("check_addr", "");// 地址检查
        applicationCheckPointHomeAddr.put("check_ebusiness", "");
        applicationCheckHomeAddr.put("app_point", "home_addr");
        applicationCheckHomeAddr.put("check_points", applicationCheckPointHomeAddr);
        
        JSONObject applicationCheckHomePhone = new JSONObject();
        JSONObject applicationCheckPointHomePhone = new JSONObject();
        applicationCheckPointHomePhone.put("key_value", "");
        applicationCheckPointHomePhone.put("check_mobile", "");// 运营商联系号码检查
        applicationCheckHomePhone.put("app_point", "home_phone");
        applicationCheckHomePhone.put("check_points", applicationCheckPointHomePhone);*/

        application_check.add(applicationChecJsonkIdCard);
        application_check.add(applicationCheckUserName);
        application_check.add(applicationCheckCellPhone);
        /* application_check.add(applicationCheckHomeAddr);
        application_check.add(applicationCheckHomePhone);*/
        // application end

        // user_info_check start
        JSONObject user_info_check = new JSONObject();
        // user_info_check end

        // behavior_check start
        JSONArray behavior_check = new JSONArray();

        JSONObject behaviorPhoneUsedTime = new JSONObject();
        if(yysReportRootBean.getMobile_info()!=null){
            String net_time = "";
            if(yysRootBean.getData()!=null){
                if(yysRootBean.getData().getTask_data()!=null){
                    if(yysRootBean.getData().getTask_data().getAccount_info()!=null){
                        if(yysRootBean.getData().getTask_data().getAccount_info().getNet_time()!=null){
                            net_time = yysRootBean.getData().getTask_data().getAccount_info().getNet_time();// 入网时间YYYY-MM-DD
                        }
                    }
                }
            }
            String[] netTimeArray = getPhoneUsedTime(net_time, yysReportRootBean.getMobile_info().getUser_mobile());
            behaviorPhoneUsedTime.put("check_point", "phone_used_time");
            behaviorPhoneUsedTime.put("score", Integer.valueOf(netTimeArray[3]));// 0:无数据, 1:通过, 2:不通过
            behaviorPhoneUsedTime.put("check_point_cn", "号码使用时间");// 号码使用时间
            behaviorPhoneUsedTime.put("result", netTimeArray[1]);// 长期使用(24个月以上，包含24个月)
            behaviorPhoneUsedTime.put("advidence", netTimeArray[2]);// 根据号码[18850168732]运营商提供的认证时间,推算该号码使用63个月

        }

        // 由于静默数据项不匹配，暂时都给空
        JSONObject behaviorPhoneSilent = new JSONObject();
        behaviorPhoneSilent.put("check_point", "phone_silent");
        behaviorPhoneSilent.put("score", 0);
        behaviorPhoneSilent.put("check_point_cn", "手机静默情况（连续24小时（精确到秒）内无通话记录计为静默一天）");// 手机静默情况(连续24小时内无通话记录计为静默一天)
        behaviorPhoneSilent.put("result", "无数据");// 157天内有23天无通话记录
        behaviorPhoneSilent.put("advidence", "运营商未提供时间数据");// 根据运营商详单数据，连续三天以上无通话记录0次

        JSONObject behaviorContactEachOther = new JSONObject();
        if(yysReportRootBean.getBehavior_analysis()!=null&&yysReportRootBean.getAll_contact_stats()!=null){
            String[] behaviorArray
                = getBehaviorContact(yysReportRootBean.getBehavior_analysis().getMutual_number_analysis_6month(),
                yysReportRootBean.getAll_contact_stats().getContact_count_mutual_6month());
            behaviorContactEachOther.put("check_point", "contact_each_other");
            behaviorContactEachOther.put("score", behaviorArray[0]);
            behaviorContactEachOther.put("check_point_cn", "互通过电话的号码数量");// 互通过电话的号码数量
            behaviorContactEachOther.put("result", behaviorArray[1]);// 数量正常(10-100)
            behaviorContactEachOther.put("advidence", behaviorArray[2]);// 互通过电话的号码有46个，比例为17.49%
        }

        JSONObject behaviorContactMacao = new JSONObject();
        if(yysReportRootBean.getBehavior_analysis()!=null){
            String[] macaoArray = getMacaoArray(yysReportRootBean.getBehavior_analysis().getCall_macau_analysis_6month());
            behaviorContactMacao.put("check_point", "contact_macao");
            behaviorContactMacao.put("score", macaoArray[0]);
            behaviorContactMacao.put("check_point_cn", "澳门电话通话情况");// 澳门电话通话情况
            behaviorContactMacao.put("result", macaoArray[1]);// 无通话记录
            behaviorContactMacao.put("advidence", macaoArray[2]);// 未发现澳门地区电话通话记录
        }

        JSONObject behaviorContact110 = new JSONObject();
        if(yysReportRootBean.getBehavior_analysis()!=null){
            String[] array110 = get110Array(yysReportRootBean.getBehavior_analysis().getCall_110_analysis_6month());
            behaviorContact110.put("check_point", "contact_110");
            behaviorContact110.put("score", array110[0]);
            behaviorContact110.put("check_point_cn", "110话通话情况");// 110话通话情况
            behaviorContact110.put("result", array110[1]);// 无通话记录
            behaviorContact110.put("advidence", array110[2]);// 未发现与110电话通话记录
        }


        JSONObject behaviorContact120 = new JSONObject();
        if(yysReportRootBean.getBehavior_analysis()!=null){
            String[] array120 = get120Array(yysReportRootBean.getBehavior_analysis().getCall_120_analysis_6month());
            behaviorContact120.put("check_point", "contact_120");
            behaviorContact120.put("score", array120[0]);
            behaviorContact120.put("check_point_cn", "120话通话情况");// 120话通话情况
            behaviorContact120.put("result", array120[1]);// 无通话记录
            behaviorContact120.put("advidence", array120[2]);// 未发现与120电话通话记录
        }


        JSONObject behaviorContactLawyer = new JSONObject();
        if(yysReportRootBean.getBehavior_analysis()!=null){
            String[] lawyerArray
                = getLawyerArray(yysReportRootBean.getBehavior_analysis().getCall_lawyer_analysis_6month());
            behaviorContactLawyer.put("check_point", "contact_lawyer");
            behaviorContactLawyer.put("score", lawyerArray[0]);
            behaviorContactLawyer.put("check_point_cn", "律师号码通话情况");// 律师号码通话情况
            behaviorContactLawyer.put("result", lawyerArray[1]);// 无通话记录
            behaviorContactLawyer.put("advidence", lawyerArray[2]);// 未发现与律师电话通话记录
        }

        JSONObject behaviorcontactCourt = new JSONObject();
        if(yysReportRootBean.getBehavior_analysis()!=null){
            String[] courtArray = getCourtArray(yysReportRootBean.getBehavior_analysis().getCall_court_analysis_6month());
            behaviorcontactCourt.put("check_point", "contact_court");
            behaviorcontactCourt.put("score", courtArray[0]);
            behaviorcontactCourt.put("check_point_cn", "");// 法院号码通话情况
            behaviorcontactCourt.put("result", courtArray[1]);// 无通话记录
            behaviorcontactCourt.put("advidence", courtArray[2]);// 未发现与法院电话通话记录
        }

        JSONObject behaviorContactNight = new JSONObject();
        behaviorContactNight.put("check_point", "contact_night");
        behaviorContactNight.put("score", 0);
        behaviorContactNight.put("check_point_cn", "夜间活动情况(23点-6点)");// 夜间活动情况(23点-6点)
        behaviorContactNight.put("result", "无数据");// 很少夜间活动(夜间通话比例低于20%)
        behaviorContactNight.put("advidence", "未找到晚上活跃的相关记录 / 未提供运营商数据");// 晚间活跃频率占全天的7.0%

        JSONObject behaviorContactLoan = new JSONObject();
        if(yysReportRootBean.getBehavior_analysis()!=null){
            String[] loanArray = getLoanArray(yysReportRootBean.getBehavior_analysis().getLoan_contact_analysis_6month());
            behaviorContactLoan.put("check_point", "contact_loan");
            behaviorContactLoan.put("score", loanArray[0]);
            behaviorContactLoan.put("check_point_cn", "贷款类号码联系情况");// 贷款类号码联系情况
            behaviorContactLoan.put("result", loanArray[1]);// 偶尔被联系(联系次数在5次以上，包含5次，且主动呼叫占比 20%-50%之间，包含20%)
            behaviorContactLoan.put("advidence", loanArray[2]);// 联系列表:[中信信用卡]主叫2次共6.67分钟，被叫0次共0.0分钟;[宜人贷公司]主叫1次共0.62分钟，被叫0次共0.0分钟;[平安易贷]主叫0次共0.0分钟，被叫2次共1.67分钟
        }

        JSONObject behaviorContactBank = new JSONObject();
        behaviorContactBank.put("check_point", "contact_bank");
        behaviorContactBank.put("score", 0);
        behaviorContactBank.put("check_point_cn", "银行类号码联系情况");// 银行类号码联系情况
        behaviorContactBank.put("result", "无数据");// 偶尔被联系(联系次数在5次以上，包含5次，且主动呼叫占比 20%-50%之间，包含20%)
        behaviorContactBank.put("advidence", "未提供运营商数据");// 联系列表:[交通银行客服电话]主叫3次共0.87分钟，被叫0次共0.0分钟;[平安银行]主叫0次共0.0分钟，被叫1次共0.58分钟;[民生银行客服电话]主叫0次共0.0分钟，被叫1次共0.47分钟

        behavior_check.add(behaviorPhoneUsedTime);
        behavior_check.add(behaviorPhoneSilent);
        behavior_check.add(behaviorContactEachOther);
        behavior_check.add(behaviorContactMacao);
        behavior_check.add(behaviorContact110);
        behavior_check.add(behaviorContact120);
        behavior_check.add(behaviorContactLawyer);
        behavior_check.add(behaviorcontactCourt);
        behavior_check.add(behaviorContactNight);
        behavior_check.add(behaviorContactLoan);
        behavior_check.add(behaviorContactBank);
        // behavior_check end

        // cell_behavior start 数据直接从data数据中筛选 不从报表中获取
        JSONArray cell_behavior = new JSONArray();
        JSONObject cellBehaviorJson = new JSONObject();

        JSONArray cellBehaviorMonthArray = new JSONArray();

        List<Call_info> call_infos = new ArrayList<Call_info>();
        if(yysRootBean.getData()!=null){
            if(yysRootBean.getData().getTask_data()!=null){
                if( yysRootBean.getData().getTask_data().getCall_info()!=null){
                    call_infos = yysRootBean.getData().getTask_data().getCall_info();
                }
            }
        }

        if (call_infos != null && call_infos.size() != 0) {
            for (Call_info call_info : call_infos) {
                JSONObject cellBehaviorMonthJson = new JSONObject();
                cellBehaviorMonthJson.put("cell_operator", yysReportRootBean.getMobile_info().getMobile_carrier());// 运营商
                cellBehaviorMonthJson.put("cell_operator_zh", yysReportRootBean.getMobile_info().getMobile_carrier());// 运营商（中文）
                cellBehaviorMonthJson.put("cell_phone_num", yysReportRootBean.getMobile_info().getUser_mobile());// 号码
                cellBehaviorMonthJson.put("cell_loc", yysReportRootBean.getMobile_info().getMobile_net_addr());// 归属地
                cellBehaviorMonthJson.put("cell_mth", call_info.getCall_cycle());// 月份
                cellBehaviorMonthJson.put("call_cnt", call_info.getTotal_call_count());// 呼叫次数
                List<Call_record> call_records = call_info.getCall_record();
                int zjcount = 0;
                int bjcount = 0;
                int zjtime = 0;
                int bjtime = 0;
                int countmoney = 0;
                if (call_records != null && call_records.size() != 0) {
                    for (Call_record call_record : call_records) {
                        if (call_record.getCall_type_name().equals("主叫")) {
                            zjcount += 1;
                            if (call_record.getCall_time() != null && !call_record.getCall_time().equals("")) {
                                zjtime += Integer.parseInt(call_record.getCall_time());
                            }
                        }
                        if (call_record.getCall_type_name().equals("被叫")) {
                            bjcount += 1;
                            if (call_record.getCall_time() != null && !call_record.getCall_time().equals("")) {
                                bjtime += Integer.parseInt(call_record.getCall_time());
                            }
                        }
                        if (call_record.getCall_cost() != null && !call_record.getCall_cost().equals("")) {
                            countmoney += Integer.parseInt(call_record.getCall_cost());
                        }
                    }
                }
                cellBehaviorMonthJson.put("call_out_cnt", zjcount);// 主叫次数
                cellBehaviorMonthJson.put("call_out_time", zjtime);// 主叫时间
                cellBehaviorMonthJson.put("call_in_cnt", bjcount);// 被叫次数
                cellBehaviorMonthJson.put("call_in_time", bjtime);// 被叫时间

                int countSms = 0;
                List<Sms_info> sms_infos = new ArrayList<Sms_info>();
                if(yysRootBean.getData()!=null){
                    if(yysRootBean.getData().getTask_data()!=null){
                        if( yysRootBean.getData().getTask_data().getSms_info()!=null){
                            sms_infos =  yysRootBean.getData().getTask_data().getSms_info();
                        }
                    }
                }

                if (sms_infos != null && sms_infos.size() != 0) {
                    for (Sms_info sms_info : sms_infos) {
                        if (sms_info.getMsg_cycle().equals(call_info.getCall_cycle())) {
                            if (sms_info.getTotal_msg_count() != null && !sms_info.getTotal_msg_count().equals("")) {
                                countSms = Integer.parseInt(sms_info.getTotal_msg_count());
                                break;
                            }
                        }
                    }
                }
                cellBehaviorMonthJson.put("net_flow", 0);// 流量
                cellBehaviorMonthJson.put("sms_cnt", countSms);// 短信数目
                cellBehaviorMonthJson.put("total_amount", (double)countmoney / 100);// 话费消费
                cellBehaviorMonthArray.add(cellBehaviorMonthJson);
            }
        }
        cellBehaviorJson.put("phone_num", yysReportRootBean.getMobile_info().getUser_mobile());// 电话号码
        cellBehaviorJson.put("behavior", cellBehaviorMonthArray);
        cell_behavior.add(cellBehaviorJson);

        // cell_behavior end

        // contact_region start 联系人区域汇总
        JSONArray contact_region = new JSONArray();
        List<Contact_area_stats_per_city> contact_area_stats_per_cities
            = yysReportRootBean.getContact_area_stats_per_city();
        if (contact_area_stats_per_cities != null && contact_area_stats_per_cities.size() != 0) {
            for (Contact_area_stats_per_city contact_area_stats_per_city : contact_area_stats_per_cities) {
                JSONObject contactRegionJson = new JSONObject();
                contactRegionJson.put("region_loc", contact_area_stats_per_city.getContact_area_city());
                contactRegionJson.put("region_uniq_num_cnt", contact_area_stats_per_city.getContact_count_6month());// 号码数量
                contactRegionJson.put("region_call_in_cnt", contact_area_stats_per_city.getCall_count_passive_6month());// 电话呼入次数
                contactRegionJson.put("region_call_out_cnt", contact_area_stats_per_city.getCall_count_active_6month());// 电话呼出次数
                contactRegionJson.put("region_call_in_time", contact_area_stats_per_city.getCall_time_passive_6month());// 电话呼入时间
                contactRegionJson.put("region_call_out_time", contact_area_stats_per_city.getCall_time_active_6month());// 电话呼出时间
                int avgCallInTime = 0;
                int avgCallOutTime = 0;
                double preCallInCount = 0;
                double preCallOutCount = 0;
                double preCallInTime = 0;
                double preCallOutTime = 0;

                if (contact_area_stats_per_city.getCall_count_passive_6month() != null
                    && !contact_area_stats_per_city.getCall_count_passive_6month().equals("")
                    && !contact_area_stats_per_city.getCall_count_passive_6month().equals("0")
                    && contact_area_stats_per_city.getCall_time_passive_6month() != null
                    && !contact_area_stats_per_city.getCall_time_passive_6month().equals("")
                    && !contact_area_stats_per_city.getCall_time_passive_6month().equals("0")) {
                    avgCallInTime = Integer.parseInt(contact_area_stats_per_city.getCall_time_passive_6month())
                        / Integer.parseInt(contact_area_stats_per_city.getCall_count_passive_6month());
                }

                if (contact_area_stats_per_city.getCall_time_active_6month() != null
                    && !contact_area_stats_per_city.getCall_time_active_6month().equals("")
                    && !contact_area_stats_per_city.getCall_time_active_6month().equals("0")
                    && contact_area_stats_per_city.getCall_count_active_6month() != null
                    && !contact_area_stats_per_city.getCall_count_active_6month().equals("")
                    && !contact_area_stats_per_city.getCall_count_active_6month().equals("0")) {
                    avgCallOutTime = Integer.parseInt(contact_area_stats_per_city.getCall_time_active_6month())
                        / Integer.parseInt(contact_area_stats_per_city.getCall_count_active_6month());
                }

                if (contact_area_stats_per_city.getCall_count_passive_6month() != null
                    && !contact_area_stats_per_city.getCall_count_passive_6month().equals("")
                    && contact_area_stats_per_city.getCall_count_active_6month() != null
                    && !contact_area_stats_per_city.getCall_count_active_6month().equals("")) {
                    if ((Integer.parseInt(contact_area_stats_per_city.getCall_count_passive_6month())
                        + Integer.parseInt(contact_area_stats_per_city.getCall_count_active_6month())) != 0) {
                        preCallInCount
                            = (double)Integer.parseInt(contact_area_stats_per_city.getCall_count_passive_6month())
                                / (Integer.parseInt(contact_area_stats_per_city.getCall_count_passive_6month())
                                    + Integer.parseInt(contact_area_stats_per_city.getCall_count_active_6month()));
                    }
                }

                if (contact_area_stats_per_city.getCall_count_passive_6month() != null
                    && !contact_area_stats_per_city.getCall_count_passive_6month().equals("")
                    && contact_area_stats_per_city.getCall_count_active_6month() != null
                    && !contact_area_stats_per_city.getCall_count_active_6month().equals("")) {
                    if ((Integer.parseInt(contact_area_stats_per_city.getCall_count_passive_6month())
                        + Integer.parseInt(contact_area_stats_per_city.getCall_count_active_6month())) != 0) {
                        preCallOutCount
                            = (double)Integer.parseInt(contact_area_stats_per_city.getCall_count_active_6month())
                                / (Integer.parseInt(contact_area_stats_per_city.getCall_count_passive_6month())
                                    + Integer.parseInt(contact_area_stats_per_city.getCall_count_active_6month()));
                    }
                }

                if (contact_area_stats_per_city.getCall_time_passive_6month() != null
                    && !contact_area_stats_per_city.getCall_time_passive_6month().equals("")
                    && contact_area_stats_per_city.getCall_time_active_6month() != null
                    && !contact_area_stats_per_city.getCall_time_active_6month().equals("")) {
                    if ((Integer.parseInt(contact_area_stats_per_city.getCall_time_passive_6month())
                        + Integer.parseInt(contact_area_stats_per_city.getCall_time_active_6month())) != 0) {
                        preCallOutTime
                            = (double)Integer.parseInt(contact_area_stats_per_city.getCall_time_passive_6month())
                                / (Integer.parseInt(contact_area_stats_per_city.getCall_time_passive_6month())
                                    + Integer.parseInt(contact_area_stats_per_city.getCall_time_active_6month()));
                    }
                }

                if (contact_area_stats_per_city.getCall_time_passive_6month() != null
                    && !contact_area_stats_per_city.getCall_time_passive_6month().equals("")
                    && contact_area_stats_per_city.getCall_time_active_6month() != null
                    && !contact_area_stats_per_city.getCall_time_active_6month().equals("")) {
                    if ((Integer.parseInt(contact_area_stats_per_city.getCall_time_passive_6month())
                        + Integer.parseInt(contact_area_stats_per_city.getCall_time_active_6month())) != 0) {
                        preCallOutCount
                            = (double)Integer.parseInt(contact_area_stats_per_city.getCall_time_active_6month())
                                / (Integer.parseInt(contact_area_stats_per_city.getCall_time_passive_6month())
                                    + Integer.parseInt(contact_area_stats_per_city.getCall_time_active_6month()));
                    }
                }

                contactRegionJson.put("region_avg_call_in_time", avgCallInTime);// 平均电话呼入时间
                contactRegionJson.put("region_avg_call_out_time", avgCallOutTime);// 平均电话呼出时间
                contactRegionJson.put("region_call_in_cnt_pct", preCallInCount);// 电话呼入次数百分比
                contactRegionJson.put("region_call_out_cnt_pct", preCallOutCount);// 电话呼出次数百分比
                contactRegionJson.put("region_call_in_time_pct", preCallInTime);// 电话呼入时间百分比
                contactRegionJson.put("region_call_out_time_pct", preCallOutTime);// 电话呼出时间百分比
                contact_region.add(contactRegionJson);

            }
        }

        // contact_region end

        // contact_list start 运营商联系人通话详情
        JSONArray contact_list = new JSONArray();
        List<All_contact_detail> all_contact_details = yysReportRootBean.getAll_contact_detail();
        if (all_contact_details != null && all_contact_details.size() != 0) {
            for (All_contact_detail all_contact_detail : all_contact_details) {
                JSONObject contactListJson = new JSONObject();
                contactListJson.put("phone_num", all_contact_detail.getContact_number());// 号码
                contactListJson.put("phone_num_loc", all_contact_detail.getContact_area());// 号码归属地
                contactListJson.put("contact_name", all_contact_detail.getContact_number());// 号码标注
                contactListJson.put("needs_type", all_contact_detail.getContact_number());// 需求类别
                contactListJson.put("call_cnt", all_contact_detail.getCall_count_6month());// 通话次数
                contactListJson.put("call_len", all_contact_detail.getCall_count_passive_6month());// 通话时长
                contactListJson.put("call_out_cnt", all_contact_detail.getCall_count_active_6month());// 呼出次数
                contactListJson.put("call_out_len", all_contact_detail.getCall_time_active_6month());// 呼出时间
                contactListJson.put("call_in_cnt", all_contact_detail.getCall_count_passive_6month());// 呼入次数
                contactListJson.put("call_in_len", all_contact_detail.getCall_time_passive_6month());// 呼入时间
                contactListJson.put("p_relation", "");// 关系推测
                contactListJson.put("contact_1w", all_contact_detail.getCall_count_1week());// 最近一周联系次数
                contactListJson.put("contact_1m", all_contact_detail.getCall_count_1month());// 最近一月联系次数
                contactListJson.put("contact_3m", all_contact_detail.getCall_count_3month());// 最近三月联系次数
                contactListJson.put("contact_3m_plus", all_contact_detail.getCall_count_6month());// 三个月以上联系次数
                contactListJson.put("contact_early_morning", all_contact_detail.getCall_count_late_night_6month());// 凌晨联系次数
                contactListJson.put("contact_morning", 0);// 上午联系次数
                contactListJson.put("contact_noon", 0);// 中午联系次数
                contactListJson.put("contact_afternoon", 0);// 下午联系次数
                contactListJson.put("contact_night", 0);// 晚上联系次数
                contactListJson.put("contact_all_day", false);// 是否全天联系
                contactListJson.put("contact_weekday", all_contact_detail.getCall_time_work_time_6month());// 周中联系次数
                contactListJson.put("contact_weekend", all_contact_detail.getCall_count_offwork_time_6month());// 周末联系次数
                contactListJson.put("contact_holiday", all_contact_detail.getCall_count_holiday_6month());// 节假日联系次数
                contact_list.add(contactListJson);
            }
        }

        // contact_list end

        JSONArray main_service = new JSONArray();
        JSONArray deliver_address = new JSONArray();
        JSONArray ebusiness_expense = new JSONArray();
        JSONArray collection_contact = new JSONArray();

        // trip_info start
        JSONArray trip_info = new JSONArray();
        List<Travel_track_analysis_per_city> travel_track_analysis_per_cities
            = yysReportRootBean.getTravel_track_analysis_per_city();
        if (travel_track_analysis_per_cities != null && travel_track_analysis_per_cities.size() != 0) {
            for (Travel_track_analysis_per_city travel_track_analysis_per_city : travel_track_analysis_per_cities) {
                JSONObject tripJson = new JSONObject();
                tripJson.put("trip_leave", travel_track_analysis_per_city.getLeave_city());// 出发地
                tripJson.put("trip_dest", travel_track_analysis_per_city.getArrive_city());// 目的地
                tripJson.put("trip_type", travel_track_analysis_per_city.getLeave_day_type());// 出行时间类型
                tripJson.put("trip_start_time", travel_track_analysis_per_city.getLeave_day());// 出行开始时间
                tripJson.put("trip_end_time", travel_track_analysis_per_city.getArrive_day());// 出行结束时间
                trip_info.add(tripJson);
            }
        }
        // trip_info end

        telecomDataJson.put("user_info_check", user_info_check);
        telecomDataJson.put("main_service", main_service);
        telecomDataJson.put("behavior_check", behavior_check);
        telecomDataJson.put("collection_contact", collection_contact);
        telecomDataJson.put("contact_list", contact_list);
        telecomDataJson.put("ebusiness_expense", ebusiness_expense);
        telecomDataJson.put("contact_region", contact_region);
        telecomDataJson.put("application_check", application_check);
        telecomDataJson.put("deliver_address", deliver_address);
        telecomDataJson.put("trip_info", trip_info);
        telecomDataJson.put("cell_behavior", cell_behavior);
        telecomDataJson.put("Provider", "数聚魔盒");
        // telecomDataJson.put("credit_person_id", 12253559);
        // telecomDataJson.put("_id", "5aefccd2f1621460cada8b6b");

        return telecomDataJson;
    }

    private String[] getPhoneUsedTime(String netTime, String phoneNumber) {
        String[] result = new String[4];
        int countMonth = 0;
        if (netTime == null || netTime.equals("")) {
            result[0] = "0";
            result[1] = "无数据";
            result[2] = "运营商未提供时间数据";
            result[3] = "0";
            return result;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str1 = netTime;
        String str2 = new java.sql.Date(DateUtil.getCurrentTime()).toString();
        Calendar bef = Calendar.getInstance();
        Calendar aft = Calendar.getInstance();
        try {
            bef.setTime(sdf.parse(str1));
            aft.setTime(sdf.parse(str2));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        countMonth = aft.get(Calendar.MONTH) + (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
        if (countMonth >= 24) {
            result[0] = String.valueOf(countMonth);
            result[1] = "长期使用(24个月以上，包含24)";
            result[2] = "根据号码[" + phoneNumber + "]运营商提供的认证时间,推算该号码使用" + countMonth + "个月";
            result[3] = "1";
            return result;
        }
        if (countMonth >= 6 && countMonth < 24) {
            result[0] = String.valueOf(countMonth);
            result[1] = "至少使用了半年以上(6-24个月，包含6)";
            result[2] = "根据号码[" + phoneNumber + "]运营商提供的认证时间,推算该号码使用" + countMonth + "个月";
            result[3] = "1";
            return result;
        }
        if (countMonth >= 3 && countMonth < 6) {
            result[0] = String.valueOf(countMonth);
            result[1] = "至少是用了三个月以上但不足半年";
            result[2] = "根据号码[" + phoneNumber + "]运营商提供的认证时间,推算该号码使用" + countMonth + "个月";
            result[3] = "1";
            return result;
        }
        if (countMonth < 3) {
            result[0] = String.valueOf(countMonth);
            result[1] = "使用了不足三个月";
            result[2] = "根据号码[" + phoneNumber + "]运营商提供的认证时间,推算该号码使用" + countMonth + "个月";
            result[3] = "2";
            return result;
        }
        return null;
    }

    /**
     * 计算互通号码数量信息
     * 
     * @return
     */
    private String[] getBehaviorContact(String moheString, String countNum) {
        String[] result = new String[3];
        if (moheString == null || moheString.equals("") || countNum == null || countNum.equals("")
            || countNum.equals("0") || moheString.equals("无数据")) {
            result[0] = "0";
            result[1] = "无数据";
            result[2] = "未提供运营商数据";
            return result;
        }

        int connectNam = 0;
        if (moheString.equals("数量众多")) {
            connectNam = 145;
            double perConnect = (double)connectNam / Double.valueOf(countNum);
            result[0] = "1";
            result[1] = "数量众多(100以上，不含100)";
            result[2] = "互通过电话的号码有" + connectNam + "个，比例为" + perConnect + "%";
            return result;
        }

        if (moheString.equals("数量正常")) {
            connectNam = 8;
            result[0] = "1";
            double perConnect = (double)connectNam / Double.valueOf(countNum);
            result[1] = "数量正常(10 - 100)";
            result[2] = "互通过电话的号码有" + connectNam + "个，比例为" + perConnect + "%";
            return result;
        }

        if (moheString.equals("数量稀少")) {
            connectNam = 1;
            result[0] = "2";
            double perConnect = (double)connectNam / Double.valueOf(countNum);
            result[1] = "数量稀少(10以内，不含10)";
            result[2] = "互通过电话的号码有" + connectNam + "个，比例为" + perConnect + "%";
            return result;
        }
        return null;
    }

    private String[] getMacaoArray(String moheString) {
        String[] result = new String[3];

        if (moheString == null || moheString.equals("") || moheString.equals("无数据")) {
            result[0] = "0";
            result[1] = "无数据";
            result[2] = "未提供运营商数据";
            return result;
        }

        if (moheString.equals("频繁通话")) {
            result[0] = "2";
            result[1] = "多次通话(3次以上)";
            result[2] = "澳门地区号码通话" + 4 + "次";
            return result;
        }

        if (moheString.equals("偶尔通话")) {
            result[0] = "1";
            result[1] = "偶尔通话(3次以内，包括3次)";
            result[2] = "澳门地区号码通话" + 2 + "次";
            return result;
        }

        if (moheString.equals("从未通话")) {
            result[0] = "1";
            result[1] = "无通话记录";
            result[2] = "未发现澳门地区电话通话记录";
            return result;
        }
        return null;
    }

    private String[] get110Array(String moheString) {
        String[] result = new String[3];

        if (moheString == null || moheString.equals("") || moheString.equals("无数据")) {
            result[0] = "0";
            result[1] = "无数据";
            result[2] = "未提供运营商数据";
            return result;
        }

        if (moheString.equals("频繁通话")) {
            result[0] = "2";
            result[1] = "多次通话(3次以上)";
            result[2] = "与110号码通话" + 4 + "次";
            return result;
        }

        if (moheString.equals("偶尔通话")) {
            result[0] = "1";
            result[1] = "偶尔通话(3次以内，包括3次)";
            result[2] = "与110号码通话" + 1 + "次";
            return result;
        }

        if (moheString.equals("从未通话")) {
            result[0] = "1";
            result[1] = "无通话记录";
            result[2] = "未发现与110电话通话记录";
            return result;
        }
        return null;
    }

    private String[] get120Array(String moheString) {
        String[] result = new String[3];

        if (moheString == null || moheString.equals("") || moheString.equals("无数据")) {
            result[0] = "0";
            result[1] = "无数据";
            result[2] = "未提供运营商数据";
            return result;
        }

        if (moheString.equals("频繁通话")) {
            result[0] = "2";
            result[1] = "多次通话(3次以上)";
            result[2] = "与120号码通话" + 4 + "次";
            return result;
        }

        if (moheString.equals("偶尔通话")) {
            result[0] = "1";
            result[1] = "偶尔通话(3次以内，包括3次)";
            result[2] = "与120号码通话" + 1 + "次";
            return result;
        }

        if (moheString.equals("从未通话")) {
            result[0] = "1";
            result[1] = "无通话记录";
            result[2] = "未发现与120电话通话记录";
            return result;
        }
        return null;
    }

    private String[] getCourtArray(String moheString) {
        String[] result = new String[3];

        if (moheString == null || moheString.equals("") || moheString.equals("无数据")) {
            result[0] = "0";
            result[1] = "无数据";
            result[2] = "未提供运营商数据";
            return result;
        }

        if (moheString.equals("频繁通话")) {
            result[0] = "2";
            result[1] = "多次通话(3次以上)";
            result[2] = "与法院号码通话" + 4 + "次";
            return result;
        }

        if (moheString.equals("偶尔通话")) {
            result[0] = "1";
            result[1] = "偶尔通话(3次以内，包括3次)";
            result[2] = "与法院号码通话" + 1 + "次";
            return result;
        }

        if (moheString.equals("从未通话")) {
            result[0] = "1";
            result[1] = "无通话记录";
            result[2] = "未发现与法院电话通话记录";
            return result;
        }
        return null;
    }

    private String[] getLawyerArray(String moheString) {
        String[] result = new String[3];

        if (moheString == null || moheString.equals("") || moheString.equals("无数据")) {
            result[0] = "0";
            result[1] = "无数据";
            result[2] = "未提供运营商数据";
            return result;
        }

        if (moheString.equals("频繁通话")) {
            result[0] = "2";
            result[1] = "多次通话(3次以上)";
            result[2] = "与律师号码通话" + 4 + "次";
            return result;
        }

        if (moheString.equals("偶尔通话")) {
            result[0] = "1";
            result[1] = "偶尔通话(3次以内，包括3次)";
            result[2] = "与律师号码通话" + 1 + "次";
            return result;
        }

        if (moheString.equals("从未通话")) {
            result[0] = "1";
            result[1] = "无通话记录";
            result[2] = "未发现与律师电话通话记录";
            return result;
        }
        return null;
    }

    private String[] getLoanArray(String moheString) {
        String[] result = new String[3];

        if (moheString == null || moheString.equals("") || moheString.equals("无数据")) {
            result[0] = "0";
            result[1] = "无数据";
            result[2] = "未提供运营商数据";
            return result;
        }

        if (moheString.equals("频繁通话")) {
            result[0] = "2";
            result[1] = "经常被联系(联系次数在5次以上，包含5次，且主动呼叫占比大于50%，包含50%)";
            result[2] = "联系列表";
            return result;
        }

        if (moheString.equals("偶尔通话")) {
            result[0] = "1";
            result[1] = "很少被联系(联系次数在5次以内，或联系次数在5次以上且主动呼叫占比低于20%)";
            result[2] = "联系列表";
            return result;
        }

        if (moheString.equals("从未通话")) {
            result[0] = "1";
            result[1] = "无该类号码纪录";
            result[2] = "未找到贷款类相关号码";
            return result;
        }
        return null;
    }

    private int getEducation(String education) {
        if (StringUtil.isEmpty(education)) {
            return 0;
        }
        if (education.equals("中专/高中以下")) {
            return 1;
        }
        if (education.equals("大专")) {
            return 2;
        }
        if (education.equals("本科")) {
            return 3;
        }
        if (education.equals("硕士")) {
            return 4;
        }
        if (education.equals("博士及以上")) {
            return 5;
        }
        return 1;
    }

    private int getProfessionType(User user) {
        // 1、上班族 2、企业主 3、个体户 4、学生 5、自由职业
        if (user == null) {
            return 1;
        }

        if (user.getJobInfo() == null) {
            return 1;
        }
        String profession = user.getJobInfo().getOccupation();
        if (StringUtil.isEmpty(profession)) {
            return 1;
        }
        if (profession.equals("上班族")) {
            return 1;
        }
        if (profession.equals("企业主")) {
            return 2;
        }
        if (profession.equals("个体户")) {
            return 3;
        }
        if (profession.equals("学生")) {
            return 4;
        }
        if (profession.equals("自由职业")) {
            return 5;
        }
        return 1;
    }

    private int getSex(String sex) {
        if (StringUtil.isEmpty(sex)) {
            return 0;
        }
        if (sex.equals("男")) {
            return 0;
        }
        if (sex.equals("女")) {
            return 1;
        }
        return 0;
    }

    private JSONArray getContactArray(User user) {
        JSONArray contactArray = new JSONArray();
        JSONObject userContact1 = new JSONObject();
        /* mobile	string	是	联系人电话
        name	string	是	联系人姓名
        relation	string	是	与联系人关系(1.父亲2.配偶,3.母亲,9.儿子,10.女儿,11.兄弟,12.姐妹;第二联系人关系：5.同学,8.亲戚,7.同事,6.朋友,100.其他)*/
        userContact1.put("name", user.getContract1XjlUsername());
        userContact1.put("mobile", user.getContract1XjlPhonenumber());
        userContact1.put("relation", user.getContract1XjlRelation());
        JSONObject userContact2 = new JSONObject();
        userContact2.put("name", user.getContract2XjlUsername());
        userContact2.put("mobile", user.getContract2XjlPhonenumber());
        userContact2.put("relation", user.getContract2XjlRelation());
        contactArray.add(userContact1);
        contactArray.add(userContact2);
        return contactArray;

    }

    private void getResultByOpenId(String openId) {

        String userName = PropertyManager.getProperty("xjl.qiancheng.userName");
        String password = PropertyManager.getProperty("xjl.qiancheng.password");
        String url = PropertyManager.getProperty("xjl.qiancheng.url") + "/risk-model/galaxy/result";
        String dataParam = "{\"open_id\":\"" + openId + "\",\"encrypt\":" + 0 + "}";
        try {
            HttpUtil.HttpResponse response
                = HttpUtil.sendDataViaHTTP(HttpUtil.HTTP_POST, url, HttpUtil.CONTENT_TYPE_JSON, dataParam, null);
            if (response.getResponseCode() == HttpUtil.STATUS_OK) {
                byte[] byteArray = response.getResponseBody();
                if (byteArray == null || byteArray.length < 1) {
                    throw new IllegalArgumentException("this byteArray must not be null or empty");
                } else {
                    String strRead = new String(byteArray, "utf-8");
                    logger.debug("content = " + strRead);
                    JSONObject json = JSONObject.parseObject(strRead);
                    String code = json.getString("code");
                    if (code.equals("0")) {
                        String data = json.getString("data");
                        JSONObject jsonData = JSONObject.parseObject(data);
                        System.out.println("finalResult " + data.toString());
                    } else {
                        String msg = json.getString("msg");
                        // 抛出异常信息
                        throw new WrongParameterValueException(msg);
                    }
                }
            } else {
                logger.info("response code = " + response.getResponseCode());
            }

        } catch (IOException e) {
            logger.error("response with error, " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void initToken() {
        TOKEN = this.getToken();
    }

    private String checkUserBaseInfo(User user) {
        String msg = "success";
        if (StringUtil.isEmpty(user.getEducation())) {
            msg = "学历信息不完整";
            return msg;
        }

        if (StringUtil.isEmpty(user.getContract1XjlUsername())) {
            msg = "其他联系人信息不完整";
            return msg;
        }

        if (StringUtil.isEmpty(user.getContract1XjlUsername())) {
            msg = "直系亲属联系人信息不完整";
            return msg;
        }

        if (StringUtil.isEmpty(user.getContract2XjlUsername())) {
            msg = "其他联系人信息不完整";
            return msg;
        }

        if (!user.isRealnameVerified()) {
            msg = "未完成实名验证";
            return msg;
        }

        if (!user.isOcrIdcardVerified()) {
            msg = "未完成身份验证";
            return msg;
        }

        if (!user.isMobileVerified()) {
            msg = "未完成运营商验证";
            return msg;
        }

        if (!user.isVideoVerified()) {
            msg = "未完成视频验证";
            return msg;
        }

        return msg;
    }

    private String checkMoHeReport(User user, Timestamp startDate, Timestamp endDate) {
        String msg = "success";
        ClubReport clubReport = clubService.getReport(user.getUserId(), "YYS", "100000", startDate, endDate);
        if (clubReport == null) {
            msg = "缺少运营商数据报告";
            logger.info("User userId = " + user.getUserId() + " name =  " + user.getUserName() + " 缺少运营商报告 ");
        } else {
            String report = clubReport.getReport();
            YysReportRootBean yysReportRootBean = JSON.parseObject(report, YysReportRootBean.class);// 报表基类
            if (yysReportRootBean.getInfo_check().getIs_identity_code_reliable() != null) {
                if (yysReportRootBean.getInfo_check().getIs_identity_code_reliable().equals("是")) {
                    // 通过运营商认证，如果运营商姓名以及身份证认证不通过，则不通过
                    if ((yysReportRootBean.getInfo_match().getReal_name_check_yys().equals("不匹配")
                        || yysReportRootBean.getInfo_match().getReal_name_check_yys().equals("无数据"))
                        && (yysReportRootBean.getInfo_match().getIdentity_code_check_yys().equals("不匹配")
                            || yysReportRootBean.getInfo_match().getIdentity_code_check_yys().equals("无数据"))) {
                        msg = "运营商姓名及身份证号码不匹配";
                    }
                } else {
                    msg = "运营商未实名认证";
                }
            } else {
                msg = "运营商未实名认证";
            }
            logger.info("QianChengDate request user baseInfo userId = " + user.getUserId() + " reportTaskId "
                + clubReport.getTaskId());
        }
        return msg;
    }

    private int getBankId(String bankCode) {
        // bank_id int 是
        // 1、工商银行,2、农业银行,3、光大银行,4、邮政储蓄银行,5、兴业银行,6、深圳发展银行,7、建设银行,8、招商银行,9、中国银行,10、浦发银行,11、平安银行,12、华夏银行,13、中信银行,14、交通银行,15、民生银行,16、广发银行17、北京银行,18、上海银行,19、上海农商银行,20、成都银行,21、渤海银行,22、南京银行,23、宁波银行,24、东亚银行,30、江西银行
        if (bankCode.equals("ABC")) {
            // 农业银行
            return 2;

        }
        if (bankCode.equals("BCCB")) {
            // 北京银行
            return 17;

        }
        if (bankCode.equals("BOC")) {
            // 中国银行
            return 9;

        }
        if (bankCode.equals("BOCO")) {
            // 交通银行
            return 14;

        }
        if (bankCode.equals("CCB")) {
            // 建设银行
            return 7;

        }
        if (bankCode.equals("CEB")) {
            // 光大银行
            return 3;

        }
        if (bankCode.equals("CGB")) {
            // 广发银行
            return 16;

        }
        if (bankCode.equals("CIB")) {
            // 兴业银行
            return 5;

        }
        if (bankCode.equals("CMB")) {
            // 招商银行
            return 8;

        }
        if (bankCode.equals("CMBC")) {
            // 民生银行
            return 15;

        }
        if (bankCode.equals("ECITIC")) {
            // 中信银行
            return 13;

        }
        if (bankCode.equals("HXB")) {
            // 华夏银行
            return 12;

        }
        if (bankCode.equals("ICBC")) {
            // 工商银行
            return 1;

        }
        if (bankCode.equals("PINGAN")) {
            // 平安银行
            return 11;

        }
        if (bankCode.equals("SHB")) {
            // 上海银行
            return 18;

        }
        if (bankCode.equals("SPDB")) {
            // 浦发银行
            return 10;

        }
        return 0;
    }

}
