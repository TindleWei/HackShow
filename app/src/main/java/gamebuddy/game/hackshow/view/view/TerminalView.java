package gamebuddy.game.hackshow.view.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import gamebuddy.game.hackshow.R;
import gamebuddy.game.hackshow.core.check.TerminalChecker;

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

    TerminalChecker terminalChecker;

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

        terminalChecker = new TerminalChecker(new TerminalChecker.Callback() {
            @Override
            public void onBackResult(String result) {
                testLines.add(result);
                startShow();
            }
        });

        initEvent();
    }

    public void initEvent() {

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startShow();
            }
        });

        editView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                limitEditLines(s);
            }
        });

        editView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if(actionId == EditorInfo.IME_ACTION_DONE){

                    String content = v.getText().toString().trim();
                    if(content.equals("")){
                        return true;
                    } else {
                        testLines.add(content);
                        v.setText("");
                        startShow();

                        terminalChecker.firstCheck(content);
                    }
                }
                return true;
            }
        });

    }

    public void limitEditLines(Editable s){
        if(editView.getLineCount()>3){
            String str = s.toString();
            int cursorStart = editView.getSelectionStart();
            int cursorEnd = editView.getSelectionEnd();
            if(cursorStart==cursorEnd && cursorStart<str.length()&&cursorStart>=1){
                str = str.substring(0, cursorStart-1)+str.substring(cursorStart);
            }else{
                str = str.substring(0, s.length()-1);
            }
            editView.setText(str);
            editView.setSelection(editView.getText().length());
        }
    }

    private List<String> testLines = new ArrayList<>();

    public void startShow() {

        if(testLines.size()==0){
            testLines.add("Hi, buddy!");
            testLines.add("Welcome to hack world!");
            testLines.add("Here you can input your order.");
            testLines.add("And our console will answer you.");
            testLines.add("bbbbb");
            testLines.add("bbbbb");
            testLines.add("bbbbb");
            testLines.add("bbbbb");
            testLines.add("bbbbb");
            testLines.add("Now I wanna say something blablablablabal, and blablablabla"
                    +"blablablablablablablablabla");
        }

        textView.setMovementMethod(ScrollingMovementMethod.getInstance());
        textView.reset();
        textView.animateText(testLines);
    }
}
