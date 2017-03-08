package car.com.wlc.cardemo.javaBean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by Administrator on 2017/1/4.
 */
@Table(name = "StyleNameBean")
public class StyleNameBean  {
    @Column(name = "brandId",isId = true)
    private String brandId;
    @Column(name ="styleName")
    private String styleName;

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }
}
