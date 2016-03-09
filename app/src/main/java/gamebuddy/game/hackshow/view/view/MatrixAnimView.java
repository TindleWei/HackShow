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
 * 像黑客帝国一样的初始动画视图
 * @author Tindle
 * @version 1.0.0
 */
public class MatrixAnimView extends View {

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

    // 动画集合  //下标小于chars.length的是字符Line,下标大于chars.length的是无字符Line
    List<ValueAnimator> animatorList;
    List<MatrixLineEntity> matrixList;

    int totalLineNum;
    float totalStartOffset;

    public MatrixAnimView(Context context) {
        super(context);
        init();
    }

    public MatrixAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MatrixAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        int textDp = 20;
        float density = getContext().getResources().getDisplayMetrics().density;
        int textSize = (int) (textDp * density + 0.5f);

        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setARGB(255, 102, 160, 0);
        textPaint.setShadowLayer(20, 0, 0, 0xff99cc00);
        textPaint.setTextSize(textSize);

        textPaint2 = new TextPaint();
        textPaint2.setAntiAlias(true);
        textPaint2.setARGB(250, 250, 250, 250);
        textPaint2.setTextSize(textSize);
        textPaint2.setShadowLayer(20, 0, 0, 0xffffffff);
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
        //  ----| | | |----  //
        char[] chars = content.toCharArray();
        float textSize = textPaint.getTextSize();
        float textRectLength = (chars.length * 2 - 1) * textSize;

        beginRectX = (screenWidth - textRectLength) / 2;

        animatorList = new ArrayList<>();
        matrixList = new ArrayList<>();

        //计算屏幕可以放置的列数, 奇数个字符和偶数个字符的计算方法不一样
        if (content.length() % 2 == 0) {
            totalLineNum = (int) (screenWidth / textSize);
            totalStartOffset = (screenWidth - textSize * totalLineNum) / 2;
        } else {
            totalLineNum = ((int) ((screenWidth / 2 - textSize / 2) / textSize)) * 2 + 1;
            totalStartOffset = (screenWidth - textSize * totalLineNum) / 2;
        }

        for (int i = 0; i < chars.length + totalLineNum; i++) {
            final int index = i;
            final MatrixLineEntity matrixLine = new MatrixLineEntity();
            matrixLine.progress = 1f;
            matrixLine.totalNum = 10;
            matrixList.add(matrixLine);

            ValueAnimator valueAnimator2 = ValueAnimator.ofFloat(1, 0)
                    .setDuration(i < chars.length ? (long) 1000 : (long) 2000);
            valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    if (index > matrixList.size() - 1) return;
                    matrixList.get(index).progress = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            int randomDelay = i < chars.length ? (int) (Math.random() * 2000 + 800) : ((int) (Math.random() * 2000));
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
        for (int j = 0; j < chars.length + totalLineNum; j++) {
            MatrixLineEntity matrixLine = matrixList.get(j);

            if (matrixLine.progress == 1) {
                continue;
            }

            float y = 0;
            float x = 0;
            if (j < chars.length) {
                y = containerHeight / 2 - textPaint.getTextSize() / 2 - matrixLine.progress * containerHeight / 2;
                x = beginRectX + j * textPaint.getTextSize() * 2;

                if (matrixLine.progress == 0) {
                    if (matrixLine.totalNum > 0) {
                        matrixLine.totalNum--;
                    }
                }

                if (matrixLine.totalNum <= 0) {
                    canvas.drawText(String.valueOf(chars[j % 8]).toUpperCase(), x, y, textPaint2);
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
            } else {
                y = containerHeight + textPaint.getTextSize() / 2 - matrixLine.progress * containerHeight;
                x = totalStartOffset + (j - chars.length) * textPaint2.getTextSize();

                if (matrixLine.progress == 0) {
                    if (matrixLine.totalNum > 0) {
                        matrixLine.totalNum--;
                    }
                }

                for (int i = 0; i < matrixLine.totalNum; i++) {
                    textPaint.setAlpha(255 - 20 * i);
                    canvas.drawText(randomLetter(), x, y, textPaint);
                    y = y - textPaint.getTextSize() * lineMul;
                }
            }


        }
        postInvalidateDelayed(64);
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
