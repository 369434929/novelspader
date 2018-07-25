package novle.spider.impl;

import novle.spider.entitys.Chapter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BxwxChapterSpider extends AbstractChaperSpider {
    public List<Chapter> getsChapter(String url) {
        List<Chapter> chapters = super.getsChapter(url);
        Collections.sort(chapters, new Comparator<Chapter>() {
            @Override
            public int compare(Chapter o1, Chapter o2) {
                String o1Url = o1.getUrl();
                String o2Url = o2.getUrl();
                //参考字符串的方法。
                String o1Index = o1Url.substring(o1Url.lastIndexOf('/') + 1, o1Url.lastIndexOf('.'));
                String o2Index = o2Url.substring(o2Url.lastIndexOf('/') + 1, o2Url.lastIndexOf('.'));
                return o1Index.compareTo(o2Index);
            }
        });
        return chapters;
    }
}
