package com.gome.upm.dao;

import java.util.LinkedList;
import java.util.List;

import com.gome.upm.domain.prtg.MoNetGroup;

public interface MoNetGroupMapper {
    int deleteByPrimaryKey(Integer groupId);

    int insert(MoNetGroup record);

    int insertSelective(MoNetGroup record);

    MoNetGroup selectByPrimaryKey(Integer groupId);

    int updateByPrimaryKeySelective(MoNetGroup record);

    int updateByPrimaryKey(MoNetGroup record);
    
    LinkedList<Integer> selectAllIds();
    
    void deleteByIds(List<Integer> list);
}