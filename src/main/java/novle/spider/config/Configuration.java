package novle.spider.config;

import java.io.Serializable;

public class Configuration implements Serializable {
    /**
     * 默认值为10090
     */
    public static final int DEFAULT_SIZE = 100;
    /**
     * 每个线程下载每一章所允许的最大尝试次数
     */
    public static final int DEFAULT_TRY_TIMES = 3;

    public String getPath() {
        return path;
    }
    public Configuration(){
        this.size = DEFAULT_SIZE;
        this.tryTimes = DEFAULT_TRY_TIMES;
    }
    public void setPath(String path) {
        this.path = path;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    /**
     * 下载后的文件的保存的根地址
     */
    private String path;
    /**
     * 每个子线程的下载章节数量
     */
    private int size;

    public int getTryTimes() {
        return tryTimes;
    }

    public void setTryTimes(int tryTimes) {
        this.tryTimes = tryTimes;
    }

    private int tryTimes;
}
