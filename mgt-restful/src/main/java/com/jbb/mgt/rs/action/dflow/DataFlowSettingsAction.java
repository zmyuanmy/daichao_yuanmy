package com.jbb.mgt.rs.action.dflow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.DataFlowSetting;
import com.jbb.mgt.core.service.ChannelService;
import com.jbb.mgt.core.service.DataFlowSettingService;
import com.jbb.mgt.core.service.UserApplyRecordService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.exception.ObjectNotFoundException;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.DateUtil;

/**
 * 流量控制的Action
 * 
 * @author wyq
 * @date 2018/04/27
 */
@Service(DataFlowSettingsAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DataFlowSettingsAction extends BasicAction {

    public static final String ACTION_NAME = "DataFlowSettingsAction";

    private static Logger logger = LoggerFactory.getLogger(DataFlowSettingsAction.class);

    @Autowired
    private DataFlowSettingService dataFlowSettingService;

    @Autowired
    private UserApplyRecordService userApplyRecordService;

    @Autowired
    private ChannelService channelService;

    private Response response;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void getDataFlowSetting() {
        logger.debug(">getDataFlowSetting");
        int orgId = this.account.getOrgId();
        DataFlowSetting dataFlowSetting = dataFlowSettingService.getDataFlowSettingByOrgId(orgId);
        if (dataFlowSetting != null) {
            this.response.dataFlowSetting = dataFlowSetting;
        }
        logger.debug("<getDataFlowSetting");
    }

    public void updateDataFlowSetting(Request req) {
        int orgId = this.account.getOrgId();
        logger.debug(">updateDataFlowSetting() , orgId ={}, closed={}, min={}, max={}, dataFlowId={}", orgId,
            req.closed, req.minValue, req.maxValue, req.dataFlowId);
        if (!req.closed) {
            boolean isDelegate = channelService.selectExistsDelegate(orgId);
            if (!isDelegate) {
                throw new WrongParameterValueException("jbb.mgt.exception.dataFlow.notDelegate");
            }
        }
        // 更新导流设置时，需要检查 按天导流的量，是否到达最达量。
        DataFlowSetting setting = dataFlowSettingService.getDataFlowSettingByOrgId(orgId);
        // 导流关闭时true=1不判断,导流打开时false=0判断
        if (setting != null && !setting.isClosed()) {
            int jbbCount = userApplyRecordService.countUserApply(orgId, null, DateUtil.getCurrentTimeStamp(),
                DateUtil.getCurrentTimeStamp());
            if (jbbCount < setting.getMaxValue()) {
                throw new WrongParameterValueException("jbb.mgt.exception.dataFlow.updateError");
            }
        }
        DataFlowSetting dataFlowSetting = generateDataFlowSettings(orgId, req);
        dataFlowSettingService.saveDataFlowSetting(dataFlowSetting);
        logger.debug("<updateDataFlowSetting");

    }

    public void updateDataFlowSettingNotMax(Integer orgId, Request req) {
        logger.debug(">updateDataFlowSettingNotMax() , req" + req);
        if (null == orgId || orgId <= 1) {
            throw new ObjectNotFoundException("jbb.mgt.exception.parameterObject.notFound", "zh", "DataFlowSetting");
        }
        if (req == null) {
            throw new WrongParameterValueException("jbb.mgt.exception.dataFlow.Error");
        }
        DataFlowSetting dataFlowSetting = generateDataFlowSettings(orgId, req);
        if (!dataFlowSetting.isClosed()) {
            boolean isDelegate = channelService.selectExistsDelegate(orgId);
            if (!isDelegate) {
                throw new WrongParameterValueException("jbb.mgt.exception.dataFlow.notDelegate");
            }
        }
        dataFlowSettingService.updateDataFlowSetting(dataFlowSetting);
        logger.debug("<updateDataFlowSettingNotMax");
    }

    private DataFlowSetting generateDataFlowSettings(int orgId, Request req) {
        DataFlowSetting setting = new DataFlowSetting();
        setting.setOrgId(orgId);
        setting.setDataFlowId(req.dataFlowId);
        if (req.minValue > req.maxValue) {
            throw new WrongParameterValueException("jbb.mgt.exception.dataFlow.wrongValue");
        }
        setting.setMinValue(req.minValue);
        setting.setMaxValue(req.maxValue);
        setting.setClosed(req.closed);
        return setting;
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {

        private DataFlowSetting dataFlowSetting;

        public DataFlowSetting getDataFlowSetting() {
            return dataFlowSetting;
        }

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        public int dataFlowId;
        public int minValue;
        public int maxValue;
        public boolean closed;
    }
}
