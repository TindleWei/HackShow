package gamebuddy.game.hackshow.view.vandor;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;

/**
 * describe
 * created by tindle
 * created time 16/1/27 下午6:17
 */
public abstract class HText implements IHText {

    protected Paint mPaint, mOldPaint;

    protected float[] gaps = new float[1024];
    protected float[] oldGaps = new float[1024];

    protected float mTextSize;

    protected CharSequence mText;
    protected CharSequence mOldText;

    protected List<CharacterDiffResult> differentList = new ArrayList<>();

    protected float oldStartX = 0;
    protected float startX = 0;
    protected float startY = 0;

    protected HTextView mHTextView;

    @Override
    public void init(HTextView hTextView, AttributeSet attrs, int defStyle) {

        mHTextView = hTextView;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mHTextView.getCurrentTextColor());
        mPaint.setStyle(Paint.Style.FILL);

        mOldPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mOldPaint.setColor(mHTextView.getCurrentTextColor());
        mOldPaint.setStyle(Paint.Style.FILL);

        mText = mHTextView.getText();
        mOldText = mHTextView.getText();

        mTextSize = mHTextView.getTextSize();

        myInit();  //I
    }

    @Override
    public void animateText(CharSequence text) {
        mHTextView.setText(text);
        mOldText = mText;
        mText = text;
        prepareAnimate();
        animatePrepare(text); //I
        animateStart(text);  //I
    }

    @Override
    public void onDraw(Canvas canvas) {
        myDraw(canvas);  //I
    }

    private void prepareAnimate() {
        mTextSize = mHTextView.getTextSize();

        mPaint.setTextSize(mTextSize);
        for (int i = 0; i < mText.length(); i++) {
            gaps[i] = mPaint.measureText(mText.charAt(i) + "");
        }

        mOldPaint.setTextSize(mTextSize);
        for (int i = 0; i < mOldText.length(); i++) {
            oldGaps[i] = mOldPaint.measureText(mOldText.charAt(i) + "");
        }

//        oldStartX = (mHTextView.getMeasuredWidth() - mHTextView.getCompoundPaddingLeft() - mHTextView.getPaddingLeft() - mOldPaint
//            .measureText(mOldText.toString())) / 2f;
//        startX = (mHTextView.getMeasuredWidth() - mHTextView.getCompoundPaddingLeft() - mHTextView.getPaddingLeft() - mPaint
//            .measureText(mText.toString())) / 2f;
//        startY = mHTextView.getBaseline();

        oldStartX = 0;
        startX = 0;
        startY = mHTextView.getBaseline();

        differentList.clear();
        differentList.addAll(CharacterUtils.diff(mOldText, mText));
    }

    public void reset(CharSequence text) {
        animateText(text);
        mHTextView.invalidate();
    }

    /**
     * 类被实例化时初始化
     */
    protected abstract void myInit();
    /**
     * 具体实现动画
     * @param text
     */
    protected abstract void animateStart(CharSequence text);

    /**
     * 每次动画前初始化调用
     * @param text
     */
    protected abstract void animatePrepare(CharSequence text);

    /**
     * 动画每次刷新界面时调用
     * @param canvas
     */
    protected abstract void myDraw(Canvas canvas);

}
