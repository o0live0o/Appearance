package com.o0live0o.app.appearance.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.o0live0o.app.appearance.R;

public class InputWithHeadView extends FrameLayout {

    private String headText;
    private TextView tvHead;
    private EditText edText;
    private View mView;

    public InputWithHeadView( @NonNull Context context) {
        super(context);
        init(context,null);
    }

    public InputWithHeadView( @NonNull Context context,  @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public InputWithHeadView( @NonNull Context context,  @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    public InputWithHeadView( @NonNull Context context,  @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }

    public void init(Context context,AttributeSet attrs){
        if(attrs == null) return;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.inputHeadView);
        headText = typedArray.getString(R.styleable.inputHeadView_input_headtext);
        typedArray.recycle();

        mView = LayoutInflater.from(context).inflate(R.layout.input_head_view,this,false);
        tvHead = mView.findViewById(R.id.ihv_text);
        edText = mView.findViewById(R.id.ihv_edit);

        tvHead.setText(headText);
        addView(mView);
    }

    public String getInputStr(){return edText.getText().toString().trim();}

    public void  setInputStr(String s){edText.setText(s);}

}
