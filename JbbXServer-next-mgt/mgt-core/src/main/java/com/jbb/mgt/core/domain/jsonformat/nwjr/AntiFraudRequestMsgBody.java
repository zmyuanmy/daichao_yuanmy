package com.jbb.mgt.core.domain.jsonformat.nwjr;

import lombok.Data;

import java.util.List;

@Data
public class AntiFraudRequestMsgBody {
    private String project_id;
    private String order_id;
    private BasicInfo  basic_info;
    private List<ContactPerson> contact_person;
    private WorkInfo work_info;
    private String device_model;
    private String notify_url;
}
