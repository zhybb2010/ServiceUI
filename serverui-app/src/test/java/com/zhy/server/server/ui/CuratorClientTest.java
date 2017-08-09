package com.zhy.server.server.ui;

import com.zhy.server.server.ui.utils.CuratorClientUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.TimeUnit;


public class CuratorClientTest {

    Logger LOG = LoggerFactory.getLogger(this.getClass());

    private ConcurrentSkipListSet watchers = new ConcurrentSkipListSet();

    @Test
    public void MainTest() throws Exception{

        String address = "192.168.1.129:2181";
        String appName = "server";

        String leader_path = "/led";

        CuratorClientUtil clientUtil = new CuratorClientUtil();
        clientUtil.getConnect(address,appName);


        //检测led节点是否存在
        Stat ledStat = clientUtil.checkExists(leader_path);
        if(ledStat== null) {
            clientUtil.createNode(leader_path);
            LOG.info("Create node {}",leader_path);
        }

        LeaderLatch example=new LeaderLatch(clientUtil.getClient(),leader_path,"client #1");
        LeaderLatch example2=new LeaderLatch(clientUtil.getClient(),leader_path,"client #2");
        example.start();
        example2.start();

//        example.await();
//        example2.await();

        Thread.sleep(2000);

        System.out.println("example" +example.hasLeadership());
        System.out.println("example2" + example2.hasLeadership());


//        if (example.hasLeadership()){
//            example.close();
//        }
//
//        if (example2.hasLeadership()){
//            example2.close();
//        }
//        //
//
//        TimeUnit.SECONDS.sleep(5);
//        System.out.println("example" +example.hasLeadership());
//        System.out.println("example2" + example2.hasLeadership());

        while (true){
            TimeUnit.SECONDS.sleep(5);
        }

    }


    public class ZKWatchRegister implements CuratorWatcher {
        @Override
        public void process(WatchedEvent event) throws Exception {
            System.out.println("event type is " + event.getType());
            switch (event.getType()){
                case NodeDeleted:
                    break;
                case None:
                    break;
                case NodeCreated:
                    break;
                case NodeDataChanged:
                    break;
                case DataWatchRemoved:
                    break;
                case ChildWatchRemoved:
                    break;
                case NodeChildrenChanged:
                    break;
                default:
            }
        }
    }
}
