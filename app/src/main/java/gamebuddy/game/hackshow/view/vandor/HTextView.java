package gamebuddy.game.hackshow.view.vandor;

import android.content.Context;
import android.graphics.Canvas;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * describe
 * created by tindle
 * created time 16/1/27 下午6:08
 */
public class HTextView extends TextView {

    private IHText mIHText = new TyperText();
    private AttributeSet attrs;
    private int defStyle;

    public HTextView(Context context) {
        super(context);
        init(null, 0);
    }

    public HTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public HTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {

        this.attrs = attrs;
        this.defStyle = defStyle;

        mIHText.init(this, attrs, defStyle);
    }

    public void animateText(CharSequence text) {
        mIHText.animateText(text);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        // 方法1
//       // super.onDraw(canvas);  // note this is Important!!
//        mIHText.onDraw(canvas);

        // 方法2
        // new refer
        float x=0;
        float y=textPaint.getTextSize();
        for (int i=0;i<wrapperLines.size();i++){
            List<String> lines = wrapperLines.get(i);
            for(String text :lines){
                canvas.drawText(text,x,y,textPaint);
                y=y+textPaint.getTextSize()*lineMul;
            }
        }
        this.invalidate();

        myDraw(canvas, y);
    }

    // refer: https://github.com/sddyljsx/Android-TextView-not-change-line-in-advance/blob/master/TextDrawView.java

    private String textToDraw = "";

    private List<List<String>> wrapperLines = new ArrayList<>();

    private List<String> textLines = new ArrayList<String>();

    private float lineMul = 1.3f;
    TextPaint textPaint = new TextPaint();


    public void animateText(List<String> lines){
        if(lines.size()==0) return;

        textPaint.setAntiAlias(true);
        textPaint.setTextSize(this.getTextSize());

        wrapperLines.clear();
        for(int i=0; i<lines.size(); i++){
            textToDraw = lines.get(i);
            textLines = new ArrayList<>();
            while (textToDraw.length()>0) {
                int size = textPaint.breakText(textToDraw, true, getMeasuredWidth(), null);
                textLines.add(textToDraw.substring(0, size));
                textToDraw = textToDraw.substring(size);
            }
            wrapperLines.add(textLines);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

//        int width, height = 0;
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//        if (widthMode == MeasureSpec.EXACTLY) {
//            width = widthSize;
//        } else {
//            width = getMeasuredWidth();
//        }
//        if (heightMode == MeasureSpec.EXACTLY) {
//            height = heightSize;
//        } else {
//            for(int i=0;i<wrapperLines.size();i++){
//                textLines = wrapperLines.get(i);
//                height += textLines.size()*(int)(textPaint.getTextSize()*lineMul)+1;
//            }
////            height = textLines.size()*(int)(textPaint.getTextSize()*lineMul)+1;
//        }
//        setMeasuredDimension(width, height);
    }


    private int currentLength = 0;
    String lastLine = "";

    protected void myDraw(Canvas canvas, float y) {

        if(wrapperLines.size()==0) return;

        int x =0;

        List<String> lastLines = wrapperLines.get(wrapperLines.size()-1);
        for(int i=0; i<lastLines.size(); i++){
            int totalLength = 0;
            for (int j=0; j<=i; j++) {
                totalLength += lastLines.get(j).length();
            }

            if(currentLength>totalLength){
                canvas.drawText(lastLines.get(i),x,y,textPaint);
                y=y+textPaint.getTextSize()*lineMul;
            }else{
                int aboveLength =0;
                for (int j=0; j<=i-1; j++) {
                    aboveLength += lastLines.get(j).length();
                }
                int l = currentLength - aboveLength;
                if(l<0){
                    break;
                }
                canvas.drawText(lastLines.get(i), 0, currentLength-aboveLength, x, y, textPaint);
                y=y+textPaint.getTextSize()*lineMul;
            }
        }

        int allLength = 0;
        for(String text: lastLines){
            allLength += text.length();
        }

        if (currentLength < allLength) {
//            if (lastLine.charAt(currentLength) == ' ') {
//                postInvalidateDelayed(48);
//            } else {
//                postInvalidateDelayed(16);
//            }
            postInvalidateDelayed(16);
            currentLength++;
        }
    }


    //////////////////

    public void reset(){
        currentLength = 0;
        lastLine = "";
    }
}
