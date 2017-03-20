package car.com.wlc.cardemo.chatmessage.chat.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;



import java.util.ArrayList;
import java.util.List;

import car.com.wlc.cardemo.R;
import car.com.wlc.cardemo.chatmessage.chat.models.User;
import car.com.wlc.cardemo.chatmessage.chat.views.adapters.MyAdapter;


/**
 * Created by wulixia on 2017/3/11.
 */
public class ChatFragment extends Fragment {
    private ListView mLv;
    private List<User> list;
    private String TAG = "123";

    public static ChatFragment newInstance() {

        Bundle args = new Bundle();

        ChatFragment fragment = new ChatFragment();
        //   fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mLv = ((ListView) view.findViewById(R.id.list_view));
        list = new ArrayList<>();
        initUsers();
        mLv.setAdapter(new MyAdapter(getContext(), list, 0));

    }

    /**
     * 初始化一些聊天用户
     */
    private void initUsers() {
        //User id
        int myId = 0;
        //User icon
        int myIcon = R.mipmap.face_2;
        //User name
        String myName = "Michael";

        int yourId = 0;
        int yourIcon =  R.mipmap.face_1;
        String yourName = "Emily";
        User me = new User(myId, myName, myIcon);
        User you = new User(yourId, yourName, yourIcon);

        list.add(me);
        list.add(you);
    }

}
