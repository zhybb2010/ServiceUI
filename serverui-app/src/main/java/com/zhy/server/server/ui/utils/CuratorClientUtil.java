package com.zhy.server.server.ui.utils;

import com.zhy.server.ui.api.utils.ICuratorClientUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by zhy on 2017/7/20.
 */
public class CuratorClientUtil implements ICuratorClientUtil {

    private static final Logger logger = LoggerFactory.getLogger(CuratorClientUtil.class);

    //retryTimes
    private static final int RETRY_TIME = Integer.MAX_VALUE;
    //retryTime
    private static final int RETRY_INTERVAL = 1000;
    //sessionTimeOut
    private static final int SESSION_TIMEOUT = 5000;
    //connectTimeout
    private static final int CONNECT_TIMEOUT = 15000;

    private CuratorFramework client;

    public void getConnect(String address,String namespace) throws Exception{
        //connect & retrun curatorClient
        client = CuratorFrameworkFactory
                .builder()
                .connectString(address)
                .connectionTimeoutMs(CONNECT_TIMEOUT)
                .sessionTimeoutMs(SESSION_TIMEOUT)
                .namespace(namespace)
                .retryPolicy(new RetryNTimes(RETRY_TIME,RETRY_INTERVAL))
                .build();
        client.start();
        logger.info("zk client start successfully!");
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

    public String createTempNode(String path) throws Exception{
        return client.create()
                .withMode(CreateMode.EPHEMERAL)
                .forPath(path);
    }


    //setData on node
    public void setData(String node, String data) throws Exception {
        client.setData()
                .withVersion(-1)//版本校验，与当前版本不一致则更新失败,-1则无视版本信息进行更新
                .forPath(node, data.getBytes());
    }

    //getData on node
    public byte[] getData(String node) throws Exception {
        byte[] bytes = client.getData()
//                .storingStatIn(new Stat())//返回节点信息到stat
                .forPath(node);
        return bytes;
    }

    //getNodes on path
    public List<String> getNode(String path) throws Exception {
        return client.getChildren()
                .forPath("/");
    }

    //removeNode
    public void removeNode(String path) throws Exception {
        client.delete()
                .guaranteed()      //删除失败，则客户端持续删除，直到节点删除为止
                .deletingChildrenIfNeeded()   //删除相关子节点
                .withVersion(-1)    //无视版本，直接删除
                .forPath(path);
    }

    public Stat checkExists(String path) throws Exception {
        return client.checkExists().forPath(path);
    }

    public CuratorFramework getClient() {
        return client;
    }
}
