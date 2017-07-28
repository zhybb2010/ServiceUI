package com.zhy.server.serverUI.utils;

import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhy on 2017/7/20.
 */
public class CuratorZookeeperClient {

    private static final Logger logger = LoggerFactory.getLogger(CuratorZookeeperClient.class);
    //连接超时时间
    private final int CONNECT_TIMEOUT = 15000;
    //重试次数
    private final int RETRY_TIME = Integer.MAX_VALUE;
    //重试间隔
    private final int RETRY_INTERVAL = 1000;

    private CuratorFramework curator;

    private volatile static CuratorZookeeperClient instance;

    /**
     * key:父路径，如/jobcenter/client/goodscenter
     * value：Map-->key:子路径，如/jobcenter/client/goodscenter/goodscenter00000001
     *              value:路径中的值
     */
    private static ConcurrentHashMap<String,Map<String,String>> zkCacheMap = new ConcurrentHashMap<String,Map<String,String>>();


    public void  connect(){
//        Collections.sync
    }
}
