package com.jbb.mgt.server.rs.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.cache.NoCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jbb.mgt.rs.action.creditCard.CreditCardAction;
import com.jbb.mgt.rs.action.creditCard.CreditCardCategorieAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.Constants;

@Path("bnhadmin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@NoCache
public class JBBBnhAdminServices extends BasicRestfulServices {

    private static Logger logger = LoggerFactory.getLogger(JBBBnhAdminServices.class);

    @GET
    @Path("/cardCategorieList")
    public ActionResponse getCreditCardCategorieById(@HeaderParam("API_KEY") String userKey) throws Exception {
        logger.debug(">getCreditCardCategorie()");
        CreditCardCategorieAction action = getBean(CreditCardCategorieAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_129};
        action.validateUserAccess(ps);
        action.getCreditCardCategorie();
        logger.debug("<getCreditCardCategorie()");
        return action.getActionResponse();
    }

    @GET
    @Path("/cardCategorie")
    public ActionResponse getCreditCardCategorieById(@HeaderParam("API_KEY") String userKey,
        @QueryParam("categoryId") Integer categoryId) throws Exception {
        logger.debug(">getCreditCardCategorieById()");
        CreditCardCategorieAction action = getBean(CreditCardCategorieAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_129};
        action.validateUserAccess(ps);
        action.getCreditCardCategorieById(categoryId);
        logger.debug("<getCreditCardCategorieById()");
        return action.getActionResponse();
    }

    @POST
    @Path("/cardCategorie")
    public ActionResponse saveCreditCardCategorie(@HeaderParam("API_KEY") String userKey,
        CreditCardCategorieAction.Request req) throws Exception {
        logger.debug(">saveCreditCardCategorie()");
        CreditCardCategorieAction action = getBean(CreditCardCategorieAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_129};
        action.validateUserAccess(ps);
        action.saveCreditCardCategorie(req);
        logger.debug("<saveCreditCardCategorie()");
        return action.getActionResponse();
    }

    @GET
    @Path("/creditCardList")
    public ActionResponse getCreditCardByCity(@HeaderParam("API_KEY") String userKey, @QueryParam("pageNo") int pageNo,
        @QueryParam("pageSize") int pageSize, @QueryParam("cityName") String cityName,
        @QueryParam("categoryId") Integer categoryId, @QueryParam("startDate") String startDate,
        @QueryParam("endDate") String endDate) throws Exception {
        logger.debug(">getCreditCardByCity()");
        CreditCardAction action = getBean(CreditCardAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_129};
        action.validateUserAccess(ps);
        action.getCreditCardByCity(pageNo, pageSize, cityName, categoryId, null, startDate, endDate);
        logger.debug("<getCreditCardByCity()");
        return action.getActionResponse();
    }

    @GET
    @Path("/creditCard")
    public ActionResponse getCreditCardByCategoryId(@HeaderParam("API_KEY") String userKey,
        @QueryParam("creditId") Integer creditId) throws Exception {
        logger.debug(">getCreditCardByCategoryId()");
        CreditCardAction action = getBean(CreditCardAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_129};
        action.validateUserAccess(ps);
        action.getCreditCardById(creditId);
        logger.debug("<getCreditCardByCategoryId()");
        return action.getActionResponse();
    }

    @POST
    @Path("/creditCard")
    public ActionResponse insertCreditCard(@HeaderParam("API_KEY") String userKey, CreditCardAction.Request req)
        throws Exception {
        logger.debug(">insertCreditCard()");
        CreditCardAction action = getBean(CreditCardAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_129};
        action.validateUserAccess(ps);
        action.insertCreditCard(req);
        logger.debug("<insertCreditCard()");
        return action.getActionResponse();
    }

    @PUT
    @Path("/creditCard")
    public ActionResponse updateCreditCard(@HeaderParam("API_KEY") String userKey, CreditCardAction.Request req)
        throws Exception {
        logger.debug(">updateCreditCard()");
        CreditCardAction action = getBean(CreditCardAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_129};
        action.validateUserAccess(ps);
        action.updateCreditCard(req);
        logger.debug("<updateCreditCard()");
        return action.getActionResponse();
    }

    @DELETE
    @Path("/creditCard")
    public ActionResponse deleteCreditCard(@HeaderParam("API_KEY") String userKey,
        @QueryParam("creditId") Integer creditId) throws Exception {
        logger.debug(">deleteCreditCard()");
        CreditCardAction action = getBean(CreditCardAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_129};
        action.validateUserAccess(ps);
        action.deleteCreditCard(creditId);
        logger.debug("<deleteCreditCard()");
        return action.getActionResponse();
    }

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
