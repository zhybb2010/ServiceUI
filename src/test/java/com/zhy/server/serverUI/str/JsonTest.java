package com.zhy.server.serverUI.str;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * Created by zhy on 2017/7/31.
 */
public class JsonTest {

    @Test
    public void testJson(){
        String json = "{" +
                "                \"batsmanName\": null," +
                "                \"active\": true," +
                "                \"onStrike\": false," +
                "                \"fours\": 3," +
                "                \"sixes\": 0," +
                "                \"runs\": 27," +
                "                \"balls\": 52," +
                "                \"playerId\": \"sr:player:656476\"" +
                "            }";

        JSONObject jsonObject = JSONObject.parseObject(json);

        String a = jsonObject.getString("batsmanName");
        System.out.println(StringUtils.isBlank(a));
    }

}
