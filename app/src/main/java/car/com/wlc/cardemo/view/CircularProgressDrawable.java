/*
 * Copyright (C) 2014 Saúl Díaz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package car.com.wlc.cardemo.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Circular Progress Drawable.
 * <p/>
 * This drawable will produce a circular shape with a ring surrounding it. The ring can appear
 * both filled and give a little cue when it is empty.
 * <p/>
 * The inner circle size, the progress of the outer ring and if it is loading parameters can be
 * controlled, as well the different colors for the three components.
 *
 * @author Saul Diaz <sefford@gmail.com>
 */
public class CircularProgressDrawable extends Drawable {
    /**
     * Factor to convert the factor to paint the arc.
     * <p/>
     * In this way the developer can use a more user-friendly [0..1f] progress
     */
    public static final int PROGRESS_FACTOR = -360;
    /**
     * Property Inner Circle Scale.
     * <p/>
     * The inner ring is supposed to defaults stay 3/4 radius off the outer ring at (75% scale), but this
     * property can make it grow or shrink via this equation: OuterRadius * Scale.
     * <p/>
     * A 100% scale will make the inner circle to be the same radius as the outer ring.
     */
    public static final String CIRCLE_SCALE_PROPERTY = "circleScale";
    /**
     * Property Progress of the outer circle.
     * <p/>
     * The progress of the circle. If {@link #setIndeterminate(boolean) indeterminate flag} is set
     * to FALSE, this property will be used to indicate the completion of the outer circle [0..1f].
     * <p/>
     * If set to TRUE, the drawable will activate the loading mode, where the drawable will
     * show a 90º arc which will be spinning around the outer circle as much as progress goes.
     */
    public static final String PROGRESS_PROPERTY = "progress";
    /**
     * Property Ring color.
     * <p/>
     * Changes the ring filling color
     */
    public static final String RING_COLOR_PROPERTY = "ringColor";
    /**
     * Property circle color.
     * <p/>
     * Changes the inner circle color
     */
    public static final String CIRCLE_COLOR_PROPERTY = "centerColor";
    /**
     * Property outline color.
     * <p/>
     * Changes the outline of the ring color.
     */
    public static final String OUTLINE_COLOR_PROPERTY = "outlineColor";
    /**
     * Logger Tag for Logging purposes.
     */
    public static final String TAG = "CircularProgressDrawable";
    /**
     * Paint object to draw the element.
     */
    private final Paint paint;

    /**
     * Ring progress.
     */
    protected float progress;
    /**
     * Color for the empty outer ring.
     */
    protected int outlineColor;
    /**
     * Color for the completed ring.
     */
    protected int ringColor;
    /**
     * Color for the inner circle.
     */
    protected int centerColor;
    /**
     * Rectangle where the filling ring will be drawn into.
     */
    protected final RectF arcElements;
    /**
     * Width of the filling ring.
     */
    protected final int ringWidth=4;
    /**
     * Scale of the inner circle. It will affect the inner circle size on this equation:
     * ([Biggest length of the Drawable] / 2) - (ringWidth / 2) * scale.
     */
    protected float circleScale;
    /**
     * Set if it is an indeterminate
     */
    protected boolean indeterminate;
    private int mBorderWidth=12;
    private int mBorderColor;
    private  float currentProgress;
    /**
     * Creates a new CouponDrawable.
     *
     * @param ringWidth    Width of the filled ring
     * @param circleScale  Scale difference between the outer ring and the inner circle
     * @param outlineColor Color for the outline color
     * @param ringColor    Color for the filled ring
     * @param centerColor  Color for the center element
     */
    CircularProgressDrawable(int ringWidth, float circleScale, int outlineColor, int ringColor, int centerColor,int mBorderColor,int mBorderWidth) {
        this.progress = 0;
        this.outlineColor = outlineColor;
        this.ringColor = ringColor;
        this.centerColor = centerColor;
        this.mBorderColor=mBorderColor;
        this.mBorderWidth=mBorderWidth;
        this.paint = new Paint();

        this.paint.setAntiAlias(true);
        this.arcElements = new RectF();
        this.circleScale = circleScale;
        this.indeterminate = false;

    }

    @Override
    public void draw(Canvas canvas) {
        final Rect bounds = getBounds();

        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(mBorderColor);
        paint.setStrokeWidth(mBorderWidth);
//         Calculations on the different components sizes
        int size = Math.min(bounds.height(), bounds.width());
        float outerRadius = (size / 2) - (mBorderWidth / 2);
        float innerRadius = outerRadius -mBorderWidth/2;
        float innerRadiusRrc=innerRadius*0.85f;
        float offsetX = (bounds.width() - innerRadiusRrc * 2) / 2;
        float offsetY = (bounds.height() - innerRadiusRrc * 2) / 2;

        canvas.drawCircle(bounds.centerX(), bounds.centerY(),
                outerRadius, paint);


        // Inner circle
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(centerColor);
        canvas.drawCircle(bounds.centerX(), bounds.centerY(), innerRadius, paint);


        int halfRingWidth = ringWidth / 2;
        float arcX0 =offsetX + halfRingWidth;
        float arcY0 = offsetY + halfRingWidth;
        float arcX = offsetX + innerRadiusRrc * 2 - halfRingWidth;
        float arcY = offsetY + innerRadiusRrc * 2 - halfRingWidth;
        // Outline Circle
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(ringWidth);
        paint.setColor(outlineColor);
        canvas.drawCircle(bounds.centerX(), bounds.centerY(), innerRadiusRrc, paint);
        // Outer Circle
        paint.setColor(ringColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(ringWidth);
        paint.setStrokeCap(Paint.Cap.ROUND);
        arcElements.set(arcX0, arcY0, arcX, arcY);
        if (indeterminate) {
            canvas.drawArc(arcElements,  progress,-90, false, paint);
        } else {
            canvas.drawArc(arcElements, -90,progress, false, paint);
        }
    }

    public Animator prepareAnimation() {
        AnimatorSet animation = new AnimatorSet();

        ObjectAnimator progressAnimation = ObjectAnimator.ofFloat(this, PROGRESS_PROPERTY,
                0f, currentProgress);
        progressAnimation.setDuration(3600);
        progressAnimation.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator colorAnimator = ObjectAnimator.ofInt(this,RING_COLOR_PROPERTY,
                ringColor,
                ringColor);
        colorAnimator.setEvaluator(new ArgbEvaluator());
        colorAnimator.setDuration(3600);

        animation.playTogether(progressAnimation, colorAnimator);
        return animation;
    }
    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        paint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return 1-paint.getAlpha();
    }

    public void setCurrentProgress(float i){
        this.currentProgress=i;
    }
    /**
     * Returns the progress of the outer ring.
     * <p/>
     * Will output a correct value only when the indeterminate mode is set to FALSE.
     *
     * @return Progress of the outer ring.
     */
    public float getProgress() {
        return progress / PROGRESS_FACTOR;
    }

    /**
     * Sets the progress [0..1f]
     *
     * @param progress Sets the progress
     */
    public void setProgress(float progress) {
        if (indeterminate) {
            this.progress = progress;
        } else {
            this.progress = PROGRESS_FACTOR * progress;
        }
        invalidateSelf();
    }

    /**
     * Returns the inner circle scale.
     *
     * @return Inner circle scale in float multiplier.
     */
    public float getCircleScale() {
        return circleScale;
    }

    /**
     * Sets the inner circle scale.
     *
     * @param circleScale Inner circle scale.
     */
    public void setCircleScale(float circleScale) {
        this.circleScale = circleScale;
        invalidateSelf();
    }

    /**
     * Get the indeterminate status of the Drawable
     *
     * @return TRUE if the Drawable is in indeterminate mode or FALSE if it is in progress mode.
     */
    public boolean isIndeterminate() {
        return indeterminate;
    }

    /**
     * Sets the indeterminate parameter.
     * <p/>
     * The indeterminate parameter will change the behavior of the Drawable. If the indeterminate
     * mode is set to FALSE, the outer ring will be able to be filled by using {@link #setProgress(float) setProgress}.
     * <p/>
     * Otherwise the drawable will enter "loading mode" and a 90º arc will be able to be spinned around
     * the inner circle.
     * <p/>
     * <b>By default, indeterminate mode is set to FALSE.</b>
     *
     * @param indeterminate TRUE to activate loading mode. FALSE to activate progress mode.
     */
    public void setIndeterminate(boolean indeterminate) {
        this.indeterminate = indeterminate;
    }

    /**
     * Gets the outline color.
     *
     * @return Outline color of the empty ring.
     */
    public int getOutlineColor() {
        return outlineColor;
    }

    /**
     * Gets the filled ring color.
     *
     * @return Returns the filled ring color.
     */
    public int getRingColor() {
        return ringColor;
    }

    /**
     * Gets the color of the inner circle.
     *
     * @return Inner circle color.
     */
    public int getCenterColor() {
        return centerColor;
    }

    /**
     * Sets the empty progress outline color.
     *
     * @param outlineColor Outline color in #AARRGGBB format.
     */
    public void setOutlineColor(int outlineColor) {
        this.outlineColor = outlineColor;
        invalidateSelf();
    }

    /**
     * Sets the progress ring  color.
     *
     * @param ringColor Ring color in #AARRGGBB format.
     */
    public void setRingColor(int ringColor) {
        this.ringColor = ringColor;
        invalidateSelf();
    }

    /**
     * Sets the inner circle color.
     *
     * @param centerColor Inner circle color in #AARRGGBB format.
     */
    public void setCenterColor(int centerColor) {
        this.centerColor = centerColor;
        invalidateSelf();
    }


    /**
     * Helper class to manage the creation of a CircularProgressDrawable
     *
     * @author Saul Diaz <sefford@gmail.com>
     */
    public static class Builder {

        int mBorderColor;
        /**
         * Witdh of the stroke of the filled ring
         */
        int ringWidth;
        int broderWidth;
        /**
         * Color of the outline of the empty ring in #AARRGGBB mode.
         */

        protected int outlineColor= Color.parseColor("#FFD6D4D4");
        /**
         * Color for the completed ring.
         */
        protected int ringColor=Color.parseColor("#ffffffff");
        /**
        /**
         * Color of the inner circle in #AARRGGBB mode.
         */
        int centerColor;
        /**
         * Scale between the outer ring and the inner circle
         */
        float circleScale = 1f;
        float  currentProgress;


        /**
         * Sets the ring width.
         *
         * @param ringWidth Default ring width
         * @return This builder
         */
        public Builder setRingWidth(int ringWidth) {
            this.ringWidth = ringWidth;
            return this;
        }

        public Builder setBroderWidth(int broderWidth) {
            this.broderWidth = broderWidth;
            return this;
        }
        /**
         * Sets the default empty outer ring outline color.
         *
         * @param outlineColor Outline color in #AARRGGBB format.
         * @return
         */
        public Builder setOutlineColor(int outlineColor) {
            this.outlineColor = outlineColor;
            return this;
        }

        /**
         * Sets the progress ring color.
         *
         * @param ringColor Ring color in #AARRGGBB format.
         * @returns This Builder
         */
        public Builder setRingColor(int ringColor) {
            this.ringColor = ringColor;
            return this;
        }


        /**
         * Sets the inner circle color.
         *
         * @param centerColor Inner circle color in #AARRGGBB format.
         * @return This builder
         */
        public Builder setCenterColor(int centerColor) {
            this.centerColor = centerColor;
            return this;
        }

        /**
         * Sets the inner circle scale. Defaults to 0.75.
         *
         * @param circleScale Inner circle scale.
         * @return This builder
         */
        public Builder setInnerCircleScale(float circleScale) {
            this.circleScale = circleScale;
            return this;
        }
        public Builder setBroderColor(int mBorderColor) {
            this.mBorderColor = mBorderColor;
            return this;
        }
        /**
         * Creates a new CircularProgressDrawable with the requested parameters
         *
         * @return New CircularProgressDrawableInstance
         */
        public CircularProgressDrawable create() {
            return new CircularProgressDrawable(ringWidth, circleScale, outlineColor, ringColor, centerColor,mBorderColor,broderWidth);
        }

    }
}
