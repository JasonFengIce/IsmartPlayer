package tv.ismar.daisy.data;

import java.util.List;

/**
 * Created by huibin on 5/31/16.
 */
public class ChannelSectionEntity {
    private int count;

    private List<ItemEntity> objects;

    private int num_pages;

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return this.count;
    }

    public void setObjects(List<ItemEntity> objects) {
        this.objects = objects;
    }

    public List<ItemEntity> getObjects() {
        return this.objects;
    }

    public void setNum_pages(int num_pages) {
        this.num_pages = num_pages;
    }

    public int getNum_pages() {
        return this.num_pages;
    }
}
