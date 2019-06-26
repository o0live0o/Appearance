package com.o0live0o.app.appearance.activitys;

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


public class DCActivity extends BaseActivity {

    private RecyclerView mRV;

    private ChekItemAdapter mChekItemAdapter;
    private List<ExteriorBean> mList;
    private ICURD mCurd;
    private CarBean mCar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dc);
        init();
    }

    @Override
    protected void onDestroy(){
        BaseActivity.RunThread = false;
        super.onDestroy();
    }

    private void init(){
        initNavBar(true,"动态底盘",false);

        mRV = findViewById(R.id.dc_rv_item);


        mCar = getIntent().getParcelableExtra("carInfo");
        mCar.setStartTime(getTime());
        initBoard(mCar.getPlateNo(),mCar.getTestId(),"1");

        mList = ExteriorList.getDCList();
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
        new StatusTask().execute("动态底盘检查","1001");
    }

    public void onSubmit(View view) {
        mCar.setEndTime(getTime());
        new SubmitTask().execute();
    }

    class SubmitTask extends AsyncTask<Void,Void, DbResult> {
        @Override
        protected DbResult doInBackground(Void... voids) {
            return CURDHelper.saveDC(mList,mCar);
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
}
