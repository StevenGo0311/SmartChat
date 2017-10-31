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
/**
 * 聊天页面
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**消息列表*/
    private ListView mListView;
    /**输入框*/
    private TextView mEditText;
    /**发送按钮*/
    private Button mButtonSend;
    /**设置按钮*/
    private Button mButtonSetting;
    /**好友姓名*/
    private TextView mTextView;
    /**存储消息的list*/
    private List<ChatMessage> messageList;
    /**发送消息的代码*/
    private static final int SEND_CODE=500000;
    /**消息内容适配器*/
    private ChatContentAdapter mChatContentAdapter;
    /**接收到的消息*/
    private ChatMessage receiveMessage;
    /**设置消息内容*/
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
        //初始化视图
        initView();
        //初始化数据
        initData();
        //向按钮写入监听
        mButtonSetting.setOnClickListener(this);
        mButtonSend.setOnClickListener(this);
    }
    /**初始化视图*/
    private void initView(){
        //从布局文件中得到各个组件
        mListView=(ListView)findViewById(R.id.id_listview_chat_content);
        mEditText=(EditText)findViewById(R.id.id_edittext_text);
        mButtonSend=(Button)findViewById(R.id.id_button_send);
        mButtonSetting=(Button)findViewById(R.id.id_button_setting);
        mTextView=(TextView)findViewById(R.id.id_textview_friend_name);
    }
    /**初始化数据*/
    private void initData(){
        messageList=new ArrayList<>();
        //1.读取好友姓名；
        mTextView.setText(DoFriendNameUtil.readFriendName(this));
        messageList.clear();
        //2.读取历史聊天记录
        messageList.addAll(SQLiteUtil.readChatMessage(this,0));
        //3.添加适配器
        setAdapter(mListView);
    }
    private void forButtonSend(){
        //1.获取editText的内容；
        final String editContent=mEditText.getText().toString().trim();
        //2.当文本框里有内容的时候进行消息处理
        if(!editContent.equals("")){
            //2.1创建消息实体
            final ChatMessage chatMessage=new ChatMessage(SEND_CODE,editContent, DateUtil.getCurrentDate());
            //2.2将消息写入历史记录
            SQLiteUtil.writeChatMessage(this,chatMessage);
            //2.3将消息添加到列表中
            messageList.add(chatMessage);
            //2.4设置适配器
            setAdapter(mListView);
            //2.5发送框清空
            mEditText.setText("");
            //判断网络
            if(InternetUtil.isInternetConnected(this)){
                new Thread(){
                    @Override
                    public void run() {
                        //创建参数对象
                        Parameter parameter=new Parameter(editContent,android.os.Build.MODEL);
                        //以上面创建的参数访问网络，并返回接收到消息的实体
                        receiveMessage=InternetUtil.doPost(parameter);
                        //将得到的消息存储到本地数据库中
                        SQLiteUtil.writeChatMessage(getApplicationContext(),receiveMessage);
                        //将该消息添加到列表
                        messageList.add(receiveMessage);
                        //通知界面更新
                        mHandler.sendEmptyMessage(0);
                    }
                }.start();
            //网络不可用时打印提示
            }else{
                Toast.makeText(this, "网络貌似不能用啊~", Toast.LENGTH_SHORT).show();
            }
        //用户输入空格的时时候打印提示
        }else{
            Toast.makeText(this, "你输入空格让我情何以堪", Toast.LENGTH_SHORT).show();
        }
    }
    /**设置listView适配器*/
    private void setAdapter(ListView listView){
        //判断适配器是否存在，如果不存在，创建一个，然后将数据设置进去，将当前位置移动到最后
        if(mChatContentAdapter==null){
            mChatContentAdapter=new ChatContentAdapter(this,messageList);
            listView.setAdapter(mChatContentAdapter);
            listView.smoothScrollToPosition(listView.getCount()-1);
        //当适配器存在的时候，直接修改数据，然后将位置移动到最后
        }else{
            mChatContentAdapter.onDataChange(messageList);
            listView.smoothScrollToPosition(listView.getCount()-1);
        }
    }
    /**设置按钮的操作，启动新的activity*/
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
        //重置数据
        initData();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //将该actiity弹出栈
        ActiivtyStack.getScreenManager().popActivity(this);

    }
}
