package com.jbb.mgt.core.domain.jsonformat.yys;

import lombok.Data;

import java.util.List;

@Data
public class Data_info {

    private String total_data_count;
    private String total_data_size;
    private String data_cycle;
    private List<Data_record> data_record;
}
