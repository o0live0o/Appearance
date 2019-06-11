package com.o0live0o.app.appearance.activitys;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;

import com.o0live0o.app.appearance.data.ExteriorList;
import com.o0live0o.app.appearance.R;
import com.o0live0o.app.appearance.adapters.NavAdapter;
import com.o0live0o.app.appearance.bean.NavBean;

import java.util.List;

public class NavActivity extends BaseActivity {

    private RecyclerView mRvNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        initNavBar(false,"功能列表",true);
        init();

    }

    private void init()
    {
        List<NavBean> list = ExteriorList.getNavList();
        mRvNav = findViewById(R.id.nav_rv);
        NavAdapter navAdapter = new NavAdapter(this,list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvNav.setLayoutManager(linearLayoutManager);
        mRvNav.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mRvNav.setAdapter(navAdapter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}