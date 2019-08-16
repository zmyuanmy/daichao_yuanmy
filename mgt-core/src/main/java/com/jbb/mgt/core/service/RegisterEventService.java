package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.mq.RegisterEvent;

public interface RegisterEventService {
    
    void publishRegisterEvent(RegisterEvent event);

    void processMessage(RegisterEvent event);
    
    void processEvent(RegisterEvent event);
    

}
