package com.gome.upm.dao;

import org.springframework.stereotype.Repository;

import com.gome.upm.domain.MoLoginInfoBO;


@Repository("gomeLoginInfoDAO")
public interface GomeLoginInfoDAO {
	Integer searchCountByTime(MoLoginInfoBO gomeLoginInfoVO);
}
