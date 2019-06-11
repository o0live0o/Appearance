package com.o0live0o.app.appearance.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.o0live0o.app.appearance.data.FinalData;
import com.o0live0o.app.appearance.R;

public class HelpActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        init();
    }

    private void init(){
        initNavBar(true,getString(R.string.grzx),false);
    }

    public void onLoginClick(View view) {
        Intent intent = new Intent(this,LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        FinalData.setOperator("");
        finish();

    }
}
