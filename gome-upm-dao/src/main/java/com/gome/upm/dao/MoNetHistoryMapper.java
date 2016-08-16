package com.gome.upm.dao;

import java.util.List;
import java.util.Map;

import com.gome.upm.domain.prtg.MoNetHistory;

public interface MoNetHistoryMapper {
    int deleteByMap(Map<String,Object> map);

    int insert(MoNetHistory record);

    int insertSelective(MoNetHistory record);
    
    List<MoNetHistory> selectByMap(Map<String,Object> map);

}