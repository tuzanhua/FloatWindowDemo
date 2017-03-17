package com.kekejl.a360floatwinowdemo;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * author：tzh on 2017/3/17 14:05
 * contact information: 188****5816
 * company :可可家里(北京)信息技术有限公司
 * describe:
 * modify instructions:
 */
public class FloatWindowBigView extends LinearLayout {

    /**
     * view的宽度
     */
    public static int viewWidth;
    /**
     * view的高度
     */
    public static int viewHeight;

    public FloatWindowBigView(Context context) {
        this(context, null);
    }

    public FloatWindowBigView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatWindowBigView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**
     * 初始化view
     */
    private void initView(final Context context) {
        LayoutInflater.from(context).inflate(R.layout.float_window_big, this);
        View view = findViewById(R.id.big_window_layout);
        viewWidth = view.getLayoutParams().width;
        viewHeight = view.getLayoutParams().height;
        Button close = (Button) findViewById(R.id.close);
        Button back = (Button) findViewById(R.id.back);
        close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击关闭悬浮窗的时候，移除所有悬浮窗，并停止Service
                MyWindowManger.removeBigWindow(context);
                MyWindowManger.removeSmallWindow(context);
                Intent intent = new Intent(getContext(), FloatWindowsService.class);
                context.stopService(intent);
            }
        });
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击返回的时候，移除大悬浮窗，创建小悬浮窗
                MyWindowManger.removeBigWindow(context);
                MyWindowManger.createSmallWindow(context);
            }
        });
    }
}
