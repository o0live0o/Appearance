package com.o0live0o.app.appearance.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.o0live0o.app.appearance.R;


public class InputView extends FrameLayout {

    private int inputIcon;
    private String inputHint;
    private boolean isPwd;

    private ImageView mLogoView;
    private View mView;
    private EditText mInputView;

    public InputView( @NonNull Context context) {
        super(context);
        init(context,null);
    }

    public InputView( @NonNull Context context,  @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public InputView( @NonNull Context context,  @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    public InputView( @NonNull Context context,  @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }

    public void init(Context context,AttributeSet attrs){
        if (attrs == null) return;
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.inputView);
        inputIcon = typedArray.getResourceId(R.styleable.inputView_input_icon,R.drawable.me);
        inputHint = typedArray.getString(R.styleable.inputView_input_hint);
        isPwd = typedArray.getBoolean(R.styleable.inputView_is_pwd,false);
        typedArray.recycle();

        mView = LayoutInflater.from(context).inflate(R.layout.input_view,this,false);
        mInputView = mView.findViewById(R.id.et_input);
        mLogoView = mView.findViewById(R.id.iv_icon);

        mLogoView.setImageResource(inputIcon);
        mInputView.setHint(inputHint);
        mInputView.setInputType(isPwd? InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD:InputType.TYPE_CLASS_TEXT);

        addView(mView);
    }

    public String getInputStr(){
        return mInputView.getText().toString();
    }

    public void setInputStr(String s){
        mInputView.setText(s);
    }
}
