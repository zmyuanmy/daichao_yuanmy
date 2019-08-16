package com.jbb.mgt.core.service.impl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jbb.mgt.core.domain.ChannelStatisticGroupName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.jbb.mgt.core.dao.ChannelStatisticDailyDao;
import com.jbb.mgt.core.domain.Channel;
import com.jbb.mgt.core.domain.ChannelStatisticDaily;
import com.jbb.mgt.core.domain.Channels;
import com.jbb.mgt.core.service.ChannelStatisticDailyService;
import com.jbb.server.common.util.DateUtil;

@Service("ChannelStatisticDailyService")
public class ChannelStatisticDailyServiceImpl implements ChannelStatisticDailyService {

    private static Logger logger = LoggerFactory.getLogger(ChannelStatisticDailyServiceImpl.class);
    @Autowired
    private ChannelStatisticDailyDao csdDao;

    private static DefaultTransactionDefinition NEW_TX_DEFINITION
        = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

    @Autowired
    private PlatformTransactionManager txManager;

    private void rollbackTransaction(TransactionStatus txStatus) {
        if (txStatus == null) {
            return;
        }

        try {
            txManager.rollback(txStatus);
        } catch (Exception er) {
            logger.warn("Cannot rollback transaction", er);
        }
    }

    @Override
    public void saveChannelStatisticDaily(ChannelStatisticDaily csd) {
        csdDao.saveChannelStatisticDaily(csd);

    }

    @Override
    public ChannelStatisticDaily getChannelStatisticDaily(int orgId, boolean detail) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void updateChannelStatisticDaily(ChannelStatisticDaily csd, boolean updateFlag, int change) {
        TransactionStatus txStatus = null;
        try {
            txStatus = txManager.getTransaction(NEW_TX_DEFINITION);
            csdDao.updateChannelStatisticDaily(csd);
            if (updateFlag) {
                csdDao.updateAllChannelStatisticDaily(csd.getChannelCode(), change, csd.getStatisticDate());
            }
            txManager.commit(txStatus);
            txStatus = null;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("update MgtFinChannelStatisticDaily false", e);
        } finally {
            // roll back not committed transaction
            rollbackTransaction(txStatus);
        }
    }

    @Override
    public List<Channels> getChannelStatisticDailys(String statisticDate, String channelCode, String groupName,
        Integer salesId) throws ParseException {

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Date date = format.parse(statisticDate);
        java.sql.Date date2 = new java.sql.Date(date.getTime());
        long l1 = date.getTime();
        long l2 = l1 + DateUtil.DAY_MILLSECONDES;
        List<Channel> channels = csdDao.selectChannelStatisticToday(1, new Timestamp(l1), new Timestamp(l2),
            channelCode, groupName, salesId);
        List<Channels> newChannels = new ArrayList<Channels>();
        
        List<ChannelStatisticDaily> csd2List = csdDao.getChannelStatisticDaily(statisticDate, false, true);
        List<ChannelStatisticDaily> csd1List = csdDao.getChannelStatisticDaily(statisticDate, true, false);
        
        Map<String, ChannelStatisticDaily> csd2Map = new HashMap<String,ChannelStatisticDaily>();
        Map<String, ChannelStatisticDaily> csd1Map = new HashMap<String,ChannelStatisticDaily>();
        
        if(!csd2List.isEmpty()){
            csd2List.forEach( item->csd2Map.put(item.getChannelCode(), item));
        }
        
        if(!csd1List.isEmpty()){
            csd1List.forEach( item->csd1Map.put(item.getChannelCode(), item));
        }
        
        for (int i = 0; i < channels.size(); i++) {
            Channels newChannel = new Channels();
            Channel channel = new Channel();
            ChannelStatisticDaily csd = new ChannelStatisticDaily();

            csd.setStatisticDate(date2);
            csd.setChannelCode(channels.get(i).getChannelCode());
            channel.setChannelCode(channels.get(i).getChannelCode());
            channel.setChannelName(channels.get(i).getChannelName());
            int price = channels.get(i).getMode() == 4 ? channels.get(i).getUvPrice() : channels.get(i).getCpaPrice();
            csd.setPrice(price);
            ChannelStatisticDaily csd2 = csd2Map.get(channel.getChannelCode());
            ChannelStatisticDaily csd1  = csd1Map.get(channel.getChannelCode());

            Integer mode = null;
            int cnt = 0;
            int adCnt = 0;
            if (csd1 != null) {
                csd.setAmount(csd1.getAmount());
                csd.setStatus(csd1.getStatus());
                mode = csd1.getMode();
                csd.setUvCnt(csd1.getUvCnt());
                if (mode == 4) {
                    cnt = csd.getUvCnt();
                }
            }
            int balance = csd2 == null ? 0 : csd2.getBalance();

            mode = mode == null ? channels.get(i).getMode() : mode;
            if (channels.get(i).getStatistic() != null) {
                cnt = mode == 4 ? cnt : channels.get(i).getStatistic().getRegisterCnt();
                adCnt = channels.get(i).getStatistic().getAdCnt();
                csd.setCnt(channels.get(i).getStatistic().getRegisterCnt());
                csd.setAdCnt(adCnt);
                csd.setExpense(price * cnt);
            }
            csd.setMode(mode);
            csd.setBalance(csd.getAmount() + balance - price * cnt);
            newChannel.setChannelStatisticDaily(csd);
            newChannel.setChannelAccountInfo(channels.get(i).getChannelAccountInfo());
            newChannel.setChannel(channel);
            newChannels.add(newChannel);
        }
        return newChannels;
    }

    @Override
    public void insertChannelsStatisticDaily(List<ChannelStatisticDaily> lists) {
        csdDao.insertChannelsStatisticDaily(lists);

    }

    @Override
    public List<Channels> getstatisticByDate(String statisticDate, String groupName, Integer salesId)
        throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(statisticDate);
        java.sql.Date date2 = new java.sql.Date(date.getTime());
        return csdDao.getstatisticByDate(date2, groupName, salesId);
    }

    @Override
    public List<ChannelStatisticGroupName> getStatisticByChannelCode(String channelCode, String startDate,
        String endDate, String groupName, Integer salesId) throws ParseException {
        long today = DateUtil.getTodayStartTs();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = format.parse(startDate);
        long l1 = date1.getTime();
        long l2 = DateUtil.getCurrentTime();
        Date date2 = new Date();
        String todayString = new SimpleDateFormat("yyyy-MM-dd").format(date2);
        if (endDate != null) {
            Date date3 = format.parse(endDate);
            l2 = date3.getTime();
        }
        boolean flag = true;
        if (l1 <= today && l2 >= today) {
            endDate = todayString;
            flag = false;
        }
        List<ChannelStatisticGroupName> list
            = csdDao.getStatisticByChannelCode(channelCode, startDate, endDate, flag, groupName, salesId);
        if (l1 <= today && l2 >= today) {
            List<Channel> channels = csdDao.selectChannelStatisticToday(1, new Timestamp(today), new Timestamp(l2),
                channelCode, groupName, salesId);
            if (channels == null || channels.size() == 0) {
                return list;
            }
            ChannelStatisticGroupName channelStatisticGroupName = new ChannelStatisticGroupName();
            List<ChannelStatisticDaily> statisticList = new ArrayList<ChannelStatisticDaily>();
            
            
            List<ChannelStatisticDaily> csd2List = csdDao.getChannelStatisticDaily(todayString, false, true);
            List<ChannelStatisticDaily> csd1List = csdDao.getChannelStatisticDaily(todayString, true, false);
            
            Map<String, ChannelStatisticDaily> csd2Map = new HashMap<String,ChannelStatisticDaily>();
            Map<String, ChannelStatisticDaily> csd1Map = new HashMap<String,ChannelStatisticDaily>();
            
            
            if(!csd2List.isEmpty()){
                csd2List.forEach( item->csd2Map.put(item.getChannelCode(), item));
            }
            
            if(!csd1List.isEmpty()){
                csd1List.forEach( item->csd1Map.put(item.getChannelCode(), item));
            }
            
            
            
            for (int i = 0; i < channels.size(); i++) {
                ChannelStatisticDaily csd = new ChannelStatisticDaily();
                Channel channel = channels.get(i);
                csd.setStatisticDate(new Date());
                csd.setChannelCode(channel.getChannelCode());
                csd.setChannelName(channel.getChannelName());
                int price = channel.getMode() == 4 ? channel.getUvPrice() : channel.getCpaPrice();
                // int price = channels.get(0).getCpaPrice();
                csd.setPrice(price);

                ChannelStatisticDaily csd2 = csd2Map.get(channel.getChannelCode());
                ChannelStatisticDaily csd1  = csd1Map.get(channel.getChannelCode());

                Integer mode = null;
                int cnt = 0;
                if (csd1 != null) {
                    csd.setAmount(csd1.getAmount());
                    csd.setStatus(csd1.getStatus());
                    mode = csd1.getMode();
                    csd.setUvCnt(csd1.getUvCnt());
                    if (mode == 4) {
                        cnt = csd.getUvCnt();
                    }
                }
                int balance = csd2 == null ? 0 : csd2.getBalance();

                mode = mode == null ? channel.getMode() : mode;
                if (channel.getStatistic() != null) {
                    cnt = channel.getMode() == 4 ? cnt : channel.getStatistic().getRegisterCnt();
                    // cnt = channel.getStatistic().getRegisterCnt();

                    csd.setCnt(channel.getStatistic().getRegisterCnt());
                    csd.setExpense(price * cnt);
                }
                csd.setMode(mode);
                csd.setBalance(csd.getAmount() + balance - price * cnt);
                statisticList.add(csd);
            }
            channelStatisticGroupName.setStatisticDate(new java.sql.Date(today));
            channelStatisticGroupName.setChannelStatisticDailies(statisticList);
            list.add(channelStatisticGroupName);
        }
        return list;

    }

    @Override
    public ChannelStatisticDaily getChannelStatisticDaily(String statisticDate, String channelCode, boolean detail,
        boolean status) {
        return csdDao.getChannelStatisticDaily(statisticDate, channelCode, detail, status);
    }

    @Override
    public List<ChannelStatisticDaily> selectChannelStatistic(String channelCode, String startDate, String endDate) {
        return csdDao.selectChannelStatistic(channelCode, startDate, endDate);
    }

    @Override
    public void updateChannelStatisticDailyList(List<ChannelStatisticDaily> list) {
        csdDao.updateChannelStatisticDailyList(list);
    }
}
