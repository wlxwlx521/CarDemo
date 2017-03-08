package car.com.wlc.cardemo.javaBean;

/**
 * Created by Administrator on 2017/3/3.
 */

public class BannerItem {
    private String title;
    private int imageId;

    public BannerItem(String title, int imageId) {
        this.title = title;
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
