package novle.spider.util;

import novle.spider.NovelSiteEnum;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.lang.model.util.Elements;
import java.io.*;
import java.util.*;

public final class NovelSpiderutil {
    private static final Map<NovelSiteEnum,Map<String , String >> context_Map = new HashMap<>();
    static {
        init();
    }
    private NovelSpiderutil(){};

    private static void init(){
        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read(new File("E:\\IDEA\\novelspader\\src\\main\\resources\\Spider-Rule.xml"));
            Element root = document.getRootElement();
            List<Element> sites = root.elements("site");
            for (Element site : sites){
               List <Element> sbus = site.elements();
               Map<String,String > subMap = new HashMap<>();

               for (Element sub : sbus){
                   String name = sub.getName();
                   String text = sub.getTextTrim();
                   subMap.put(name,text);
               }
                context_Map.put(NovelSiteEnum.getEnumByurl(subMap.get("url")),subMap);
            }
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 拿到对应网站的解析规则
     * @param novelSiteEnum
     * @return
     */
    public static Map<String,String> getContext(NovelSiteEnum novelSiteEnum){
        return context_Map.get(novelSiteEnum);
    }
    /**
     * 多个文件合并为一个文件，合并规则：按文件名分割排序
     * @param path 基础目录，该根目录下的所有文本文件都会被合并到 mergeToFile
     * @param mergeToFile 被合并的文本文件，这个参数可以为null,合并后的文件保存在path/merge.txt
     */
    public static void multiFileMerge(String path, String mergeToFile, boolean deleteThisFile) {
        mergeToFile = mergeToFile == null ? path + "/merge.txt" : mergeToFile;
        File[] files = new File(path).listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".txt");
            }
        });
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                int o1Index = Integer.parseInt(o1.getName().split("\\-")[0]);
                int o2Index = Integer.parseInt(o2.getName().split("\\-")[0]);
                if (o1Index > o2Index) {
                    return 1;
                } else if (o1Index == o2Index){
                    return 0;
                } else {
                    return -1;
                }
            }
        });
        PrintWriter out = null;
        try {
            out = new PrintWriter(new File(mergeToFile), "UTF-8");
            for (File file : files) {
                BufferedReader bufr = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
                String line = null;
                while ((line = bufr.readLine()) != null) {
                    out.println(line);
                }
                bufr.close();

                if (deleteThisFile) {
                    file.delete();
                }
            }
        } catch (IOException e1) {
            throw new RuntimeException(e1);
        } finally {
            out.close();
        }
    }
}
