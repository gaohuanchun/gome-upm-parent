package com.gome.upm.dao;

import java.util.List;

import com.gome.upm.domain.prtg.MoNetSensorThreshold;

public interface MoNetSensorThresholdMapper {

    List<MoNetSensorThreshold> selectAll();
    
    List<MoNetSensorThreshold> selectAll2();
    
    int deleteByPrimaryKey(Integer sensorId);

    int insert(MoNetSensorThreshold record);

    int insertSelective(MoNetSensorThreshold record);

    MoNetSensorThreshold selectByPrimaryKey(Integer sensorId);

    int updateByPrimaryKeySelective(MoNetSensorThreshold record);

    int updateByPrimaryKey(MoNetSensorThreshold record);
}