package com.demo.parse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.entry.UrlEntry;
import com.demo.pachong.MyCrawler;
import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.bg.PixelBoundaryBackground;
import com.kennycason.kumo.font.KumoFont;
import com.kennycason.kumo.font.scale.SqrtFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.nlp.tokenizers.ChineseWordTokenizer;
import com.kennycason.kumo.palette.ColorPalette;
import com.kennycason.kumo.palette.LinearGradientColorPalette;


import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MyJsonParse {
    public InputStream loadData(){
        InputStream dataStream = MyJsonParse.class.getClassLoader().getResourceAsStream("data.json");
        return dataStream;
    }
    public List<String> parseData(List<UrlEntry> urlEntries) {
        List<String> totalResult = new ArrayList<>();
        for (UrlEntry urlEntry : urlEntries) {
            MyCrawler crawler = new MyCrawler();
            String jsonStr = crawler.getResult(urlEntry.getUrl());
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("comments");
            List<String> result = new ArrayList<>();
            jsonArray.forEach(item-> {
                String text = JSONObject.parseObject(item.toString()).getString("text");

                result.add(text);
            });
            totalResult.addAll(result);
        }
        return totalResult;
    }

    public List<String> parseData() {
        JSONObject jsonObject = null;
        try {
            jsonObject = JSONObject.parseObject(new String(loadData().readAllBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("comments");
        List<String> result = new ArrayList<>();
        jsonArray.forEach(item-> {
            String text = JSONObject.parseObject(item.toString()).getString("text");
            text = text.replaceAll("\\[.*]", "");
            result.add(text);
        });
        result.forEach(System.out::println);
        return result;
    }

    public void generateImage(List<UrlEntry> entries) throws IOException {
        FrequencyAnalyzer analyzer = new FrequencyAnalyzer();
        analyzer.setWordFrequenciesToReturn(600);
        analyzer.setMinWordLength(2);
        analyzer.setWordTokenizer(new ChineseWordTokenizer());
        List<WordFrequency> wordList = analyzer.load(parseData(entries));
        WordCloud wordCloud = new WordCloud(new Dimension(1324, 896), CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(1);
        Font font = new Font("STSong-Light", 2, 20);
        wordCloud.setKumoFont(new KumoFont(font));
        wordCloud.setBackgroundColor(new Color(255,255,255));
        wordCloud.setBackground(new CircleBackground(250));
        wordCloud.setBackground(new PixelBoundaryBackground("test.jpg"));
        wordCloud.setColorPalette(new LinearGradientColorPalette(Color.RED,Color.BLUE,Color.GREEN,30,30));
        wordCloud.setFontScalar(new SqrtFontScalar(12,45));
        wordCloud.build(wordList);
        File file = new File("test.png");
        if (!file.exists()) {
            file.createNewFile();
        }
        wordCloud.writeToFile("test.png");

    }

    public void generateImage() throws IOException {
        FrequencyAnalyzer analyzer = new FrequencyAnalyzer();
        analyzer.setWordFrequenciesToReturn(300);
        analyzer.setMinWordLength(1);
        analyzer.setWordTokenizer(new ChineseWordTokenizer());
        List<WordFrequency> wordList = analyzer.load(parseData());
        WordCloud wordCloud = new WordCloud(new Dimension(451, 400), CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(2);
        Font font = new Font("STSong-Light", 2, 20);
        wordCloud.setKumoFont(new KumoFont(font));
        wordCloud.setBackgroundColor(new Color(255,255,255));
//        wordCloud.setBackground(new CircleBackground(250));
        wordCloud.setBackground(new PixelBoundaryBackground("tt.png"));
        wordCloud.setColorPalette(new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1), new Color(0x40C5F1), new Color(0x40D3F1), new Color(0xFFFFFF)));
        wordCloud.setFontScalar(new SqrtFontScalar(12,45));
        wordCloud.build(wordList);
        File file = new File("test.png");
        if (!file.exists()) {
            file.createNewFile();
        }
        wordCloud.writeToFile("test.png");

    }

    public static void main(String[] args) throws IOException {
        System.out.println("这才不是有默契呢[抠鼻][抠鼻][抠鼻][a]".replaceAll("\\[.*]", ""));
        new MyJsonParse().generateImage();
    }
}
