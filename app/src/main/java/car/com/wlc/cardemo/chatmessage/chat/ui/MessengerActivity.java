package car.com.wlc.cardemo.chatmessage.chat.ui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;


import java.util.ArrayList;
import java.util.Random;

import car.com.wlc.cardemo.R;
import car.com.wlc.cardemo.chatmessage.chat.models.Message;
import car.com.wlc.cardemo.chatmessage.chat.models.User;
import car.com.wlc.cardemo.chatmessage.chat.utils.AppData;
import car.com.wlc.cardemo.chatmessage.chat.utils.ChatBot;
import car.com.wlc.cardemo.chatmessage.chat.utils.MessageList;
import car.com.wlc.cardemo.chatmessage.chat.utils.MyMessageStatusFormatter;
import car.com.wlc.cardemo.chatmessage.chat.views.ChatView;


/**
 * Simple chat example activity
 * Created by wulixia on 2017/3/10.
 */
public class MessengerActivity extends Activity {

    private ChatView mChatView;
    private MessageList mMessageList;
    private ArrayList<User> mUsers;
    private User me;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        me = (User) getIntent().getSerializableExtra("user");

        initUsers();

        mChatView = (ChatView) findViewById(R.id.chat_view);

        //Set UI parameters if you need
        mChatView.setRightBubbleColor(ContextCompat.getColor(this, R.color.blue500));
        mChatView.setLeftBubbleColor(ContextCompat.getColor(this, R.color.gray300));
        mChatView.setBackgroundColor(ContextCompat.getColor(this, R.color.blueGray200));
        mChatView.setSendButtonColor(ContextCompat.getColor(this, R.color.lightBlue500));
        mChatView.setSendIcon(R.mipmap.ic_action_send);
        mChatView.setRightMessageTextColor(Color.WHITE);
        mChatView.setLeftMessageTextColor(Color.BLACK);
        mChatView.setUsernameTextColor(ContextCompat.getColor(this, R.color.blueGray500));
        mChatView.setSendTimeTextColor(ContextCompat.getColor(this, R.color.blueGray500));
        mChatView.setDateSeparatorColor(ContextCompat.getColor(this, R.color.blueGray500));
        mChatView.setInputTextHint("new message...");
        mChatView.setMessageMarginTop(5);
        mChatView.setMessageMarginBottom(5);

        //Click Send Button
        mChatView.setOnClickSendButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initUsers();
                //new message
                Message message = new Message.Builder()
                        .setUser(mUsers.get(0))
                        .setRightMessage(true)
                        .setMessageText(mChatView.getInputText())
                        .hideIcon(false)
                        .setStatusIconFormatter(new MyMessageStatusFormatter(MessengerActivity.this))
                        .setStatusTextFormatter(new MyMessageStatusFormatter(MessengerActivity.this))
                        .setMessageStatusType(Message.MESSAGE_STATUS_ICON)
                        .build();
                if (mUsers.get(0).getIcon() == 0) {
                    Log.d(getClass().getName(), mUsers.get(0).getName() + "'s icon is null ");
                }
                //Set random status(Delivering, delivered, seen or fail)
                int messageStatus = new Random().nextInt(4);
                message.setStatus(messageStatus);
                //Set to chat view
                mChatView.send(message);
                //Add message list
                mMessageList.add(message);
                //Reset edit text
                mChatView.setInputText("");

                //Ignore hey
                if (!message.getMessageText().contains("hey")) {
                    //talkContent
                    String talk = "车马炮欢迎你的加入！";

                    messageStatus = new Random().nextInt(4);
                    //Receive message
                    final Message receivedMessage = new Message.Builder()
                            .setUser(mUsers.get(1))
                            .setRightMessage(false)
                            .setMessageText(ChatBot.talk(mUsers.get(1).getName(),message.getMessageText()))
                            .setStatusIconFormatter(new MyMessageStatusFormatter(MessengerActivity.this))
                            .setStatusTextFormatter(new MyMessageStatusFormatter(MessengerActivity.this))
                            .setMessageStatusType(Message.MESSAGE_STATUS_ICON)
                            .setStatus(messageStatus)
                            .build();

                    // This is a demo bot
                    // Return within 3 seconds
                    int sendDelay = (new Random().nextInt(4) + 1) * 1000;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mChatView.receive(receivedMessage);
                            //Add message list
                            mMessageList.add(receivedMessage);
                        }
                    }, sendDelay);
                }
            }

        });

        //Load saved messages
        loadMessages();

    }



    private void initUsers() {
        mUsers = new ArrayList<>();
        mUsers.add(me);

        int yourId = 1;
        int yourIcon =  R.mipmap.robot;
        String yourName = "Robot";

//        final UserInfo me = new UserInfo(myId, myName, myIcon);
        final User you = new User(yourId, yourName, yourIcon);
        mUsers.add(you);
    }

    /**
     * Load saved messages
     */
    private void loadMessages() {
        mMessageList = AppData.getMessageList(this);
        if (mMessageList == null) {
            mMessageList = new MessageList();
        } else {
            for (int i = 0; i < mMessageList.size(); i++) {
                Message message = mMessageList.get(i);
                //Set extra info because they were removed before save messages.
                for (User user : mUsers) {
                    if (message.getUser().getId() == user.getId()) {
                        message.getUser().setIcon(user.getIcon());
                    }
                }
                if (!message.isDateCell()) {
                    if (message.isRightMessage()) {
                        message.hideIcon(false);
                        mChatView.send(message);
                    } else {
                        mChatView.receive(message);
                    }

                }
                message.setMessageStatusType(Message.MESSAGE_STATUS_ICON_RIGHT_ONLY);
                message.setStatusIconFormatter(new MyMessageStatusFormatter(this));
                message.setStatusTextFormatter(new MyMessageStatusFormatter(this));
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initUsers();
    }

    @Override
    public void onPause() {
        super.onPause();
        //Save message
        AppData.putMessageList(this, mMessageList);
    }

}
