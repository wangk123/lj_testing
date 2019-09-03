package com.lj.testing.dao.mapper.huami;

import com.lj.testing.dao.pojo.huami.HmUser;

public interface HmUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HmUser record);

    int insertSelective(HmUser record);

    HmUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HmUser record);

    int updateByPrimaryKey(HmUser record);
}