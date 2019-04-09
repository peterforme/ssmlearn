package com.how2java.complaint.mapper;

import com.how2java.complaint.pojo.ComplaintImage;
import com.how2java.complaint.pojo.ComplaintImageExample;
import java.util.List;

public interface ComplaintImageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ComplaintImage record);

    int insertSelective(ComplaintImage record);

    List<ComplaintImage> selectByExample(ComplaintImageExample example);

    ComplaintImage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ComplaintImage record);

    int updateByPrimaryKey(ComplaintImage record);
}