package com.o0live0o.app.appearance;

import android.app.Activity;

import java.util.Stack;

public class ActivityStack {

    private static volatile ActivityStack mInstance;

    private static Stack<Activity> mActivities;

    private ActivityStack(){
        mActivities = new Stack<>();
    }

    public static ActivityStack getInstance(){
        if(mInstance == null){
            synchronized (ActivityStack.class){
                if (mInstance == null){
                    mInstance = new ActivityStack();
                }
            }
        }
        return mInstance;
    }

    public void attach(Activity activity){
        mActivities.add(activity);
    }

    public void detach(Activity activity){
        int size = mActivities.size();
        for (int i = 0;i<size;i++){
            Activity activity1 = mActivities.get(i);
            if (activity1 == activity){
                mActivities.remove(i);
            }
        }
    }

    public void exit(){
        for (Activity activity : mActivities){
            activity.finish();
        }
        System.exit(0);
    }
}
