package com.authine.cloudpivot.ext.controller;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

import com.authine.cloudpivot.engine.api.model.runtime.AttachmentModel;
import com.authine.cloudpivot.web.api.controller.CustomizedOSSController;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class Meinv {

    static String file = "D:\\photo\\meinv";//下载的目标路径

    public static void downImage(String imgurl, String fileName) {
        System.out.println(fileName);
        //判断目标文件夹是否存在
        File files = new File(file);
        if (!files.exists()) {
            files.mkdirs();
        }

        InputStream is;
        FileOutputStream out;
        try {
            URL url = new URL(imgurl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            is = connection.getInputStream();
            // 创建文件
            File fileofImg = new File(file + "/" + fileName );
            out = new FileOutputStream(fileofImg);
            int i = 0;
            while ((i = is.read()) != -1) {
                out.write(i);
            }
            is.close();
            out.close();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException {
        //downImage("https://t1.huishahe.com/uploads/tu/202106/9999/7d9442ca0a.jpg","7d9442ca0a.jpg");

        List<String> pageUrlList=new ArrayList<>();
        Set<String> urlSet=new HashSet<>();


        for (int i = 0; i < 190; i++) {
            pageUrlList.add("https://www.mmonly.cc/mmtp/qcmn/list_16_" + (i + 1) + ".html");
        }

        for (String pageUrl : pageUrlList) {
            List<String> urlList=new ArrayList<>();
            try {
                String html = getWebContent(pageUrl);
                Document document = Jsoup.parse(html);

                Elements elements = document.getElementsByTag("img");
                for (Element element : elements) {
                    String src = element.attr("src");
                    if(!StringUtils.isEmpty(src) && src.contains("https://t1.huishahe.com")){
                        urlSet.add(src);
                        if (!urlList.contains(src)) {
                            urlList.add(src);
                        }
                    }
                }

                for (String uslStr : urlList) {
                    int i = uslStr.lastIndexOf("/");
                    if (i > 0) {
                        String fileName = uslStr.substring(i + 1);
                        System.out.println(fileName);

                        DownLoadTask task = new DownLoadTask(uslStr, fileName);
                        pool.submit(task);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        System.out.println("AAA2.main");
    }

    private static final ThreadFactory threadFactory = new CustomizableThreadFactory("upload-file-pool-");
    private static final BlockingDeque<Runnable> blockingDeque = new LinkedBlockingDeque<>();
    private static final ThreadPoolExecutor pool = new ThreadPoolExecutor(15, 20, 30, TimeUnit.SECONDS, blockingDeque, threadFactory);


    public static String getWebContent(String urlString) throws IOException {
        return getWebContent(urlString,"UTF-8",1000000);

    }

    /**
     * 获取http 源码
     * @param urlString
     * @param charset
     * @param timeout
     * @return
     * @throws IOException
     */
    public static String getWebContent(String urlString, final String charset, int timeout) throws IOException {
        if (urlString == null || urlString.length() == 0) {
            return null;
        }
        urlString = (urlString.startsWith("http://") || urlString.startsWith("https://")) ? urlString : ("http://" + urlString).intern();
        URL url = new URL(urlString);


        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty(
                "User-Agent",
                "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727)");//增加报头，模拟浏览器，防止屏蔽
        conn.setRequestProperty("Accept", "text/html");//只接受text/html类型，当然也可以接受图片,pdf,*/*任意，就是tomcat/conf/web里面定义那些

        conn.setConnectTimeout(timeout);
        try {
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        InputStream input = conn.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input,
                charset));
        String line = null;
        StringBuffer sb = new StringBuffer();
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\r\n");
        }
        if (reader != null) {
            reader.close();
        }
        if (conn != null) {
            conn.disconnect();
        }
        return sb.toString();
    }
    static class DownLoadTask implements Runnable {

        private String imgurl;
        private String fileName;

        public DownLoadTask(String imgurl,String fileName) {
            this.imgurl = imgurl;
            this.fileName = fileName;
        }

        @Override
        public void run() {
            downImage(imgurl,fileName);
        }
    }
}


