package gamebuddy.game.hackshow.view.view;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * describe
 * created by tindle
 * created time 16/2/16 下午4:11
 */
public class StarwarView extends TextView {

    /**
     * Epsilon used to determine if two float values are equal
     */
    private static final float FLOAT_EPSILON = 0.001f;

    private float mScrollPosition = 0f;

    private TextPaint mTextPaint;

    public StarwarView(Context context) {
        super(context);
        init(context);
    }

    public StarwarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public StarwarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(getTextSize());
        mTextPaint.setColor(getCurrentTextColor());
        //set shadow
        // 设定阴影(柔边, X 轴位移, Y 轴位移, 阴影颜色)
        // mTextPaint.setShadowLayer(20, 1, 1, 0xFF669900);

        this.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    public void setScrollPosition(float scrollPosition) {
        if (scrollPosition < 0f) {
            scrollPosition = 0f;
        } else if (scrollPosition > 1f) {
            scrollPosition = 1f;
        }

        // Only invalidate if really necessary
        if (Math.abs(mScrollPosition - scrollPosition) > FLOAT_EPSILON) {
            mScrollPosition = scrollPosition;
            invalidate();
        }
    }

    private final Camera mCamera = new Camera();
    private Matrix mMatrix = new Matrix();
    private StaticLayout mTextLayout;

    @Override
    protected void onDraw(Canvas canvas) {
        final CharSequence text = getText();
        if (TextUtils.isEmpty(text)) {
            return;
        }
        int contentWidth = getWidth();
        int contentHeight = getHeight();

        final int saveCnt = canvas.save();

        canvas.getMatrix();
        mCamera.save();

        int cX = contentWidth / 2;
        int cY = contentHeight / 2;

        mCamera.rotateZ(30f);
        mCamera.translate(0, mTextLayout.getHeight(), 200f);
        mCamera.getMatrix(mMatrix);
        mMatrix.preTranslate(-cX, -cY);
        mMatrix.postTranslate(cX, cY);
        mCamera.restore();

        canvas.concat(mMatrix);
        canvas.translate(0f,   mScrollPosition * (mTextLayout.getHeight()));

        mTextLayout.draw(canvas);
        canvas.restoreToCount(saveCnt);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        final CharSequence text = getText();
        measureAndLayoutText(text);
    }

    private void measureAndLayoutText(CharSequence text) {
        if(TextUtils.isEmpty(text)){
            mTextLayout = null;
            return;
        }
        int availableWidth = getWidth();
        mTextLayout = new StaticLayout(text, mTextPaint, availableWidth, Layout.Alignment.ALIGN_CENTER, 1.1f, 0f, true);
    }
}
