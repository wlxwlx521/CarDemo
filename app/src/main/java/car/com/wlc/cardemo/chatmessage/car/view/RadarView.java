package car.com.wlc.cardemo.chatmessage.car.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import car.com.wlc.cardemo.R;


/**
 * Created by Administrator on 2017/3/11.
 */

public class RadarView extends View implements Runnable {
    private static final String TAG = "OnKeyClearCircleView";
    private Paint paint;
    private Paint outCirclePaint;
    private Paint textPaint;
    private Paint outArcPaint;
    private Paint radarPain;
    private Paint pointPain;

    private int radarRotateDegree;
    private int innerCircleColor;
    private int innerCircleRadius;
    private int outCircleColor;
    private float outArcwidth;

    private SweepGradient outArcSweepGradient;
    private SweepGradient radarSweepGradient;

    private Bitmap pointDrawable;
    private Matrix pointRotate=new Matrix();

    private int progress;
    private float textSize;
    private int padding;

    private float startAngle;
    private float radarSweepAngle;
    private float pointRotateDegree;

    private boolean isSart;

    public RadarView(Context context) {
        super(context);
        init(null, 0);
    }

    public RadarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public RadarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.OnKeyClearCircleView, defStyle, 0);
        innerCircleColor = a.getColor(R.styleable.OnKeyClearCircleView_innerCircleColor, Color.WHITE);
        outCircleColor = a.getColor(R.styleable.OnKeyClearCircleView_outCircleColor, Color.GRAY);
        innerCircleRadius = a.getInt(R.styleable.OnKeyClearCircleView_innerCircleRadius, 40);
        progress = a.getInt(R.styleable.OnKeyClearCircleView_progress,0);
        textSize = a.getDimension(R.styleable.OnKeyClearCircleView_textSize, 20);
        outArcwidth = a.getDimension(R.styleable.OnKeyClearCircleView_outArcwidth, 20);
        a.recycle();


        pointDrawable = BitmapFactory.decodeResource(getResources(),R.drawable.point);
        isSart = false;
        startAngle = 0;
        radarRotateDegree = 0;
        radarSweepAngle = 0;
        pointRotateDegree = 0;
        padding = 5;
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(innerCircleColor);
        outCirclePaint = new Paint();
        outCirclePaint.setAntiAlias(true);
        outCirclePaint.setColor(outCircleColor);
        outCirclePaint.setStyle(Paint.Style.FILL);
        textPaint = new Paint();
        textPaint.setTextSize(textSize);
        textPaint.setAntiAlias(true);
        outArcPaint = new Paint();
        outArcPaint.setAntiAlias(true);
        outArcPaint.setStyle(Paint.Style.STROKE);
        outArcPaint.setStrokeWidth(outArcwidth);
        outArcPaint.setStrokeCap(Paint.Cap.ROUND);
        radarPain = new Paint();
        outArcPaint.setAntiAlias(true);
        pointPain = new Paint();
        pointPain.setAntiAlias(true);
        Thread thread=new Thread(this);
        thread.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int d = (width >= height) ? height : width;
        setMeasuredDimension(d,d);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int pointX =  width/2;
        int pointY = height/2;
        RectF rectf = new RectF(outArcwidth/2,outArcwidth/2,width-outArcwidth/2,height-outArcwidth/2);
        //outArcSweepGradient = new SweepGradient(0,0,getResources().getColor(R.color.start_color),getResources().getColor(R.color.end_color));
        outArcSweepGradient = new SweepGradient(pointX,pointY,Color.WHITE,Color.WHITE);
        outArcPaint.setShader(outArcSweepGradient);
        canvas.drawArc(rectf,startAngle,180,false,outArcPaint);
        //1.画圆
        canvas.drawCircle(pointX,pointY,pointX -outArcwidth-padding,outCirclePaint);

        if(radarSweepAngle < 180){
            radarSweepGradient = new SweepGradient(pointX,pointY,Color.WHITE,Color.RED);
        }else{
            radarSweepGradient = new SweepGradient(pointX,pointY,Color.WHITE,Color.parseColor("#F44336"));
        }
        radarPain.setShader(radarSweepGradient);
        RectF rectfRadar = new RectF(outArcwidth+padding,outArcwidth+padding,width-outArcwidth-padding,height-outArcwidth-padding);
        canvas.drawArc(rectfRadar,0,radarSweepAngle,true,radarPain);

        canvas.save();
        canvas.translate(pointX,pointY);
        pointRotate.setRotate(pointRotateDegree);
        canvas.drawBitmap(pointDrawable, pointRotate, pointPain);
        canvas.restore();
        //2.画圆
        canvas.drawCircle(pointX,pointY,innerCircleRadius,paint);

        float textWidth = textPaint.measureText(progress + "%");
        if(progress < 50){
            //textPaint.setColor(oxbf3800);
            textPaint.setColor(Color.BLACK);
        }else{
            //textPaint.setColor(new Color(ox6ec705));
            textPaint.setColor(Color.BLACK);
        }
        //显示进度
        canvas.drawText(progress+"分",pointX - textWidth/2,pointY + textSize/2 ,textPaint);
    }

    @Override
    public void run() {
        while(true){
            if(isSart){
                this.startAngle += 20;
                if(this.startAngle > 360){
                    this.startAngle = this.startAngle-360;
                }
                this.radarSweepAngle += 10;
                if(this.radarSweepAngle > 360){
                    this.radarSweepAngle = this.radarSweepAngle-360;
                }
                this.pointRotateDegree += 10;
                if(this.pointRotateDegree > 360){
                    this.pointRotateDegree = this.pointRotateDegree-360;
                }
                progress = (int)radarSweepAngle*100/360;

                postInvalidate();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //开始动画
    public void startRadar(){
        this.isSart =  true;
    }

    //结束动画
    public void stopRadar(){
        this.isSart =false;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}

