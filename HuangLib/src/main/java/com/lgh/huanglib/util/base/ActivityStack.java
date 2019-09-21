package com.lgh.huanglib.util.base;

import android.app.Activity;

import com.lgh.huanglib.util.L;
import com.lgh.huanglib.util.config.MyApplication;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Stack;

/**
 * Activity堆栈管理。
 *
 * <p>
 * create at 2017/9/9 11:58
 */
public class ActivityStack extends MyApplication {
    private final static String TAG = "ActivityStack";

//    // 运用list来保存们每一个activity是关键
//    private List<Activity> mList = new LinkedList<Activity>();
    // 为了实现每次使用该类时不创建新的对象而创建的静态对象
    private static ActivityStack instance;

    // 构造方法
    private ActivityStack() {
    }

    // 实例化一次
    public synchronized static ActivityStack getInstance() {
        if (null == instance) {
            instance = new ActivityStack();
        }
        return instance;
    }
    // 杀进程
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }
    /***寄存整个应用Activity**/
    private final Stack<WeakReference<Activity>> activitys = new Stack<WeakReference<Activity>>();

    /**
     * 将Activity压入Application栈
     * @param task 将要压入栈的Activity对象
     */
    public  void addActivity(WeakReference<Activity> task) {
        L.e("tash","pushTask "+task.get().getLocalClassName());
        activitys.push(task);
    }

    /**
     * 将传入的Activity对象从栈中移除
     * @param task
     */
    public  void removeActivity(WeakReference<Activity> task) {
        activitys.remove(task);
    }

    /**
     * 根据指定位置从栈中移除Activity
     * @param taskIndex Activity栈索引
     */
    public  void removeActivity(int taskIndex) {
        if (activitys.size() > taskIndex)
            activitys.remove(taskIndex);
    }

    /**
     * 将栈中Activity移除至栈顶
     */
    public  void removeToTop() {
        int end = activitys.size();
        int start = 1;
        for (int i = end - 1; i >= start; i--) {
            if (!activitys.get(i).get().isFinishing()) {
                activitys.get(i).get().finish();
            }
        }
    }

    /**
     * 移除全部（用于整个应用退出）
     */
    public  void removeAll() {
        //finish所有的Activity
        for (WeakReference<Activity> task : activitys) {
            if (!task.get().isFinishing()) {
                task.get().finish();
            }
        }
        activitys.clear();
    }

    // 获得当前栈顶Activity
    public Activity currentActivity() {
        WeakReference<Activity> activity = activitys.get(activitys.size() - 1);
        return activity.get();
    }

    public void exitClass(Class<?> classA) {
        try {
            L.e("lgh", "activitys.size()  = " + activitys.size());
            Iterator<WeakReference<Activity>> iterator = activitys.iterator();
            while (iterator.hasNext()) {
                WeakReference<Activity> task= iterator.next();
                if (task.get()==null){
                    iterator.remove();
                    continue;
                }
                if (task.get().getClass().equals(classA)) {
                    iterator.remove();
                    task.get().finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exitIsNotHaveMain(Class<?> classA) {
        try {
            L.e("lgh", "activitys.size()  = " + activitys.size());
            Iterator<WeakReference<Activity>> iterator = activitys.iterator();
            while (iterator.hasNext()) {
                WeakReference<Activity> task= iterator.next();
                if (task.get()==null){
                    iterator.remove();
                    continue;
                }
                if (!task.get().getClass().equals(classA)) {
                    iterator.remove();
                    task.get().finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //移除除了classA和classB之外的activity
    public void exitIsNotHaveMain(Class<?> classA ,Class<?> classB) {
        try {
            Iterator<WeakReference<Activity>> iterator = activitys.iterator();
            while (iterator.hasNext()) {
                WeakReference<Activity> task= iterator.next();
                if (task.get()==null){
                    iterator.remove();
                    continue;
                }
                if (!task.get().getClass().equals(classA)&&!task.get().getClass().equals(classB)) {
                    iterator.remove();
                    task.get().finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

