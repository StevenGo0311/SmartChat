package com.stevengo.myapplication.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.stevengo.myapplication.R;
import com.stevengo.myapplication.entitys.ChatMessage;
import com.stevengo.myapplication.utils.CustomUrlSpan;
import com.stevengo.myapplication.utils.DateUtils;
import com.stevengo.myapplication.utils.IconCacheUtil;

import java.util.List;

/**
 * Created by StevenGo on 2017/10/3.
 *
 */

public class ChatContentAdapter extends BaseAdapter {
    private List<ChatMessage> mChatMessages;
    private Context mContext;
    private final int SEND=0;
    private final int RECEIVE=1;
    private LayoutInflater inflater;
    private final static String ICON_NAME_RECEIVE="iconRecevie.jpg";
    private final static String ICON_NAME_SEND="iconSend.jpg";
    public ChatContentAdapter(Context context ,List<ChatMessage> chatMessages){
        this.mContext=context;
        this.mChatMessages =chatMessages;
        inflater= LayoutInflater.from(mContext);
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public int getCount() {
        return mChatMessages.size();
    }

    @Override
    public Object getItem(int i) {
        return mChatMessages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public int getItemViewType(int position) {
        int type = mChatMessages.get(position).getCode();
        if (type<100000) {
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
        this.mChatMessages=chatContent;
        this.notifyDataSetChanged();

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ChatMessage chatMessage= mChatMessages.get(i);
        ViewHolderSend viewHolderSend;
        ViewHolderReceive viewHolderReceive;

        if(view==null){
            switch (getItemViewType(i)){
                case RECEIVE:
                    view=inflater.inflate(R.layout.item_listview_receive_message,null);
                    viewHolderReceive=new ViewHolderReceive();
                    viewHolderReceive.textView=(TextView) view.findViewById(R.id.id_textview_message_receive);
                    viewHolderReceive.imageView=(ImageView)view.findViewById(R.id.id_imageview_image_receive);
                    viewHolderReceive.textViewTime=(TextView)view.findViewById(R.id.id_textView_time_receive);
                    viewHolderReceive.imageViewIcon=(ImageView)view.findViewById(R.id.id_imageview_icon_receive);
                    Bitmap iconReceive= IconCacheUtil.readIcon(mContext,ICON_NAME_RECEIVE);
                    if(iconReceive!=null){
                        viewHolderReceive.imageViewIcon.setImageBitmap(iconReceive);
                    }
                    else{
                        viewHolderReceive.imageViewIcon.setImageResource(R.drawable.icon_test_receive);
                    }
                    viewHolderReceive.imageView.setVisibility(View.VISIBLE);
                    if(i%5==0){
                        String date= DateUtils.getDate();
                        viewHolderReceive.textViewTime.setText(date);
                        viewHolderReceive.textViewTime.setVisibility(View.VISIBLE);
                    }
                    else{
                        viewHolderReceive.textViewTime.setVisibility(View.GONE);
                    }
                    switch (chatMessage.getCode()){
                        case 100000:
                            viewHolderReceive.textView.setText(chatMessage.getText());
                            viewHolderReceive.imageView.setVisibility(View.GONE);
                            break;
                        case 200000:
                            viewHolderReceive.textView.setText(chatMessage.getText()+chatMessage.getUrl());
                            viewHolderReceive.imageView.setVisibility(View.GONE);
                            break;
                        default:
                            viewHolderReceive.textView.setText(chatMessage.getText());
                            viewHolderReceive.imageView.setVisibility(View.GONE);
                            break;
                    }
                    interceptHyperLink(viewHolderReceive.textView);
                    view.setTag(viewHolderReceive);
                    break;
                case SEND:
                    view=inflater.inflate(R.layout.item_listview_send_message,null);
                    viewHolderSend=new ViewHolderSend();
                    viewHolderSend.textView=(TextView)view.findViewById(R.id.id_textview_message_send);
                    viewHolderSend.imageView=(ImageView)view.findViewById(R.id.id_imageview_image_send);
                    viewHolderSend.textViewTime=(TextView)view.findViewById(R.id.id_textView_time_send);
                    viewHolderSend.imageViewIcon=(ImageView)view.findViewById(R.id.id_imageview_icon_send);
                    Bitmap iconSend= IconCacheUtil.readIcon(mContext,ICON_NAME_SEND);
                    if(iconSend!=null){
                        viewHolderSend.imageViewIcon.setImageBitmap(iconSend);
                    }
                    else{
                        viewHolderSend.imageViewIcon.setImageResource(R.drawable.icon_test_send);
                    }
                    viewHolderSend.textView.setText(chatMessage.getText());
                    viewHolderSend.imageView.setVisibility(view.GONE);
                    if(i%5==0){
                        String date= DateUtils.getDate();
                        viewHolderSend.textViewTime.setText(date);
                        viewHolderSend.textViewTime.setVisibility(View.VISIBLE);
                    }else{
                        viewHolderSend.textViewTime.setVisibility(View.GONE);
                    }
                    view.setTag(viewHolderSend);
                    break;
            }
        }else{
            switch (getItemViewType(i)){
                case RECEIVE:
                    viewHolderReceive=(ViewHolderReceive) view.getTag();
                    Bitmap iconReceive= IconCacheUtil.readIcon(mContext,ICON_NAME_RECEIVE);
                    if(iconReceive!=null){
                        viewHolderReceive.imageViewIcon.setImageBitmap(iconReceive);
                    }
                    else{
                        viewHolderReceive.imageViewIcon.setImageResource(R.drawable.icon_test_receive);
                    }
                    viewHolderReceive.imageView.setVisibility(View.VISIBLE);
                    if(i%5==0){
                        String date= DateUtils.getDate();
                        viewHolderReceive.textViewTime.setText(date);
                        viewHolderReceive.textViewTime.setVisibility(View.VISIBLE);
                    }
                    else{
                        viewHolderReceive.textViewTime.setVisibility(View.GONE);
                    }
                    switch (chatMessage.getCode()){
                        case 100000:
                            viewHolderReceive.textView.setText(chatMessage.getText());
                            viewHolderReceive.imageView.setVisibility(View.GONE);
                            break;
                        case 200000:
                            viewHolderReceive.textView.setText(chatMessage.getText()+chatMessage.getUrl());
                            viewHolderReceive.imageView.setVisibility(View.GONE);
                            break;
                        default:
                            viewHolderReceive.textView.setText(chatMessage.getText());
                            viewHolderReceive.imageView.setVisibility(View.GONE);
                            break;
                    }
                    interceptHyperLink(viewHolderReceive.textView);
                    view.setTag(viewHolderReceive);
                    break;
                case SEND:
                    viewHolderSend=(ViewHolderSend) view.getTag();
                    Bitmap iconSend= IconCacheUtil.readIcon(mContext,ICON_NAME_SEND);
                    if(iconSend!=null){
                        viewHolderSend.imageViewIcon.setImageBitmap(iconSend);
                    }
                    else{
                        viewHolderSend.imageViewIcon.setImageResource(R.drawable.icon_test_send);
                    }
                    viewHolderSend.textView.setText(chatMessage.getText());
                    viewHolderSend.imageView.setVisibility(view.GONE);
                    if(i%5==0){
                        String date= DateUtils.getDate();
                        viewHolderSend.textViewTime.setText(date);
                        viewHolderSend.textViewTime.setVisibility(View.VISIBLE);
                    }else{
                        viewHolderSend.textViewTime.setVisibility(View.GONE);
                    }
                    break;
            }
        }
        return view;
    }
    class ViewHolderSend{
        TextView textView;
        ImageView imageView;
        TextView textViewTime;
        ImageView imageViewIcon;
    }
    class ViewHolderReceive{
        TextView textView;
        ImageView imageView;
        TextView textViewTime;
        ImageView imageViewIcon;
    }
    private void interceptHyperLink(TextView tv) {
        //Log.d("StevenGo","拦截");
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        CharSequence text = tv.getText();
        if (text instanceof Spannable) {
            int end = text.length();
            Spannable spannable = (Spannable) tv.getText();
            URLSpan[] urlSpans = spannable.getSpans(0, end, URLSpan.class);
            if (urlSpans.length == 0) {
                return;
            }

            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
            // 循环遍历并拦截 所有http://开头的链接
            for (URLSpan uri : urlSpans) {
                String url = uri.getURL();
                if (url.indexOf("http://") == 0) {
                    CustomUrlSpan customUrlSpan = new CustomUrlSpan(mContext,url);
                    spannableStringBuilder.setSpan(customUrlSpan, spannable.getSpanStart(uri),
                            spannable.getSpanEnd(uri), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                }
            }
            tv.setText(spannableStringBuilder);
        }
    }


}
