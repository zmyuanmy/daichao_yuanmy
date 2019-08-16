package com.jbb.mgt.core.domain.jsonformat.nwjr;

import lombok.Data;

import java.util.List;

@Data
public class MailListBackFlowBody {
    private String id_card;
    private String device_id;
    private String os;
    private int equipment_status;
    private List<PhoneBook> phone_book;

}
