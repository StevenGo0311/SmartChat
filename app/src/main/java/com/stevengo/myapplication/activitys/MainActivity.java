package com.stevengo.myapplication.activitys;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.stevengo.myapplication.R;
import com.stevengo.myapplication.adapters.ChatContentAdapter;
import com.stevengo.myapplication.entitys.ChatMessage;
import com.stevengo.myapplication.entitys.Parameter;
import com.stevengo.myapplication.utils.ActiivtyStack;
import com.stevengo.myapplication.utils.DateUtil;
import com.stevengo.myapplication.utils.DoFriendNameUtil;
import com.stevengo.myapplication.utils.GeneralUtil;
import com.stevengo.myapplication.utils.InternetUtil;
import com.stevengo.myapplication.utils.SQLiteUtil;
import com.stevengo.myapplication.utils.SoftHideKeyBoardUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView mListView;
    private TextView mEditText;
    private Button mButtonSend;
    private Button mButtonSetting;
    private TextView mTextView;

    private ImageView mIconReceive;
    private ImageView mIconSend;

    private List<ChatMessage> messageList;
    private static final int SEND_CODE=500000;

    private final static String ICON_NAME_RECEIVE="iconRecevie.jpg";
    private final static String ICON_NAME_SEND="iconSend.jpg";

    private ChatContentAdapter mChatContentAdapter;
    private ChatMessage receiveMessage;
    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            setAdapter(mListView);
            //listView.setAdapter(new ChatContentAdapter(getApplicationContext(),messageList));
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //沉浸式
        GeneralUtil.fullSceeen(this);
        setContentView(R.layout.activity_main);
        ActiivtyStack.getScreenManager().pushActivity(this);
        //解决输入法遮挡输入栏的问题
        SoftHideKeyBoardUtil.assistActivity(this);
        initView();
        initData();
        mButtonSetting.setOnClickListener(this);
        mButtonSend.setOnClickListener(this);
    }
    private void initView(){
        mListView=(ListView)findViewById(R.id.id_listview_chat_content);
        mEditText=(EditText)findViewById(R.id.id_edittext_text);
        mButtonSend=(Button)findViewById(R.id.id_button_send);
        mButtonSetting=(Button)findViewById(R.id.id_button_setting);
        mTextView=(TextView)findViewById(R.id.id_textview_friend_name);
    }
    private void initData(){
        messageList=new ArrayList<>();
        //1.读取好友姓名；
        mTextView.setText(DoFriendNameUtil.readFriendName(this));
        //2.读取历史聊天记录
        messageList.clear();
        messageList.addAll(SQLiteUtil.readChatMessage(this,0));
        setAdapter(mListView);
    }
    private void forButtonSend(){
        //1.获取editText的内容；
        final String editContent=mEditText.getText().toString().trim();
        if(!editContent.equals("")){
            final ChatMessage chatMessage=new ChatMessage(SEND_CODE,editContent, DateUtil.getCurrentDate());
            SQLiteUtil.writeChatMessage(this,chatMessage);
            messageList.add(chatMessage);
            setAdapter(mListView);
            mEditText.setText("");
            //判断网络
            if(InternetUtil.isInternetConnected(this)){
                new Thread(){
                    @Override
                    public void run() {
                        Parameter parameter=new Parameter(editContent,android.os.Build.MODEL);
                        receiveMessage=InternetUtil.doPost(parameter);
                        SQLiteUtil.writeChatMessage(getApplicationContext(),receiveMessage);
                        messageList.add(receiveMessage);
                        mHandler.sendEmptyMessage(0);
                    }
                }.start();
            }else{
                Toast.makeText(this, "网络貌似不能用啊~", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "你输入空格让我情何以堪", Toast.LENGTH_SHORT).show();
        }
    }
    private void setAdapter(ListView listView){
        if(mChatContentAdapter==null){
            mChatContentAdapter=new ChatContentAdapter(this,messageList);
            listView.setAdapter(mChatContentAdapter);
            listView.smoothScrollToPosition(listView.getCount()-1);
        }else{
            mChatContentAdapter.onDataChange(messageList);
            listView.smoothScrollToPosition(listView.getCount()-1);
        }
    }
    private void forButtonSetting(){
        Intent intent=new Intent(MainActivity.this,SettingActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.id_button_setting:
                forButtonSetting();
                break;
            case R.id.id_button_send:
                forButtonSend();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
    @Override
    protected void onDestroy() {
        ActiivtyStack.getScreenManager().popActivity(this);
        super.onDestroy();
    }
}
