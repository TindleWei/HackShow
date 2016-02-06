package gamebuddy.game.hackshow.view.view;

import android.content.Context;
import android.graphics.Canvas;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * describe
 * created by tindle
 * created time 16/2/6 上午9:50
 */
public class TerminalTextView extends TextView {

    private String textToDraw = "";

    private List<List<String>> wrapperLines = new ArrayList<>();

    private List<String> textLines = new ArrayList<String>();

    private float lineMul = 1.3f;

    private TextPaint textPaint = new TextPaint();

    private int currentLength = 0;

    //最终显示的行数
    private int actualLines = 0;

    public TerminalTextView(Context context) {
        super(context);
        init();
    }

    public TerminalTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TerminalTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {

    }

    public void animateText(List<String> lines) {
        if (lines.size() == 0) return;

        textPaint.setAntiAlias(true);
        textPaint.setTextSize(this.getTextSize());

        wrapperLines.clear();
        for (int i = 0; i < lines.size(); i++) {
            textToDraw = lines.get(i);
            textLines = new ArrayList<>();
            while (textToDraw.length() > 0) {
                int size = textPaint.breakText(textToDraw, true, getMeasuredWidth(), null);
                textLines.add(textToDraw.substring(0, size));
                textToDraw = textToDraw.substring(size);
            }
            wrapperLines.add(textLines);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);

        if (wrapperLines.size() == 0) return;

        actualLines = 0;

        float x = 0;
        float y = textPaint.getTextSize();
        for (int i = 0; i < wrapperLines.size()-1; i++) {
            List<String> lines = wrapperLines.get(i);
            for (String text : lines) {
                canvas.drawText(text, x, y, textPaint);
                y = y + textPaint.getTextSize() * lineMul;
                actualLines++;
            }
        }

        drawLastLine(canvas, y);

        this.requestLayout();
        this.invalidate();
    }

    public void drawLastLine(Canvas canvas, float y) {
        if (wrapperLines.size() == 0) return;

        int x = 0;

        List<String> lastLines = wrapperLines.get(wrapperLines.size() - 1);
        for (int i = 0; i < lastLines.size(); i++) {
            int totalLength = 0;
            for (int j = 0; j <= i; j++) {
                totalLength += lastLines.get(j).length();
            }

            if (currentLength > totalLength) {
                canvas.drawText(lastLines.get(i), x, y, textPaint);
                y = y + textPaint.getTextSize() * lineMul;
                actualLines++;
            } else {
                int aboveLength = 0;
                for (int j = 0; j <= i - 1; j++) {
                    aboveLength += lastLines.get(j).length();
                }
                int l = currentLength - aboveLength;
                if (l < 0) {
                    break;
                }
                canvas.drawText(lastLines.get(i), 0, currentLength - aboveLength, x, y, textPaint);
                y = y + textPaint.getTextSize() * lineMul;
                actualLines++;
            }
        }

        int allLength = 0;
        for (String text : lastLines) {
            allLength += text.length();
        }

        if (currentLength < allLength) {
//            if (lastLine.charAt(currentLength) == ' ') {
//                postInvalidateDelayed(48);
//            } else {
//                postInvalidateDelayed(16);
//            }
//            postInvalidateDelayed(16);
            currentLength++;
        }
    }

    public void reset() {
        currentLength = 0;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = (int)(textPaint.getTextSize() * lineMul * actualLines);
        if(height<100){
            height=100;
        }
        Log.e("FLAG", "line: "+actualLines);
        setMeasuredDimension(width, height);

    }
}
