package kara.ai.com.loadinganimation;

import android.animation.TypeEvaluator;

/**
 * Created by wlwlxgg on 2018/6/21.
 */

public class PointEvaluator implements TypeEvaluator {
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        Point startPoint = (Point) startValue;
        Point endPoint = (Point) endValue;
        float left = startPoint.getLeft() + fraction * (endPoint.getLeft() - startPoint.getLeft());
        float top = startPoint.getTop() + fraction * (endPoint.getTop() - startPoint.getTop());
        float right = startPoint.getRight() + fraction * (endPoint.getRight() - startPoint.getRight());
        float bottom = startPoint.getBottom() + fraction * (endPoint.getBottom() - endPoint.getBottom());
        return new Point(left, top, right, bottom);
    }
}
