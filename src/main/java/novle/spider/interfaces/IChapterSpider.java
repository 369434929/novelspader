package novle.spider.interfaces;

import novle.spider.entitys.Chapter;

import java.util.List;

public interface IChapterSpider {
    /**
     * 给我一个url 返回一个章节列表.
     * @param url
     * @return
     */
    public List<Chapter> getsChapter(String url);
}
