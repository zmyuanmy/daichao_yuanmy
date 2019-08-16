package com.jbb.mgt.core.domain.jsonformat.nwjr;

import lombok.Data;

import java.util.List;

@Data
public class ShortMessageBackFlowBody {
    private String id_card;
    private String device_id;
    private List<MessageList> messageListList;
}
