package com.kekejl.a360floatwinowdemo;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * author：tzh on 2017/3/17 09:36
 * contact information: 188****5816
 * company :可可家里(北京)信息技术有限公司
 * describe:
 * modify instructions:
 */
public class FloatWindowsService extends Service {

    /**
     * 用于在线程中创建或者移除悬浮窗
     */
    private Handler handler = new Handler();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        try {
//            Thread.sleep(1000);
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    isHome();
//                }
//            }).start();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        MyWindowManger.createSmallWindow(this);

        //如果需要更改小悬浮窗的使用内存自己开子线程更新数据

        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 判断当前界面是不是桌面
     *
     * @return
     */
    private boolean isHome() {
        ActivityManager mActivityManger = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTasks = mActivityManger.getRunningTasks(1);
        for (ActivityManager.RunningTaskInfo ri : runningTasks) {
            Log.e("tzh", "isHome:" + ri.topActivity.getPackageName());
        }
        return getHomes().contains(runningTasks.get(0).topActivity.getPackageName());
    }

    /**
     * 获得属于桌面的应用的应用包名称
     *
     * @return 返回包含所有包名的字符串列表
     */
    private List<String> getHomes() {

        List<String> names = new ArrayList<>();
        PackageManager pm = this.getPackageManager();
//        List<PackageInfo> installedPackages = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
//        for (PackageInfo pInfo : installedPackages) {
//            Log.e("tzh", "包名:长度" + installedPackages.size() + "===" + pInfo.packageName); //包名
//        }
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);

        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo ri : resolveInfos) {
            names.add(ri.activityInfo.packageName);
            Log.e("tzh", "c长度:" + resolveInfos.size() + "===" + ri.activityInfo.packageName);
        }
        return names;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
