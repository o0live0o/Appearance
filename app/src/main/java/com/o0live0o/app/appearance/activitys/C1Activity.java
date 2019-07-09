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

public class C1Activity extends BaseActivity {

    private RecyclerView mRV;
    private EditText mEtRemark;
    private ChekItemAdapter mChekItemAdapter;
    private List<ExteriorBean> mList;
    private CarBean mCar;

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
        new StatusTask().execute(((Button)view).getText().toString(),"");
    }

    public void onCaputure(View view) {
    }

    public void onStart(View view) {
        mCar.setStartTime(getTime());
        String xml = CreateXML.create211(mCar);
        new SendService().execute(xml,"211","WriteXmlDoc");
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

    class SendService extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            String xml = strings[0];
            String jkid = strings[1];
            String method = strings[2];
            Map<String,String> map = new HashMap<>();
            map.put("jkid",jkid);
            map.put("jczdm",FinalData.getStationNo());
            map.put("key","");
            map.put("WriteXmlDoc",xml);
            try {
                WebServiceHelper.getInstance().SendWebservice(map,method);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            return null;
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
            showToast(s);
        }
    }


}
