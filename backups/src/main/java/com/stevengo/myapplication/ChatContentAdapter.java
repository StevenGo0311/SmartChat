package com.stevengo.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by StevenGo on 2017/10/3.
 *
 */

public class ChatContentAdapter extends BaseAdapter {
    private List<ChatMessage> mChatContent;
    private Context mContext;
    private final int SEND=0;
    private final int RECEIVE=1;
    LayoutInflater inflater;
    public ChatContentAdapter(Context context ,List<ChatMessage> chatContent){
        this.mContext=context;
        this.mChatContent =chatContent;
        inflater= LayoutInflater.from(mContext);
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public int getCount() {
        return mChatContent.size();
    }

    @Override
    public Object getItem(int i) {
        return getItem(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public int getItemViewType(int position) {
        int type = mChatContent.get(position).getOrigin();
        if (type == 0) {
            return SEND;
        } else {
            return RECEIVE;
        }
    }
    @Override
    public int getViewTypeCount() {
        return 2;
    }
    public void onDataChange(List<ChatMessage> chatContent){
        this.mChatContent=chatContent;
        this.notifyDataSetChanged();

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ChatMessage chatMessage= mChatContent.get(i);
        ViewHolderSend viewHolderSend;
        ViewHolderReceive viewHolderReceive;

        if(view==null){

            switch (getItemViewType(i)){
                case RECEIVE:
                    view=inflater.inflate(R.layout.list_item_receive,null);
                    viewHolderReceive=new ViewHolderReceive();
                    viewHolderReceive.textView=(TextView) view.findViewById(R.id.id_textview_message_receive);
                    viewHolderReceive.textView.setText(chatMessage.getContent());
                    view.setTag(viewHolderReceive);
                    break;
                case SEND:
                    view=inflater.inflate(R.layout.list_item_send,null);
                    viewHolderSend=new ViewHolderSend();
                    viewHolderSend.textView=(TextView)view.findViewById(R.id.id_textview_message_send);
                    viewHolderSend.textView.setText(chatMessage.getContent());
                    view.setTag(viewHolderSend);
                    break;
            }
        }else{
            switch (getItemViewType(i)){
                case RECEIVE:
                    viewHolderReceive=(ViewHolderReceive) view.getTag();
                    viewHolderReceive.textView.setText(chatMessage.getContent());
                    break;
                case SEND:
                    viewHolderSend=(ViewHolderSend) view.getTag();
                    viewHolderSend.textView.setText(chatMessage.getContent());
                    break;
            }
        }
        return view;
    }
    class ViewHolderSend{
        TextView textView;
    }
    class ViewHolderReceive{
        TextView textView;
    }


}
