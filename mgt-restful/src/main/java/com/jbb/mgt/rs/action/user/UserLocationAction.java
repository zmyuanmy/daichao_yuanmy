package com.jbb.mgt.rs.action.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.Location;
import com.jbb.mgt.core.service.UserLocationService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;

@Service(UserLocationAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserLocationAction extends BasicAction {

    public static final String ACTION_NAME = "UserLocationAction";

    private static Logger logger = LoggerFactory.getLogger(UserLocationAction.class);

    private Response response;
    @Autowired
    private UserLocationService userLocationService;

    @Override
    protected ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void insertUserLocation(Request req) {
        if (req != null) {
            Location location = generateAccount(req);
            userLocationService.insertLocation(location);
        }
    }

    private Location generateAccount(Request req) {
        Location location = new Location();
        location.setUserId(this.user.getUserId());
        location.setLatitude(req.latitude);
        location.setLongitude(req.longitude);
        location.setAccuracy(req.accuracy);
        location.setAltitude(req.altitude);
        location.setAltitudeAccuracy(req.altitudeAccuracy);
        location.setHeading(req.heading);
        location.setSpeed(req.speed);
        location.setAddress(userLocationService.getRegeo(req.latitude, req.longitude));
        return location;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        public double latitude;// 纬度
        public double longitude;// 经度
        public double accuracy;// 精度
        public double altitude;// 海拔
        public double altitudeAccuracy;// 海拔精度
        public double heading;// 方向
        public double speed;// 速度

    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {

    }

}
