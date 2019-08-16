package com.jbb.mgt.rs.action.dflow;

import java.util.ArrayList;
import java.util.List;

import com.jbb.mgt.core.domain.OrgDflowBase;
import com.jbb.mgt.core.service.OrgDFlowBaseService;
import com.jbb.server.common.exception.WrongParameterValueException;
import org.apache.velocity.runtime.directive.Foreach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.DataFlowBase;
import com.jbb.mgt.core.service.DataFlowBaseService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.mgt.server.rs.pojo.RsDataFlowBase;

/**
 * 流量基本信息Action
 * 
 * @author wyq
 * @date 2018/04/28
 */
@Service(DataFlowBaseAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DataFlowBaseAction extends BasicAction {

    public static final String ACTION_NAME = "DataFlowConfigAction";

    private static Logger logger = LoggerFactory.getLogger(DataFlowBaseAction.class);

    private Response response;

    @Autowired
    private OrgDFlowBaseService orgDFlowBaseService;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    @Autowired
    private DataFlowBaseService dataFlowBaseService;

    public void getDataFlowConfig(Boolean includeHidden) {
        logger.debug(">getDataFlowConfig()");
        boolean includeHiddenF = includeHidden == null || includeHidden == false ? false : includeHidden;
        this.response.dataFlowBaseList = new ArrayList<RsDataFlowBase>();
        List<DataFlowBase> list = null;
        list = orgDFlowBaseService.getOrgDflowBase(this.account.getOrgId(), includeHiddenF);
        if (list.size() != 0) {
            for (DataFlowBase base : list) {
                this.response.dataFlowBaseList.add(new RsDataFlowBase(base));
            }
        } else {
            list = dataFlowBaseService.getDataFlowBases(includeHiddenF);
            for (DataFlowBase base : list) {
                this.response.dataFlowBaseList.add(new RsDataFlowBase(base));
            }
        }
        logger.debug("<getDataFlowConfig()");
    }

    public void saveOrgDFlowBase(DataFlowBaseAction.Request req) {
        logger.debug(">saveOrgDFlowBase(), req = " + req);
        if (this.account.getOrgId() == 1 && null != req && null != req.dflows && req.dflows.size() > 0) {
            orgDFlowBaseService.deleteOrgDflowBase(req.dflows.get(0).getOrgId());
            for (OrgDflowBase odb : req.dflows) {
                if (null == odb.getOrgId() || odb.getOrgId() <= 0) {
                    throw new WrongParameterValueException("jbb.error.exception.wrong.paramvalue", "zh", "orgId");
                }
                if (null == odb.getDflowId() || odb.getDflowId() <= 0) {
                    throw new WrongParameterValueException("jbb.error.exception.wrong.paramvalue", "zh", "dflowId");
                }
            }
            orgDFlowBaseService.saveOrgDFlowBase(req.dflows);
        }
        logger.debug("<saveOrgDFlowBase()");
    }

    public void getOrgDFlowBase(Integer orgId) {
        logger.debug(">getOrgDFlowBase(), orgId = " + orgId);
        if (null != orgId && orgId > 0) {
            int[] ints = orgDFlowBaseService.gerdflowId(orgId);
            this.response.dflows = ints;
        }
        logger.debug("<getOrgDFlowBase()");
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {

        private List<RsDataFlowBase> dataFlowBaseList;
        private int[] dflows;

        public List<RsDataFlowBase> getDataFlowBaseList() {
            return dataFlowBaseList;
        }

        public int[] getDflows() {
            return dflows;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        public int dataFlowId;
        public List<OrgDflowBase> dflows;
    }
}
