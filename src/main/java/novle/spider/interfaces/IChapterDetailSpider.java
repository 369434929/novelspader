package novle.spider.interfaces;

import novle.spider.entitys.ChapterDetail;

public interface IChapterDetailSpider {
 /**
  * 给一个url  返回一个对应小说的实体内容
  * @param url
  * @return
  */
 public ChapterDetail getChapterDetail(String url);
}
