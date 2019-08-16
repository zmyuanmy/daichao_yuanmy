package com.jbb.mgt.server.rs.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.cache.NoCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jbb.mgt.rs.action.xjtQzReport.XjtQzReportAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;

@Path("report")
@Produces(MediaType.APPLICATION_JSON)
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@NoCache
public class JBBXjtReportServices extends BasicRestfulServices {

    private static Logger logger = LoggerFactory.getLogger(JBBXjtReportServices.class);

    @POST
    @Path("/qzReport")
    public ActionResponse qzReportNotify(@HeaderParam(API_KEY) String userKey, XjtQzReportAction.Request req) {
        XjtQzReportAction action = getBean(XjtQzReportAction.ACTION_NAME);
        action.qzReportNotify(req);
        return action.getActionResponse();
    }
}
