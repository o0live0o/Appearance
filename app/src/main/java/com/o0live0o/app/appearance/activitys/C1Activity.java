package com.o0live0o.app.appearance.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.o0live0o.app.appearance.ExteriorList;
import com.o0live0o.app.appearance.FinalData;
import com.o0live0o.app.appearance.ICURD;
import com.o0live0o.app.appearance.L;
import com.o0live0o.app.appearance.R;
import com.o0live0o.app.appearance.adapters.ChekItemAdapter;
import com.o0live0o.app.appearance.bean.CarBean;
import com.o0live0o.app.appearance.bean.ExteriorBean;
import com.o0live0o.app.appearance.enums.CheckState;
import com.o0live0o.app.appearance.listener.ExteriorChangeListener;
import com.o0live0o.app.appearance.views.LabelView;

import java.util.List;

public class C1Activity extends BaseActivity {

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
        setContentView(R.layout.activity_c1);
        init();
    }

    private void init(){
        initNavBar(true,"底盘检查",false);

        mRV = findViewById(R.id.c1_rv_checklist);
        lvPlateNo = findViewById(R.id.c1_lv_plateno);
        lvTestId = findViewById(R.id.c1_lv_testid);
        lvOperator = findViewById(R.id.c1_lv_operator);

        mCar = getIntent().getParcelableExtra("carInfo");
        mCar.setStartTime(getTime());
        lvPlateNo.setValTxt(mCar.getPlateNo());
        lvTestId.setValTxt(mCar.getTestId());
        lvOperator.setValTxt(FinalData.getOperator());

        mList = ExteriorList.getC1List();
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
    }
}
