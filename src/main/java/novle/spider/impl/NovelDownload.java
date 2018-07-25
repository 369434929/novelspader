package novle.spider.impl;

import novle.spider.NovelSiteEnum;
import novle.spider.config.Configuration;
import novle.spider.entitys.Chapter;
import novle.spider.entitys.ChapterDetail;
import novle.spider.interfaces.IChapterDetailSpider;
import novle.spider.interfaces.IChapterSpider;
import novle.spider.interfaces.INovelDownload;
import novle.spider.util.ChapterDetailSpiderFactory;
import novle.spider.util.ChapterSpiderFactory;
import novle.spider.util.NovelSpiderutil;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.*;

/**
 * 如何实现多线程下载任意网站的小说
 * 	1.拿到该网站的某本小说的所有章节：章节列表
 * 	2.通过计算，将这些章节分配给指定数量的线程，让他们去解析，然后保存到文本文件中
 * 	3.通知主线程，将这些零散的小文件合并成一个大文件。最后将那些分片的小文件删除掉。
 */
public class NovelDownload implements INovelDownload {
    @Override
    public String download(String url,Configuration config) {
        IChapterSpider spider = ChapterSpiderFactory.getChapterSpider(url);
        Map<String, List<Chapter>> downloadTaskAlloc = new HashMap<>();
        List<Chapter> chapterList =spider.getsChapter(url);
        int size = config.getSize();
        //Math.ceil  有小数 向上取整  10.1 -->  11
        int maxThreadSize = (int)Math.ceil(chapterList.size()*1.0/size);
        for (int i = 0; i < maxThreadSize; i++) {
            // i = 0 0-99	-- > 100  0 100
            // i = 1 100-199
            // i = 2 200-299
            // i = 3 300-399
            // ...
            // i = 19 1900-1999
            // i = 20 2000-2052
            // 总共才2053章
            int fromIndex = i * config.getSize();
            int toIndex = i == maxThreadSize - 1 ? chapterList.size() : i * config.getSize() + config.getSize();
            downloadTaskAlloc.put(fromIndex + "-" + toIndex, chapterList.subList(fromIndex, toIndex));
        }
        ExecutorService service = Executors.newFixedThreadPool(maxThreadSize);
        Set<String> keySet = downloadTaskAlloc.keySet();
        List<Future<String>> tasks = new ArrayList<>();

        //通过这两段代码就可以创建缺失的路径
        String savePath = config.getPath() + "/" + NovelSiteEnum.getEnumByurl(url).getUrl();
        new File(savePath).mkdirs();

        for (String key : keySet) {

            tasks.add(service.submit(new DownloadCallable(savePath + "/" + key + ".txt", downloadTaskAlloc.get(key), config.getTryTimes())));
        }
        service.shutdown();
        for (Future<String> future : tasks) {
            try {
                System.out.println(future.get() + ",下载完成！");
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        NovelSpiderutil.multiFileMerge(savePath, null, true);
        return savePath + "/merge.txt";
    }
}
class DownloadCallable implements Callable<String> {
    private List<Chapter> chapters;
    private String path;
    private int tryTimes;
    public DownloadCallable(String path, List<Chapter> chapters, int tryTimes) {
        this.path = path;
        this.chapters = chapters;
        this.tryTimes = tryTimes;
    }
    @Override
    public String call() throws Exception {
        try (
                PrintWriter out = new PrintWriter(new File(path), "UTF-8");
        ) {
            for (Chapter chapter : chapters) {
                IChapterDetailSpider spider = ChapterDetailSpiderFactory.getChapterDetailSpider(chapter.getUrl());
                ChapterDetail detail = null;

                for (int i = 0; i < tryTimes; i++) {
                    try {
                        detail = spider.getChapterDetail(chapter.getUrl());
                        out.println(detail.getTitle());
                        out.println(detail.getContent());
                        break;
                    } catch (RuntimeException e) {
                        System.err.println("尝试第[" + (i + 1) + "/" + tryTimes + "]次下载失败了！");
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return path;
    }

}
