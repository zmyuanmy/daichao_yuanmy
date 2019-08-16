package com.jbb.mgt.core.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.domain.HumanLpPhone;
import com.jbb.mgt.core.domain.QiFengUserData;
import com.jbb.mgt.core.domain.QiFengUserLink;
import com.jbb.mgt.core.service.QiFengService;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.StringUtil;
import com.qftx.other.QftxOpen4OutUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("QiFengService")
public class QiFengServiceImpl implements QiFengService {

    private static Logger logger = LoggerFactory.getLogger(QiFengServiceImpl.class);

    @Override
    public void qiFengCustAdd(List<HumanLpPhone> humans) {
        logger.info(">qiFengCustAdd(), humans = " + humans);
        String url = PropertyManager.getProperty("jbb.mgt.qifeng.api.url");
        String authId = PropertyManager.getProperty("jbb.mgt.qifeng.api.authId");
        String authSecret = PropertyManager.getProperty("jbb.mgt.qifeng.api.authSecret");

        // 公司名称
        String companyId = PropertyManager.getProperty("jbb.mgt.qifeng.api.companyId");
        // 请求的接口类型
        String reqType = PropertyManager.getProperty("jbb.mgt.qifeng.api.reqType");
        // 类型:1-资源，2客户，3签约客户
        String type = PropertyManager.getProperty("jbb.mgt.qifeng.api.type");
        String ownerAcc = PropertyManager.getProperty("jbb.mgt.qifeng.api.ownerAcc");

        Timestamp creationDate = DateUtil.getCurrentTimeStamp();
        Date ts = new Date(creationDate.getTime());

        // 拼接数据
        JSONArray array = new JSONArray();
        JSONObject obj = new JSONObject();
        for (int i = 0; i < humans.size(); i++) {
            HumanLpPhone human = humans.get(i);
            if (human != null) {
                // 生成唯一标识 客户ID
                String time = new SimpleDateFormat("yyyyMMddHHmmss").format(DateUtil.getCurrentTimeStamp().getTime());
                String reqId = time + UUID.randomUUID().toString().substring(0, 20).toLowerCase().replace("-", "");
                String sex = "";
                String name = "";
                if (!StringUtil.isEmpty(human.getSex())) {
                    sex = human.getSex().equals("男") ? "1" : "2";
                }
                if (!StringUtil.isEmpty(human.getUserName())) {
                    name = human.getUserName().length() > 1 ? human.getUserName().substring(0, 1) : human.getUserName();
                }
                String date = human.getcDate() == null ? ""
                    : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(human.getcDate().getTime());
                List<QiFengUserLink> links = new ArrayList<QiFengUserLink>();
                links.add(new QiFengUserLink(sex, human.getPhoneNumber(), date));
                QiFengUserData userDates = new QiFengUserData(reqId, name, links);
                array.add(userDates);
            }
        }
        obj.put("type", type);
        obj.put("data", array);
        if (StringUtil.isEmpty(ownerAcc)) {
            obj.put("ownerAcc", ownerAcc);
        }

        logger.debug("qiFengCustAdd data:" + obj.toString());

        // 主内容：
        String content = QftxOpen4OutUtil.content(companyId, reqType, ts, obj.toString());
        logger.debug("qiFengCustAdd content:" + content);
        // 发送：
        try {
            String result = QftxOpen4OutUtil.aes(url, authId, authSecret, content, ts);
            logger.debug("qiFengCustAdd result:" + result);
            if (!StringUtil.isEmpty(result)) {
                JSONObject jsonObject = JSONObject.fromObject(result);
                String code = jsonObject.getString("code");
                String desc = jsonObject.getString("desc");
                if (code.equals("1")) {
                    logger.debug("qiFeng CustAdd success");
                } else {
                    logger.warn("qiFeng CustAdd error = " + desc);
                }
            }
        } catch (Exception e) {
            logger.error("qiFengCustAdd error , error = " + e.getMessage());
        }
        logger.info("<qiFengCustAdd()");
    }

}
