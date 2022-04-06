package com.demo;

import com.demo.entry.UrlEntry;
import com.demo.myenum.VidEnum;
import com.demo.parse.MyJsonParse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyApplication {
    public static void main(String[] args) throws IOException {
        new MyJsonParse().generateImage(generateUrl());
    }
    public static List<UrlEntry> generateUrl() {
        List<UrlEntry> result = new ArrayList<>();
        for (VidEnum value : VidEnum.values()) {
            UrlEntry test = new UrlEntry.Builder().setVid(value.vid).build();
            result.add(test);
        }
        return result;
    }
}
