package car.com.wlc.cardemo.chatmessage.chat.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;



import java.util.ArrayList;
import java.util.List;

import car.com.wlc.cardemo.R;
import car.com.wlc.cardemo.chatmessage.chat.models.User;
import car.com.wlc.cardemo.chatmessage.chat.views.adapters.MyAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {


    private ListView mLv;
    private List<User> list;

    public static ContactFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ContactFragment fragment = new ContactFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_layout, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mLv = ((ListView) view.findViewById(R.id.list_view));
        list=new ArrayList<>();
        initUsers();
        mLv.setAdapter(new MyAdapter(getContext(),list,1));
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = list.get(position);
                Intent intent = new Intent(getActivity(), MessengerActivity.class);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });

    }

    /**
     * 初始化一些聊天联系人
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

        final User me = new User(myId, myName, myIcon);
        final User you = new User(yourId, yourName, yourIcon);

        list.add(me);
        list.add(you);
    }
}
