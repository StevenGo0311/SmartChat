package com.stevengo.myapplication.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.stevengo.myapplication.R;
import com.stevengo.myapplication.entitys.ChatMessage;
import com.stevengo.myapplication.utils.CustomUrlSpan;
import com.stevengo.myapplication.utils.DateUtil;
import com.stevengo.myapplication.utils.IconCacheUtil;

import java.util.List;

/**
 * Created by StevenGo on 2017/10/3.
 *消息内容适配器
 */

public class ChatContentAdapter extends BaseAdapter {
    /**消息实体*/
    private List<ChatMessage> mChatMessages;
    /**上下文*/
    private Context mContext;
    /**消息来源标记，发送*/
    private final int SEND=0;
    /**消息来源标记，接收*/
    private final int RECEIVE=1;
    private LayoutInflater inflater;
    /**头像名称*/
    private final static String ICON_NAME_RECEIVE="iconRecevie.jpg";
    private final static String ICON_NAME_SEND="iconSend.jpg";
    /**构造，初始化消息阿文和消息，同时得到LayoutInflater对象*/
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
    /**设置填充类型*/
    @Override
    public int getItemViewType(int position) {
        //得到消息代码
        int type = mChatMessages.get(position).getCode();
        //如果是500000设置为发送，如果是其他的设置为接收
        if (type==500000) {
            return SEND;
        } else {
            return RECEIVE;
        }
    }
    /**设置填充类型数量为2*/
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    /**更改数据*/
    public void onDataChange(List<ChatMessage> chatContent){
        this.mChatMessages=chatContent;
        this.notifyDataSetChanged();

    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ChatMessage chatMessage= mChatMessages.get(i);
        ViewHolderSend viewHolderSend;
        ViewHolderReceive viewHolderReceive;
        //判断view是否为空，如果为空进行下面的操作
        if(view==null){
            //判断消息来源
            switch (getItemViewType(i)){
                //接收
                case RECEIVE:
                    view=inflater.inflate(R.layout.item_listview_receive_message,null);
                    //从布局中获取组件
                    viewHolderReceive=new ViewHolderReceive();
                    viewHolderReceive.textView=(TextView) view.findViewById(R.id.id_textview_message_receive);
                    viewHolderReceive.imageView=(ImageView)view.findViewById(R.id.id_imageview_image_receive);
                    viewHolderReceive.textViewTime=(TextView)view.findViewById(R.id.id_textView_time_receive);
                    viewHolderReceive.imageViewIcon=(ImageView)view.findViewById(R.id.id_imageview_icon_receive);
                    Bitmap iconReceive= IconCacheUtil.readIcon(mContext,ICON_NAME_RECEIVE);
                    //获取头像
                    if(iconReceive!=null){
                        viewHolderReceive.imageViewIcon.setImageBitmap(iconReceive);
                    }
                    //当头像不存在的时候设置为默认头像
                    else{
                        viewHolderReceive.imageViewIcon.setImageResource(R.drawable.icon_test_receive);
                    }
//                    viewHolderReceive.imageView.setVisibility(View.VISIBLE);
                    //添加时间标记
                    if(i%5==0){
                        viewHolderReceive.textViewTime.setText(DateUtil.formatDate(chatMessage.getDate()));
                        viewHolderReceive.textViewTime.setVisibility(View.VISIBLE);
                    }
                    else{
                        viewHolderReceive.textViewTime.setVisibility(View.GONE);
                    }
                    //根据消息代码判断消息类型，然后显示不同的效果
                    switch (chatMessage.getCode()){
                        case 100000:
                            viewHolderReceive.textView.setText(chatMessage.getText());
//                            viewHolderReceive.imageView.setVisibility(View.GONE);
                            break;
                        case 200000:
                            viewHolderReceive.textView.setText(chatMessage.getText()+chatMessage.getUrl());
//                            viewHolderReceive.imageView.setVisibility(View.GONE);
                            break;
                        default:
                            viewHolderReceive.textView.setText(chatMessage.getText());
//                            viewHolderReceive.imageView.setVisibility(View.GONE);
                            break;
                    }
                    //拦截系统对超链接的处理
                    interceptHyperLink(viewHolderReceive.textView);
                    //设置tag
                    view.setTag(viewHolderReceive);
                    break;
                //发送
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
//                    viewHolderSend.imageView.setVisibility(view.GONE);
                    if(i%5==0){
                       viewHolderSend.textViewTime.setText(DateUtil.formatDate(chatMessage.getDate()));
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
//                    viewHolderReceive.imageView.setVisibility(View.VISIBLE);
                    if(i%5==0){
                        viewHolderReceive.textViewTime.setText(DateUtil.formatDate(chatMessage.getDate()));
                        viewHolderReceive.textViewTime.setVisibility(View.VISIBLE);
                    }
                    else{
                        viewHolderReceive.textViewTime.setVisibility(View.GONE);
                    }
                    switch (chatMessage.getCode()){
                        case 100000:
                            viewHolderReceive.textView.setText(chatMessage.getText());
//                            viewHolderReceive.imageView.setVisibility(View.GONE);
                            break;
                        case 200000:
                            viewHolderReceive.textView.setText(chatMessage.getText()+chatMessage.getUrl());
//                            viewHolderReceive.imageView.setVisibility(View.GONE);
                            break;
                        default:
                            viewHolderReceive.textView.setText(chatMessage.getText());
//                            viewHolderReceive.imageView.setVisibility(View.GONE);
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
//                    viewHolderSend.imageView.setVisibility(view.GONE);
                    if(i%5==0){
                       viewHolderSend.textViewTime.setText(DateUtil.formatDate(chatMessage.getDate()));
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
    /**拦截系统对超连接的处理*/
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
