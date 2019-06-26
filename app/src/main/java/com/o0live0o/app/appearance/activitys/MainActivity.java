package com.o0live0o.app.appearance.activitys;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.o0live0o.app.appearance.ActivityStack;
import com.o0live0o.app.appearance.service.CURDHelper;
import com.o0live0o.app.appearance.data.FinalData;
import com.o0live0o.app.appearance.MyApplication;
import com.o0live0o.app.appearance.R;
import com.o0live0o.app.appearance.adapters.CarListAdapter;
import com.o0live0o.app.appearance.bean.CarBean;
import com.o0live0o.app.appearance.views.LabelView;
import com.o0live0o.app.dbutils.DbResult;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private CarListAdapter mCarListAdapter;

    private RecyclerView mRvCar;
    private LabelView labView;
    private TextView tvPlateNo;
    private TextView tvVin;
    private Spinner lineSpinner;

    private List<CarBean> mCarList = new ArrayList<>();

    private List<String> lineList = new ArrayList<>();
    private ArrayAdapter<String> lineAdapter;

    private String mCheckType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initNavBar(true, this.getString(R.string.main_title),false);
        init();

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }


    private void init() {
        mCheckType = getIntent().getStringExtra("CheckType");
        mRvCar = findViewById(R.id.rv_car);
        labView = findViewById(R.id.main_lv_operator);
        labView.setValTxt(FinalData.getOperator());
        tvPlateNo = findViewById(R.id.main_tv_palteno);
        tvVin = findViewById(R.id.main_tv_vin);
        lineSpinner = findViewById(R.id.main_select_line);
        new SearchAsyncTask().execute(null,mCheckType);
        //loadCarList(null);

        lineList.add("全部");
        //初始化下拉列表
        for (int i = 1;i<6;i++){
            lineList.add(String.valueOf(i));
        }
        lineAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,lineList);
        lineAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        lineSpinner.setAdapter(lineAdapter);
    }

    public void main_btn_search(View view) {
        CarBean car = new CarBean();
        car.setPlateNo(tvPlateNo.getText().toString().trim());
        car.setVin(tvVin.getText().toString().trim());
        car.setLineNo(lineSpinner.getSelectedItem().toString());
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
