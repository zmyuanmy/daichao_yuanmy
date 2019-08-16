package com.jbb.mgt.core.domain.jsonformat.nwjr;

import lombok.Data;

import java.util.List;

@Data
public class OperatorInfoBackFlowBody {
    private User user;
    private List<CallRecordList> call_record_list;
    private List<MessageRecordList> message_record_list;
    private List<BillList> bill_list;
    private List<NateWorkFlowList> network_flow_info_list;
    private List<PaymentRecordList> payment_record_list;
}
