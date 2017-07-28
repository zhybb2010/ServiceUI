package com.zhy.server.serverUI.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.management.ManagementFactory;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by zhy on 2017/7/17.
 */
@RequestMapping
@Service
public class Home {

    private Logger logger = LoggerFactory.getLogger(Home.class);

    @Value("${secrect}")
    public String secrect;

    @RequestMapping
    @ResponseBody
    public String HelloWorld(){

        logger.info("test : {}","info");
        logger.debug("test : {}","debug");
        logger.warn("test : {}","warn");
        logger.error("test : {}","error");
        return secrect;
    }

    @RequestMapping("env")
    @ResponseBody
    public String getEnv(){
        Map map = System.getenv();
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            System.out.print(entry.getKey() + "=");
            System.out.println(entry.getValue());
        }
        return "getEnv";
    }

    @RequestMapping("pid")
    @ResponseBody
    public String pid(){
        return getPid();
    }

    private String getPid() {
        try {
            String jvmName = ManagementFactory.getRuntimeMXBean().getName();
            return jvmName.split("@")[0];
        }
        catch (Throwable ex) {
            return null;
        }
    }

}
