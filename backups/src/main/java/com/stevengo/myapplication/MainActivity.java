package com.stevengo.myapplication;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView mListView;
    private EditText mEditText;
    private Button mButton;

    private String mContentSend;
    private List<ChatMessage> messageList;
    ChatMessageReceive chatMessageReceive;
    ChatContentAdapter mChatContentAdapter;

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(mChatContentAdapter==null){
                mChatContentAdapter=new ChatContentAdapter(getApplicationContext(),messageList);
                mListView.setAdapter(mChatContentAdapter);
                mListView.smoothScrollToPosition(mListView.getCount()-1);
            }else{
                mChatContentAdapter.onDataChange(messageList);
                mListView.smoothScrollToPosition(mListView.getCount()-1);
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView=(ListView)findViewById(R.id.id_listView_chat_content);
        mEditText=(EditText)findViewById(R.id.id_editText_text);
        mButton=(Button)findViewById(R.id.id_button_send);

        messageList=new ArrayList<>();
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContentSend=mEditText.getText().toString().trim();
                Log.d("StevenGo",mContentSend);
                if(!mContentSend.equals("")){
                    messageList.add(new ChatMessage(0,mContentSend));
                    if(mChatContentAdapter==null){
                        mChatContentAdapter=new ChatContentAdapter(getApplicationContext(),messageList);
                        mListView.setAdapter(mChatContentAdapter);
                        mListView.smoothScrollToPosition(mListView.getCount()-1);
                    }else{
                        mChatContentAdapter.onDataChange(messageList);
                        mListView.smoothScrollToPosition(mListView.getCount()-1);
                    }
                    mEditText.getText().clear();
                    new Thread(){
                        @Override
                        public void run() {
                            chatMessageReceive=InternetUtil.doGet(new Parameter(mContentSend));
                            messageList.add(new ChatMessage(1,chatMessageReceive.getText()));
                            mHandler.sendEmptyMessage(0);
                            for (int i = 0; i <messageList.size() ; i++) {
                                Log.d("StevenGo","正在打"+i+":"+messageList.get(i).getContent());
//
                            }
                        }
                    }.start();
                }
                else{
                    Toast.makeText(MainActivity.this, "输入内容不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
