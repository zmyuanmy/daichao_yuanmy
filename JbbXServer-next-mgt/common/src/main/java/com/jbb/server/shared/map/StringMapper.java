package com.jbb.server.shared.map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbb.domain.DateFlowChannelSetting;
import com.jbb.domain.LoanPlatformPolicy;
import com.jbb.domain.SelfGroupsPolicy;
import com.jbb.domain.SpecialEntryOrgsPolicy;
import com.jbb.domain.SpecialGroupsPolicy;
import com.jbb.domain.XorGroupsPolicy;
import com.jbb.server.common.exception.ExecutionException;
import com.jbb.server.common.util.StringUtil;

/**
 * Object to String and String to object mapping wrapper class This implementation utilises Jackson ObjectMapper.
 */
public class StringMapper {
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static LoanPlatformPolicy readPolicy(String str) throws Exception {
        return mapper.readValue(str, LoanPlatformPolicy.class);
    }
    
    public static XorGroupsPolicy readXorGroupsPolicy(String str) throws Exception {
    	if(StringUtil.isEmpty(str)){
    		return null;
    	}
        return mapper.readValue(str, XorGroupsPolicy.class);
    }
    
    public static DateFlowChannelSetting readDateFlowChannelSetting(String str) throws Exception {
    	if(StringUtil.isEmpty(str)){
    		return null;
    	}
        return mapper.readValue(str, DateFlowChannelSetting.class);
    }
    
    public static SelfGroupsPolicy readSelfGroupsPolicy(String str) throws Exception {
    	if(StringUtil.isEmpty(str)){
    		return null;
    	}
        return mapper.readValue(str, SelfGroupsPolicy.class);
    }
    
    public static SpecialEntryOrgsPolicy readSSpecialEntryOrgsPolicy(String str) throws Exception {
    	if(StringUtil.isEmpty(str)){
    		return null;
    	}
        return mapper.readValue(str, SpecialEntryOrgsPolicy.class);
    }
    
    public static SpecialGroupsPolicy readSpecialGroupsPolicy(String str) throws Exception {
    	if(StringUtil.isEmpty(str)){
    		return null;
    	}
        return mapper.readValue(str, SpecialGroupsPolicy.class);
    }

    public static String toString(LoanPlatformPolicy input) {
        try {
            return mapper.writeValueAsString(input);
        } catch (Exception e) {
            throw new ExecutionException(e);
        }
    }
}
