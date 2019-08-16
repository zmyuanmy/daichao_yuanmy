package com.jbb.mgt.server.rs.services;

import java.io.IOException;

import javax.ws.rs.Consumes;
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

import com.jbb.mgt.rs.action.Property.PropertyAction;
import com.jbb.mgt.rs.action.account.AccountAction;
import com.jbb.mgt.rs.action.community.CommunityPfPhoneAction;
import com.jbb.mgt.rs.action.dflow.DataFlowBaseAction;
import com.jbb.mgt.rs.action.dflow.DataFlowSettingsAction;
import com.jbb.mgt.rs.action.h5Merchant.H5MerchantAction;
import com.jbb.mgt.rs.action.manualRechage.ManualRechageAction;
import com.jbb.mgt.rs.action.organization.CreatOrganizationAction;
import com.jbb.mgt.rs.action.organization.OrganizationAction;
import com.jbb.mgt.rs.action.organization.OrganizationLenderAction;
import com.jbb.mgt.rs.action.organization.OrganizationRelationAction;
import com.jbb.mgt.rs.action.platform.PlatformStatisticUploadAction;
import com.jbb.mgt.rs.action.platform.PlatformsUploadAction;
import com.jbb.mgt.rs.action.user.UserAction;
import com.jbb.mgt.rs.action.user.UserApplyRecordAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.Constants;


@Path("admin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@NoCache
public class JbbMgtAdminService extends BasicRestfulServices {

    private static Logger logger = LoggerFactory.getLogger(JbbMgtAdminService.class);

    @GET
    @Path("/system/properties")
    public ActionResponse getPropertiesByName(@HeaderParam(API_KEY) String userKey, @QueryParam("name") String name) {
        logger.debug(">getPropertiesByName()");
        PropertyAction action = getBean(PropertyAction.ACTION_NAME);
        // 检查userKey
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        // 验证权限
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P_CHANNEL, Constants.MGT_P1_101,
            Constants.MGT_P1_102, Constants.MGT_P1_107, Constants.MGT_P1_108, Constants.MGT_P1_109,
            Constants.MGT_P1_110};
        action.validateUserAccess(ps);
        action.getPropertiesByName(name);
        logger.debug("<getPropertiesByName()");
        return action.getActionResponse();
    }

    @PUT
    @Path("/system/property")
    public ActionResponse updatePropertiesByName(@HeaderParam(API_KEY) String userKey, @QueryParam("name") String name,
        @QueryParam("value") String value, PropertyAction.Request req) {
        logger.debug(">updatePropertiesByName()");
        PropertyAction action = getBean(PropertyAction.ACTION_NAME);
        // 检查userKey
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        // 验证权限
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P_CHANNEL, Constants.MGT_P1_101,
            Constants.MGT_P1_102, Constants.MGT_P1_107, Constants.MGT_P1_108, Constants.MGT_P1_109,
            Constants.MGT_P1_110};
        action.validateUserAccess(ps);
        action.saveOrUpdatePropertiesByName(name, value, req);
        logger.debug("<updatePropertiesByName()");
        return action.getActionResponse();
    }

    @POST
    @Path("/org/relation")
    public ActionResponse insertOrgRelation(@HeaderParam(API_KEY) String userKey,
        @QueryParam("subOrgId") Integer subOrgId) {
        logger.debug(">insertOrgRelation()");
        OrganizationRelationAction action = getBean(OrganizationRelationAction.ACTION_NAME);
        // 检查userKey
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        // 验证权限
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_105};
        action.validateUserAccess(ps);
        action.insertOrgRelation(subOrgId);
        logger.debug("<insertOrgRelation()");
        return action.getActionResponse();
    }

    @PUT
    @Path("/org/relation")
    public ActionResponse delOrgRelation(@HeaderParam(API_KEY) String userKey,
        @QueryParam("subOrgId") Integer subOrgId) {
        logger.debug(">delOrgRelation()");
        OrganizationRelationAction action = getBean(OrganizationRelationAction.ACTION_NAME);
        // 检查userKey
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        // 验证权限
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_105};
        action.validateUserAccess(ps);
        action.delOrgRelation(subOrgId);
        logger.debug("<delOrgRelation()");
        return action.getActionResponse();
    }

    @GET
    @Path("/org/relation")
    public ActionResponse getOrgRelation(@HeaderParam(API_KEY) String userKey) {
        logger.debug(">getOrgRelation()");
        OrganizationRelationAction action = getBean(OrganizationRelationAction.ACTION_NAME);
        // 检查userKey
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        // 验证权限
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_105};
        action.validateUserAccess(ps);
        action.getOrgRelations();
        logger.debug("<getOrgRelation()");
        return action.getActionResponse();
    }

    @GET
    @Path("/organization/lenders")
    public ActionResponse getOrgLenders(@HeaderParam(API_KEY) String userKey) throws IOException {
        logger.debug(">getOrgLender() ");
        OrganizationLenderAction action = getBean(OrganizationLenderAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        // 验证权限
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_105};
        action.validateUserAccess(ps);
        action.getOrgLenders();
        logger.debug("<getOrgLender() ");
        return action.getActionResponse();
    }

    @GET
    @Path("/organizations")
    public ActionResponse getOrganizations(@HeaderParam(API_KEY) String userKey,
        @QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate,
        @QueryParam("getStatistic") Boolean getStatistic, @QueryParam("getAdStatistic") Boolean getAdStatistic,
        @QueryParam("userType") Integer userType, @QueryParam("simple") Boolean simple,
        @QueryParam("salesId") Integer[] salesId, @QueryParam("includeDelete") Boolean includeDelete) {
        logger.debug(">getOrganizations()");
        OrganizationAction action = getBean(OrganizationAction.ACTION_NAME);
        // 检查userKey
        action.validateUserKey(userKey);
        action.validateJbbAccount();

        if (null != simple && simple) {
            // simple 为true 无需检查权限, 只检查调用者是否 jbb 组织账号
        } else {
            // 验证权限
            int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
                Constants.MGT_P1_105};
            action.validateUserAccess(ps);
        }

        action.getOrganizations(startDate, endDate, getStatistic, getAdStatistic, userType, simple, salesId,
            includeDelete);
        logger.debug("<getOrganizations()");
        return action.getActionResponse();
    }

    @GET
    @Path("/organizations/jbbusers")
    public ActionResponse selectUserApplyRecords(@HeaderParam(API_KEY) String userKey,
        @QueryParam("orgId") Integer orgId, @QueryParam("userType") Integer userType,
        @QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate,
        @QueryParam("pageNo") Integer pageNo, @QueryParam("pageSize") Integer pageSize) {
        logger.debug(">selectUserApplyRecords()");
        UserApplyRecordAction action = getBean(UserApplyRecordAction.ACTION_NAME);
        // 检查userKey
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        // 验证权限
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P_CHANNEL, Constants.MGT_P1_101,
            Constants.MGT_P1_102, Constants.MGT_P1_105};
        action.validateUserAccess(ps);
        action.selectUserApplyRecords(orgId, userType, startDate, endDate, pageNo, pageSize);
        logger.debug("<getOrganizations()");
        return action.getActionResponse();
    }

    /**
     * 借帮帮组织查询导流去向(仅仅借帮帮组织有权限)
     */
    @GET
    @Path("/channel/users")
    public ActionResponse getUserApplyDetails(@HeaderParam(API_KEY) String userKey, @QueryParam("pageNo") int pageNo,
        @QueryParam("pageSize") int pageSize, @QueryParam("channelCode") String channelCode,
        @QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate,
        @QueryParam("zhima") Integer zhima, @QueryParam("userType") Integer userType,
        @QueryParam("includeWool") Boolean includeWool, @QueryParam("includeEmptyMobile") Boolean includeEmptyMobile,
        @QueryParam("includeHiddenChannel") Boolean includeHiddenChannel) {
        logger.debug(">getUserApplyDetails()");
        UserAction action = getBean(UserAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        // 验证权限
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_103, Constants.MGT_P1_103};
        action.validateUserAccess(ps);
        action.selectUserApplyDetails(pageNo, pageSize, channelCode, startDate, endDate, zhima, userType, includeWool,
            includeEmptyMobile, includeHiddenChannel);
        logger.debug("<getUserApplyDetails()");
        return action.getActionResponse();
    }

    @POST
    @Path("/org/creatOrg")
    public ActionResponse creatOrg(@HeaderParam(API_KEY) String userKey, CreatOrganizationAction.Request request)
        throws IOException {
        logger.debug(">creatOrganizationAction() ");
        CreatOrganizationAction action = getBean(CreatOrganizationAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_106};
        action.validateUserAccess(ps);
        action.creatOrg(request);
        logger.debug("<creatOrganizationAction() ");
        return action.getActionResponse();
    }

    @PUT
    @Path("/org/creatOrg")
    public ActionResponse updateOrg(@HeaderParam(API_KEY) String userKey, @QueryParam("orgId") Integer orgId,
        @QueryParam("cnt") Integer cnt, @QueryParam("weight") Integer weight,
        @QueryParam("dataEnabled") Boolean dataEnabled, @QueryParam("filterScript") String filterScript,
        @QueryParam("delegateEnabled") Boolean delegateEnabled, @QueryParam("delegateWeight") Integer delegateWeight,
        @QueryParam("delegateH5Type") Integer delegateH5Type) throws IOException {
        logger.debug(">updateOrg() ");
        CreatOrganizationAction action = getBean(CreatOrganizationAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        // 验证权限
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_103};
        action.validateUserAccess(ps);
        action.updateOrg(orgId, cnt, weight, dataEnabled, filterScript, delegateEnabled, delegateWeight,
            delegateH5Type);
        logger.debug("<updateOrg() ");
        return action.getActionResponse();
    }

    @GET
    @Path("/org/getOrg")
    public ActionResponse getOrg(@HeaderParam(API_KEY) String userKey, @QueryParam("orgId") Integer orgId)
        throws IOException {
        logger.debug(">creatOrganizationAction() ");
        CreatOrganizationAction action = getBean(CreatOrganizationAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_105, Constants.MGT_P1_106};
        action.validateUserAccess(ps);
        action.getOrg(orgId);
        logger.debug("<creatOrganizationAction() ");
        return action.getActionResponse();
    }

    @PUT
    @Path("/dataflow/setting")
    public ActionResponse updateDataFlowSetting(@HeaderParam(API_KEY) String userKey,
        @QueryParam("orgId") Integer orgId, DataFlowSettingsAction.Request req) {
        logger.debug(">updateDataFlowSettings()");
        DataFlowSettingsAction action = getBean(DataFlowSettingsAction.ACTION_NAME);
        // 检查userKey
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        // 验证权限
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_105};
        action.validateUserAccess(ps);
        action.updateDataFlowSettingNotMax(orgId, req);
        logger.debug("<updateDataFlowSettings()");
        return action.getActionResponse();
    }

    @POST
    @Path("/merchant")
    public ActionResponse insertH5Merchant(@HeaderParam(API_KEY) String userKey, H5MerchantAction.Request req) {
        logger.debug(">insertH5Merchant()");
        H5MerchantAction action = getBean(H5MerchantAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        // 验证权限
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_104};
        action.validateUserAccess(ps);
        action.insertH5Merchant(req);
        logger.debug("<insertH5Merchant()");
        return action.getActionResponse();
    }

    @PUT
    @Path("/merchant")
    public ActionResponse updateH5Merchant(@HeaderParam(API_KEY) String userKey, H5MerchantAction.Request req) {
        logger.debug(">updateH5Merchant()");
        H5MerchantAction action = getBean(H5MerchantAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        // 验证权限
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_104};
        action.validateUserAccess(ps);
        action.updateH5Merchant(req);
        logger.debug("<updateH5Merchant()");
        return action.getActionResponse();
    }

    @GET
    @Path("/merchants")
    public ActionResponse selectMerchants(@HeaderParam(API_KEY) String userKey) {
        logger.debug(">selectMerchants()");
        H5MerchantAction action = getBean(H5MerchantAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        // 此组织成员都可获取
        action.selectH5Merchants();
        logger.debug("<selectMerchants()");
        return action.getActionResponse();
    }

    @POST
    @Path("/org/dflowConfig")
    public ActionResponse saveOrgDFlowBase(@HeaderParam(API_KEY) String userKey, DataFlowBaseAction.Request req) {
        logger.debug(">saveOrgDFlowBase()");
        DataFlowBaseAction action = getBean(DataFlowBaseAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        // 验证权限
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_105, Constants.MGT_P1_106};
        action.validateUserAccess(ps);
        action.validateJbbAccount();
        action.saveOrgDFlowBase(req);
        logger.debug("<saveOrgDFlowBase()");
        return action.getActionResponse();
    }

    @GET
    @Path("/org/accounts")
    public ActionResponse getAccountsByRoles(@HeaderParam(API_KEY) String userKey,
        @QueryParam("roleId") int[] roleIds) {
        logger.debug(">getAccountsByRoles(), roles = " + roleIds);
        AccountAction action = getBean(AccountAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        action.getAccounts(roleIds);
        logger.debug("<getAccountsByRoles()");
        return action.getActionResponse();
    }

    @GET
    @Path("/org/dflowConfig")
    public ActionResponse getDFlowId(@HeaderParam(API_KEY) String userKey, @QueryParam("orgId") Integer orgId) {
        logger.debug(">getDFlowId()");
        DataFlowBaseAction action = getBean(DataFlowBaseAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        // 验证权限
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_105, Constants.MGT_P1_106};
        action.validateUserAccess(ps);
        action.getOrgDFlowBase(orgId);
        logger.debug("<getDFlowId()");
        return action.getActionResponse();
    }

    @POST
    @Path("/rechange")
    public ActionResponse manualRechage(@HeaderParam(API_KEY) String userKey, @QueryParam("orgId") Integer orgId,
        @QueryParam("amount") Integer amount, @QueryParam("optype") Integer optype) {
        logger.debug(">manualRechage()");
        ManualRechageAction action = getBean(ManualRechageAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        // 验证权限
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102};
        action.validateUserAccess(ps);
        action.validateJbbAccount();
        action.manualRechage(orgId, amount, optype);
        logger.debug("<manualRechage()");
        return action.getActionResponse();
    }


    

  



    @GET
    @Path("community/pf/phones")
    public ActionResponse getCommunityPfPhone(@HeaderParam(API_KEY) String userKey,
        @QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate,
        @QueryParam("pageSize") Integer pageSize) {
        logger.debug(">getCommunityPfPhone()");
        CommunityPfPhoneAction action = getBean(CommunityPfPhoneAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        // 验证权限
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P_COMMUNITY};
        action.validateUserAccess(ps);
        action.getCommunityPfPhone(startDate, endDate, pageSize);
        logger.debug("<getCommunityPfPhone()");
        return action.getActionResponse();
    }

    @POST
    @Path("community/pf/phones")
    public ActionResponse updateCommunityPfPhone(@HeaderParam(API_KEY) String userKey,
        CommunityPfPhoneAction.Request req) {
        logger.debug(">updateHumanbyPhone()");
        CommunityPfPhoneAction action = getBean(CommunityPfPhoneAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        // 验证权限
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P_COMMUNITY};
        action.validateUserAccess(ps);
        action.updateHumanbyPhone(req);
        logger.debug("<updateHumanbyPhone()");
        return action.getActionResponse();
    }
    @POST
    @Path("/platforms/upload")
    @Consumes({MediaType.APPLICATION_OCTET_STREAM, "text/plain"})
    public ActionResponse postPlatformsFile(@HeaderParam(API_KEY) String userKey,
        byte[] content, @QueryParam("fileName") String fileName) throws IOException {
        if (logger.isDebugEnabled()) {
            logger.debug(">postPlatformsFile()" + ", content.length=" + (content != null ? content.length : 0));
        }
        PlatformsUploadAction action = getBean(PlatformsUploadAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_129};
        action.validateUserAccess(ps);
        action.platformsUpload(content, fileName);
        logger.debug("<postPlatformsFile()");
        return action.getActionResponse();
    }

    @POST
    @Path("/platformStatistic/upload")
    @Consumes({MediaType.APPLICATION_OCTET_STREAM, "text/plain"})
    public ActionResponse postPlatformStatisticFile(@HeaderParam(API_KEY) String userKey, byte[] content,
        @QueryParam("fileName") String fileName) throws IOException {
        if (logger.isDebugEnabled()) {
            logger.debug(">postPlatformStatisticFile()" + ", content.length=" + (content != null ? content.length : 0));
        }
        PlatformStatisticUploadAction action = getBean(PlatformStatisticUploadAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_133};
        action.validateUserAccess(ps);
        action.platformStatisticUpload(content, fileName);
        logger.debug("<postPlatformStatisticFile()");
        return action.getActionResponse();
    }

}
