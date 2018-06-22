package kara.ai.com.loadinganimation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

/**
 * Created by wlwlxgg on 2018/6/21.
 */

public class LoadingView extends View {


    private Paint mPaint;
    private int whiteViewW;
    private int whiteViewH;
    private int blackViewW;
    private int blackViewH;
    private Point curPoint;
    private boolean isWhiteEnd = false;
    private boolean isBlackEnd = false;

    private boolean flag = false;

    public LoadingView(Context context) {
        super(context);
        initPaint();
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        initPaint();
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        initPaint();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttrs(context, attrs);
        initPaint();
    }


    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.my_view);
        whiteViewH = typedArray.getInteger(R.styleable.my_view_white_h, 120);
        whiteViewW = typedArray.getInteger(R.styleable.my_view_white_w, 120);
        blackViewH = typedArray.getInteger(R.styleable.my_view_black_h, 80);
        blackViewW = typedArray.getInteger(R.styleable.my_view_black_w, 80);
        typedArray.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 获取宽-测量规则的模式和大小
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        // 获取高-测量规则的模式和大小
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        // 设置wrap_content的默认宽 / 高值
        // 默认宽/高的设定并无固定依据,根据需要灵活设置
        // 类似TextView,ImageView等针对wrap_content均在onMeasure()对设置默认宽 / 高值有特殊处理,具体读者可以自行查看
        int mWidth = whiteViewW;
        int mHeight = whiteViewH;

        // 当布局参数设置为wrap_content时，设置默认值
        if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT && getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(mWidth, mHeight);
            // 宽 / 高任意一个布局参数为= wrap_content时，都设置默认值
        } else if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(mWidth, heightSize);
        } else if (getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(widthSize, mHeight);
        }
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        curPoint = new Point((whiteViewW - blackViewW) / 2, (whiteViewW + blackViewW) / 2,
                (whiteViewH + blackViewH) / 2, (whiteViewH + blackViewH) / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!isWhiteEnd) {
            drawWhite(canvas);
            drawBlack(canvas);
            startWhiteAnimation();
        } else {
            if (!isBlackEnd) {
                drawWhite(canvas);
                drawBlack(canvas);
                startBlackAnimation();
            } else {
                drawWhite(canvas);
                drawBlack(canvas);
            }
        }

    }

    private void drawWhite(Canvas canvas) {
        mPaint.setColor(Color.WHITE);
        canvas.drawRect(0, 0, whiteViewW, whiteViewH, mPaint);

    }

    private void drawBlack(Canvas canvas) {
        mPaint.setColor(Color.BLACK);
        canvas.drawRect(curPoint.getLeft(), curPoint.getTop(),
                curPoint.getRight(), curPoint.getBottom(), mPaint);

    }

    private void startWhiteAnimation() {
        final ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "rotation", 180f, 360f);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                isWhiteEnd = true;
                isBlackEnd = false;
                invalidate();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        objectAnimator.setDuration(300);
        objectAnimator.start();
    }


    //该动画为中间小方块从下往上或者从下往上
    private void startBlackAnimation() {
        Point startPoint = new Point((whiteViewW - blackViewW) / 2, (whiteViewW + blackViewW) / 2,
                (whiteViewH + blackViewH) / 2, (whiteViewH + blackViewH) / 2);
        Point endPoint = new Point((whiteViewW - blackViewW) / 2, (whiteViewH - blackViewH) / 2,
                (whiteViewH + blackViewH) / 2, (whiteViewH + blackViewH) / 2);
        ValueAnimator anim;
        if (!flag) {
            anim = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
            flag = true;
        } else {
            anim = ValueAnimator.ofObject(new PointEvalutor2(), endPoint,
                    new Point((whiteViewW - blackViewW) / 2, (whiteViewW - blackViewW) / 2,
                    (whiteViewH + blackViewH) / 2, (whiteViewH - blackViewH) / 2));
            flag = false;
        }
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                curPoint = (Point) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        anim.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animator) {
                isBlackEnd = true;
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                isWhiteEnd = false;
                invalidate();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        anim.setDuration(800);
        anim.start();
    }

}
