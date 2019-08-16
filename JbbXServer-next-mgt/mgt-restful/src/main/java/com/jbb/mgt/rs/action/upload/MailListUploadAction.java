package com.jbb.mgt.rs.action.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.User;
import com.jbb.mgt.core.domain.UserContant;
import com.jbb.mgt.core.service.UserContantService;
import com.jbb.mgt.core.service.UserService;
import com.jbb.mgt.core.service.XjlUserService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.exception.ObjectNotFoundException;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.PhoneNumberUtil;
import com.jbb.server.common.util.StringUtil;
import com.jbb.server.common.util.UTF8Util;

import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.property.StructuredName;
import ezvcard.property.Telephone;

@Service(MailListUploadAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MailListUploadAction extends BasicAction {

    public static final String ACTION_NAME = "MailListUploadAction";

    private static final int MAX_CONTENT_LENGTH_M = 10;
    private static final int MAX_CONTENT_LENGTH = MAX_CONTENT_LENGTH_M * 1024 * 1024;

    private static Logger logger = LoggerFactory.getLogger(MailListUploadAction.class);

    private Response response;

    @Autowired
    private UserService userService;

    @Autowired
    private XjlUserService xjlUserService;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new MailListUploadAction.Response();
    }

    @Autowired
    private UserContantService userContantService;

    public void uploadContants(File file, Integer userId) throws IOException {
        logger.debug(">uploadContants()");
        if (null == userId || userId == 0) {
            logger.debug("<uploadContants() missing userId");
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "userId");
        }

        User user = userService.selectUserById(userId, this.account.getOrgId());
        if (user == null) {
            throw new ObjectNotFoundException("jbb.mgt.exception.user.notFound", "zh");
        }

        if (user.getJbbUserId() == null) {
            throw new ObjectNotFoundException("jbb.mgt.exception.jbbUser.notFound", "zh");
        }

        if (file == null) {
            logger.debug("<uploadContants() missing file");
            throw new MissingParameterException("jbb.mgt.exception.file.empty");
        }

        InputStream inputStream = new FileInputStream(file);
        byte[] byt = new byte[inputStream.available()];
        inputStream.read(byt);
        if ((byt == null) || (byt.length == 0)) {
            logger.debug("<uploadContants() missing file");
            throw new MissingParameterException("jbb.mgt.exception.file.empty");
        }
        if (byt.length > MAX_CONTENT_LENGTH) {
            logger.debug("<uploadContants() file byte > MAX_CONTENT_LENGTH  fileSize:{}", byt.length);
            throw new WrongParameterValueException("jbb.mgt.exception.file.maxLength", "zh",
                String.valueOf(MAX_CONTENT_LENGTH_M));
        }
        String code;
        if (UTF8Util.isUtf8(byt)) {
            code = "UTF-8";
        } else {
            code = "gb2312";
        }
        InputStreamReader reader = new InputStreamReader(new FileInputStream(file), code);
        List<VCard> vCards = Ezvcard.parse(reader).all();
        if (CollectionUtils.isEmpty(vCards)) {
            logger.debug("<uploadContants() error file");
            throw new WrongParameterValueException("jbb.mgt.exception.file.readError");
        }

        ArrayList<UserContant> userContants = new ArrayList<>();
        for (VCard vCard : vCards) {
            // 用户姓名读取

            String userName = getFormattedName(vCard);
            // 手机号读取
            List<Telephone> telephoneNumbers = vCard.getTelephoneNumbers();
            String vcard = null;
            if (vCard != null) {
                vcard = JSON.toJSONString(vCard).replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "");
            }
            if (userName == null || userName.isEmpty()) {
                userName = "";
            } else {
                userName = userName.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "");

            }
            for (Telephone telephone : telephoneNumbers) {
                // String phoneNumber = telephone.getText().replaceAll("[^0-9]+", "");
                String phoneNumber = PhoneNumberUtil.trimTelNum(telephone.getText());
                phoneNumber = phoneNumber.length() > 20 ? phoneNumber.substring(0, 20) : phoneNumber;
                UserContant userContant = new UserContant(user.getJbbUserId(), phoneNumber, userName, vcard);
                userContants.add(userContant);

            }
            if (CollectionUtils.isEmpty(telephoneNumbers)) {
                vCard.setFormattedName(userName);
                UserContant userContant = new UserContant(user.getJbbUserId(), "", userName, vcard);
                userContants.add(userContant);
            }
        }
        userContantService.insertOrUpdateUserContant(userContants);
        logger.debug("<uploadContants()");
    }

    private String getFormattedName(VCard vCard) {
        StringBuffer name = new StringBuffer("");
        StructuredName fn = vCard.getStructuredName();
        if (fn == null) {
            return "";
        }
        if (StringUtils.isNotEmpty(fn.getFamily())) {
            name.append(fn.getFamily());
        }
        if (StringUtils.isNotEmpty(fn.getGiven())) {
            name.append(fn.getGiven());
        }
        return String.valueOf(name);
    }

    public void getUserContants(Integer userId) {
        logger.debug(">getUserContants() userId:{}", userId);
        if (null == userId || userId == 0) {
            logger.debug("<getUserContants() missing userId");
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "userId");
        }
        User user = userService.selectUserById(userId, this.account.getOrgId());
        if (user == null) {
            throw new ObjectNotFoundException("jbb.mgt.exception.user.notFound", "zh");
        }
        this.response.userContants = userContantService.selectUserContantByJbbUserId(user.getJbbUserId());
        logger.debug("<uploadMailList()");
    }

    public void getXjlUserContants(Integer userId) {
        logger.debug(">getUserContants() userId:{}", userId);
        if (null == userId || userId == 0) {
            logger.debug("<getUserContants() missing userId");
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "userId");
        }
        User user = userService.selectXjlUserByApplyId(null, userId, this.account.getOrgId());
        if (user == null && !checkUserExistInXjl(userId)) {
            throw new ObjectNotFoundException("jbb.mgt.exception.user.notFound", "zh");
        }
        this.response.userContants = userContantService.selectUserContantByJbbUserId(user.getJbbUserId());
        logger.debug("<uploadMailList()");
    }

    private boolean checkUserExistInXjl(Integer userId) {
        return xjlUserService.checkExistByUserId(this.account.getOrgId(), userId, null);
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {

        private List<UserContant> userContants;

        public List<UserContant> getUserContants() {
            return userContants;
        }
    }

}
