package cn.ismartv.ismartplayer.data;

/**
 * Created by huaijie on 5/17/16.
 */
public class ChannelEntity {
    private boolean chargeable;

    private String homepage_url;

    private String icon_focus_url;

    private String homepage_template;

    private String name;

    private int template;

    private String url;

    private String icon_url;

    private int style;

    private String channel;

    public void setChargeable(boolean chargeable) {
        this.chargeable = chargeable;
    }

    public boolean getChargeable() {
        return this.chargeable;
    }

    public void setHomepage_url(String homepage_url) {
        this.homepage_url = homepage_url;
    }

    public String getHomepage_url() {
        return this.homepage_url;
    }

    public void setIcon_focus_url(String icon_focus_url) {
        this.icon_focus_url = icon_focus_url;
    }

    public String getIcon_focus_url() {
        return this.icon_focus_url;
    }

    public void setHomepage_template(String homepage_template) {
        this.homepage_template = homepage_template;
    }

    public String getHomepage_template() {
        return this.homepage_template;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setTemplate(int template) {
        this.template = template;
    }

    public int getTemplate() {
        return this.template;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public String getIcon_url() {
        return this.icon_url;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public int getStyle() {
        return this.style;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getChannel() {
        return this.channel;
    }
}
