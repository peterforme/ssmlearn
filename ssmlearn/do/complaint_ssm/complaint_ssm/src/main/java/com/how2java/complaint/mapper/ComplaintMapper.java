package com.how2java.complaint.mapper;

import com.how2java.complaint.pojo.Complaint;
import com.how2java.complaint.pojo.ComplaintExample;
import java.util.List;

public interface ComplaintMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Complaint record);

    int insertSelective(Complaint record);

    List<Complaint> selectByExampleWithBLOBs(ComplaintExample example);

    List<Complaint> selectByExample(ComplaintExample example);

    Complaint selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Complaint record);

    int updateByPrimaryKeyWithBLOBs(Complaint record);

    int updateByPrimaryKey(Complaint record);
}