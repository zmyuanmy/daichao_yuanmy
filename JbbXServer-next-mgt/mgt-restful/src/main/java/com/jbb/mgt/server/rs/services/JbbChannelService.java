package com.jbb.mgt.server.rs.services;

import com.jbb.mgt.rs.action.xiaoCaiMi.XiaoCaiMiAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import net.sf.json.JSONObject;
import org.jboss.resteasy.annotations.cache.NoCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("channel")
@Produces(MediaType.APPLICATION_JSON)
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@NoCache
public class JbbChannelService extends BasicRestfulServices {

    private static Logger logger = LoggerFactory.getLogger(JbbChannelService.class);

    @POST
    @Path("/check/mobilePhone")
    @Consumes({MediaType.APPLICATION_JSON})
    public JSONObject checkChannelUserMobilePhone(@Context HttpServletRequest request,
        @Context HttpServletResponse response) {
        logger.debug(">checkChannelUserMobilePhone()");
        XiaoCaiMiAction action = getBean(XiaoCaiMiAction.ACTION_NAME);
        JSONObject jsonObject = action.checkChannelUserMobilePhone(request, response);
        logger.debug("<checkChannelUserMobilePhone()");
        return jsonObject;
    }

    @POST
    @Path("/joint/login")
    public JSONObject jointUserLogin(@Context HttpServletRequest req) {
        logger.debug(">jointUserLogin()");
        XiaoCaiMiAction action = getBean(XiaoCaiMiAction.ACTION_NAME);
        JSONObject jsonObject = action.jointUserLogin(req);
        logger.debug("<jointUserLogin()");
        return jsonObject;
    }

}
