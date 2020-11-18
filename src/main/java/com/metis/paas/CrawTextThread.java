package com.metis.paas;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.util.List;

/**
 * @Description: CrawTextThread
 * @Author: YiHui
 * @Date: 2020-11-16 11:08
 * @Version: ing
 */
public class CrawTextThread extends Thread{
    public static String PATH = "H:\\Code\\csv2InfluxDB\\repository\\";
    List<String> UrlList;
    String rule = "";
    String rule_title = "h1";
    String rule_content = "content";

    public CrawTextThread(List<String> urlList) {
        this.UrlList = urlList;
    }

    /**
     * 创建文件
     *
     * @param fileName
     * @return
     */
    public static void createFile(File fileName) throws Exception {
        try {
            if (!fileName.exists()) {
                fileName.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void writeTxtFile(String content, File fileName) throws Exception {
        RandomAccessFile mm = null;
        FileOutputStream o = null;
        try {
            o = new FileOutputStream(fileName);
            o.write(content.getBytes("UTF-8"));
            o.close();
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            if (mm != null) {
                mm.close();
            }
        }
    }

    public static String filterHtml(String str) {
        return str.replaceAll(" ", "").replaceAll("<br>", "\r\n");
    }

    @Override
    public void run() {
        currentThread().setName("一个都别跑:");
        String title;
        String content;
        for (String url : UrlList) {
            System.out.println("url:\n" + url);
            try {
                Document document = Jsoup.connect(url).timeout(6000).get();
                System.out.println("url:\n" + url);
                title = document.select("h1").toString();
                content = document.select("#content").html();

                System.out.println("线程:"+currentThread().getName()+"爬取URL—>"+url);
                File file = new File(PATH + title.replaceAll("<h1>", "").replaceAll("</h1>", "")+".txt");
                createFile(file);
                System.out.println("创建文件:"+file.getPath());
                writeTxtFile(filterHtml(content), file);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
