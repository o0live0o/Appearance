package com.o0live0o.app.appearance.activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.o0live0o.app.appearance.adapters.ChekItemAdapter.CheckUnpassItem;
import com.o0live0o.app.appearance.bean.C1Bean;
import com.o0live0o.app.appearance.bean.ResultBean;
import com.o0live0o.app.appearance.service.CURDHelper;
import com.o0live0o.app.appearance.data.ExteriorList;
import com.o0live0o.app.appearance.data.FinalData;
import com.o0live0o.app.appearance.service.ICURD;
import com.o0live0o.app.appearance.log.L;
import com.o0live0o.app.appearance.R;
import com.o0live0o.app.appearance.adapters.ChekItemAdapter;
import com.o0live0o.app.appearance.bean.CarBean;
import com.o0live0o.app.appearance.bean.ExteriorBean;
import com.o0live0o.app.appearance.enums.CheckState;
import com.o0live0o.app.appearance.listener.ExteriorChangeListener;
import com.o0live0o.app.appearance.service.WebServiceHelper;
import com.o0live0o.app.appearance.utils.CreateXML;
import com.o0live0o.app.appearance.utils.ResourceMan;
import com.o0live0o.app.appearance.views.LabelView;
import com.o0live0o.app.dbutils.DbResult;
import com.o0live0o.app.dbutils.SSMSHelper;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class C1Activity extends BaseActivity {

    private RecyclerView mRV;
    private EditText mEtRemark;
    private ChekItemAdapter mChekItemAdapter;
    private List<ExteriorBean> mList;
    private CarBean mCar;

    private Button mBtnStart;
    private Button mBtnCapture;
    private Button mBtnSubmit;

    private Map<String,List<String>> remarkMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c1);
        init();
    }

    private void init(){
        initNavBar(true,"底盘检查",false);
        mRV = findViewById(R.id.c1_rv_checklist);
        mEtRemark = findViewById(R.id.c1_et_remark);
        remarkMap = new HashMap<>();
        mCar = getIntent().getParcelableExtra("carInfo");
        initBoard(mCar.getPlateNo(),mCar.getTestId(),mCar.getLineNumber());

        initControl();

        mList = ExteriorList.getC1List();
        mChekItemAdapter = new ChekItemAdapter(this,mList);
        mChekItemAdapter.setCheckUnpassItem(new CheckUnpassItem(){
            @Override
            public void onCheck(int i,boolean b) {
                try {
                    if (mRV.getScrollState() == RecyclerView.SCROLL_STATE_IDLE
                            && !mRV.isComputingLayout()) {
                        if (b) {

                            List<Integer> list = new ArrayList<>();
                            String[] s1 = getResources().getStringArray(ResourceMan.getResId("c1_" + mList.get(i).getItemId(), R.array.class));
                            AlertDialog alertDialog = new AlertDialog.Builder(C1Activity.this)
                                    .setTitle("不合格原因")
                                    .setMultiChoiceItems(s1, null, new DialogInterface.OnMultiChoiceClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                            if (isChecked) {
                                                list.add(which);
                                            } else {
                                                Iterator iterator = list.iterator();
                                                while (iterator.hasNext()) {
                                                    int tempI = (Integer) iterator.next();
                                                    if (tempI == which) {
                                                        iterator.remove();
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    })
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            for (int j = 0; j < list.size(); j++) {
                                                String tempStr = s1[list.get(j)];
                                                if (!remarkMap.containsKey(String.valueOf(mList.get(i).getItemId()))) {
                                                    remarkMap.put(String.valueOf(mList.get(i).getItemId()), new ArrayList<>());
                                                }
                                                if (!remarkMap.get(String.valueOf(mList.get(i).getItemId())).contains(tempStr))
                                                    remarkMap.get(String.valueOf(mList.get(i).getItemId())).add(tempStr);
                                            }
                                            updateReamrk();
                                        }
                                    })
                                    .create();
                            alertDialog.show();

                        } else {
                            if (remarkMap.containsKey(String.valueOf(mList.get(i).getItemId()))) {
                                remarkMap.get(String.valueOf(mList.get(i).getItemId())).clear();
                            }
                            updateReamrk();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        mChekItemAdapter.setExteriorChangeListener(new ExteriorChangeListener() {
            @Override
            public void onChange(int poison, CheckState checkState) {
                try {
                    if(mRV.getScrollState() == RecyclerView.SCROLL_STATE_IDLE
                            && !mRV.isComputingLayout()) {
                        mList.get(poison).setItemState(checkState);
                        mChekItemAdapter.notifyItemChanged(poison);
                    }
                } catch (Exception ex) {
                    L.d(ex.getMessage());
                }
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRV.setLayoutManager(linearLayoutManager);
        mRV.setAdapter(mChekItemAdapter);
    }

    private void initControl(){
        mBtnStart = findViewById(R.id.c1_btn_start);
        mBtnCapture = findViewById(R.id.c1_btn_capture);
        mBtnSubmit = findViewById(R.id.c1_btn_submit);
        changeBtnState(true,false,false);
    }

    private void updateReamrk(){
        StringJoiner stringJoiner = new StringJoiner(";");
        for (List<String> tempList : remarkMap.values()) {
            for (String s : tempList
            ) {
                stringJoiner.add(s);
            }
        }
        mEtRemark.setText(stringJoiner.toString());

    }

    @Override
    protected void onDestroy() {
        BaseActivity.RunThread = false;
        super.onDestroy();
    }

    public void onSubmit(View view) {
        mCar.setEndTime(getTime());
        new SubmitTask().execute();
    }

    public void onPre(View view) {
        new StatusTask().execute(((Button)view).getText().toString(),"1");
    }

    public void onCaputure(View view) {
        String xml = CreateXML.create803(mCar, getTime(), "00", "0323");
        new SendService().execute(xml, "803");
        changeBtnState(false,false,true);
    }

    public void onStart(View view) {
        mCar.setStartTime(getTime());
        new SendStartSerivce().execute();
        changeBtnState(false,true,false);
        startTimes();
    }

    //更新LED状态
    class StatusTask extends AsyncTask<String,Void,DbResult>{

        @Override
        protected DbResult doInBackground(String... strings) {
            String led= strings[0];
            String status = strings[1];
            return CURDHelper.sendStatus(mCar.getPlateNo()+"@"+led,mCar,status);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog("","更新状态……");
        }

        @Override
        protected void onPostExecute(DbResult dbResult) {
            super.onPostExecute(dbResult);
            hideProgressDialog();
        }
    }

    //结束检测
    class SubmitTask extends AsyncTask<Void,Void, DbResult>{

        @Override
        protected DbResult doInBackground(Void... voids) {

            ResultBean resultBean = new ResultBean();
            C1Bean c1 = new C1Bean();
            c1.setRzxxbj(mList.stream().filter(s->s.getItemId() == 46).map(s->s.getItemState() == CheckState.PASS ? "1":"0").collect(Collectors.joining("")));
            c1.setRcdxbj(mList.stream().filter(s->s.getItemId() == 47).map(s->s.getItemState() == CheckState.PASS ? "1":"0").collect(Collectors.joining("")));
            c1.setRxsxbj(mList.stream().filter(s->s.getItemId() == 48).map(s->s.getItemState() == CheckState.PASS ? "1":"0").collect(Collectors.joining("")));
            c1.setRzdxbj(mList.stream().filter(s->s.getItemId() == 49).map(s->s.getItemState() == CheckState.PASS ? "1":"0").collect(Collectors.joining("")));
            c1.setRqtbj(mList.stream().filter(s->s.getItemId() == 50).map(s->s.getItemState() == CheckState.PASS ? "1":"0").collect(Collectors.joining("")));
            c1.setJyyjy("");

            //写入过程数据
            String xml = CreateXML.caeate428(mCar, c1);
            try {
                L.d("发送过程数据："+xml);
                resultBean = WebServiceHelper.getInstance().SendWebservice("428", "writeObjectXml", "WriteXmlDoc", xml);
                L.d("发送过程数据："+resultBean.getMsg());
                final String s1 = resultBean.getMsg();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showToast(s1);
                    }
                });
            }catch (Exception e)
            {
                L.d("发送过程数据异常："+e.getMessage());
            }

//            if (resultBean.isSucc()){
                //写入结束指令
            try {
                xml = CreateXML.create212(mCar);
                L.d("发送结束命令："+xml);
                resultBean = WebServiceHelper.getInstance().SendWebservice("212", "writeObjectXml", "WriteXmlDoc", xml);
                final String s2 = resultBean.getMsg();
                L.d("发送结束命令："+resultBean.getMsg());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showToast(s2);
                    }
                });
            }catch (Exception ex)
            {
                L.d("发送结束命令异常："+ex.getMessage());
            }
//            }

//            if (resultBean.isSucc()){
                //写入录像结束指令
            try {
                xml = CreateXML.create803(mCar, getTime(), "02", "0323");
                L.d("发送停止录像命令："+xml);
                resultBean = WebServiceHelper.getInstance().SendWebservice("803", "writeObjectXml", "WriteXmlDoc", xml);
                final String s3 = resultBean.getMsg();
                L.d("发送停止录像命令："+resultBean.getMsg());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showToast(s3);
                    }
                });
            }catch (Exception ex) {
                L.d("发送停止录像命令异常："+ex.getMessage());
            }
//            }

            return CURDHelper.saveC1(mList,mCar);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog("","正在保存……");
        }

        @Override
        protected void onPostExecute(DbResult dbResult) {
            super.onPostExecute(dbResult);
            hideProgressDialog();
            if(dbResult.isSucc()){
                showToast("保存成功");
                finish();
            }else {
                showToast("保存失败:"+dbResult.getMsg());
            }
        }
    }

    //联网发送开始和录像指令
    class SendStartSerivce extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... voids) {

            ResultBean resultBean = new ResultBean();
            String xml = "";
            //发送联网录像指令
            xml = CreateXML.create803(mCar,getTime(),"01","0323");
             resultBean= WebServiceHelper.getInstance().SendWebservice("803","writeObjectXml","WriteXmlDoc",xml);
             final String s1 = resultBean.getMsg();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showToast(s1);
                }
            });

//            if (resultBean.isSucc()) {
                //发送联网开始命令
                xml = CreateXML.create211(mCar);
                resultBean = WebServiceHelper.getInstance().SendWebservice("211", "writeObjectXml", "WriteXmlDoc", xml);
                final String s2 = resultBean.getMsg();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showToast(s2);
                    }
                });
//            }

//            if (resultBean.isSucc()) {
            //更新LED状态
              DbResult dbResult =  CURDHelper.sendStatus(mCar.getPlateNo() + "@" + "人工检查", mCar, "1");
              resultBean.setSucc(dbResult.isSucc());
              resultBean.setMsg(dbResult.getMsg());
//            }
            return resultBean.isSucc()+":"+resultBean.getMsg();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog("","正在开始……");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            hideProgressDialog();
        }
    }

    //发送拍照指令
    class SendService extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            String xml = strings[0];
            String jkid = strings[1];
            return  WebServiceHelper.getInstance().SendWebservice(jkid,"writeObjectXml","WriteXmlDoc",xml).getMsg();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog("","正在上传……");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            hideProgressDialog();

        }
    }

    //改变按钮的状态
    private void changeBtnState(boolean bstart,boolean bcapture,boolean bsubmit){

        mBtnStart.setEnabled(bstart);
        mBtnStart.setBackgroundColor(bstart ? Color.parseColor("#87CEEB"): Color.parseColor("#D3D3D3"));

        mBtnCapture.setEnabled(bcapture);
        mBtnCapture.setBackgroundColor(bcapture ? Color.parseColor("#87CEEB"): Color.parseColor("#D3D3D3"));

        mBtnSubmit.setEnabled(bsubmit);
        mBtnSubmit.setBackgroundColor(bsubmit ? Color.parseColor("#87CEEB"): Color.parseColor("#D3D3D3"));
    }


}
