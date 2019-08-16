
package com.jbb.mgt.rs.action.platform;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.Account;
import com.jbb.mgt.core.domain.OrgRecharges;
import com.jbb.mgt.core.domain.Platform;
import com.jbb.mgt.core.domain.TeleMarketing;
import com.jbb.mgt.core.domain.TeleMarketingDetail;
import com.jbb.mgt.core.service.AccountService;
import com.jbb.mgt.core.service.LoanPlatformService;
import com.jbb.mgt.core.service.OrgRechargesService;
import com.jbb.mgt.core.service.TeleMarketingDetailService;
import com.jbb.mgt.core.service.TeleMarketingService;
import com.jbb.mgt.rs.action.batch.POIUtil;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.ParsingException;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.StringUtil;
import com.jbb.server.common.util.UTF8Util;

import lombok.Getter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(PlatformsUploadAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PlatformsUploadAction extends BasicAction {

    public static final String ACTION_NAME = "PlatformsUploadAction";

    private static final int MAX_CONTENT_LENGTH_M = 2;
    private static final int MAX_CONTENT_LENGTH = MAX_CONTENT_LENGTH_M * 1024 * 1024;

    private static final int MAX_LINES = 3000;
    private final static String xls = "xls";
    private final static String xlsx = "xlsx";
    private final static String txt = "txt";
    private final static String csv = "csv";

    private static Logger logger = LoggerFactory.getLogger(PlatformsUploadAction.class);
    int unitPrice = PropertyManager.getIntProperty("jbb.mgt.phonecheck.price", 5);

    @Autowired
    TeleMarketingService teleMarketingService;
    @Autowired
    private AccountService accountService;
    @Autowired
    TeleMarketingDetailService detailService;
    @Autowired
    private LoanPlatformService loanPlatformService;

    private Response response;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void platformsUpload(byte[] content, String fileName) throws IOException {
        // TODO Auto-generated method stub
        if ((content == null) || (content.length == 0)) {
            logger.debug("<uploadFile() missing file content");
            throw new WrongParameterValueException("jbb.mgt.exception.file.empty");
        }

        // if (content.length > MAX_CONTENT_LENGTH) {
        // logger.debug("<uploadFile() file content > MAX_CONTENT_LENGTH");
        // throw new WrongParameterValueException("jbb.mgt.exception.file.maxLength", "zh",
        // String.valueOf(MAX_CONTENT_LENGTH_M));
        // }
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        InputStream input = new ByteArrayInputStream(content);
        List<String[]> List = POIUtil.readExcel(fileName, input);
        for (int i = 0; i < List.size(); i++) {
            String[] cell = List.get(i);
            try {
                Platform platform = new Platform();
                platform.setName(cell[0]);
                platform.setShortName(cell[1]);
                platform.setGroupName(cell[2]);
                Account a = accountService.getAccountByNickName(cell[3]);
                platform.setSalesId(a.getAccountId());
                platform.setUrl(cell[4]);
                Integer priceMode = null;
                if (cell[5].equalsIgnoreCase("cpa")) {
                    priceMode = 1;
                    platform.setCpaPrice((int)(Double.parseDouble(cell[6]) * 100));

                } else if (cell[5].equalsIgnoreCase("uv")) {
                    priceMode = 2;
                    platform.setUvPrice((int)(Double.parseDouble(cell[6]) * 100));
                } else {
                    priceMode = 1;
                    platform.setCpaPrice((int)(Double.parseDouble(cell[6]) * 100));
                }
                platform.setPriceMode(priceMode);
                platform.setDesc1(cell[7]);
                platform.setMinAmount((int)(Double.parseDouble(cell[8]) * 100));
                platform.setMaxAmount((int)(Double.parseDouble(cell[9]) * 100));
                platform.setInterestRate(cell[10]);
                platform.setEstimatedUvCnt(Integer.parseInt(cell[11]));
                platform.setLoanPeriod(cell[12]);
                platform.setApprovalTime(cell[13]);
                platform.setMinBalance((int)(Double.parseDouble(cell[14]) * 100));
                platform.setSupportIos(cell[15].equals("是") ? true : false);
                platform.setSupportAndroid(cell[16].equals("是") ? true : false);
                platform.setLogo(cell[17]);
                platform.setCreator(this.account.getAccountId());
                platform.setType(1);
                loanPlatformService.savePlatform(platform);
            } catch (DuplicateKeyException e) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("name", cell[0]);
                map.put("shortName", cell[1]);
                map.put("msg", "短名称重复！");
                list.add(map);
            } catch (ArrayIndexOutOfBoundsException e) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("name", cell[0]);
                map.put("shortName", cell[1]);
                map.put("msg", "数据项未填写完整！");
                list.add(map);
            } catch (NullPointerException e) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("name", cell[0]);
                map.put("shortName", cell[1]);
                map.put("msg", "商务人员不存在！");
                list.add(map);
            } catch (ParsingException | NumberFormatException e) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("name", cell[0]);
                map.put("shortName", cell[1]);
                map.put("msg", "数字填写格式有误！");
                list.add(map);

            } catch (Exception e) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("name", cell[0]);
                map.put("shortName", cell[1]);
                map.put("msg", "其他异常！");
                list.add(map);
            }
        }
        this.response.list = list;
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    @Getter
    public static class Response extends ActionResponse {
        List<Map<String, String>> list;
    }

}
