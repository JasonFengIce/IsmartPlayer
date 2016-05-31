package tv.ismar.daisy.data;

/**
 * Created by huibin on 5/31/16.
 */
public class ChannelListEntity {

    private int count;

    private String title;

    private String url;

    private int template;

    private boolean cycle_play;

    private boolean fixed;

    private String slug;

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return this.count;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    public void setTemplate(int template) {
        this.template = template;
    }

    public int getTemplate() {
        return this.template;
    }

    public void setCycle_play(boolean cycle_play) {
        this.cycle_play = cycle_play;
    }

    public boolean getCycle_play() {
        return this.cycle_play;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    public boolean getFixed() {
        return this.fixed;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getSlug() {
        return this.slug;
    }
}
