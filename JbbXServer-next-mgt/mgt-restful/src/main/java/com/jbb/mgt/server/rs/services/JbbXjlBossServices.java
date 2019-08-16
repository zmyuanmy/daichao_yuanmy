package com.jbb.mgt.server.rs.services;


import com.jbb.mgt.rs.action.boss.BossAction;
import org.jboss.resteasy.annotations.cache.NoCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("boss")
@Produces(MediaType.APPLICATION_JSON)
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@NoCache
public class JbbXjlBossServices extends BasicRestfulServices{

    private static Logger logger = LoggerFactory.getLogger(JbbXjlBossServices.class);

    @GET
    @Path("/xjt/nwjr/getResult")
    public String notifyBoss(@Context HttpServletRequest request) throws Exception {
        logger.debug(">notifyBoss()");
        BossAction action = getBean(BossAction.ACTION_NAME);
        String result = action.notifyBoss(request);
        logger.debug("<notifyBoss()");
        return result;
    }
}
