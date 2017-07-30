package com.zhy.server.serverUI.utils;

import org.apache.curator.CuratorZookeeperClient;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhy on 2017/7/20.
 */
public class CuratorZookeeperClientUtil {

    private static final Logger logger = LoggerFactory.getLogger(CuratorZookeeperClientUtil.class);
    //连接超时时间
    private final int CONNECT_TIMEOUT = 15000;
    //重试次数
    private final int RETRY_TIME = Integer.MAX_VALUE;
    //重试间隔
    private final int RETRY_INTERVAL = 1000;

    /** Zookeeper info */
    private static final String ZK_ADDRESS = "192.168.1.129:2181";
    public static final String ZK_PATH = "/test/zhy";
    public static final String NAMESPACE = "curator";
    private CuratorFramework client;

    public CuratorFramework getConnect() throws Exception{
        //1.连接上zk，获取zk客户端
        client = CuratorFrameworkFactory
                .builder()
                .connectString(ZK_ADDRESS)
                .namespace(NAMESPACE)
                .retryPolicy(new RetryNTimes(RETRY_TIME,RETRY_INTERVAL))
                .build();
        client.start();
        logger.info("zk client start successfully!");

        return client;
    }

    // create node
    public String createNode(String path) throws Exception{
        return client.create()
                .creatingParentsIfNeeded()
                .forPath(path);
    }

    public String createNode(String path, String data) throws Exception{
        return client.create()
                .creatingParentsIfNeeded()
                .forPath(path,data.getBytes());
    }

    //setData on node
    public void setData(String node, String data) throws Exception {
        client.setData()
                .forPath(node, data.getBytes());
    }

    //getData on node
    public byte[] getData(String node) throws Exception {
        byte[] bytes = client.getData()
                .forPath(node);
        return bytes;
    }

    //getNodes on path
    public List<String> getNode(String path) throws Exception {
        return client.getChildren()
                .forPath("/");
    }

    //removeNode
    public void removeNode() throws Exception {
        client.delete().forPath(ZK_PATH);
    }

}
