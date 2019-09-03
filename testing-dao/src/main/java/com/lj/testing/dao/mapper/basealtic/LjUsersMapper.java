package com.lj.testing.dao.mapper.basealtic;

import com.lj.testing.dao.pojo.basealtic.LjUsers;

public interface LjUsersMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LjUsers record);

    int insertSelective(LjUsers record);

    LjUsers selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LjUsers record);

    int updateByPrimaryKey(LjUsers record);
}