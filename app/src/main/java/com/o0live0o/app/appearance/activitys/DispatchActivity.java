package com.o0live0o.app.appearance.activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.o0live0o.app.appearance.MyApplication;
import com.o0live0o.app.appearance.R;
import com.o0live0o.app.appearance.adapters.CarListAdapter;
import com.o0live0o.app.appearance.adapters.CarsAdapter;
import com.o0live0o.app.appearance.bean.BooleanSerializer;
import com.o0live0o.app.appearance.bean.CarBean;
import com.o0live0o.app.appearance.data.FinalData;
import com.o0live0o.app.appearance.log.L;
import com.o0live0o.app.appearance.service.CURDHelper;
import com.o0live0o.app.appearance.utils.Tools;
import com.o0live0o.app.appearance.views.LabelView;
import com.o0live0o.app.dbutils.DbResult;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Iterator;
import java.util.List;

public class DispatchActivity extends BaseActivity {

    private RecyclerView mRvCar;
    private List<CarBean> mCarList = new ArrayList<>();
    private String mBusinessType;
    private CarsAdapter mCarsAdapter = null;
    private CarBean mSearchCar = new CarBean();
    private TextView tvPlateNo;
    private TextView tvVin;
    private LabelView labView;
    private int checkIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch);
        initNavBar(true, "车辆调度",false);
        init();
    }

    private void init() {
        tvPlateNo = findViewById(R.id.dis_tv_palteno);
        tvVin = findViewById(R.id.dis_tv_vin);
        labView = findViewById(R.id.dis_lv_operator);
        labView.setValTxt(FinalData.getOperator());
        mRvCar = findViewById(R.id.dis_rv_car);
        mRvCar.setItemAnimator(new DefaultItemAnimator());
        bindSearchCondition();
        new SearchAsyncTask().execute(mSearchCar);
    }

    private void bindSearchCondition(){
        mSearchCar.setPlateNo(tvPlateNo.getText().toString().trim());
        mSearchCar.setVin(tvVin.getText().toString().trim());

    }

    public void dis_btn_search(View view) {
        bindSearchCondition();
        new SearchAsyncTask().execute(mSearchCar);
    }

    class SearchAsyncTask extends AsyncTask<Object,Void,DbResult> {

        @Override
        protected DbResult doInBackground(Object... objects) {
            CarBean car = (CarBean) objects[0];
            return CURDHelper.getCarList(car,"00");
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

                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").registerTypeAdapter(Boolean.class,new BooleanSerializer())
                            .registerTypeAdapter(boolean.class,new BooleanSerializer()).create();

                    Type type = new TypeToken<List<CarBean>>() {
                    }.getType();

                    List<CarBean> list = gson.fromJson(dbResult.getMsg(), type);
                    list.stream().forEach(s->{
                        s.setSyxz(Tools.RemoveChinese(s.getSyxz()));
                        s.setCheckType(Tools.RemoveChinese(s.getCheckType()));
                    });
                    mCarList.clear();
                    mCarList.addAll(list);

                    if (mCarsAdapter == null) {
                        mCarsAdapter = new CarsAdapter(DispatchActivity.this, mCarList);

                        mCarsAdapter.setOnCheckItem(new CarsAdapter.OnCheckItem() {
                            @Override
                            public void onCheck(CarBean car,int i) {
                                String[] s1 = new String[]{"1","2","3","4","5"};

                                AlertDialog alertDialog = new AlertDialog.Builder(DispatchActivity.this)
                                        .setTitle("线号选择")
                                        .setSingleChoiceItems(s1, 0, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                checkIndex = which;

                                            }
                                        })
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                mCarsAdapter.remove(i);
                                                showToast("选择线号"+s1[checkIndex]);
                                                new SubmitAsyncTask().execute(car,s1[checkIndex]);
                                            }
                                        })
                                        .create();
                                alertDialog.show();

                            }
                        });
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DispatchActivity.this);
                        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        mRvCar.setLayoutManager(linearLayoutManager);
                        mRvCar.setAdapter(mCarsAdapter);

                    }
                    mCarsAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MyApplication.getContext(), dbResult.getMsg(), Toast.LENGTH_LONG).show();
                }
            }catch (Exception ex)
            {
                L.d(ex.getMessage());
                showDialog(ex.getMessage());
            }finally {
                hideProgressDialog();
            }
        }
    }

    class SubmitAsyncTask extends AsyncTask<Object,Void,DbResult>{
        @Override
        protected DbResult doInBackground(Object... objects) {
            CarBean car = (CarBean) objects[0];
            String line = (String)objects[1];
            return CURDHelper.onLine(car,line);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog("","正在请求……");
        }

        @Override
        protected void onPostExecute(DbResult dbResult) {
            super.onPostExecute(dbResult);
            hideProgressDialog();
        }
    }
}
