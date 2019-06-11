package com.o0live0o.app.appearance.activitys;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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
import com.o0live0o.app.appearance.views.LabelView;
import com.o0live0o.app.dbutils.DbResult;

import java.util.List;

public class F1Activity extends BaseActivity {

    private RecyclerView mRV;
    private LabelView lvPlateNo;
    private LabelView lvTestId;
    private LabelView lvOperator;

    private ChekItemAdapter mChekItemAdapter;
    private List<ExteriorBean> mList;
    private ICURD mCurd;
    private CarBean mCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appearance);
        initNavBar(true,"外观检查",false);
        init();
    }

    private void init(){

        mRV = findViewById(R.id.rv_checklist);
        lvPlateNo = findViewById(R.id.f1_lv_plateno);
        lvTestId = findViewById(R.id.f1_lv_testid);
        lvOperator = findViewById(R.id.f1_lv_operator);

        mCar = getIntent().getParcelableExtra("carInfo");
        mCar.setStartTime(getTime());
        lvPlateNo.setValTxt(mCar.getPlateNo());
        lvTestId.setValTxt(mCar.getTestId());
        lvOperator.setValTxt(FinalData.getOperator());
        initRcView();

    }

    /*
    * 初始化RecycleView
    */
    private void initRcView(){
        mList = ExteriorList.getExteriorList();
        mList.stream().forEach(item->{if(ExteriorList.getCheckItem().contains(item.getItemId())){item.setItemState(CheckState.PASS);}});
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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRV.setLayoutManager(linearLayoutManager);
        mRV.setAdapter(mChekItemAdapter);
    }

    public void onSubmit(View view) {
        mCar.setEndTime(getTime());
        new SubmitAsyncTask().execute(mCar,mList);
    }

//如果检验项目有DC，则跳转到动态底盘检测界面
    private void toDC()
    {
        try {
            Intent intent = new Intent(F1Activity.this,DCActivity.class);
            startActivity(intent);
            finish();
        }catch (Exception ex){
            L.d(ex.getMessage());
        }

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
            if (mCar.getCheckItem().contains("DC")){
                Intent intent = new Intent(F1Activity.this,DCActivity.class);
                intent.putExtra("carInfo",mCar);
                startActivity(intent);
                finish();
            }else {
                finish();
            }
        }
    }

}
