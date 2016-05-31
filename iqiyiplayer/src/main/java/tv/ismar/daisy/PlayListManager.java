package cn.ismartv.iqiyiplayer;

import com.qiyi.sdk.player.IMedia;
import com.qiyi.sdk.player.SdkVideo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * DEBUG CODE, simulate playlist
 *
 */
public class PlayListManager {
    public static final IMedia MEDIA_STANDARD = new SdkVideo("202203201", "377866500", false);  //流畅码流
    public static final IMedia MEDIA_HIGH = new SdkVideo("202203201", "377866500", false);  //高清码流
    public static final IMedia MEDIA_720P = new SdkVideo("202203201", "377866500", false);  //720P码流
    public static final IMedia MEDIA_1080P = new SdkVideo("202203201", "377866500", false);  //1080P码流
    public static final IMedia MEDIA_4K = new SdkVideo("223725200", "223725200", true);  //4K码流
    
    public static final IMedia MEDIA_H265 = new SdkVideo("202203201", "377866500", false);  //H265码流
    public static final IMedia MEDIA_DOLBY = new SdkVideo("202168401", "310271100", false);  //杜比码流
    
    public static final IMedia MEDIA_VIP_MINUTE1 = new SdkVideo("101160800", "101160800", true);  //VIP, 分钟试看
    public static final IMedia MEDIA_VIP_MINUTE2 = new SdkVideo("367710700", "367710700", true);  //VIP, 分钟试看

    public static final IMedia MEDIA_VIP_WHOLE1 = new SdkVideo("102155301", "99920700", true);  //VIP, 整集试看
    public static final IMedia MEDIA_VIP_WHOLE2 = new SdkVideo("102155301", "99920600", true);
    
    public static final IMedia MEDIA_VIP_CANNOTPREVIEW1 = new SdkVideo("203062601", "409313100", true);  //VIP, 不能试看


    private int mIndex;
    private List<IMedia> mPlaylist = new ArrayList<IMedia>();
    private AtomicBoolean mInitialized = new AtomicBoolean(false);
    private void preparePlaylist() {
        mPlaylist = new ArrayList<IMedia>();
        mPlaylist.add(MEDIA_STANDARD);
        mPlaylist.add(MEDIA_HIGH);
        mPlaylist.add(MEDIA_720P);
        mPlaylist.add(MEDIA_1080P);
        mPlaylist.add(MEDIA_4K);
        mPlaylist.add(MEDIA_DOLBY);
        mPlaylist.add(MEDIA_H265);
        mPlaylist.add(MEDIA_VIP_MINUTE1);
        mPlaylist.add(MEDIA_VIP_MINUTE2);
        mPlaylist.add(MEDIA_VIP_WHOLE1);
        mPlaylist.add(MEDIA_VIP_WHOLE2);
        mPlaylist.add(MEDIA_VIP_CANNOTPREVIEW1);
    }
    public synchronized void initialize() {
        preparePlaylist();
        mIndex = 0;
        mInitialized.set(true);
    }
    
    public synchronized IMedia getCurrent() {
        if (!mInitialized.get()) {
            throw new IllegalStateException("getCurrent: please invoke initialize() before using it");
        }
        if (mPlaylist.isEmpty() || mIndex < 0 || mIndex >= mPlaylist.size()) {
            return null;
        }
        return mPlaylist.get(mIndex);
    }
    
    public synchronized boolean moveToNext() {
        mIndex++;
        if (mIndex >= mPlaylist.size()) {
            mIndex = 0;
        }
        return mIndex < mPlaylist.size(); 
    }
    
    public synchronized void reset() {
        mIndex = 0;
        mPlaylist.clear();
        mInitialized.set(false);
    }
}
