package com.o0live0o.app.appearance.activitys;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.o0live0o.app.appearance.ActivityStack;
import com.o0live0o.app.appearance.bean.BooleanSerializer;
import com.o0live0o.app.appearance.log.L;
import com.o0live0o.app.appearance.service.CURDHelper;
import com.o0live0o.app.appearance.data.FinalData;
import com.o0live0o.app.appearance.MyApplication;
import com.o0live0o.app.appearance.R;
import com.o0live0o.app.appearance.adapters.CarListAdapter;
import com.o0live0o.app.appearance.bean.CarBean;
import com.o0live0o.app.appearance.utils.Tools;
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
    private Spinner c1NoSpinner;
    private List<CarBean> mCarList = new ArrayList<>();
    private FrameLayout frameLayout;
    private List<String> lineList = new ArrayList<>();
    private List<String> c1NoList = new ArrayList<>();
    private ArrayAdapter<String> lineAdapter;
    private ArrayAdapter<String> c1NoAdapter;
    private String mCheckType;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private CarBean mSearchCar = new CarBean();
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

    @Override
    protected void onResume(){
        super.onResume();
        new SearchAsyncTask().execute(mSearchCar,mCheckType);
    }

    private void init() {
        pref = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        mCheckType = getIntent().getStringExtra("CheckType");
        mRvCar = findViewById(R.id.rv_car);
        labView = findViewById(R.id.main_lv_operator);
        labView.setValTxt(FinalData.getOperator());
        tvPlateNo = findViewById(R.id.main_tv_palteno);
        tvVin = findViewById(R.id.main_tv_vin);
        lineSpinner = findViewById(R.id.main_select_line);
        c1NoSpinner = findViewById(R.id.main_select_c1no);
        frameLayout = findViewById(R.id.main_search_container);

        if (!mCheckType.equals(FinalData.C1)){
            frameLayout.setVisibility(View.GONE);
        }

        //loadCarList(null);

        lineList.add("全部");
        //初始化下拉列表
        for (int i = 1;i<10;i++) {
            lineList.add(String.valueOf(i));
            c1NoList.add(String.valueOf(i));
        }
        lineAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,lineList);
        lineAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        c1NoAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,c1NoList);
        c1NoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        lineSpinner.setAdapter(lineAdapter);
        c1NoSpinner.setAdapter(c1NoAdapter);

        String defC1 = pref.getString("c1_no",  "1");
        if (c1NoList.contains(defC1)) {
            int ic1No = Integer.parseInt(defC1);
            c1NoSpinner.setSelection(ic1No - 1);
        }
        bindSearchCondition();
        new SearchAsyncTask().execute(mSearchCar,mCheckType);

    }

    private void bindSearchCondition(){
        mSearchCar.setPlateNo(tvPlateNo.getText().toString().trim());
        mSearchCar.setVin(tvVin.getText().toString().trim());
        mSearchCar.setLineNumber(lineSpinner.getSelectedItem().toString());
        mSearchCar.setC1Number(c1NoSpinner.getSelectedItem().toString());
        editor = pref.edit();
        editor.putString("c1_no", c1NoSpinner.getSelectedItem().toString());
        editor.apply();
    }

    public void main_btn_search(View view) {
        bindSearchCondition();
        new SearchAsyncTask().execute(mSearchCar, mCheckType);
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
                L.d(ex.getMessage());
                showDialog(ex.getMessage());
            }finally {
                hideProgressDialog();
            }
        }
    }

}
