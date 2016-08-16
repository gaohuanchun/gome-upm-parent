package com.gome.upm.dao;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.gome.upm.common.Page;
import com.gome.upm.domain.prtg.MoNetDevice;

public interface MoNetDeviceMapper {
    int insert(MoNetDevice record);

    int insertSelective(MoNetDevice record);
    
    MoNetDevice selectByPrimaryKey(Integer deviceId);
    
    int updateByPrimaryKeySelective(MoNetDevice record);
    
    List<MoNetDevice> selectList(Page<MoNetDevice> page);
    Integer selectCount(Page<MoNetDevice> page);
    
    List<MoNetDevice> selectListCommon(MoNetDevice record);
    
    Integer selectCountByMap(Map<String,Object> map);
    
    LinkedList<Integer> selectAllIds();
    
    void deleteByIds(List<Integer> list);
}