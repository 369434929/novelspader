package novle.spider.entitys;


import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Objects;

/**
 * 章节详细内容实体
 */
public class ChapterDetail implements Serializable {
    private static final long serialVersionUID = -8499950305178984006L;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPrev() {
        return prev;
    }

    public void setPrev(String prev) {
        this.prev = prev;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChapterDetail)) return false;
        ChapterDetail that = (ChapterDetail) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(content, that.content) &&
                Objects.equals(prev, that.prev) &&
                Objects.equals(next, that.next);
    }

    @Override
    public int hashCode() {

        return Objects.hash(title, content, prev, next);
    }

    @Override
    public String toString() {
        return "ChapterDetail{" +
                "title='" + title + '\'' +
                ", content='" + StringUtils.abbreviate(content,30)  + '\'' +
                ", prev='" + prev + '\'' +
                ", next='" + next + '\'' +
                '}';
    }

    private String title;
    private String content;
    private String prev;
    private String next;

    public static void main(String [] args){
        ChapterDetail chapterDetail = new ChapterDetail();
        chapterDetail.setContent("（前面还有一个序章，大家不要漏看。）石村，位于苍莽山脉中，四周高峰大壑，茫茫群山巍峨 清晨，朝霞灿灿，仿若碎金一般洒落，沐浴在人身上暖洋洋在吞吐天精的一些老人也都露出笑容。");
        System.out.println(chapterDetail);
    }

}

