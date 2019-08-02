package com.o0live0o.app.appearance.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.o0live0o.app.appearance.R;
import com.o0live0o.app.appearance.adapters.ChekItemAdapter;
import com.o0live0o.app.appearance.bean.CarBean;
import com.o0live0o.app.appearance.bean.ExteriorBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class C1ToDCActivity extends BaseActivity {

    private RecyclerView mRV;
    private EditText mEtRemark;
    private ChekItemAdapter mChekItemAdapter;
    private List<ExteriorBean> mList;
    private CarBean mCar;

    private Button mBtnStart;
    private Button mBtnCapture;
    private Button mBtnSubmit;

    private Map<String,List<String>> remarkMap;
    private Map<String,List<String>> subItemMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c1_to_dc);
        init();
    }

    private void init() {
        initNavBar(true,"底盘检查",false);
        mRV = findViewById(R.id.c1dc_rv_checklist);
        mEtRemark = findViewById(R.id.c1dc_et_remark);
        remarkMap = new HashMap<>();
        subItemMap = new HashMap<>();
        mCar = getIntent().getParcelableExtra("carInfo");
        initBoard(mCar.getPlateNo(),mCar.getTestId(),mCar.getLineNumber());
    }

    public void onStart(View view) {
    }

    public void onCaputure(View view) {
    }

    public void onSubmit(View view) {
    }

    public void onPre(View view) {
    }
}
