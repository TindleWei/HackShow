package gamebuddy.game.hackshow.view.vandor.deprecated;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * describe
 * created by tindle
 * created time 16/1/15 下午6:00
 */
public class WTextView extends TextView{

    private int currentLength;
    private int currentLine;

    public WTextView(Context context) {
        super(context);
    }

    public WTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(){
        String[] a = "sdfs sdf".split(" ");
    }

    public void animateText(String[] lines){

    }
}
