package kara.ai.com.loadinganimation;

/**
 * Created by wlwlxgg on 2018/6/21.
 */

public class Point {

    private float left;
    private float top;
    private float right;
    private float bottom;

    public Point(float left, float top, float right, float bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public float getLeft() {
        return left;
    }

    public void setLeft(float left) {
        this.left = left;
    }

    public float getTop() {
        return top;
    }

    public void setTop(float top) {
        this.top = top;
    }

    public float getRight() {
        return right;
    }

    public void setRight(float right) {
        this.right = right;
    }

    public float getBottom() {
        return bottom;
    }

    public void setBottom(float bottom) {
        this.bottom = bottom;
    }

    @Override
    public String toString() {
        return "left=" + left + "  top=" + top + "  right=" + right + "  bottom=" + bottom + "\n";
    }
}
