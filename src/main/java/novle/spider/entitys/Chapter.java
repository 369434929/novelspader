package novle.spider.entitys;

import java.io.Serializable;
import java.util.Objects;


/**
 * 小说的章节的内容
 * @author ChenerTao
 * @date 2018年6月30日
 *
 */

public class Chapter implements Serializable {
    /**
     * 为什么要添加序列化版本ID了(serialVersionUID)?
     *
     * 通过判断实体类的serialVersionUID来验证版本一致性的。在进行反序列化时，JVM会把传来的字节流中的serialVersionUID与本地相应实体类的serialVersionUID进行比较，
     *
     * 如果相同就认为是一致的，可以进行反序列化，否则就会出现序列化版本不一致的异常，并且服务器上的项目运行一段时间后就会莫名其妙的崩掉，因为实体类没有序列化
     *
     * 和生成版本ID造成内存溢出
     */
    private static final long serialVersionUID = -473385236059162380L;

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Chapter)) return false;
        Chapter chapter = (Chapter) o;
        return Objects.equals(title, chapter.title) &&
                Objects.equals(url, chapter.url);
    }

    @Override
    public int hashCode() {

        return Objects.hash(title, url);
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    private String title;
    private String url;

}
