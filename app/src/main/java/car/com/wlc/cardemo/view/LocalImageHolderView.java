package car.com.wlc.cardemo.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.holder.Holder;

import car.com.wlc.cardemo.R;
import car.com.wlc.cardemo.javaBean.BannerItem;

/**
 * Created by wulixia on 2017/3/2.
 */

public class LocalImageHolderView implements Holder<BannerItem> {
    private View view;
    @Override
    public View createView(Context context) {

        LayoutInflater from = LayoutInflater.from(context);
        view = from.inflate(R.layout.banner_item, null);

        return view;
    }

    @Override
    public void UpdateUI(Context context, int position, BannerItem data) {
        ((TextView) view.findViewById(R.id.banner_text)).setText(data.getTitle());
        ((ImageView) view.findViewById(R.id.banner_image)).setBackgroundResource(data.getImageId());
    }
}
