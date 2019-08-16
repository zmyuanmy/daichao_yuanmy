package com.jbb.boss.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jbb.boss.service.BossService;
import com.jbb.mgt.boss.*;
import com.jbb.mgt.core.dao.*;
import com.jbb.mgt.core.domain.*;
import com.jbb.mgt.core.domain.User;
import com.jbb.mgt.core.domain.jsonformat.nwjr.*;
import com.jbb.mgt.core.domain.jsonformat.nwjr.request.*;
import com.jbb.mgt.core.domain.jsonformat.yys.*;
import com.jbb.mgt.core.domain.jsonformat.yys.report.YysReportRootBean;
import com.jbb.mgt.core.service.ClubService;
import com.jbb.mgt.helipay.service.impl.QuickPayServiceImpl;
import com.jbb.mgt.helipay.util.HeliUtil;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("BossService")
public class BossServiceImpl implements BossService {

    @Autowired
    private UserDeviceDao userDeviceDao;

    @Autowired
    private UserContantDao userContantDao;

    @Autowired
    private ClubService clubService;

    @Autowired
    private UserJobDao userJobDao;

    @Autowired
    private UserAddressDao userAddressDao;

    @Autowired
    private BossOrderDao bossOrderDao;

    @Autowired
    private FlowBackLogDao flowBackLogDao;

    private static Logger logger = LoggerFactory.getLogger(BossServiceImpl.class);

    @Override
    public void backFlowAddressBook(com.jbb.mgt.core.domain.User user) throws Exception {

        MailListBackFlow mailListBackFlow = new MailListBackFlow();
        UserDevice userDevice = userDeviceDao.getUserLastDevice(user.getUserId());
        if (userDevice == null) {
            logger.info("backFlowAddressBook Error userDevice Is Null");
            return;
        }
        MailListBackFlowBody mailListBackFlowBody = new MailListBackFlowBody();
        mailListBackFlowBody.setDevice_id(userDevice.getSerial());
        mailListBackFlowBody.setEquipment_status(0);
        mailListBackFlowBody.setId_card(user.getIdCard());
        mailListBackFlowBody.setOs(userDevice.getPlatform());
        mailListBackFlow.setCurrent_time(DateUtil.getSystemTimeString());
        mailListBackFlow.setFunction_code(BossConstants.BACK_FLOW_ADDRESS_BOOK);

        // phone_book详情
        List<PhoneBook> phoneBookList = getPhoneBookList(user);
        mailListBackFlowBody.setPhone_book(phoneBookList);

        mailListBackFlow.setBody(mailListBackFlowBody);
        JSONObject jsonObject = (JSONObject)JSONObject.toJSON(mailListBackFlow);
        HttpRequestUtil.postHttpsRequest(jsonObject.toJSONString(), "DC_" + BossConstants.BACK_FLOW_ADDRESS_BOOK);
        saveFlowBackLog(user.getUserId(),BossConstants.BACK_FLOW_ADDRESS_BOOK,userDevice.getSerial());
    }

    @Override
    public void backFlowMessage(User user) throws Exception {

        UserDevice userDevice = userDeviceDao.getUserLastDevice(user.getUserId());
        if (userDevice == null) {
            logger.info("backFlowMessage Error userDevice Is Null");
            return;
        }

        ShortMessageBackFlow shortMessageBackFlow = new ShortMessageBackFlow();
        shortMessageBackFlow.setCurrent_time(DateUtil.getSystemTimeString());
        shortMessageBackFlow.setFunction_code(BossConstants.BACK_FLOW_MESSAGE);

        ShortMessageBackFlowBody shortMessageBackFlowBody = new ShortMessageBackFlowBody();

        // 短信信息暂时没有 自己先模拟
        List<MessageList> messageLists = new ArrayList<>();
        MessageList messageList = new MessageList();
        messageList.setContent("中秋节快乐");
        messageList.setMobile(user.getPhoneNumber());
        messageList.setTime(String.valueOf(DateUtil.getCurrentTime()));
        messageList.setType("1");
        messageLists.add(messageList);

        shortMessageBackFlowBody.setId_card(user.getIdCard());
        shortMessageBackFlowBody.setDevice_id(userDevice.getSerial());
        shortMessageBackFlowBody.setMessageListList(messageLists);
        shortMessageBackFlow.setBody(shortMessageBackFlowBody);
        JSONObject jsonObject = (JSONObject)JSONObject.toJSON(shortMessageBackFlow);
        HttpRequestUtil.postHttpsRequest(jsonObject.toJSONString(), "DC_" + BossConstants.BACK_FLOW_MESSAGE);
        saveFlowBackLog(user.getUserId(),BossConstants.BACK_FLOW_MESSAGE,userDevice.getSerial());
    }

    @Override
    public void backFlowAppInfo(User user) throws Exception {

        UserDevice userDevice = userDeviceDao.getUserLastDevice(user.getUserId());
        if (userDevice == null) {
            logger.info("backFlowAppInfo Error userDevice Is Null");
            return;
        }

        InstallationListBackFlow installationListBackFlow = new InstallationListBackFlow();
        installationListBackFlow.setFunction_code(BossConstants.BACK_FLOW_APP_INFO);
        installationListBackFlow.setCurrent_time(DateUtil.getSystemTimeString());

        InstallationListBackFlowBody installationListBackFlowBody = new InstallationListBackFlowBody();
        installationListBackFlowBody.setId_card(user.getIdCard());
        installationListBackFlowBody.setDevice_id(userDevice.getSerial());

        List<InstalledAppList> installedAppLists = new ArrayList<>();
        InstalledAppList installedAppList = new InstalledAppList();
        installedAppList.setName("支付宝");
        installedAppLists.add(installedAppList);

        installationListBackFlowBody.setInstalled_app_list(installedAppLists);
        installationListBackFlow.setBody(installationListBackFlowBody);
        JSONObject jsonObject = (JSONObject)JSONObject.toJSON(installationListBackFlow);
        HttpRequestUtil.postHttpsRequest(jsonObject.toJSONString(), "DC_" + BossConstants.BACK_FLOW_APP_INFO);
        saveFlowBackLog(user.getUserId(),BossConstants.BACK_FLOW_APP_INFO,userDevice.getSerial());

    }

    @Override
    public void backFlowCallLog(User user) throws Exception {

        UserDevice userDevice = userDeviceDao.getUserLastDevice(user.getUserId());
        if (userDevice == null) {
            logger.info("backFlowCallLog Error userDevice Is Null");
            return;
        }

        ClubReport clubReport = clubService.getReport(user.getUserId(), "YYS", "100000", null, null);
        if (clubReport == null) {
            logger.info("backFlowCallLog Error clubReport Is Null");
            return;
        }

        String data = clubReport.getData();
        YysRootBean yysRootBean = JSON.parseObject(data, YysRootBean.class);// 运营商数据基类

        List<CallHistoryList> callHistoryLists = getCallHistoryList(yysRootBean);

        CallRecordBackFlow callRecordBackFlow = new CallRecordBackFlow();
        callRecordBackFlow.setFunction_code(BossConstants.BACK_FLOW_CALL_BACK);
        callRecordBackFlow.setCurrent_time(DateUtil.getSystemTimeString());

        CallRecordBackFlowBody callRecordBackFlowBody = new CallRecordBackFlowBody();
        callRecordBackFlowBody.setCall_history_list(callHistoryLists);
        callRecordBackFlowBody.setId_card(user.getIdCard());
        callRecordBackFlowBody.setDevice_id(userDevice.getSerial());
        callRecordBackFlow.setBody(callRecordBackFlowBody);
        JSONObject jsonObject = (JSONObject)JSONObject.toJSON(callRecordBackFlow);
        HttpRequestUtil.postHttpsRequest(jsonObject.toJSONString(),"DC_"+BossConstants.BACK_FLOW_CALL_BACK);
        saveFlowBackLog(user.getUserId(),BossConstants.BACK_FLOW_CALL_BACK,userDevice.getSerial());

    }

    @Override
    public void backFlowOperatorInfo(User user) throws Exception {

        OperatorInfoBackFlow operatorInfoBackFlow = new OperatorInfoBackFlow();
        OperatorInfoBackFlowBody operatorInfoBackFlowBody = new OperatorInfoBackFlowBody();
        operatorInfoBackFlow.setFunction_code(BossConstants.BACK_FLOW_OPERATOR_INFO);

        ClubReport clubReport = clubService.getReport(user.getUserId(), "YYS", "100000", null, null);
        if (clubReport == null) {
            logger.info("backFlowOperatorInfo Error clubReport Is Null");
            return;
        }

        String data = clubReport.getData();

        System.out.println("report data "+data);
        String report = clubReport.getReport();
        YysRootBean yysRootBean = JSON.parseObject(data, YysRootBean.class);// 运营商数据基类
        YysReportRootBean yysReportRootBean = JSON.parseObject(report, YysReportRootBean.class);// 报表基类

        if (yysRootBean == null || yysReportRootBean == null) {
            logger.info("backFlowOperatorInfo Error yysRootBean or yysReportRootBean Is Null");
            return;
        }

        // nwjrUser info
        com.jbb.mgt.core.domain.jsonformat.nwjr.User nwjrUser = new com.jbb.mgt.core.domain.jsonformat.nwjr.User();

        if (yysReportRootBean.getMobile_info() != null) {
            nwjrUser.setUser_source(yysReportRootBean.getMobile_info().getMobile_carrier());
            nwjrUser.setPackage_name(yysReportRootBean.getMobile_info().getPackage_type());
        }

        if (yysRootBean.getData().getTask_data().getBase_info() != null) {
            nwjrUser.setUser_source(yysReportRootBean.getMobile_info().getMobile_carrier());
            nwjrUser.setAddr(yysRootBean.getData().getTask_data().getBase_info().getCert_addr());
            nwjrUser.setId_card(yysRootBean.getData().getTask_data().getBase_info().getCert_num());
            nwjrUser.setPhone(yysRootBean.getData().getTask_data().getBase_info().getUser_number());
            nwjrUser.setReal_name(yysRootBean.getData().getTask_data().getBase_info().getUser_name());
            nwjrUser.setContact_phone(yysRootBean.getData().getTask_data().getBase_info().getUser_contact_no());
        }
        if (yysRootBean.getData().getTask_data().getAccount_info() != null) {
            nwjrUser.setPhone_remain(yysRootBean.getData().getTask_data().getAccount_info().getAccount_balance());
            nwjrUser.setReg_time(yysRootBean.getData().getTask_data().getAccount_info().getNet_time());
            nwjrUser.setScore(yysRootBean.getData().getTask_data().getAccount_info().getCredit_point());
            nwjrUser.setStar_level(yysRootBean.getData().getTask_data().getAccount_info().getCredit_level());
            nwjrUser.setAuthentication(yysRootBean.getData().getTask_data().getAccount_info().getReal_info());
            nwjrUser.setPhone_status(yysRootBean.getData().getTask_data().getAccount_info().getMobile_status());

        }
        operatorInfoBackFlowBody.setUser(nwjrUser);

        // call_record_list info
        List<CallRecordList> callRecordLists = getCallRecordList(yysRootBean);
        operatorInfoBackFlowBody.setCall_record_list(callRecordLists);

        // message_record_list info
        List<MessageRecordList> messageRecordLists = getMessageRecordList(yysRootBean);
        operatorInfoBackFlowBody.setMessage_record_list(messageRecordLists);

        // bill_list info
        List<BillList> billLists = getBillList(yysRootBean);
        operatorInfoBackFlowBody.setBill_list(billLists);

        // network_flow_info_list info
        List<NateWorkFlowList> nateWorkFlowLists = getDataInfoList(yysRootBean);
        operatorInfoBackFlowBody.setNetwork_flow_info_list(nateWorkFlowLists);

        // payment_record_list info
        List<PaymentRecordList> paymentRecordLists = getPayRecordList(yysRootBean);
        operatorInfoBackFlowBody.setPayment_record_list(paymentRecordLists);

        operatorInfoBackFlow.setBody(operatorInfoBackFlowBody);

        JSONObject jsonObject = (JSONObject)JSONObject.toJSON(operatorInfoBackFlow);
        HttpRequestUtil.postHttpsRequest(jsonObject.toJSONString(), "DC_" + BossConstants.BACK_FLOW_OPERATOR_INFO);
        saveFlowBackLog(user.getUserId(),BossConstants.BACK_FLOW_OPERATOR_INFO,"11");
    }

    @Override
    public void getBossReportRequest(User user) throws Exception {

        String appId = PropertyManager.getProperty("xjl.nwjr.appid");

        AntiFraudRequest antiFraudRequest = new AntiFraudRequest();
        antiFraudRequest.setApp_id(appId);
        antiFraudRequest.setCurrent_time(DateUtil.getSystemTimeString());
        antiFraudRequest.setFunction_code(BossConstants.GET_BOSS_REPORT_REQUEST);
        antiFraudRequest.setReserve(null);
        antiFraudRequest.setSession_id(HeliUtil.generateOrderId());
        antiFraudRequest.setVersion("1.0");

        AntiFraudRequestMsgBody antiFraudRequestMsgBody = new AntiFraudRequestMsgBody();

        UserJob userJob = userJobDao.selectJobInfoByAddressId(user.getUserId());
        WorkInfo workInfo = new WorkInfo();
        if (userJob != null) {
            UserAddresses userAddresses = userJob.getJobAddress();

            workInfo.setCompany_name(userJob.getCompany());
            // workInfo.setCompany_type(1000);
            // workInfo.setCompany_register_capital(1);
            // workInfo.setPhone("0999-00998");
            // workInfo.setCompany_position_level(1);
            // workInfo.setWork_email("8888@qq.com");
            // workInfo.setTotal_work_year(1);
            // workInfo.setWork_year(1);
            // workInfo.setSalary(1);
            String province = "";
            String city = "";
            String area = "";
            String address = "";
            if (userAddresses != null) {
                province = userAddresses.getProvince();
                city = userAddresses.getCity();
                area = userAddresses.getArea();
                address = userAddresses.getAddress();
            }
            workInfo.setCompany_province(province);
            workInfo.setCompany_city(city);
            workInfo.setCompany_area(area);
            workInfo.setCompany_address(address);
        }
        antiFraudRequestMsgBody.setWork_info(workInfo);

        UserAddresses userAddresses = userAddressDao.selectUserAddressesUserIdAndType(user.getUserId(), 0);
        String province = "";
        String city = "";
        String area = "";
        String address = "";
        if (userAddresses != null) {
            province = userAddresses.getProvince();
            city = userAddresses.getCity();
            area = userAddresses.getArea();
            address = userAddresses.getAddress();
        }
        BasicInfo basicInfo = new BasicInfo();
        basicInfo.setIdentity_card(user.getIdCard());
        basicInfo.setReal_name(user.getUserName());
        basicInfo.setMobile_phone(user.getPhoneNumber());
        basicInfo.setRegister_time(user.getCreationDate().getTime());
        basicInfo.setLiving_address(address);
        basicInfo.setLiving_city(city);
        basicInfo.setLiving_area(area);
        basicInfo.setLiving_province(province);
        basicInfo.setDegree(getEducation(user.getEducation()));
        UserDevice userDevice = userDeviceDao.getUserLastDevice(user.getUserId());
        if (userDevice == null) {
            logger.info("getBossReportRequest Info userDevice Is Null");
        }
        if (userDevice.getPlatform().toLowerCase().equals("android")) {
            basicInfo.setDevice_type(0);
        } else if (userDevice.getPlatform().toLowerCase().equals("ios")) {
            basicInfo.setDevice_type(1);
        } else {
            basicInfo.setDevice_type(2); // 网页
        }
        antiFraudRequestMsgBody.setBasic_info(basicInfo);

        // 联系人
        List<ContactPerson> contactPersonList = getContactList(user);

        String orderId = HeliUtil.generateOrderId();
        antiFraudRequestMsgBody.setProject_id(HeliUtil.generateOrderId()); // 1.1版本新增字段 借款记录id
        antiFraudRequestMsgBody.setOrder_id(orderId); // 每次请求orderId值必须不一样
        antiFraudRequestMsgBody.setContact_person(contactPersonList);
        String notifyUrl = PropertyManager.getProperty("xjl.nwjr.notify.url");
        antiFraudRequestMsgBody.setNotify_url(notifyUrl);
        antiFraudRequest.setMsg_body(antiFraudRequestMsgBody);
        JSONObject jsonObject = (JSONObject)JSONObject.toJSON(antiFraudRequest);
        HttpRequestUtil.postHttpsRequest(jsonObject.toJSONString(), "RCE_" + BossConstants.GET_BOSS_REPORT_REQUEST);

        //调用接口成功 创建boss反欺诈业务流水表
        BossOrder bossOrder =  new BossOrder();
        bossOrder.setOrderId(orderId);
        bossOrder.setUserId(user.getUserId());
        bossOrder.setCreationDate(DateUtil.getCurrentTimeStamp());
        bossOrderDao.insertBossOrder(bossOrder);

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

    private List<ContactPerson> getContactList(User user) {
        List<ContactPerson> contactPersonList = new ArrayList<>();
        ContactPerson contactPerson = new ContactPerson();
        contactPerson.setName(user.getContract1XjlUsername());
        contactPerson.setPhone(user.getContract1XjlPhonenumber());
        contactPerson.setRelation(user.getContract1XjlRelation());
        ContactPerson contactPerson1 = new ContactPerson();
        contactPerson1.setName(user.getContract2XjlUsername());
        contactPerson1.setPhone(user.getContract2XjlPhonenumber());
        contactPerson1.setRelation(user.getContract2XjlRelation());
        contactPersonList.add(contactPerson);
        contactPersonList.add(contactPerson1);
        return contactPersonList;

    }

    private List<PhoneBook> getPhoneBookList(User user) {
        List<PhoneBook> phoneBookList = new ArrayList<>();
        List<UserContant> userContants = userContantDao.selectUserContantByJbbUserId(user.getJbbUserId());
        if (userContants != null && userContants.size() != 0) {
            for (UserContant userContant : userContants) {
                PhoneBook phoneBook = new PhoneBook();
                phoneBook.setMobile(userContant.getPhoneNumber());
                phoneBook.setRemark(userContant.getUserName());
                phoneBookList.add(phoneBook);
            }
        }
        return phoneBookList;
    }

    private List<CallHistoryList> getCallHistoryList(YysRootBean yysRootBean) {

        List<Call_info> call_infos = new ArrayList<Call_info>();
        if (yysRootBean.getData() != null) {
            if (yysRootBean.getData().getTask_data() != null) {
                if (yysRootBean.getData().getTask_data().getCall_info() != null) {
                    call_infos = yysRootBean.getData().getTask_data().getCall_info();
                }
            }
        }
        List<CallHistoryList> callHistoryLists = new ArrayList<>();
        if (call_infos != null && call_infos.size() != 0) {
            for (Call_info call_info : call_infos) {
                List<Call_record> call_records = call_info.getCall_record();
                if (call_records != null && call_records.size() != 0) {
                    for (Call_record call_record : call_records) {
                        CallHistoryList callHistoryList = new CallHistoryList();
                        callHistoryList.setDuration(Integer.parseInt(call_record.getCall_time()));
                        callHistoryList
                            .setTime(String.valueOf(DateUtil.parseStrnew(call_record.getCall_start_time()).getTime()));
                        callHistoryList.setMobile(call_record.getCall_other_number());
                        if (call_record.getCall_type_name().equals("主叫")) {
                            callHistoryList.setType(1);
                        } else {
                            callHistoryList.setType(2);
                        }
                        callHistoryLists.add(callHistoryList);
                    }
                }
            }
        }
        return callHistoryLists;
    }

    private List<CallRecordList> getCallRecordList(YysRootBean yysRootBean) {

        List<CallRecordList> callRecordLists = new ArrayList<>();

        List<Call_info> call_infos = new ArrayList<Call_info>();
        if (yysRootBean.getData() != null) {
            if (yysRootBean.getData().getTask_data() != null) {
                if (yysRootBean.getData().getTask_data().getCall_info() != null) {
                    call_infos = yysRootBean.getData().getTask_data().getCall_info();
                }
            }
        }
        if (call_infos != null && call_infos.size() != 0) {
            for (Call_info call_info : call_infos) {
                List<Call_record> call_records = call_info.getCall_record();
                if (call_records != null && call_records.size() != 0) {
                    for (Call_record call_record : call_records) {
                        CallRecordList callRecordList = new CallRecordList();
                        callRecordList.setTrade_type("1");
                        callRecordList.setTrade_time(call_record.getCall_land_type());
                        callRecordList.setCall_time(call_record.getCall_start_time());
                        callRecordList.setTrade_addr(call_record.getCall_address());
                        callRecordList.setReceive_phone(call_record.getCall_other_number());
                        if (call_record.getCall_type_name().equals("主叫")) {
                            callRecordList.setCall_type("1");
                        } else if (call_record.getCall_type_name().equals("被叫")) {
                            callRecordList.setCall_type("2");
                        } else {
                            callRecordList.setCall_type("3");
                        }
                        callRecordList.setBusiness_name(call_record.getContact_type());
                        callRecordList.setFee(call_record.getCall_cost());
                        callRecordList.setSpecial_offer("0.0");
                        callRecordLists.add(callRecordList);
                    }
                }
            }
        }
        return callRecordLists;
    }

    private List<MessageRecordList> getMessageRecordList(YysRootBean yysRootBean) {

        List<MessageRecordList> messageRecordLists = new ArrayList<>();

        List<Sms_info> sms_infos = new ArrayList<Sms_info>();
        if (yysRootBean.getData() != null) {
            if (yysRootBean.getData().getTask_data() != null) {
                if (yysRootBean.getData().getTask_data().getSms_info() != null) {
                    sms_infos = yysRootBean.getData().getTask_data().getSms_info();
                }
            }
        }
        if (sms_infos != null && sms_infos.size() != 0) {
            for (Sms_info sms_info : sms_infos) {
                List<Sms_record> sms_records = sms_info.getSms_record();
                if (sms_records != null && sms_records.size() != 0) {
                    for (Sms_record sms_record : sms_records) {
                        MessageRecordList messageRecordList = new MessageRecordList();
                        messageRecordList.setSend_time(sms_record.getMsg_start_time());
                        if (sms_record.getMsg_type().equals("发送")) {
                            messageRecordList.setTrade_way(1);
                        } else if (sms_record.getMsg_type().equals("接收")) {
                            messageRecordList.setTrade_way(2);
                        } else {
                            messageRecordList.setTrade_way(3);
                        }
                        messageRecordList.setReceiver_phone(sms_record.getMsg_other_num());
                        messageRecordList.setBusiness_name(sms_record.getMsg_biz_name());
                        messageRecordList.setFee(sms_record.getMsg_cost());
                        messageRecordList.setTrade_addr(sms_record.getMsg_address());
                        if (sms_record.getMsg_channel().indexOf("短信") >= 0) {
                            messageRecordList.setTrade_type("1");
                        } else {
                            if (sms_record.getMsg_channel().equals("彩信")) {
                                messageRecordList.setTrade_type("2");
                            } else {
                                messageRecordList.setTrade_type("3");
                            }
                        }
                        messageRecordLists.add(messageRecordList);
                    }
                }
            }
        }
        return messageRecordLists;
    }

    private List<BillList> getBillList(YysRootBean yysRootBean) {
        List<BillList> billLists = new ArrayList<>();
        List<Bill_info> bill_infos = new ArrayList<Bill_info>();
        if (yysRootBean.getData() != null) {
            if (yysRootBean.getData().getTask_data() != null) {
                if (yysRootBean.getData().getTask_data().getBill_info() != null) {
                    bill_infos = yysRootBean.getData().getTask_data().getBill_info();
                }
            }
        }
        if (bill_infos != null && bill_infos.size() != 0) {
            for (Bill_info bill_info : bill_infos) {
                BillList billList = new BillList();
                billList.setMonth(bill_info.getBill_cycle());
                billList.setCall_pay(bill_info.getBill_total());
                billList.setPreferential_fee(bill_info.getBill_discount());
                billList.setGeneration_fee("0.0");
                billList.setScore(bill_info.getBill_total());
                List<Bill_record> bill_records = bill_info.getBill_record();
                if (bill_records != null && bill_records.size() != 0) {
                    for (Bill_record bill_record : bill_records) {
                        if(bill_record.getFee_name()!=null&&!bill_record.getFee_name().equals("")){
                            if (bill_record.getFee_name().indexOf("固定") >= 0) {
                                billList.setPackage_fee(bill_record.getFee_amount());
                                continue;
                            }

                            if (bill_record.getFee_name().indexOf("语音") >= 0) {
                                billList.setTel_fee(bill_record.getFee_amount());
                                continue;
                            }

                            if (bill_record.getFee_name().indexOf("流量") >= 0) {
                                billList.setNet_fee(bill_record.getFee_amount());
                                continue;
                            }

                            if (bill_record.getFee_name().indexOf("短信") >= 0) {
                                billList.setMsg_fee(bill_record.getFee_amount());
                                continue;
                            }

                            if (bill_record.getFee_name().indexOf("增值业务") >= 0) {
                                billList.setAddtional_fee(bill_record.getFee_amount());
                                continue;
                            }

                            if (bill_record.getFee_name().indexOf("其他") >= 0) {
                                billList.setOther_fee(bill_record.getFee_amount());
                                continue;
                            }
                        }
                    }
                }
                billLists.add(billList);
            }
        }
        return billLists;
    }

    private List<NateWorkFlowList> getDataInfoList(YysRootBean yysRootBean) {
        List<NateWorkFlowList> nateWorkFlowLists = new ArrayList<>();
        List<Data_info> data_infos = new ArrayList<Data_info>();
        if (yysRootBean.getData() != null) {
            if (yysRootBean.getData().getTask_data() != null) {
                if (yysRootBean.getData().getTask_data().getData_info() != null) {
                    data_infos = yysRootBean.getData().getTask_data().getData_info();
                }
            }
        }
        if (data_infos != null && data_infos.size() != 0) {
            for (Data_info data_info : data_infos) {
                List<Data_record> data_records = data_info.getData_record();
                if (data_records != null && data_records.size() != 0) {
                    for (Data_record data_record : data_records) {
                        NateWorkFlowList nateWorkFlowList = new NateWorkFlowList();
                        nateWorkFlowList.setFee(data_record.getData_cost());
                        nateWorkFlowList.setNet_type(data_record.getData_type());
                        nateWorkFlowList.setNet_way("CMNET");// 上网方式 未找到信息
                        nateWorkFlowList.setPreferential_fee("0.0");// 优惠项/套餐 未找到信息
                        nateWorkFlowList.setStart_time(data_record.getData_start_time());
                        nateWorkFlowList.setTotal_time(data_record.getData_time());
                        nateWorkFlowList.setTotal_traffic(data_record.getData_size());
                        nateWorkFlowList.setTrade_addr(data_record.getData_address());
                        nateWorkFlowLists.add(nateWorkFlowList);
                    }
                }
            }
        }
        return nateWorkFlowLists;
    }

    private List<PaymentRecordList> getPayRecordList(YysRootBean yysRootBean) {
        List<PaymentRecordList> paymentRecordLists = new ArrayList<>();
        List<Payment_info> payment_infos = new ArrayList<Payment_info>();
        if (yysRootBean.getData() != null) {
            if (yysRootBean.getData().getTask_data() != null) {
                if (yysRootBean.getData().getTask_data().getPayment_info() != null) {
                    payment_infos = yysRootBean.getData().getTask_data().getPayment_info();
                }
            }
        }

        if (payment_infos != null && payment_infos.size() != 0) {
            for (Payment_info payment_info : payment_infos) {
                PaymentRecordList paymentRecordList = new PaymentRecordList();
                paymentRecordList.setFee(payment_info.getPay_fee());
                paymentRecordList.setRecharge_time(payment_info.getPay_date() + " 00:00:00");
                paymentRecordList.setRecharge_way(payment_info.getPay_type());
                paymentRecordLists.add(paymentRecordList);
            }
        }
        return paymentRecordLists;
    }

    private void saveFlowBackLog(int userId,String type,String deviceId){
        FlowBackLog flowBackLog = new FlowBackLog();
        flowBackLog.setDeviceId(deviceId);
        flowBackLog.setType(type);
        flowBackLog.setUserId(userId);
        flowBackLog.setStatus("0");
        flowBackLog.setCreationDate(DateUtil.getCurrentTimeStamp());
        flowBackLogDao.insertFlowBackLog(flowBackLog);
    }
}
