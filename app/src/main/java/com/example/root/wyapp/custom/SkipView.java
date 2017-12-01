package com.example.root.wyapp.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by root on 2017/7/18.
 */

public class SkipView extends View{

    public static final int TEXT_SIZE = 30;//文字的大小
    public static final int TEXT_MARGIN = 10;//文字距离中间圆边框的间距
    public static final int ARC_WIDTH = 8;//外部圆弧的笔画宽度
    private float arcR;
    private float circleR;
    private float textW;
    private Paint circlePaint;
    private Paint arcPaint;
    private Paint textPaint;
    private RectF rectF;
    private boolean swing = false;
    private Thread thread;
    int hu = 30;
    int maxhu = 360;

    public SkipView(Context context) {
        this(context,null);
    }

    public SkipView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        swing = true;
        thread.start();
    }

    public interface onSikpListener{
        void onSikp();
    }
    private onSikpListener onSikpListener;
    public void setOnSikpListener(onSikpListener listener){
        onSikpListener = listener ;
    }


    //onDetachedFromWindow 控件跟窗口接触绑定了
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        swing = false;
    }

    private void initView() {
        //文字画笔
        textPaint = new Paint();
        textPaint.setTextSize(TEXT_SIZE);
        textPaint.setColor(Color.WHITE);
        textPaint.setAntiAlias(true);

        //圆的画笔
        circlePaint = new Paint();
        circlePaint.setColor(Color.GRAY);
        circlePaint.setAntiAlias(true);

        //圆弧画笔
        arcPaint = new Paint();
        arcPaint.setColor(Color.RED);
        arcPaint.setAntiAlias(true);

        textW = textPaint.measureText("跳过");
        circleR = textW + TEXT_MARGIN * 2;
        arcR = circleR + ARC_WIDTH * 2;

        rectF = new RectF(0 , 0 , arcR , arcR );

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (swing){
                    hu +=30 ;
                    if(hu>=maxhu) {
                            swing = false;
                        //开启任务栈
                        onSikpListener.onSikp();
                        //FLAG_ACTIVITY_SINGLE_TOP

                    }
                    SystemClock.sleep(500);
                    postInvalidate();//在子线程中重绘：postInvalidate()
                }
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int modeW = MeasureSpec.getMode(widthMeasureSpec);
        int modeH = MeasureSpec.getMode(heightMeasureSpec);
        int sizeW = MeasureSpec.getSize(widthMeasureSpec);
        int sizeH = MeasureSpec.getSize(heightMeasureSpec);

        if(modeW== MeasureSpec.AT_MOST) {
            sizeW = (int) arcR;
        }if(modeH== MeasureSpec.AT_MOST) {
            sizeH = (int) arcR;
        }
        setMeasuredDimension(sizeW,sizeH);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.rotate(-90,getMeasuredWidth()/2,getMeasuredHeight()/2);
        canvas.drawArc(rectF,0,hu,true,arcPaint);
        canvas.restore();

        canvas.drawCircle(getMeasuredWidth()/2,getMeasuredHeight()/2,circleR/2,circlePaint);

        canvas.drawText("跳过",getMeasuredWidth()/2-textW/2,getMeasuredHeight()/2,textPaint);
    }
}
