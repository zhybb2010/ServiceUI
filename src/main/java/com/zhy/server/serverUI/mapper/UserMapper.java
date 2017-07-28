package com.zhy.server.serverUI.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by zhy on 2017/7/28.
 */
@Mapper
public interface UserMapper {

    @Select("select id,name,age from User")
    List<Map<String,Object>> queryUser();

    void insert();
}
