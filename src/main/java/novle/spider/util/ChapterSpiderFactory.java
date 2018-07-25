package novle.spider.util;

import novle.spider.NovelSiteEnum;
import novle.spider.impl.BxwxChapterSpider;
import novle.spider.impl.DefaultChapterSpider;
import novle.spider.interfaces.IChapterSpider;

public class ChapterSpiderFactory {
    private ChapterSpiderFactory() {}

    /**
     * 通过给定的url，返回一个实现了IChapterSpider接口的实现类
     * @param url
     * @return
     */
    public static IChapterSpider getChapterSpider(String url) {
        NovelSiteEnum novelSiteEnum = NovelSiteEnum.getEnumByurl(url);
        IChapterSpider chapterSpider = null;
        switch (novelSiteEnum) {
            case Bxwx :
                chapterSpider = new BxwxChapterSpider(); break;
            case Dingdianxioashuo:
            case bquge:
            case KanShuZhong :
                chapterSpider = new DefaultChapterSpider(); break;
        }
        return chapterSpider;
    }
}
