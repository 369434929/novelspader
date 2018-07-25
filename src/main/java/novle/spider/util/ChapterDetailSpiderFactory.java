package novle.spider.util;

import novle.spider.NovelSiteEnum;
import novle.spider.impl.DefaultChapterDetailSpider;
import novle.spider.interfaces.IChapterDetailSpider;

public class ChapterDetailSpiderFactory {
    private ChapterDetailSpiderFactory() {}
    /**
     * 通过给定的url拿到实现了IChapterDetailSpider的具体实现类
     * @param url
     * @return
     */
    public static IChapterDetailSpider getChapterDetailSpider(String url) {
        IChapterDetailSpider spider = null;
        NovelSiteEnum novelSiteEnum = NovelSiteEnum.getEnumByurl(url);
        switch (novelSiteEnum) {
            case Dingdianxioashuo :
            case bquge :
            case KanShuZhong :
            case Bxwx :
                spider = new DefaultChapterDetailSpider();
                break;
        }
        return spider;
    }
}
