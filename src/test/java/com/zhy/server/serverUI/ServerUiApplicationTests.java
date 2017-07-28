package com.zhy.server.serverUI;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class ServerUiApplicationTests {

	@Test
	public void contextLoads() {

		char[] scoreChar = "n9".toCharArray();
		int a = 0;
		for (int k = 0; k < scoreChar.length; k++) {

			System.out.println(scoreChar[k]);

			if (scoreChar[k] > 58){
				System.out.println("----"+scoreChar[k]);
			}else {
				a+=(scoreChar[k] -48);
			}
//			if (StringUtils.isNotBlank(scoreChar[k])){
//				if (StringUtils.isNumeric(ballScore)){
//					tEventOver.over_score += Integer.parseInt(ballScore);
//				}
//			}
		}
		System.out.println(a);
	}


	@Test
	public void test2(){

		String a =  "['1w2','2','3','4','5','6','7']['2','3','4','5','6','7','8','9']['1n2',\"\",\"\",\"\",\"\",\"\"]";

		JSONArray ball_score = JSONArray.parseArray(a.replace("][",",").replace("\"\"",""));
		List<Object> list = ball_score.subList(ball_score.size() - 6 , ball_score.size());
		System.out.println(list.size());
		for(int i = 0;i<list.size();i++) {
			System.out.println(list.get(i)+ ",");
		}

	}

	public static int sum(String value) {
		String strings[] = value.split("\\D+");
		int sum = 0;
		for (String s : strings) {
			if (!s.equals("")) {
				sum += Integer.parseInt(s);
			}
		}
		return sum;
	}



	@Test
	public void test3(){

		JSONObject battingTeam = new JSONObject();
		battingTeam.put("teamOvers" , 20.3f);

		String overs = battingTeam.getString("teamOvers");
		System.out.println(overs);
		String[] over = overs.split("\\.");
		System.out.println(over.length);

		int team_overs = Integer.valueOf(over[0]);
		int team_balls = Integer.valueOf(over[1]);

		System.out.println(team_overs + "   "+ team_balls );

	}
}
