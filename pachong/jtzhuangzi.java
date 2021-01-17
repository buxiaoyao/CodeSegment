import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//https://www.addd.net/
<!-- https://mvnrepository.com/artifact/org.jsoup/jsoup -->
<!--        <dependency>-->
<!--            <groupId>org.jsoup</groupId>-->
<!--            <artifactId>jsoup</artifactId>-->
<!--            <version>1.13.1</version>-->
<!--        </dependency>-->

public class PaChong {

    public static void main(String[] args) throws IOException {
        //String bookUrl="https://www.addd.net/category/pianjiedagang/";
        //String bookName="庄子篇解大纲.txt";

        //String bookUrl="https://www.addd.net/category/pomozhang/";
        //String bookName="庄子破魔章.txt";

        //String bookUrl="https://www.addd.net/category/zhuangzijibengong/";
        //String bookName="庄子基本功.txt";


        //String bookUrl="https://www.addd.net/category/zhuangzi/";
        //String bookName="庄子白皮书.txt";

        //String bookUrl="https://www.addd.net/category/zhuangzike2013/";
        //String bookName="庄子课2013版.txt";

        //String bookUrl="https://www.addd.net/category/wuzhangyuqingzhi/";
        //String bookName="五脏与情志.txt";

        //String bookUrl="https://www.addd.net/category/tiaoyinyang/";
        //String bookName="重讲调阴阳.txt";

        //String bookUrl="https://www.addd.net/category/%e9%98%bb%e5%8a%9b%e6%9c%80%e5%b0%8f%e4%b9%8b%e8%b7%af/";
        //String bookName="阻力最小之路.txt";
        //
        //String bookUrl="https://www.addd.net/category/liaoyumima/";
        //String bookName="疗愈密码精简版.txt";


        String bookUrl="https://www.addd.net/category/renshengditu/";
        String bookName="医道家的四张人体地图.txt";


        String html = getWebContent(bookUrl);
        Document document = Jsoup.parse(html);

        Elements elements = document.select("article[class=excerpt excerpt-c3]");
        for (int i = 0; i < elements.size(); i++) {
            Element element = elements.get(i);
            Elements aEle = element.getElementsByTag("a");
            String title = "第"+(i+1)+"章："+aEle.text();
            String url = aEle.attr("href");
            String webContent = getWebContent(url);
            String content = getContent(Jsoup.parse(webContent));

            String result = title + "\n\n\n" + content;
            appendFile(result,bookName);
            System.out.println(result);
        }


        System.out.println("PaChong.main   ok   ===================");

    }
    public static void appendFile(String text,String filename) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(new File("C:\\XXX\\常用文档\\", filename),
                    true));
            out.write(text + "\n");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("exception occoured" + e);
        }
    }



    public static String getContent(Document doc){
        String content="";
        //String text = doc.getElementsByClass("article-title").text();
        Elements elementsByTag = doc.select("article[class=article-content]");
        Element element = elementsByTag.get(0);
        Elements pList = element.getElementsByTag("p");
        for (Element pEle : pList) {
            content+=pEle.text()+"\n\n";
            //System.out.println(pEle.text()+"\n");
        }

        return content;

    }



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
}
