package novle.spider.impl;

import novle.spider.util.NovelSpiderHttpGet;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.util.Map;

public abstract class AbstractSpider {
    /**
     * 抓取指定小说的内容，无论是章节列表还是小说内容
     * @param url
     * @param EnumMap
     * @return
     * @throws Exception
     */
    protected String crawl(String url,Map<String ,String> EnumMap) throws Exception{
        //通过HttpClientBuilder.create()方法返回一个httpClient实体
        //为什么这样写，ry-with-resources.自动资源释放。
        //这种特性从JDK1.7开始存在的。这样可以自动释放HTTP，所以声明在外面

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build();
             CloseableHttpResponse httpResponse = httpClient.execute(new NovelSpiderHttpGet(url))
        ){
            //模拟发送一个请求
            //将返回的结果转换为字符串
            String result = EntityUtils.toString(httpResponse.getEntity(),EnumMap.get("charset"));
            return result;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
