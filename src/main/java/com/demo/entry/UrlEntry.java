package com.demo.entry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class UrlEntry {
    public static String baseUri = "http://211.149.188.208:5656/api/open/dy/v/comments?";
    private String vid;
    private String url;

    public String getVid() {
        return vid;
    }

    public String getUrl() {
        return url;
    }

    public UrlEntry(Builder builder) {
        this.url = builder.url;
        this.vid = builder.vid;
    }

    public static class Builder {
        public String token;
        public String vid;
        public String url;

        public Builder() {

        }

        public Builder setVid(String vid) {
            this.vid = vid;
            return this;
        }

        public UrlEntry build() {
            InputStream resourceAsStream = UrlEntry.class.getClassLoader().getResourceAsStream("token.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(resourceAsStream));
            try {
                token = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.url = baseUri + "token=" + token + "&vid=" + vid;
            return new UrlEntry(this);
        }
    }

    public static void main(String[] args) {
        UrlEntry test = new Builder().setVid("test").build();
        System.out.println(test);
    }

    @Override
    public String toString() {
        return "UrlEntry{" +
                "vid='" + vid + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
