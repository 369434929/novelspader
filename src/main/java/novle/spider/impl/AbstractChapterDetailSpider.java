package novle.spider.impl;

import novle.spider.NovelSiteEnum;
import novle.spider.entitys.Chapter;
import novle.spider.entitys.ChapterDetail;
import novle.spider.interfaces.IChapterDetailSpider;
import novle.spider.util.NovelSpiderutil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;
import java.util.Map;

public abstract class AbstractChapterDetailSpider extends AbstractSpider implements IChapterDetailSpider {

    /**
     *抓取到内容
     * @param url
     * @return
     */
    @Override
    public ChapterDetail getChapterDetail(String url) {
       try {

           //拿到小说网站的枚举，获得解析规则
           Map<String , String > EnumMap = NovelSpiderutil.getContext(NovelSiteEnum.getEnumByurl(url));
           //获取到小说网页的内容
           String result = (super.crawl(url,EnumMap)).replace("&nbsp;","  ").replace("<br />","${n}").replace("<br/>","${n}");
           Document document = Jsoup.parse(result);
           document.setBaseUri(url);
           //解析标题，内容，上下章节等内容
           EnumMap = NovelSpiderutil.getContext(NovelSiteEnum.getEnumByurl(url));

           //拿标题内容
           String titleSelector = EnumMap.get("chapter-detail-title-selector");
           String [] splits = parseSselector(titleSelector.split("\\,"));
           ChapterDetail detail = new ChapterDetail();
           //使用选择器选到对应的节点，通过节点选择
           detail.setTitle(document.select(splits[0]).get(Integer.parseInt(splits[1])).text());

           //拿章节内容
           String contentSelector = EnumMap.get("chapter-detail-content-selector");
           detail.setContent(document.select(contentSelector).first().text().replace("${n}","\n"));

           //拿上一章内容（url）
           String prevSelector = EnumMap.get("chapter-detail-prev-selector");
           String [] prevsplits = parseSselector(prevSelector.split("\\,"));
           detail.setPrev(document.select(prevsplits[0]).get(Integer.parseInt(prevsplits[1])).absUrl("href"));

           //拿下一章内容(url)
           String nextSelevtor = EnumMap.get("chapter-detail-next-selector");
           String [] nextsplist = parseSselector(nextSelevtor.split("\\,"));
           detail.setNext(document.select(nextsplist[0]).get(Integer.parseInt(nextsplist[1])).absUrl("href"));

           return detail;
       }catch(Exception e ){
           throw new RuntimeException(e);
       }
    }
    private String [] parseSselector(String [] splits){
        String [] newsplits = new String[2];
        if (splits.length == 1){
            newsplits[0] = splits[0];
            newsplits[1] = "0";
            return newsplits;
        }else {
            return splits;
        }
    }

}
