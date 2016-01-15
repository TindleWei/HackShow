package gamebuddy.game.hackshow.view.view;

import android.content.Context;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import gamebuddy.game.hackshow.R;
import gamebuddy.game.hackshow.core.provider.ClickType;
import gamebuddy.game.hackshow.model.CommandInput;
import io.nlopez.smartadapters.views.BindableLinearLayout;

/**
 * describe
 * created by tindle
 * created time 16/1/14 上午10:42
 */
public class CommandInputView extends BindableLinearLayout<CommandInput>{

    @Bind(R.id.et_input)
    EditText et_input;

    public CommandInputView(Context context){
        super(context);
    }

    @Override
    public int getOrientation() {
        return LinearLayout.VERTICAL;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_command_input;
    }

    @Override
    public void bind(CommandInput commandInput) {

        et_input = (EditText)findViewById(R.id.et_input);
        et_input.setText("Haha");

        et_input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_GO||(event!=null&&event.getKeyCode()== KeyEvent.KEYCODE_ENTER)){

                    et_input.setText("Action_GO");
                    notifyItemAction(ClickType.DOC_ARTICLE_CLICKED);
                    return true;
                }
                return false;
            }
        });
    }
}
