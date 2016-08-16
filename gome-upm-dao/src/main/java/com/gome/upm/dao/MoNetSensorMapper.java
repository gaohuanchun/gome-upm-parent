package com.gome.upm.dao;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.gome.upm.common.Page;

import com.gome.upm.domain.prtg.MoNetSensor;

public interface MoNetSensorMapper {
    int deleteByPrimaryKey(Integer sensorId);

    int insert(MoNetSensor record);

    int insertSelective(MoNetSensor record);

    MoNetSensor selectByPrimaryKey(Integer sensorId);

    int updateByPrimaryKeySelective(MoNetSensor record);

    int updateByPrimaryKey(MoNetSensor record);
    
    List<MoNetSensor> selectList(Page<MoNetSensor> page);
    
    Integer selectCount(Page<MoNetSensor> page);
    
    List<MoNetSensor> selectListAlarm(Page<MoNetSensor> page);
    
    Integer selectCountAlarm(Page<MoNetSensor> page);
    
    /**
     * 获取停机状态传感器
     * @param record
     * @return
     */
    List<MoNetSensor> selectListStop(String[] sensorIds);
    
    List<MoNetSensor> selectListByMap(Map<String,Object> map);
    
    Integer selectCountByMap(Map<String,Object> map);
    
    
    LinkedList<Integer> selectAllIds();
    
    
    void deleteByIds(List<Integer> list);
}