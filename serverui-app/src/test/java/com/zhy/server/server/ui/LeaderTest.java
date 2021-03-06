package com.zhy.server.server.ui;

/**
 * Created by Frank on 2017/7/31.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingServer;
import org.apache.curator.utils.CloseableUtils;

import com.google.common.collect.Lists;


public class LeaderTest {
        private static final int CLIENT_QTY=10;
        private static final String PATH="/examples/leader";
        public static void main(String[] args){
            List<CuratorFramework> clients=Lists.newArrayList();
            List<LeaderLatch> examples=Lists.newArrayList();
            TestingServer server = null;
            try {
                server=new TestingServer();
                //创建了10个LeaderLatch，启动后它们中的一个会被选举为leader
                for(int i=0;i<CLIENT_QTY;i++){
                    CuratorFramework client=CuratorFrameworkFactory.newClient(server.getConnectString(),new ExponentialBackoffRetry(1000,3));
                    clients.add(client);
                    LeaderLatch example=new LeaderLatch(client,PATH,"client #"+i);
                    examples.add(example);
                    client.start();
                    example.start();
                }

                // 因为选举会花费一些时间，start后并不能马上就得到leader。
                Thread.sleep(20000);
                LeaderLatch currentLeader=null;
                for(int i=0;i<CLIENT_QTY;i++){
                    LeaderLatch example=examples.get(i);
                    //通过hasLeadership查看自己是否是leader
                    if(example.hasLeadership()){
                        currentLeader=example;
                        break;
                    }
                }

                System.out.println("current leader is "+currentLeader.getId());
                System.out.println("release the leader "+currentLeader.getId());
                //只能通过close释放当前的领导权。
                currentLeader.close();

                //await是一个阻塞方法， 尝试获取leader地位，但是未必能上位。
                examples.get(0).await(2,TimeUnit.SECONDS);
                System.out.println("client #0 maybe is elected as the leader or not although it want to be");
                //可以通过.getLeader().getId()可以得到当前的leader的ID
                System.out.println("the new leader is "+examples.get(0).getLeader().getId());

                System.out.println("Press enter/return to quit \n");
                new BufferedReader(new InputStreamReader(System.in)).readLine();
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                System.out.println("Shutting down....");
                for(LeaderLatch exampleClient: examples){
                    CloseableUtils.closeQuietly(exampleClient);
                }
                for(CuratorFramework client:clients){
                    CloseableUtils.closeQuietly(client);
                }
                CloseableUtils.closeQuietly(server);
            }

        }
    }