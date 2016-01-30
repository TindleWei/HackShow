package gamebuddy.game.hackshow.view.vandor.deprecated;

import android.graphics.Paint;

/**
 * describe
 * created by tindle
 * created time 16/1/15 下午6:35
 */
public class TyperText2 {

    protected Paint mPaint;

    protected float[] gaps = new float[100];

    protected float mTextSize;

    protected String[] mLines;

    protected float startX    = 0; // 新的字符串开始画的x位置
    protected float startY    = 0; // 字符串开始画的y, baseline

    protected WTextView mWTextView;

    public void init(WTextView wTextView) {

        mWTextView = wTextView;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mWTextView.getCurrentTextColor());
        mPaint.setStyle(Paint.Style.FILL);
    }

    public void animateText(String[] lines) {
        StringBuffer b = new StringBuffer();
        for(int i=0; i<lines.length;i++) {
            b.append(lines[i]);
            b.append("\n");
        }
        mWTextView.setText(b.toString());

        mLines = lines;

        prepareAnimate();
    }

    private void prepareAnimate() {
        mTextSize = mWTextView.getTextSize();

        mPaint.setTextSize(mTextSize);
//        for()
    }

}
