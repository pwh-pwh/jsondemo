package com.demo.pachong;

import com.demo.myenum.VidEnum;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.*;

public class MyCrawler {
    public String getResult(String url) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);
        try(CloseableHttpResponse response = httpClient.execute(httpGet)) {
            InputStream content = response.getEntity().getContent();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(content));
            StringBuffer sb = new StringBuffer();
            bufferedReader.lines().forEach(e->sb.append(e));
            return sb.toString();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
