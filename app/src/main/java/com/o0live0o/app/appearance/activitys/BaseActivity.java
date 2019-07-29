package com.o0live0o.app.appearance.activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.o0live0o.app.appearance.ActivityStack;
import com.o0live0o.app.appearance.MyApplication;
import com.o0live0o.app.appearance.R;
import com.o0live0o.app.appearance.data.FinalData;
import com.o0live0o.app.appearance.log.L;
import com.o0live0o.app.appearance.views.LabelView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseActivity extends Activity {

    //导航条
    private ImageView mNav_Back, mNav_Me;
    private TextView mTitle;

    //基本信息公告栏
    private LabelView lvHPHM,lvTestId,lvLineNo,lvOperator;
    private TextView tvSecond;
    private int iSecond = 0;
    public static boolean RunThread = false;

    private static Thread mThread = null;
    private static boolean bRun = true;

    private ProgressDialog progressDialog;


    protected void initNavBar(boolean isShowBack, String title, boolean isShowMe) {
        mNav_Back = findViewById(R.id.nav_back);
        mNav_Me = findViewById(R.id.nav_me);
        mTitle = findViewById(R.id.nav_text);

        mNav_Back.setVisibility(isShowBack ? View.VISIBLE : View.GONE);
        mNav_Me.setVisibility(isShowMe ? View.VISIBLE : View.GONE);
        mTitle.setText(title);

        mNav_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mNav_Me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyApplication.getContext(),HelpActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
        //ActivityStack.getInstance().attach(this);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        //ActivityStack.getInstance().detach(this);
    }

    protected void showProgressDialog(String title, String message) {
        if (progressDialog == null) {
            progressDialog = ProgressDialog.show(this, title, message, true, false);
        } else if (progressDialog.isShowing()) {
            progressDialog.setTitle(title);
            progressDialog.setMessage(message);
        }
        progressDialog.show();
    }

    protected void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    protected void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    protected String getTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    protected void showDialog(String msg){
        AlertDialog alertDialog = new AlertDialog.Builder(this).setMessage(msg).create();
        alertDialog.show();
    }

    protected void showDialog1(){
        AlertDialog alertDialog =   new AlertDialog.Builder(this)
                .setTitle("")
                .setMultiChoiceItems(new String[]{"苹果","番茄","香蕉"},null,null)
                .setPositiveButton("确定",null).create();
        alertDialog.show();

    }

    protected void initBoard(String hphm,String testId,String lineNo) {
        lvHPHM = findViewById(R.id.board_lv_plateno);
        lvLineNo = findViewById(R.id.board_lv_line);
        lvTestId = findViewById(R.id.board_lv_testid);
        lvOperator = findViewById(R.id.board_lv_operator);
        tvSecond = findViewById(R.id.board_second);

        lvOperator.setValTxt(FinalData.getOperator());
        lvTestId.setValTxt(testId);
        lvLineNo.setValTxt(lineNo);
        lvHPHM.setValTxt(hphm);
    }

    protected void startTimes(){
        iSecond = 0;
        RunThread = true;
        if (mThread != null){
            try {
                mThread.interrupt();
            }catch (Exception ex){
                ex.printStackTrace();
            }

        }
        mThread = new Thread(new MyThread());
        mThread.start();
        bRun = false;
    }

    Handler handler = new Handler(){
        public void handleMessage(Message msg){
            tvSecond.setText(String.valueOf(msg.obj));
            super.handleMessage(msg);
        }
    };


    public class MyThread implements Runnable{
        @Override
        public void run() {
            while (RunThread){
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.obj = iSecond;
                    iSecond ++;
                    handler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

}
