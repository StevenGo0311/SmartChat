package com.stevengo.myapplication.activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.stevengo.myapplication.R;
import com.stevengo.myapplication.utils.ActiivtyStack;
import com.stevengo.myapplication.utils.DoFriendNameUtil;
import com.stevengo.myapplication.utils.GeneralUtil;
import com.stevengo.myapplication.utils.IconCacheUtil;
import com.stevengo.myapplication.utils.SQLiteUtil;

import java.io.File;

/**
 * 设置页面
 */

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    /**返回按钮*/
    private Button mBack;
    /**对方的头像*/
    private ImageView mIconReceive;
    /**自己的头像*/
    private ImageView mIconSend;
    /**好友姓名的容器*/
    private RelativeLayout mSettingFriendName;
    /**好友的姓名*/
    private TextView mFriendName;
    /**关于*/
    private TextView mAboutSmartChat;
    /**特别鸣谢*/
    private TextView mSpecialThanks;
    /**关闭应用程序的按钮*/
    private Button mFinishApplication;
    /**清空聊天记录的组件*/
    private LinearLayout mClearChatRecord;
    /**消息记录*/
    private TextView mChatRecord;

    /**对方头像的图像名称*/
    private final static String ICON_NAME_RECEIVE="iconRecevie.jpg";
    /**自己的头像名称*/
    private final static String ICON_NAME_SEND="iconSend.jpg";
    /**单击源*/
    private int clickSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //将本activity添加到activity栈中
        ActiivtyStack.getScreenManager().pushActivity(this);
        //沉浸式
        GeneralUtil.fullSceeen(this);
        //加载布局
        setContentView(R.layout.activity_setting);
        //初始化视图
        initView();
        //初始化数据
        initDate();
        //添加监听
        addClickListener();
    }
    /**初始化视图*/
    private void initView(){
        //通过id在布局文件中找到组件
        mBack= (Button) findViewById(R.id.id_button_back);
        mIconReceive=(ImageView)findViewById(R.id.id_imageview_icon_receive);
        mIconSend=(ImageView)findViewById(R.id.id_imageview_icon_send);
        mSettingFriendName=(RelativeLayout) findViewById(R.id.id_raletive_layout_setting_name);
        mFriendName=(TextView)findViewById(R.id.id_textview_setting_name);
        mAboutSmartChat=(TextView)findViewById(R.id.id_textview_about_smart_chat);
        mSpecialThanks=(TextView)findViewById(R.id.id_textview_special_thanks);
        mFinishApplication=(Button)findViewById(R.id.id_button_finish);
        mClearChatRecord=(LinearLayout)findViewById(R.id.id_linearlayout_layout_clear_record);
        mChatRecord=(TextView)findViewById(R.id.id_textview_message_record);
    }
    /**初始化数据*/
    private void initDate(){
        //从文件中读取对方头像
        Bitmap iconReceive=IconCacheUtil.readIcon(this,ICON_NAME_RECEIVE);
        //从文件中读取本方头像
        Bitmap iconSend= IconCacheUtil.readIcon(this,ICON_NAME_SEND);
        //判断是否读取到了用户头像，如果没有设置为默认头像
        if(iconReceive!=null){
            mIconReceive.setImageBitmap(iconReceive);
        }
        else{
            mIconReceive.setImageResource(R.drawable.icon_test_receive);
        }
        if(iconSend!=null){
            mIconSend.setImageBitmap(iconSend);
        }
        else{
            mIconSend.setImageResource(R.drawable.icon_test_send);
        }
        //设置好友姓名
        mFriendName.setText(DoFriendNameUtil.readFriendName(this));
        //设置消息记录的条数
        mChatRecord.setText("共"+SQLiteUtil.chatMessageNumbers(getApplicationContext())+"条");
    }
    /**给组件设置监听*/
    private void addClickListener(){
        mBack.setOnClickListener(this);
        mIconReceive.setOnClickListener(this);
        mIconSend.setOnClickListener(this);
        mSettingFriendName.setOnClickListener(this);
        mAboutSmartChat.setOnClickListener(this);
        mSpecialThanks.setOnClickListener(this);
        mFinishApplication.setOnClickListener(this);
        mClearChatRecord.setOnClickListener(this);
    }
    //响应单击事件
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.id_button_back:
                finish();
                break;
            case R.id.id_imageview_icon_receive:
                clickSource=1;
                showTypeDialog();
                break;
            case R.id.id_imageview_icon_send:
                clickSource=2;
                showTypeDialog();
                break;
            case R.id.id_raletive_layout_setting_name:
                settingNameDialog();
                break;
            case R.id.id_linearlayout_layout_clear_record:
                clearMessageRecordDialog();
                break;
            case R.id.id_textview_about_smart_chat:
                aboutSmartChatDialog();
                break;
            case R.id.id_textview_special_thanks:
                specialThanksDialog();
                break;
            case R.id.id_button_finish:
                ActiivtyStack.getScreenManager().clearAllActivity();
                break;

        }
    }
    /**弹出设置好友姓名的对话框*/
    private void settingNameDialog(){
        View dialogConEdit= LayoutInflater.from(this).inflate(R.layout.alertdialog_setting_friend_name,null);
        //得到文本框
        final EditText editText=(EditText) dialogConEdit.findViewById(R.id.id_edittext_setting_name);
        AlertDialog alertDialog=new AlertDialog.Builder(this)
                .setTitle(R.string.setting_friend_name)
                .setView(dialogConEdit)
                //设置确定按钮的动作
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //得到用户的输入，并去掉空格
                        String newName=editText.getText().toString().trim();
                        //判断用户的输入是否为空
                        if(!newName.equals("")){
                            mFriendName.setText(newName);
                            DoFriendNameUtil.writeFriendName(getApplicationContext(),newName);
                        }
                        else{
                            Toast.makeText(SettingActivity.this, "输入内容不能为空", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                //取消按钮不执行任何操作
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create();
        alertDialog.show();
    }
    /**关于SmartChat的对话框*/
    private void aboutSmartChatDialog(){
        View dialog= LayoutInflater.from(this).inflate(R.layout.alertdialog_about,null);
        AlertDialog alertDialog=new AlertDialog.Builder(this)
                .setTitle(R.string.about_smart_chat)
                .setView(dialog)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).create();
        alertDialog.show();
    }
    /**特别鸣谢的对话框*/
    private void specialThanksDialog(){
        View dialog= LayoutInflater.from(this).inflate(R.layout.alertdialog_thanks,null);
        AlertDialog alertDialog=new AlertDialog.Builder(this)
                .setTitle(R.string.special_thanks)
                .setView(dialog)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).create();
        alertDialog.show();
    }
    /**更换头像的方式，拍照，相册*/
    private void showTypeDialog(){
        View dialogSelectMethod= LayoutInflater.from(this).inflate(R.layout.alertdialog_select_method,null);
        final TextView textViewCamera=(TextView) dialogSelectMethod.findViewById(R.id.id_textView_camera);
        final TextView textViewGallery=(TextView) dialogSelectMethod.findViewById(R.id.id_textView_gallery);
        final AlertDialog alertDialog=new AlertDialog.Builder(this)
                .setTitle(R.string.select_method)
                .setView(dialogSelectMethod)
                .create();
        alertDialog.show();
        //对两个组件设置单击事件
        textViewCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //打开照相机
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //拍照，命名为tempIcon.jpg
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "tempIcon.jpg")));
                startActivityForResult(intentCamera, 0x01);// 采用ForResult打开
                //关闭对话框
                alertDialog.dismiss();
            }
        });
        textViewGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //打开相册
                Intent intentGallery = new Intent(Intent.ACTION_PICK, null);
                intentGallery.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intentGallery, 0x02);
                alertDialog.dismiss();
            }
        });

    }
    /**清除聊天记录的会话框*/
    private void clearMessageRecordDialog(){
        AlertDialog alertDialog=new AlertDialog.Builder(this)
                .setTitle(R.string.clear_record)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SQLiteUtil.clearChatMessage(getApplicationContext());
                        //从数据库中得到消息记录的数量
                        mChatRecord.setText("共"+SQLiteUtil.chatMessageNumbers(getApplicationContext())+"条");
                        Toast.makeText(getApplicationContext(), "清除成功", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create();
        alertDialog.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            //当拍完照后对其进行裁剪
            case 0x01:
                if(resultCode==RESULT_OK){
                    File temp = new File(Environment.getExternalStorageDirectory() + "/tempIcon.jpg");
                    cropPhoto(Uri.fromFile(temp));
                }
                break;
            //将从相册里面拿到的图片进行裁剪
            case 0x02:
                if(resultCode==RESULT_OK){
                    cropPhoto(data.getData());
                }
                break;
            //裁剪图片
            case 0x03:
                //判断数据是否为空
                if (data != null) {
                    Bundle extras = data.getExtras();
                    Bitmap icon = extras.getParcelable("data");
                    if (icon != null) {
                       //针对不同的单击源设置不同的头像
                        switch (clickSource){
                            case 1:
                                IconCacheUtil.writeIcon(this,icon,ICON_NAME_RECEIVE);
                                mIconReceive.setImageBitmap(icon);
                                break;
                            case 2:
                                IconCacheUtil.writeIcon(this,icon,ICON_NAME_SEND);
                                mIconSend.setImageBitmap(icon);
                                break;
                        }
                    }
                }
                break;
        }
    }
    /**裁剪图片*/
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 0x03);
    }
    @Override
    protected void onDestroy() {
        //将该activity弹出栈
        ActiivtyStack.getScreenManager().popActivity(this);
        super.onDestroy();
    }
}
