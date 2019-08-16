package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.DataJuXinLiUrls;
import com.jbb.mgt.core.domain.DataYxUrls;
import org.apache.ibatis.annotations.Param;

public interface DataJuXinLiUrlsService {
    DataJuXinLiUrls selectDataYxUrlsByUserIdAndReportType(@Param(value = "userId") int userId,
        @Param(value = "reportType") String reportType);

    void insertDataYxUrls(DataYxUrls dataYxUrls);

    void insertDataJuXinLiUrls(DataJuXinLiUrls dataJuXinLiUrls);

    void updateDataJuXinLiUrls(DataJuXinLiUrls dataJuXinLiUrls);
}
