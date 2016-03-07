package gamebuddy.game.hackshow.view.view;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.view.View;
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

//    public static final int CHECKER_TYPE_LOGIN = 0;
//    public static final int CHECKER_TYPE_COMMAND = 1;
    private int mCheckerType = 1;

    private View containerView;
    private ScrollView scrollView;
    private LinearLayout linearView;
    private TerminalTextView textView;
    private TerminalEditView editView;

    private List<String> testLines = new ArrayList<>();

//    CommandChecker terminalChecker;

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

    public void initWithType(int type){
        mCheckerType = type;
        init();
    }

    private void init(){
        containerView = inflate(this.getContext(), R.layout.layout_terminal, null);
        scrollView = (ScrollView)containerView.findViewById(R.id.scroll_view);
        linearView = (LinearLayout)containerView.findViewById(R.id.linear_view);
        textView = (TerminalTextView)containerView.findViewById(R.id.text_view);
        editView = (TerminalEditView)containerView.findViewById(R.id.edit_view);

        this.addView(containerView);

//        terminalChecker = new CommandChecker(new CommandChecker.Callback() {
//            @Override
//            public void onBackResult(String result) {
//                testLines.add(result);
//                startShow();
//                textView.invalidate();
//            }
//        });

        initEvent();
    }

    public void initEvent() {

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startShow();
            }
        });

//        editView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//
//                if (actionId == EditorInfo.IME_ACTION_DONE) {
//
//                    String content = v.getText().toString().trim();
//                    if (content.equals("")) {
//                        return true;
//                    } else {
//                        testLines.add(content);
//                        v.setText("");
//                        startShow();
//
//                        terminalChecker.startCheck(content);
//                    }
//                }
//                return true;
//            }
//        });

    }

    public void startShow() {

        textView.setMovementMethod(ScrollingMovementMethod.getInstance());
        textView.reset();
        textView.resetTextLine(testLines);
    }

    public void injectTextLine(String line){
//        testLines.add(line);
        textView.reset();
        textView.insertTextLine(line);
//        textView.resetTextLine(testLines);
        textView.invalidate();
    }


    public void setTextCallback(TerminalTextView.DisplayCallback callback){
        textView.setDisplayCallback(callback);
    }

    public void setEditCallback(TerminalEditView.EditCallback callback){
        editView.setEditCallback(callback);
    }

}
