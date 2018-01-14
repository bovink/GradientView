package com.bovink.gradient;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 使用GradientDrawable作为背景的View
 *
 * @author bovink
 * @since 2018/1/14
 */

public class GradientView extends View implements View.OnTouchListener {
    /**
     * 默认触摸亮度
     */
    private final static int DEFAULT_LUM = 45;
    /**
     * 触摸控件时，控件的亮度。默认使用DEFAULT_LUM。
     */
    private int mTouchLum = DEFAULT_LUM;
    /**
     * 默认使用GradientDrawable作为背景。
     */
    private GradientDrawable mBackgroundDrawable = new GradientDrawable();
    /**
     * 控件是否可点击。默认不可点击。
     */
    private boolean mClickable = false;

    public GradientView(Context context) {
        super(context);
    }

    public GradientView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GradientView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.GradientView, defStyleAttr, 0);

        setOnTouchListener(this);

        updateBackgroundCornerRadius(a);

        updateBackgroundStroke(a);

        updateBackgroundColor(a);

        updateBackground();

        a.recycle();

    }

    /**
     * 更新背景四角的半径
     *
     * @param a 属性集合
     */
    private void updateBackgroundCornerRadius(TypedArray a) {

        mBackgroundDrawable.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);

        // 获取半径
        final int radius = a.getDimensionPixelSize(
                R.styleable.GradientView_corners_radius, 0);
        mBackgroundDrawable.setCornerRadius(radius);

        // 获取四角的半径
        final int topLeftRadius = a.getDimensionPixelSize(
                R.styleable.GradientView_corners_topLeftRadius, radius);
        final int topRightRadius = a.getDimensionPixelSize(
                R.styleable.GradientView_corners_topRightRadius, radius);
        final int bottomLeftRadius = a.getDimensionPixelSize(
                R.styleable.GradientView_corners_bottomLeftRadius, radius);
        final int bottomRightRadius = a.getDimensionPixelSize(
                R.styleable.GradientView_corners_bottomRightRadius, radius);

        // 当分别设置的半径与一次性设置的半径不同时，重新设置四角半径
        if (topLeftRadius != radius || topRightRadius != radius ||
                bottomLeftRadius != radius || bottomRightRadius != radius) {
            mBackgroundDrawable.setCornerRadii(new float[]{
                    topLeftRadius, topLeftRadius,
                    topRightRadius, topRightRadius,
                    bottomRightRadius, bottomRightRadius,
                    bottomLeftRadius, bottomLeftRadius});
        }
    }

    /**
     * 更新背景的描边
     *
     * @param a 属性集合
     */
    private void updateBackgroundStroke(TypedArray a) {

        // 描边宽度
        final int width = a.getDimensionPixelSize(
                R.styleable.GradientView_stroke_width, 0);

        // 描边颜色
        final int color = a.getColor(
                R.styleable.GradientView_stroke_color, Color.TRANSPARENT);

        // 虚线宽度
        final float dashWidth = a.getDimension(
                R.styleable.GradientView_stroke_dashWidth, 0.0f);

        if (dashWidth != 0.0f) {
            // 虚线间隙
            final float dashGap = a.getDimension(
                    R.styleable.GradientView_stroke_dashGap, 0.0f);
            mBackgroundDrawable.setStroke(width, color, dashWidth, dashGap);
        } else {
            mBackgroundDrawable.setStroke(width, color);
        }
    }

    /**
     * 更新背景的填充颜色
     *
     * @param a 属性集合
     */
    private void updateBackgroundColor(TypedArray a) {
        // 填充颜色
        final int color = a.getColor(
                R.styleable.GradientView_solid_color, Color.TRANSPARENT);
        mBackgroundDrawable.setColor(color);
    }

    /**
     * 更新背景
     */
    private void updateBackground() {
        setBackground(mBackgroundDrawable);
    }

    /**
     * 设置Gradient的填充颜色
     *
     * @param color 填充颜色
     */
    public void setGradientColor(int color) {
        mBackgroundDrawable.setColor(color);
    }

    /**
     * 设置Gradient的填充颜色
     *
     * @param colors 填充颜色
     */
    public void setGradientColors(int[] colors) {
        mBackgroundDrawable.setColors(colors);
    }

    /**
     * 设置Gradient的描边
     *
     * @param strokeWidth 描边宽度
     * @param strokeColor 描边颜色
     */
    public void setGradientStroke(int strokeWidth, int strokeColor) {
        mBackgroundDrawable.setStroke(strokeWidth, strokeColor);
    }

    /**
     * 设置Gradient的描边
     *
     * @param strokeWidth 描边宽度
     * @param strokeColor 描边颜色
     * @param dashWidth   虚线宽度
     * @param dashGap     虚线间隙
     */
    public void setGradientStroke(int strokeWidth, int strokeColor,
                                  float dashWidth, float dashGap) {
        mBackgroundDrawable.setStroke(strokeWidth, strokeColor, dashWidth, dashGap);
    }

    /**
     * 设置Gradient四角的半径
     *
     * @param radius 半径
     */
    public void setGradientCornerRadius(float radius) {
        mBackgroundDrawable.setCornerRadius(radius);
    }

    /**
     * 设置Gradient四角的半径
     *
     * @param radii 半径
     */
    public void setGradientCornerRadii(float[] radii) {
        mBackgroundDrawable.setCornerRadii(radii);
    }

    /**
     * 设置触摸亮度
     *
     * @param touchLum 亮度
     */
    public void setTouchLum(int touchLum) {
        mTouchLum = touchLum;
        if (touchLum < 0 || touchLum > 100) {
            throw new IllegalArgumentException("touchLum can not be less than ZERO or greater than ONE-HUNDRED");
        }
    }

    /**
     * 获取触摸亮度
     *
     * @return 亮度
     */
    public int getTouchLum() {
        return mTouchLum;
    }

    /**
     * 根据触摸亮度设置颜色滤色片
     *
     * @param touchLum 亮度
     */
    private void setGradientColorFilter(int touchLum) {

        float lum = (touchLum - 50) * 2 * 255 * 0.01f;

        mBackgroundDrawable.setColorFilter(new ColorMatrixColorFilter(new float[]{
                1, 0, 0, 0, lum,
                0, 1, 0, 0, lum,
                0, 0, 1, 0, lum,
                0, 0, 0, 1, 0
        }));
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (!mClickable) {
            return true;
        }
        // 如父布局不为空且可点击，则父布局执行触摸事件
        if (getParent() != null) {
            View parent = (View) getParent();
            if (parent.isClickable()) {
                parent.onTouchEvent(event);
            }
        }
        // 响应触摸事件
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                setGradientColorFilter(getTouchLum());
                break;
            case MotionEvent.ACTION_UP:
                setGradientColorFilter(50);
                break;
        }
        return false;
    }

    @Override
    public void setClickable(boolean clickable) {
        super.setClickable(clickable);
        mClickable = clickable;
    }
}
