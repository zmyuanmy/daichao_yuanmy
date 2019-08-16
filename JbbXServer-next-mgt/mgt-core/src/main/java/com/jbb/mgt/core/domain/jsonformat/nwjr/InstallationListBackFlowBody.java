package com.jbb.mgt.core.domain.jsonformat.nwjr;

import lombok.Data;

import java.util.List;

@Data
public class InstallationListBackFlowBody {
    private String id_card;
    private String device_id;
    private List<InstalledAppList> installed_app_list;
}
