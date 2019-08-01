package com.o0live0o.app.appearance.activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.o0live0o.app.appearance.MyApplication;
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
import com.o0live0o.app.appearance.utils.GenerateItem;
import com.o0live0o.app.appearance.utils.ResourceMan;
import com.o0live0o.app.appearance.views.LabelView;
import com.o0live0o.app.dbutils.DbResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;


public class F1Activity extends BaseActivity {

    private RecyclerView mRV;
    private EditText mEtRemark;

    private ChekItemAdapter mChekItemAdapter;
    private List<ExteriorBean> mList;
    private ICURD mCurd;
    private CarBean mCar;
    private Map<String,List<String>> remarkMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appearance);
        initNavBar(true,"外观检查",false);
        init();
    }

    @Override
    protected void onDestroy(){
        BaseActivity.RunThread = false;
        super.onDestroy();
    }

    private void init(){
        mRV = findViewById(R.id.rv_checklist);
        remarkMap = new HashMap<>();
        mEtRemark = findViewById(R.id.f1_et_remark);
        mCar = getIntent().getParcelableExtra("carInfo");
        mCar.setStartTime(getTime());
        initBoard(mCar.getPlateNo(),mCar.getTestId(),mCar.getLineNumber());
        startTimes();
        initRcView();
    }

    /*
    * 初始化RecycleView
    */
    private void initRcView(){
        mList = ExteriorList.getExteriorList();

        GenerateItem generateItem = new GenerateItem();
        List<Integer> defaultList =   generateItem.generate(mCar);
        mList.stream().forEach(item->{if(defaultList.contains(item.getItemId())){item.setItemState(CheckState.PASS);}});
        mChekItemAdapter = new ChekItemAdapter(this,mList);
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

        mChekItemAdapter.setCheckUnpassItem(new ChekItemAdapter.CheckUnpassItem(){
            @Override
            public void onCheck(int i,boolean b) {
                try {
                    if (mRV.getScrollState() == RecyclerView.SCROLL_STATE_IDLE
                            && !mRV.isComputingLayout()) {
                        if (b) {

                            List<Integer> list = new ArrayList<>();
                            String[] s1 = getResources().getStringArray(ResourceMan.getResId("f1_" + mList.get(i).getItemId(), R.array.class));
                            AlertDialog alertDialog = new AlertDialog.Builder(F1Activity.this)
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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
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

    public void onSubmit(View view) {
        mCar.setEndTime(getTime());
        new SubmitAsyncTask().execute(mCar,mList);
    }

    class SubmitAsyncTask extends AsyncTask<Object,Void, DbResult>{

        @Override
        protected DbResult doInBackground(Object... objects) {
            DbResult dbResult = new DbResult();
            CarBean car = (CarBean) objects[0];
            List<ExteriorBean> list = (List<ExteriorBean>) objects[1];
            try {
                dbResult = CURDHelper.saveF1(list, mCar);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return dbResult;
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
            showToast(String.valueOf(dbResult.isSucc()));
            finish();
//            if (FinalData.isCheckDC() && FinalData.isF1_To_DC() && mCar.getCheckItem().contains(FinalData.DC)){
//                Intent intent = new Intent(F1Activity.this,DCActivity.class);
//                intent.putExtra("carInfo",mCar);
//                startActivity(intent);
//                finish();
//            }else {
//                finish();
//            }
        }
    }

}
