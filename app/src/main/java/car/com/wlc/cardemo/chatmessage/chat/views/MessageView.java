package car.com.wlc.cardemo.chatmessage.chat.views;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import car.com.wlc.cardemo.chatmessage.chat.models.Message;
import car.com.wlc.cardemo.chatmessage.chat.utils.TimeUtils;
import car.com.wlc.cardemo.chatmessage.chat.views.adapters.MessageAdapter;

/**
 * Created by wulixia on 2017/3/11.
 */
public class MessageView extends ListView implements View.OnFocusChangeListener{

    /**
     * All contents such as right message, left message, date label
     */
    private ArrayList<Object> mChatList = new ArrayList<>();
    /**
     * Only messages
     */
    private ArrayList<Message> mMessageList = new ArrayList<>();

    private MessageAdapter mMessageAdapter;

    private OnKeyboardAppearListener mOnKeyboardAppearListener;

    /**
     * MessageView is refreshed at this time
     */
    private long mRefreshInterval = 60000;
    /**
     * Refresh scheduler
     */
    private Timer mRefreshTimer;

    private Handler mHandler;


    public interface OnKeyboardAppearListener {
        void onKeyboardAppeared(boolean hasChanged);
    }

    public MessageView(Context context, ArrayList<Message> messages) {
        super(context);
        init(messages);
    }


    public MessageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MessageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public void init(ArrayList<Message> list) {
        mChatList = new ArrayList<>();
        setChoiceMode(ListView.CHOICE_MODE_NONE);

        for(int i=0; i < list.size(); i++){
            setMessage(list.get(i));
        }

        init();
    }

    /**
     * Initialize list
     */
    public void init() {
        setDividerHeight(0);
        mMessageAdapter = new MessageAdapter(getContext(), 0, mChatList);
        setAdapter(mMessageAdapter);

        mHandler = new Handler();
        mRefreshTimer = new Timer(true);
        mRefreshTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mMessageAdapter.notifyDataSetChanged();
                    }
                });
            }
        }, 1000, mRefreshInterval);
    }

    /**
     * Set date text before set message if sent at the different day
     * @param message new message
     */
    public void setMessage(Message message){
        if(mChatList.size() == 0){
            mChatList.add(message.getDateSeparateText());
        }else{
            //Get previous message to compare date
            Message prevMessage = mMessageList.get(mMessageList.size() - 1);

            //This is just difference between days
            if(TimeUtils.getDiffDays(prevMessage.getCreatedAt(), message.getCreatedAt()) != 0){
                //Set date label because of different day
                mChatList.add(message.getDateSeparateText());
            }

        }
        mChatList.add(message);
        mMessageList.add(message);
        mMessageAdapter.notifyDataSetChanged();

    }


    public void setOnKeyboardAppearListener(OnKeyboardAppearListener listener) {
        mOnKeyboardAppearListener = listener;
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);

        // if ListView became smaller
        if (height < oldHeight) {
            mOnKeyboardAppearListener.onKeyboardAppeared(true);
        }
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (hasFocus) {
            //Scroll to end
            scrollToEnd();
        }
    }

    public void scrollToEnd() {
        smoothScrollToPosition(getCount() - 1);
    }

    public void setLeftBubbleColor(int color) {
        mMessageAdapter.setLeftBubbleColor(color);
    }

    public void setRightBubbleColor(int color) {
        mMessageAdapter.setRightBubbleColor(color);
    }

    public void setUsernameTextColor(int color) {
        mMessageAdapter.setUsernameTextColor(color);
    }

    public void setSendTimeTextColor(int color) {
        mMessageAdapter.setSendTimeTextColor(color);
    }

    public void setMessageStatusColorTextColor(int color) {
        mMessageAdapter.setStatusTextColor(color);
    }

    public void setDateSeparatorTextColor(int color) {
        mMessageAdapter.setDateSeparatorColor(color);
    }

    public void setRightMessageTextColor(int color) {
        mMessageAdapter.setRightMessageTextColor(color);
    }

    public void setLeftMessageTextColor(int color) {
        mMessageAdapter.setLeftMessageTextColor(color);
    }

    public void setMessageMarginTop(int px) {
        mMessageAdapter.setMessageTopMargin(px);
    }

    public void setMessageMarginBottom(int px) {
        mMessageAdapter.setMessageBottomMargin(px);
    }

    /**
     * Return last object (right message or left message or date text)
     * @return last object of chat
     */
    public Object getLastChatObject() {
        if (mChatList.size() == 0) {
            return null;
        }
        return mChatList.get(mChatList.size() - 1);
    }

    public void setRefreshInterval(long refreshInterval) {
        mRefreshInterval = refreshInterval;
    }
}
