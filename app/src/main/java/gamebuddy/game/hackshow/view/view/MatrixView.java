package gamebuddy.game.hackshow.view.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * describe
 * created by tindle
 * created time 16/2/8 上午10:51
 */
public class MatrixView extends View {

    Paint textPaint2;
    Paint textPaint;
    String contentStr;
    boolean isAnimStart = false;
    private float lineMul = 1.0f;
    // Matrix字体的Rect的起始位置
    float beginRectX;
    float mProgress;
    int containerWidth;
    int containerHeight;

    public MatrixView(Context context) {
        super(context);
        init();
    }

    public MatrixView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MatrixView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        textPaint = new TextPaint();
        textPaint.setColor(Color.GREEN);

        int textDp = 16;
        float density = getContext().getResources().getDisplayMetrics().density;
        int textSize = (int) (textDp * density + 0.5f);

        textPaint.setTextSize(textSize);

        textPaint2 = new TextPaint();
        textPaint2.setColor(Color.WHITE);
        textPaint2.setTextSize(textSize);
    }

    public void setTextContent(String content) {
        contentStr = content;
    }

    public void animateStart(String content) {
        isAnimStart = true;

        this.contentStr = content;

        int screenWidth = getContext().getResources().getDisplayMetrics().widthPixels;

        // What the matrix text finally look like.
        // ----| | | | |---- //
        // ----| | | |---- //
        char[] chars = content.toCharArray();
        float textSize = textPaint.getTextSize();
        float textRectLength = (chars.length * 2 - 1) * textSize;

        beginRectX = (screenWidth - textRectLength) / 2;

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1, 0)
                .setDuration((long) 1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mProgress = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!isAnimStart) {
            return;
        }

        char[] chars = contentStr.toCharArray();
        for (int j = 0; j < chars.length; j++) {
            float y = containerHeight / 2 + textPaint.getTextSize() / 2 - mProgress * containerHeight / 2;
            float x = beginRectX + j * textPaint.getTextSize() * 2;

            canvas.drawText(String.valueOf(chars[j]).toUpperCase(), x, y, textPaint2);
            y = y - textPaint2.getTextSize() * lineMul;

            for (int i = 1; i < 10; i++) {
                textPaint.setAlpha(255 - 20 * i);
                canvas.drawText(randomLetter(), x, y, textPaint);
                y = y - textPaint.getTextSize() * lineMul;
            }
        }
        postInvalidateDelayed(32);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        containerWidth = MeasureSpec.getSize(widthMeasureSpec);
        containerHeight = MeasureSpec.getSize(heightMeasureSpec);

    }

    public String randomLetter() {
        //A:65 a:97
        int letterRandom = (int) (Math.random() * 26 + 65);
        char letter = (char) letterRandom;
        return String.valueOf(letter);
    }
}
