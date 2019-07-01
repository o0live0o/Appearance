package com.o0live0o.app.appearance.activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.o0live0o.app.appearance.utils.ResourceMan;
import com.o0live0o.app.appearance.views.LabelView;
import com.o0live0o.app.dbutils.DbResult;
import com.o0live0o.app.dbutils.SSMSHelper;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringJoiner;
import java.util.Timer;
import java.util.TimerTask;

public class C1Activity extends BaseActivity {

    private RecyclerView mRV;
    private EditText mEtRemark;
    private ChekItemAdapter mChekItemAdapter;
    private List<ExteriorBean> mList;
    private CarBean mCar;
    private Timer mTimer;
    private  int iSecond = 0;
    private List<String> mRemarkList;

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

        mCar = getIntent().getParcelableExtra("carInfo");
        mRemarkList = new ArrayList<>();
        initBoard(mCar.getPlateNo(),mCar.getTestId(),mCar.getLineNo());

        mList = ExteriorList.getC1List();
        mChekItemAdapter = new ChekItemAdapter(this,mList);
        mChekItemAdapter.setCheckUnpassItem(new CheckUnpassItem(){
            @Override
            public void onCheck(int i) {
                try {
                    if(mRV.getScrollState() == RecyclerView.SCROLL_STATE_IDLE
                            && !mRV.isComputingLayout()) {
                        List<Integer> list = new ArrayList<>();
                        String[] s1 = getResources().getStringArray(ResourceMan.getResId("c1_"+mList.get(i).getItemId(), R.array.class));
                        AlertDialog alertDialog = new AlertDialog.Builder(C1Activity.this)
                                .setTitle("不合格原因")
                                .setMultiChoiceItems(s1, null, new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                        if (isChecked) {
                                            list.add(which);
                                        } else {
                                            Iterator iterator = list.iterator();
                                            while (iterator.hasNext()){
                                                int tempI = (Integer) iterator.next();
                                                if (tempI == which){
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
                                        for (int i = 0; i < list.size(); i++) {
                                            String tempStr = s1[list.get(i)];
                                            if (!mRemarkList.contains(tempStr)) {
                                                mRemarkList.add(tempStr);
                                            }
                                        }
                                        StringJoiner stringJoiner = new StringJoiner(";");
                                        for (String s : mRemarkList
                                        ) {
                                            stringJoiner.add(s);
                                        }
                                        mEtRemark.setText(stringJoiner.toString());
                                    }
                                })
                                .create();
                        alertDialog.show();
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
        new StatusTask().execute(((Button)view).getText().toString(),"");
    }

    public void onCaputure(View view) {
    }

    public void onStart(View view) {
        mCar.setStartTime(getTime());
        new StatusTask().execute("人工检查","1001");
        startTimes();
    }

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
            if(dbResult.isSucc()){
                showToast("状态更新成功");
            }else {
                showToast("状态更新失败:"+dbResult.getMsg());
            }
        }
    }

    class SubmitTask extends AsyncTask<Void,Void, DbResult>{

        @Override
        protected DbResult doInBackground(Void... voids) {
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


}
