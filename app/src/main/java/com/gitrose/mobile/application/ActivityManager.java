package com.gitrose.mobile.application;

import android.app.Activity;
import java.util.Iterator;
import java.util.Stack;

public class ActivityManager {
    private static Stack<Activity> activityStack;
    private static ActivityManager instance;

    private ActivityManager() {
    }

    public static ActivityManager getScreenManager() {
        if (instance == null) {
            instance = new ActivityManager();
        }
        return instance;
    }

    public void popActivity(Activity activity) {
        if (activity != null) {
            activity.finish();
            activityStack.remove(activity);
        }
    }

    public Activity currentActivity() {
        if (activityStack == null || activityStack.empty()) {
            return null;
        }
        return (Activity) activityStack.peek();
    }

    public void pushActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack();
        }
        activityStack.push(activity);
    }

    public <T extends Activity> void popAllActivityExceptOne(Class<T> cls) {
        while (activityStack.size() > 1) {
            Activity activity = currentActivity();
            if (activity != null && !activity.getClass().equals(cls)) {
                popActivity(activity);
            } else {
                return;
            }
        }
    }

    public void popAllActivity() {
        while (!activityStack.isEmpty()) {
            Activity activity = (Activity) activityStack.pop();
            if (activity != null) {
                activity.finish();
            } else {
                return;
            }
        }
    }

    public Activity getActivity(String name) {
        Iterator it = activityStack.iterator();
        while (it.hasNext()) {
            Activity activity = (Activity) it.next();
            if (activity.getClass().getName().equals(name)) {
                return activity;
            }
        }
        return null;
    }

    public <T extends Activity> Activity getActivity(Class<T> cls) {
        Iterator it = activityStack.iterator();
        while (it.hasNext()) {
            Activity activity = (Activity) it.next();
            if (activity.getClass().equals(cls)) {
                return activity;
            }
        }
        return null;
    }

    public Stack<Activity> getStack() {
        return activityStack;
    }
}
