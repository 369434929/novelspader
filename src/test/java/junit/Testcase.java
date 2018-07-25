package junit;

import novle.spider.NovelSiteEnum;
import novle.spider.config.Configuration;
import novle.spider.entitys.Chapter;
import novle.spider.impl.DefaultChapterDetailSpider;
import novle.spider.impl.DefaultChapterSpider;
import novle.spider.impl.NovelDownload;
import novle.spider.interfaces.IChapterDetailSpider;
import novle.spider.interfaces.IChapterSpider;
import novle.spider.interfaces.INovelDownload;
import novle.spider.util.NovelSpiderutil;
import org.junit.Test;

import java.util.List;

public class Testcase {
    @Test
    public void testGetschaper() throws Exception{
        IChapterSpider spider = new DefaultChapterSpider();
        List<Chapter> chapters = spider.getsChapter("http://www.biquge.com.tw/16_16209/");
        for (Chapter chapter : chapters){
            System.out.println(chapter);
        }
    }

    /**
     * 该测试方法用来获取看书中网站的章节列表
     * @throws Exception
     */
    @Test
    public void testGetschaper2() throws Exception{
        IChapterSpider spider = new DefaultChapterSpider();
        List<Chapter> chapters = spider.getsChapter("http://www.kanshuzhong.com/book/1236/");
        for (Chapter chapter : chapters){
            System.out.println(chapter);
        }
    }

    @Test
    public void testGetSiteContext(){
        System.out.println(NovelSpiderutil.getContext(NovelSiteEnum.getEnumByurl("x23us.com")));
    }

    @Test
    public void testGetChapterDetail(){
        IChapterDetailSpider spider = new DefaultChapterDetailSpider();
        System.out.println(spider.getChapterDetail("http://www.biquge.com.tw/0_5/4616.html").getContent());
    }
    /**
     *该测试方法用于测试章节内容的 抓取。
     */
    @Test
    public void testGetChapterDetail2(){
        IChapterDetailSpider spider = new DefaultChapterDetailSpider();
        System.out.println(spider.getChapterDetail("http://www.kanshuzhong.com/book/1236/20071670.html").getContent());
    }
    @Test
    public void testDownload(){
        INovelDownload download = new NovelDownload();
        Configuration config = new Configuration();
        config.setPath("F:\\123");
        config.setSize(100);
        download.download("http://www.biquge.com.tw/11_11850/",config);
    }
}
