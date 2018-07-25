package novle.spider.impl;

import novle.spider.NovelSiteEnum;
import novle.spider.entitys.Chapter;
import novle.spider.interfaces.IChapterSpider;
import novle.spider.util.NovelSpiderutil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractChaperSpider extends AbstractSpider implements IChapterSpider {

    /**
     * 抽象的类
     * @param url
     * @return
     */
    @Override
    public List<Chapter> getsChapter(String url) {
        try {
            Map<String , String > EnumMap = NovelSpiderutil.getContext(NovelSiteEnum.getEnumByurl(url));
            String result = crawl(url,EnumMap);
            Document document = Jsoup.parse(result);
            //jsop中提供一个处理绝对路径还是相对路径的方法
            document.setBaseUri(url);
            Elements as = document.select(EnumMap.get("chapter-list-selecttor"));
            List <Chapter> chapters = new ArrayList<>();
            for (Element a : as){
               Chapter chapter = new Chapter();
               chapter.setTitle(a.text());
               chapter.setUrl(a.absUrl("href"));
               chapters.add(chapter);
            }
            return chapters;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
