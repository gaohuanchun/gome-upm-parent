package com.gome.upm.dao;

import com.gome.upm.domain.PcInfo;

public interface PcInfoMapper {

    int deleteByPrimaryKey(String ipdz);

    int insert(PcInfo record);

    int insertSelective(PcInfo record);

    PcInfo selectByPrimaryKey(String ipdz);

    int updateByPrimaryKeySelective(PcInfo record);

    int updateByPrimaryKey(PcInfo record);
}