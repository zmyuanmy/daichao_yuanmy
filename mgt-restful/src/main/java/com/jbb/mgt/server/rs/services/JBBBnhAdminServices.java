package com.jbb.mgt.server.rs.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.cache.NoCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jbb.mgt.rs.action.creditCard.CreditCardAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.Constants;

@Path("bnhadmin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@NoCache
public class JBBBnhAdminServices extends BasicRestfulServices {

    private static Logger logger = LoggerFactory.getLogger(JBBBnhAdminServices.class);
    @GET
    @Path("/cityList")
    public ActionResponse selectCity(@HeaderParam("API_KEY") String userKey) throws Exception {
        logger.debug(">selectCity()");
        CreditCardAction action = getBean(CreditCardAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_129};
        action.validateUserAccess(ps);
        action.selectCity();
        logger.debug("<selectCity()");
        return action.getActionResponse();
    }

}
