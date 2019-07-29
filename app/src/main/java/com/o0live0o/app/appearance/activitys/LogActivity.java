package com.o0live0o.app.appearance.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.o0live0o.app.appearance.R;
import com.o0live0o.app.appearance.log.L;
import com.o0live0o.app.appearance.log.LogAdapter;

import java.util.LinkedList;

public class LogActivity extends BaseActivity {
    private RecyclerView rvLogs;
    private LinkedList<String> logs;
    private LogAdapter logsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        initNavBar(true,"日  志",false);
        init();
    }

    private void init() {
        rvLogs = findViewById(R.id.rv_logs);
        logs = L.logList;
        logsAdapter = new LogAdapter(logs);
        rvLogs.setLayoutManager(new LinearLayoutManager(this));
        rvLogs.setAdapter(logsAdapter);
    }
}
