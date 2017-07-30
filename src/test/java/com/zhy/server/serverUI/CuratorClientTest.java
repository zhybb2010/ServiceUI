package com.zhy.server.serverUI;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Curator framework's client test.
 * Output:
 *  $ create /zktest hello
 *  $ ls /
 *  [zktest, zookeeper]
 *  $ get /zktest
 *  hello
 *  $ set /zktest world
 *  $ get /zktest
 *  world
 *  $ delete /zktest
 *  $ ls /
 *  [zookeeper]
 */
public class CuratorClientTest {

    /** Zookeeper info */
    private static final String ZK_ADDRESS = "192.168.1.129:2181";
    private static final String ZK_PATH = "/test/zhy";
    private CuratorFramework client;

    @Test
    public void MainTest() throws Exception{

//        print(createNode("/curator"));

//        getNode("/").forEach(CuratorClientTest::print);



    }



    @Before
    public void zkGetNode() throws Exception{
        //1.连接上zk，获取zk客户端
        client = CuratorFrameworkFactory
                        .builder()
                        .connectString(ZK_ADDRESS)
                        .namespace("curator")
                        .retryPolicy(new RetryNTimes(2000,20000))
                        .build();
        client.start();
        System.out.println("zk client start successfully!");
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


    private static void print(String... cmds) {
        StringBuilder text = new StringBuilder("$ ");
        for (String cmd : cmds) {
            text.append(cmd).append(" ");
        }
        System.out.println(text.toString());
    }

    private static void print(Object result) {
        System.out.println(result instanceof byte[] ? new String((byte[]) result) : result);
    }
}
