package gamebuddy.game.hackshow.view.vandor;

import android.graphics.Canvas;

/**
 * describe
 * created by tindle
 * created time 16/1/27 下午6:16
 */
public class TyperText extends HText {
    private int currentLength;

    int x = 0, y = 0;

    @Override
    protected void myInit() {

    }

    @Override
    protected void animateStart(CharSequence text) {
        currentLength = 0;
        mHTextView.invalidate();
    }

    @Override
    protected void animatePrepare(CharSequence text) {

    }

    String lastLine = "";

    @Override
    protected void myDraw(Canvas canvas) {

        y = 60;

        String lines[] = mText.toString().split("\n");
        for(int i=0; i<lines.length-1; i++){
            canvas.drawText(lines[i], x, y, mPaint);
            y += mPaint.descent() - mPaint.ascent();
        }
        lastLine = lines[lines.length-1];


        canvas.drawText(lastLine, 0, currentLength, startX, y, mPaint);


        if (currentLength < lastLine.length()) {
            if(lastLine.charAt(currentLength)==' '){
                mHTextView.postInvalidateDelayed(48);
            }else {
                mHTextView.postInvalidateDelayed(16);
            }
            currentLength++;
        }
    }
}
