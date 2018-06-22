package kara.ai.com.loadinganimation;

import android.animation.TypeEvaluator;

/**
 * Created by wlwlxgg on 2018/6/22.
 */

public class PointEvalutor2 implements TypeEvaluator {

    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        Point startPoint = (Point) startValue;
        Point endPoint = (Point) endValue;
        float left = startPoint.getLeft();
        float top = startPoint.getTop();
        float right = startPoint.getRight();
        float bottom = startPoint.getBottom() - fraction * (startPoint.getBottom() - endPoint.getBottom());
        return new Point(left, top, right, bottom);
    }
}
