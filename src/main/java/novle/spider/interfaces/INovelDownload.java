package novle.spider.interfaces;


import novle.spider.config.Configuration;

public interface INovelDownload {
    /**
     * 下载一个小说到本地磁盘
     * @param url    指定小说的章节列表页面的url
     * @param config   下载的线程及其基本配置
     * @return
     */
    public String download(String url,Configuration config);
}
