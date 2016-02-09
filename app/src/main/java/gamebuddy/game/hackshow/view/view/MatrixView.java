package gamebuddy.game.hackshow.view.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * describe
 * created by tindle
 * created time 16/2/8 上午10:51
 */
public class MatrixView extends View {

    // 一条数据链对象
    public class MatrixLineEntity {
        public float progress;
        public int totalNum;
    }

    Paint textPaint2;
    Paint textPaint;
    String contentStr;
    boolean isAnimStart = false;
    private float lineMul = 1.0f;
    // Matrix字体的Rect的起始位置
    float beginRectX;
    int containerWidth;
    int containerHeight;

    // 动画集合
    List<ValueAnimator> animatorList;
    List<MatrixLineEntity> matrixList;

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
        textPaint.setARGB(255, 102, 160, 0);

        int textDp = 16;
        float density = getContext().getResources().getDisplayMetrics().density;
        int textSize = (int) (textDp * density + 0.5f);

        textPaint.setTextSize(textSize);

        textPaint2 = new TextPaint();
        textPaint2.setARGB(250, 250, 250, 250);
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

        animatorList = new ArrayList<>();
        matrixList = new ArrayList<>();

        for (int i = 0; i < chars.length; i++) {
            final int index = i;
            final MatrixLineEntity matrixLine = new MatrixLineEntity();
            matrixLine.progress = 1f;
            matrixLine.totalNum = 10;
            matrixList.add(matrixLine);

            ValueAnimator valueAnimator2 = ValueAnimator.ofFloat(1, 0)
                    .setDuration((long) 1000);
            valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    if (index > matrixList.size() - 1) return;
                    matrixList.get(index).progress = (float) animation.getAnimatedValue();
                }
            });
            int randomDelay = (int) (Math.random() * 2000);
            valueAnimator2.setStartDelay(randomDelay);
            valueAnimator2.start();
        }

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!isAnimStart) {
            return;
        }

        char[] chars = contentStr.toCharArray();
        for (int j = 0; j < chars.length; j++) {
            MatrixLineEntity matrixLine = matrixList.get(j);

            float y = containerHeight / 2 - textPaint.getTextSize() / 2 - matrixLine.progress * containerHeight / 2;
            float x = beginRectX + j * textPaint.getTextSize() * 2;

            if (matrixLine.progress == 0) {
                if (matrixLine.totalNum > 0) {
                    matrixLine.totalNum--;
                }
            }

            if (matrixLine.totalNum <= 0) {
                canvas.drawText(String.valueOf(chars[j]).toUpperCase(), x, y, textPaint2);
                y = y - textPaint2.getTextSize() * lineMul;
            } else {
                canvas.drawText(randomLetter(), x, y, textPaint2);
                y = y - textPaint2.getTextSize() * lineMul;
            }

            for (int i = 1; i < matrixLine.totalNum; i++) {
                textPaint.setAlpha(255 - 20 * i);
                canvas.drawText(randomLetter(), x, y, textPaint);
                y = y - textPaint.getTextSize() * lineMul;
            }
        }
        postInvalidateDelayed(32);

        drawFullMatrixLine(canvas);
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

    public void drawFullMatrixLine(Canvas canvas) {

    }
}
