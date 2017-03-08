package car.com.wlc.cardemo.view;

import android.content.Context;
import android.util.AttributeSet;

import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.MapView;


/**
 * Created by Administrator on 2017/1/21.
 */

public class MyMapView extends MapView {

    public MyMapView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet,0);
    }

    public MyMapView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public MyMapView(Context context, AMapOptions aMapOptions) {

        super(context, aMapOptions);

    }
}
