package gamebuddy.game.hackshow.view.view;

import android.content.Context;
import android.graphics.Paint;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

/**
 * describe
 * created by tindle
 * created time 16/2/29 下午6:13
 */
public class TerminalEditView extends EditText {

    public interface EditCallback {
        void onActionDone(String content);
    }

    EditCallback mCallback = null;

    public void setEditCallback(EditCallback editCallback) {
        this.mCallback = editCallback;
    }

    public TerminalEditView(Context context) {
        super(context);
        init();
    }

    public TerminalEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TerminalEditView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        Paint editPaint = this.getPaint();
        editPaint.setShadowLayer(10, 0, 0, 0xff99cc00);

        this.setRawInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        this.setImeOptions(EditorInfo.IME_ACTION_DONE);

        this.addTextChangedListener(new TextWatcher() {
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

        this.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    String content = v.getText().toString().trim();
                    if (content.equals("")) {
                        return true;
                    } else {
                        setText("");
                        if (mCallback != null)
                            mCallback.onActionDone(content);


//                        testLines.add(content);
//                        v.setText("");
//                        startShow();
//
//                        terminalChecker.startCheck(content);
                    }
                }
                return true;
            }
        });
    }

    public void limitEditLines(Editable s) {
        if (this.getLineCount() > 3) {
            String str = s.toString();
            int cursorStart = this.getSelectionStart();
            int cursorEnd = this.getSelectionEnd();
            if (cursorStart == cursorEnd && cursorStart < str.length() && cursorStart >= 1) {
                str = str.substring(0, cursorStart - 1) + str.substring(cursorStart);
            } else {
                str = str.substring(0, s.length() - 1);
            }
            this.setText(str);
            this.setSelection(this.getText().length());
        }
    }

}
