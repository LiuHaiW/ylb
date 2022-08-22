package com.liu.ylb;

import com.alibaba.fastjson.JSONObject;
import com.liu.ylb.vo.Student;
import org.junit.jupiter.api.Test;

public class TestJson {
    @Test
    public void studentToJson(){
        Student student = new Student();
        student.setId(1001);
        student.setName("lisi");
        String stu = JSONObject.toJSONString(student);
        System.out.println(stu);
    }
    @Test
    public void JSONToStudent(){
        String json = "{\"id\":1002,\"name\":\"李四\"}";
        Student student = JSONObject.parseObject(json,Student.class);
        System.out.println(student);
    }
    @Test
    public void testR(){
        String json = "{\"id\":1002,\"name\":\"李四\"}";
        JSONObject jsonObject = JSONObject.parseObject(json);
        String name = jsonObject.getString("name");
        int id = jsonObject.getIntValue("id");
        System.out.println(name+id);
    }
    @Test
    public void test(){
        String json="{\n" +
                "    \"code\": \"10000\",\n" +
                "    \"charge\": false,\n" +
                "    \"remain\": 1305,\n" +
                "    \"msg\": \"查询成功\",\n" +
                "    \"result\": {\n" +
                "        \"ReturnStatus\": \"Success\",\n" +
                "        \"Message\": \"ok\",\n" +
                "        \"RemainPoint\": 420842,\n" +
                "        \"TaskID\": 18424321,\n" +
                "        \"SuccessCounts\": 1\n" +
                "    }\n" +
                "}";
        JSONObject object = JSONObject.parseObject(json);
        String code = object.getString("code");
        System.out.println("code = " + code);

        JSONObject result = object.getJSONObject("result");
        String returnStatus = result.getString("ReturnStatus");
        System.out.println("returnStatus = " + returnStatus);


        String string = JSONObject.parseObject(json).getJSONObject("result").getString("ReturnStatus");
        System.out.println("string = " + string);

    }
}
