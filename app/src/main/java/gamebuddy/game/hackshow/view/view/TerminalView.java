package gamebuddy.game.hackshow.view.view;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

import gamebuddy.game.hackshow.R;

/**
 * describe
 * created by tindle
 * created time 16/2/6 上午9:26
 */
public class TerminalView extends FrameLayout{

    private View containerView;
    private ScrollView scrollView;
    private LinearLayout linearView;
    private TerminalTextView textView;
    private EditText editView;

    public TerminalView(Context context) {
        super(context);
        init();
    }

    public TerminalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TerminalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        containerView = inflate(this.getContext(), R.layout.layout_terminal, null);
        scrollView = (ScrollView)containerView.findViewById(R.id.scroll_view);
        linearView = (LinearLayout)containerView.findViewById(R.id.linear_view);
        textView = (TerminalTextView)containerView.findViewById(R.id.text_view);
        editView = (EditText)containerView.findViewById(R.id.edit_view);
        this.addView(containerView);

        initEvent();
    }

    public void initEvent() {

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startShow();
            }
        });

    }

    private List<String> testLines = new ArrayList<>();

    public void startShow() {

        if(testLines.size()==0){
            testLines.add("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            testLines.add("bbbbb");
            testLines.add("bbbbb");
            testLines.add("bbbbb");
            testLines.add("bbbbb");
            testLines.add("bbbbb");
            testLines.add("cccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc"
                    +"cccccccccccccccccc");
        }

        textView.setMovementMethod(ScrollingMovementMethod.getInstance());
        textView.reset();
        textView.animateText(testLines);
    }
}
