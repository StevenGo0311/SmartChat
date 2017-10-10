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

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBack;
    private ImageView mIconReceive;
    private ImageView mIconSend;
    private RelativeLayout mSettingFriendName;
    private TextView mFriendName;
    private TextView mAboutSmartChat;
    private TextView mSpecialThanks;
    private Button mFinishApplication;
    private LinearLayout mClearChatRecord;
    private TextView mChatRecord;

    private final static String ICON_NAME_RECEIVE="iconRecevie.jpg";
    private final static String ICON_NAME_SEND="iconSend.jpg";
    private int clickSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActiivtyStack.getScreenManager().pushActivity(this);
        //沉浸式
        GeneralUtil.fullSceeen(this);
        setContentView(R.layout.activity_setting);
        initView();
        initDate();
        addClickListener();
    }
    private void initView(){
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
    private void initDate(){
        Bitmap iconReceive=IconCacheUtil.readIcon(this,ICON_NAME_RECEIVE);
        Bitmap iconSend= IconCacheUtil.readIcon(this,ICON_NAME_SEND);
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
        mFriendName.setText(DoFriendNameUtil.readFriendName(this));
        mChatRecord.setText("共"+SQLiteUtil.chatMessageNumbers(getApplicationContext())+"条");


    }
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
    private void settingNameDialog(){
        View dialogConEdit= LayoutInflater.from(this).inflate(R.layout.alertdialog_setting_friend_name,null);
        final EditText editText=(EditText) dialogConEdit.findViewById(R.id.id_edittext_setting_name);
        AlertDialog alertDialog=new AlertDialog.Builder(this)
                .setTitle(R.string.setting_friend_name)
                .setView(dialogConEdit)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String newName=editText.getText().toString().trim();
                        if(!newName.equals("")){
                            mFriendName.setText(newName);
                            DoFriendNameUtil.writeFriendName(getApplicationContext(),newName);
                        }
                        else{
                            Toast.makeText(SettingActivity.this, "输入内容不能为空", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create();
        alertDialog.show();
    }
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
    private void showTypeDialog(){
        View dialogSelectMethod= LayoutInflater.from(this).inflate(R.layout.alertdialog_select_method,null);
        final TextView textViewCamera=(TextView) dialogSelectMethod.findViewById(R.id.id_textView_camera);
        final TextView textViewGallery=(TextView) dialogSelectMethod.findViewById(R.id.id_textView_gallery);
        final AlertDialog alertDialog=new AlertDialog.Builder(this)
                .setTitle(R.string.select_method)
                .setView(dialogSelectMethod)
                .create();
        alertDialog.show();
        textViewCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "tempIcon.jpg")));
                startActivityForResult(intentCamera, 0x01);// 采用ForResult打开
                alertDialog.dismiss();
            }
        });
        textViewGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentGallery = new Intent(Intent.ACTION_PICK, null);
                intentGallery.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intentGallery, 0x02);
                alertDialog.dismiss();
            }
        });

    }
    private void clearMessageRecordDialog(){
        AlertDialog alertDialog=new AlertDialog.Builder(this)
                .setTitle(R.string.clear_record)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SQLiteUtil.clearChatMessage(getApplicationContext());
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
            case 0x01:
                if(resultCode==RESULT_OK){
                    File temp = new File(Environment.getExternalStorageDirectory() + "/tempIcon.jpg");
                    cropPhoto(Uri.fromFile(temp));
                }
                break;
            case 0x02:
                if(resultCode==RESULT_OK){
                    cropPhoto(data.getData());
                }
                break;
            case 0x03:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    Bitmap icon = extras.getParcelable("data");
                    if (icon != null) {
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
        ActiivtyStack.getScreenManager().popActivity(this);
        super.onDestroy();
    }
}
