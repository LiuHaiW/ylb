package com.liu.ylb;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TestClient {
    @Test
    public void testHttpClient(){
        CloseableHttpClient client = HttpClients.createDefault();
        String url = "https://way.jd.com/chuangxin/dxjk?mobile=13568813957&content=test&appkey=3680fa919b771148da626bbcbd459475";
        HttpGet httpGet = new HttpGet(url);
        try {
            CloseableHttpResponse closeableHttpResponse = client.execute(httpGet);
            if(closeableHttpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                String json = EntityUtils.toString(closeableHttpResponse.getEntity());
                System.out.println(json);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
