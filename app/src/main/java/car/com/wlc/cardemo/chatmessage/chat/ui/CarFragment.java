package car.com.wlc.cardemo.chatmessage.chat.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by wulixia on 2017/3/11.
 */
public class CarFragment extends Fragment {
    public static CarFragment newInstance() {
        
        Bundle args = new Bundle();
        
        CarFragment fragment = new CarFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
