package com.kekejl.a360floatwinowdemo;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.WindowManager;

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
}
