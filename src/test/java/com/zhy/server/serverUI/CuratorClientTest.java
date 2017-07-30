package com.zhy.server.serverUI;

import com.mysql.jdbc.TimeUtil;
import com.zhy.server.serverUI.utils.CuratorZookeeperClientUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.WatchedEvent;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentSkipListSet;


public class CuratorClientTest {

    Logger LOG = LoggerFactory.getLogger(this.getClass());

    private ConcurrentSkipListSet watchers = new ConcurrentSkipListSet();

    @Test
    public void MainTest() throws Exception{

        CuratorZookeeperClientUtil clientUtil = new CuratorZookeeperClientUtil();
        CuratorFramework client = clientUtil.getConnect();
        client.getData().usingWatcher(new ZKWatchRegister()).inBackground().forPath(clientUtil.ZK_PATH);

        while (true){
            Thread.sleep(5000L);
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
