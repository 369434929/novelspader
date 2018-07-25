package novle.spider;

/**
 *
 * 已经支持的小说网站枚举
 */
public enum  NovelSiteEnum {
    Dingdianxioashuo(1,"23us.com"),
    bquge(2,"biquge.com.tw"),
    KanShuZhong(3, "kanshuzhong.com"),
    Bxwx(4, "bxwx8.org");
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url;
    private NovelSiteEnum(int id, String url){
        this.id = id;
        this.url = url;
    }

    public static NovelSiteEnum getEnumByid(int id){
        switch(id){
            case 1: return Dingdianxioashuo;
            case 2: return bquge;
            case 3: return KanShuZhong;
            case 4: return Bxwx;
            default:throw new RuntimeException("id = "+ id+ "是不被支持的网站！");
        }
    }
    public static NovelSiteEnum getEnumByurl(String url){
        for (NovelSiteEnum novelSiteEnum : values()){
            if (url.contains(novelSiteEnum.url)){
                return  novelSiteEnum;
            }
        }
        throw new RuntimeException("url="+ url +"是不被支持的路径");
    }
}
