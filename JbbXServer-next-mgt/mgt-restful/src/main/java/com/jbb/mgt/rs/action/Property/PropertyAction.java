package com.jbb.mgt.rs.action.Property;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.Property;
import com.jbb.mgt.core.service.SystemService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.mgt.server.rs.pojo.RsProperty;
import com.jbb.server.common.exception.AccessDeniedException;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.StringUtil;

/**
 * Property Action类
 *
 * @author wyq
 * @date 2018/6/6 19:49
 */
@Service(PropertyAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PropertyAction extends BasicAction {

    public static final String ACTION_NAME = "PropertyAction";

    private static Logger logger = LoggerFactory.getLogger(PropertyAction.class);

    private Response response;

    @Autowired
    private SystemService systemService;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new PropertyAction.Response();
    }

    public void getPropertiesByName(String name) {
        logger.debug(">getPropertiesByName, name=" + name);
        if (this.account.getOrgId() != 1) {
            throw new AccessDeniedException("jbb.mgt.exception.HaveNoRightToAccess");
        }
        List<Property> list = systemService.selectSystemPropertiesByName(name);
        this.response.properties = new ArrayList<RsProperty>(list.size());
        for (Property property : list) {
            this.response.properties.add(new RsProperty(property));
        }
        logger.debug(">getPropertiesByName");
    }

    public void saveOrUpdatePropertiesByName(String name, String value, Request req) {
        logger.debug(">saveOrUpdatePropertiesByName, name=" + name + ", value=" + value);
        if (this.account.getOrgId() != 1) {
            throw new AccessDeniedException("jbb.mgt.exception.HaveNoRightToAccess");
        }
        if (null != req && null != req.properties && req.properties.size() != 0) {
            for (int i = 0; i < req.properties.size(); i++) {
                if (!StringUtil.isEmpty(req.properties.get(i).getName())) {
                    systemService.saveSystemProperty(req.properties.get(i).getName(), req.properties.get(i).getValue());
                }
            }
        } else {
            if (StringUtil.isEmpty(name)) {
                throw new WrongParameterValueException("jbb.error.exception.wrong.paramvalue", "zh", "name");
            }
            if (StringUtil.isEmpty(value)) {
                value = null;
            }
            systemService.saveSystemProperty(name, value);
        }

        logger.debug(">saveOrUpdatePropertiesByName");
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        private List<RsProperty> properties;

        public List<RsProperty> getProperties() {
            return properties;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        public List<Property> properties;
    }

}
