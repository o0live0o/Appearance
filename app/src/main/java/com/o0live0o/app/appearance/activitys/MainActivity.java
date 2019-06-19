package com.o0live0o.app.appearance.activitys;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.o0live0o.app.appearance.service.CURDHelper;
import com.o0live0o.app.appearance.data.FinalData;
import com.o0live0o.app.appearance.MyApplication;
import com.o0live0o.app.appearance.R;
import com.o0live0o.app.appearance.adapters.CarListAdapter;
import com.o0live0o.app.appearance.bean.CarBean;
import com.o0live0o.app.appearance.views.LabelView;
import com.o0live0o.app.dbutils.DbResult;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {

    private CarListAdapter mCarListAdapter;

    private RecyclerView mRvCar;
    private LabelView labView;
    private TextView tvPlateNo;
    private TextView tvVin;

    private List<CarBean> mCarList = new ArrayList<>();

    private String mCheckType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initNavBar(true, this.getString(R.string.main_title),false);
        init();
    }



    private void init() {
        mCheckType = getIntent().getStringExtra("CheckType");
        mRvCar = findViewById(R.id.rv_car);
        labView = findViewById(R.id.main_lv_operator);
        labView.setValTxt(FinalData.getOperator());
        tvPlateNo = findViewById(R.id.main_tv_palteno);
        tvVin = findViewById(R.id.main_tv_vin);
        new SearchAsyncTask().execute(null,mCheckType);
        //loadCarList(null);
    }

    public void main_btn_search(View view) {
        CarBean car = new CarBean();
        car.setPlateNo(tvPlateNo.getText().toString().trim());
        car.setVin(tvVin.getText().toString().trim());
        new SearchAsyncTask().execute(car, mCheckType);
    }


    class SearchAsyncTask extends AsyncTask<Object,Void,DbResult>{

        @Override
        protected DbResult doInBackground(Object... objects) {

            CarBean car = (CarBean) objects[0];
            String type = (String)objects[1];
            return CURDHelper.getCarList(car,type);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog("","正在加载……");
        }

        @Override
        protected void onPostExecute(DbResult dbResult) {
            super.onPostExecute(dbResult);
            try {
                if (dbResult.isSucc()) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<CarBean>>() {
                    }.getType();
                    List<CarBean> list = gson.fromJson(dbResult.getMsg(), type);
                    mCarList.clear();
                    mCarList.addAll(list);
                    if (mCarListAdapter == null) {
                        mCarListAdapter = new CarListAdapter(MainActivity.this, mCarList, mCheckType);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        mRvCar.setLayoutManager(linearLayoutManager);
                        mRvCar.setAdapter(mCarListAdapter);
                    }
                    mCarListAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MyApplication.getContext(), dbResult.getMsg(), Toast.LENGTH_LONG).show();
                }
            }catch (Exception ex)
            {

            }finally {
                hideProgressDialog();
            }
        }
    }

}
