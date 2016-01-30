package gamebuddy.game.hackshow.view.vandor;

import android.graphics.Canvas;
import android.util.AttributeSet;

/**
 * describe
 * created by tindle
 * created time 16/1/27 下午6:10
 */
public interface IHText {
    void init(HTextView hTextView, AttributeSet attrs, int defStyle);
    void animateText(CharSequence text);
    void onDraw(Canvas canvas);
    void reset(CharSequence text);
}
