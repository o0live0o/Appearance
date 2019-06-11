package com.o0live0o.app.appearance.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class InputWithHeadView extends FrameLayout {
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
        
    }

}
