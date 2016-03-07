package gamebuddy.game.hackshow.view.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 终端命令行的文字显示器
 * <p>尽管继承自TextView,但是文字是canvas draw上去的<br>
 *
 * @author Tindle
 * @version 1.0.0
 */
public class TerminalTextView extends TextView {

    /*文字的展现方式，有两种*/
    public static final int DISPLAY_A_WHOLE_LINE = 0;
    public static final int DISPLAY_LETTER_AFTER_LETTER = 1;
    //指上面的Type
    private int displayLineType = 0;

    // 画笔及一些属性
    private Paint textPaint;
    private float lineMul = 1.3f;

    // 用户输入文字的接收集合,用来存储大行(没有拆分成屏幕宽度的字符串)
    private List<String> largeLines = new ArrayList<String>();
    // 文字拆分后的大行集合,没个大行中有若干小行
    private List<List<String>> wrapperLines = new ArrayList<>();

    //末行的当前显示字符的索引
    private int currentLength = 0;

    //正在显示小行的最大行数 用来确定view的高度
    private int smallLineIndex = 0;
    //最终显示的行数 的上一次记录
    private int oldSmallLineIndex = 0;

    // 正在绘制的大行的索引
    private int largeLineIndex = 0;
    private boolean isShowing = false;

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
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(this.getTextSize());
        textPaint.setColor(this.getCurrentTextColor());
        textPaint.setShadowLayer(10, 0, 0, 0xff99cc00);
    }

    /**
     * Method:  将文字插入缓存中<br>
     * Description:  <br>
     * Creator:  <br>
     * Date:
     */
    public void insertTextLine(String line) {
        // largeLines.add(line);
        String textToDraw = line;
        List<String> newLines = new ArrayList<>();
        while (textToDraw.length() > 0) {
            int size = textPaint.breakText(textToDraw, true, getMeasuredWidth(), null);
            newLines.add(textToDraw.substring(0, size));
            textToDraw = textToDraw.substring(size);
        }
        wrapperLines.add(newLines);
    }

    /**
     * Method:  重新录入全部数据<br>
     * Description:  <br>
     * Creator:  <br>
     * Date:
     */
    public void resetTextLine(List<String> lines) {
        if (lines.size() == 0) return;
        // 填充数据
        wrapperLines.clear();
        for (int i = 0; i < lines.size(); i++) {
            String textToDraw = lines.get(i);
            List<String> newLines = new ArrayList<>();
            while (textToDraw.length() > 0) {
                int size = textPaint.breakText(textToDraw, true, getMeasuredWidth(), null);
                newLines.add(textToDraw.substring(0, size));
                textToDraw = textToDraw.substring(size);
            }
            wrapperLines.add(newLines);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 阻止TextView本身的绘制
        // super.onDraw(canvas);
        if (wrapperLines.size() == 0)
            return;

//        if (largeLineIndex >= wrapperLines.size()){
//            largeLineIndex = wrapperLines.size()-1;
//        }

        smallLineIndex = 0;

        float x = 0;
        float y = textPaint.getTextSize();

        // 绘制正在显示大行之前的所有行
        for (int i = 0; i < largeLineIndex; i++) {
            List<String> lines = wrapperLines.get(i);
            for (String text : lines) {
                canvas.drawText(text, x, y, textPaint);
                y = y + textPaint.getTextSize() * lineMul;
                smallLineIndex++;
            }
        }

        // 绘制最新正在显示的大行
        drawNowLine(canvas, y);

        // @hi 当显示完所有行,就不用重绘高度了， 考虑用showingIndexLine代替
        if (smallLineIndex != 0 && smallLineIndex != oldSmallLineIndex) {
            oldSmallLineIndex = smallLineIndex;
            this.requestLayout();
        } else {
            // 行数不变，不重新绘制高度
            return;
        }
    }

    public void drawNowLine(Canvas canvas, float y) {

        if(largeLineIndex>=wrapperLines.size()){
            return;
        }

        int x = 0;
        //正在显示大行的所有小行
        List<String> nowLines = wrapperLines.get(largeLineIndex);

        for (int i = 0; i < nowLines.size(); i++) {

            // 该大行正在显示小行以及之前小行的所有文字个数
            int totalLength = 0;
            for (int j = 0; j <= i; j++) {
                totalLength += nowLines.get(j).length();
            }

            if (currentLength > totalLength) {
                //该行已经显示完了
                canvas.drawText(nowLines.get(i), x, y, textPaint);
                y = y + textPaint.getTextSize() * lineMul;
                smallLineIndex++;
            } else {
                //该行还没有显示完
                int aboveLength = 0;
                for (int j = 0; j <= i - 1; j++) {
                    aboveLength += nowLines.get(j).length();
                }
                int distance = currentLength - aboveLength;
                if (distance < 0) break;

                canvas.drawText(nowLines.get(i), 0, distance, x, y, textPaint);
                y = y + textPaint.getTextSize() * lineMul;
                smallLineIndex++;
                break;//跳出for循环
            }
        }

        // 当前大行的文字总长度
        int allLength = 0;
        for (String text : nowLines) {
            allLength += text.length();
        }

        // 判断大行是否显示完成
        if (currentLength < allLength) {
            currentLength++;
            this.postInvalidateDelayed(40);
        } else if (currentLength == allLength) {
            currentLength = 0;
            largeLineIndex++;
            if(largeLineIndex>=wrapperLines.size()){
                //已经结束所有显示，回调结束接口
                if (displayCallback != null) {
                    displayCallback.onEndDisplayed();
                }
            } else {
                this.postInvalidateDelayed(1000);
            }
        }
    }

    public void reset() {
        currentLength = 0;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = (int) (this.getTextSize() * lineMul * smallLineIndex);
        if (height < 100) {
            height = 100;
        }

        Log.e("FLAG", "line: " + smallLineIndex);
        setMeasuredDimension(width, height);
    }

    public interface DisplayCallback {
        void onEndDisplayed();
    }

    public DisplayCallback displayCallback;

    public void setDisplayCallback(DisplayCallback callback) {
        this.displayCallback = callback;
    }
}
