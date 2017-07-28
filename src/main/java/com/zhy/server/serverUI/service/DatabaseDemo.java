package com.zhy.server.serverUI.service;

import com.zhy.server.serverUI.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zhy on 2017/7/28.
 */

@RequestMapping
@Service
public class DatabaseDemo {

    @Autowired
    UserMapper userMapper;

    @RequestMapping("getUser")
    @ResponseBody
    public Object getUser(){
        return userMapper.queryUser();
    }
}
