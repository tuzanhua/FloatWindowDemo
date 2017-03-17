package com.kekejl.a360floatwinowdemo;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * author：tzh on 2017/3/17 14:24
 * contact information: 188****5816
 * company :可可家里(北京)信息技术有限公司
 * describe:
 * modify instructions:
 */
public class MyWindowManger {

    private static WindowManager mWidowManger;
    private static FloatWindowSmallView floatWindowSmallView;
    private static WindowManager.LayoutParams smllWindowParams;
    private static ActivityManager mActivityManager;
    private static FloatWindowBigView bigWindow;
    private static WindowManager.LayoutParams bigWindowParams;

    /**
     * 创建小悬浮窗的方法
     *
     * @param context
     */
    public static void createSmallWindow(Context context) {
        WindowManager windowManger = getWindowManger(context);
        int screenWidth = windowManger.getDefaultDisplay().getWidth();
        int screenHeight = windowManger.getDefaultDisplay().getHeight();
        if (floatWindowSmallView == null) {
            floatWindowSmallView = new FloatWindowSmallView(context);
            if (smllWindowParams == null) {
                smllWindowParams = new WindowManager.LayoutParams();
                //设置layoutmanger 的参数信息 这是设置悬浮窗的核心部分 各种不同的参数会生成不同的效果
                //type 的类型请自行百度
                smllWindowParams.type = WindowManager.LayoutParams.TYPE_PHONE;
                smllWindowParams.format = PixelFormat.RGBA_8888;
                smllWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                smllWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
                smllWindowParams.width = FloatWindowSmallView.viewWidth;
                smllWindowParams.height = FloatWindowSmallView.viewHeight;
                smllWindowParams.x = screenWidth - FloatWindowSmallView.viewWidth;
                smllWindowParams.y = screenHeight / 2;
            }
            //参数设置给小悬浮窗
            floatWindowSmallView.setParams(smllWindowParams);
            //将小悬浮窗 添加到窗体上
            windowManger.addView(floatWindowSmallView, smllWindowParams);
        }
    }


    /**
     * @param context
     */
    public static void createBigWindowView(Context context) {
        WindowManager windowManager = getWindowManger(context);
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        int screenHeight = windowManager.getDefaultDisplay().getHeight();
        if (bigWindow == null) {
            bigWindow = new FloatWindowBigView(context);
            if (bigWindowParams == null) {
                bigWindowParams = new WindowManager.LayoutParams();
                bigWindowParams.x = screenWidth / 2 - FloatWindowBigView.viewWidth / 2;
                bigWindowParams.y = screenHeight / 2 - FloatWindowBigView.viewHeight / 2;
                bigWindowParams.type = WindowManager.LayoutParams.TYPE_PHONE;
                bigWindowParams.format = PixelFormat.RGBA_8888;
                bigWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
                bigWindowParams.width = FloatWindowBigView.viewWidth;
                bigWindowParams.height = FloatWindowBigView.viewHeight;
            }
            windowManager.addView(bigWindow, bigWindowParams);
        }
    }

    /**
     * 获取windowManger 的实例
     *
     * @param context
     * @return
     */
    private static WindowManager getWindowManger(Context context) {
        if (mWidowManger == null)
            mWidowManger = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return mWidowManger;
    }

    /**
     * 计算已使用内存的百分比，并返回。
     *
     * @param context 可传入应用程序上下文。
     * @return 已使用内存的百分比，以字符串形式返回。
     */
    public static String getUsedPercentValue(Context context) {
        String dir = "/proc/meminfo";
        try {
            FileReader fr = new FileReader(dir);
            BufferedReader br = new BufferedReader(fr, 2048);
            String memoryLine = br.readLine();
            String subMemoryLine = memoryLine.substring(memoryLine.indexOf("MemTotal:"));
            br.close();
            long totalMemorySize = Integer.parseInt(subMemoryLine.replaceAll("\\D+", ""));
            long availableSize = getAvailableMemory(context) / 1024;
            int percent = (int) ((totalMemorySize - availableSize) / (float) totalMemorySize * 100);
            return percent + "%";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "悬浮窗";
    }

    /**
     * 获取当前可用内存，返回数据以字节为单位。
     *
     * @param context 可传入应用程序上下文。
     * @return 当前可用内存。
     */
    private static long getAvailableMemory(Context context) {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        getActivityManager(context).getMemoryInfo(mi);
        return mi.availMem;
    }

    /**
     * 如果ActivityManager还未创建，则创建一个新的ActivityManager返回。否则返回当前已创建的ActivityManager。
     *
     * @param context 可传入应用程序上下文。
     * @return ActivityManager的实例，用于获取手机可用内存。
     */
    private static ActivityManager getActivityManager(Context context) {
        if (mActivityManager == null) {
            mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        }
        return mActivityManager;
    }

    /**
     * 移除小悬浮窗
     *
     * @param context
     */
    public static void removeSmallWindow(Context context) {
        if (floatWindowSmallView != null) {
            WindowManager windowManger = getWindowManger(context);
            windowManger.removeView(floatWindowSmallView);
            floatWindowSmallView = null;
        }
    }

    /**
     * 移除小悬浮窗
     *
     * @param context
     */
    public static void removeBigWindow(Context context) {
        if (bigWindow != null) {
            WindowManager windowManger = getWindowManger(context);
            windowManger.removeView(bigWindow);
            bigWindow = null;
        }
    }
}
