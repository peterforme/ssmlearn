package com.how2java.complaint.mapper;

import com.how2java.complaint.pojo.BackUser;
import com.how2java.complaint.pojo.BackUserExample;
import java.util.List;

public interface BackUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BackUser record);

    int insertSelective(BackUser record);

    List<BackUser> selectByExample(BackUserExample example);

    BackUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BackUser record);

    int updateByPrimaryKey(BackUser record);
}