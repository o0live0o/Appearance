package com.o0live0o.app.appearance.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.o0live0o.app.appearance.R;

public class LabelView extends FrameLayout {

    private String headTxt;
    private String valTxt;

    private View mView;
    private TextView tvHead;
    private TextView tvVal;

    public LabelView( @NonNull Context context) {
        super(context);
        init(context,null);
    }

    public LabelView( @NonNull Context context,  @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public LabelView( @NonNull Context context,  @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    public LabelView( @NonNull Context context,  @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }

    public void init(Context context,AttributeSet attrs){
        if (attrs == null) return;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.labelView);
        headTxt = typedArray.getString(R.styleable.labelView_head_text);
        valTxt = typedArray.getString(R.styleable.labelView_val_text);
        typedArray.recycle();

        mView = LayoutInflater.from(context).inflate(R.layout.show_view,this,false);
        tvHead = mView.findViewById(R.id.sw_head);
        tvVal = mView.findViewById(R.id.sw_val);

        tvHead.setText(headTxt);
        tvVal.setText(valTxt);

        addView(mView);
    }

    public void setValTxt(String s){
        tvVal.setText(s);
    }
}
