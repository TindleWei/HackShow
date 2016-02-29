package gamebuddy.game.hackshow.view.activity;

import android.os.Bundle;

import java.util.ArrayList;

import butterknife.Bind;
import gamebuddy.game.hackshow.R;
import gamebuddy.game.hackshow.view.view.TerminalTextView;
import gamebuddy.game.hackshow.view.view.TerminalView;

/**
 * describe
 * created by tindle
 * created time 16/2/26 下午4:49
 */
public class LoginActivity extends BaseActivity{

    @Bind(R.id.terminal_view)
    TerminalView terminalView;

    ArrayList<String> textLines = new ArrayList<>();

    static int indexLine = 0;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        indexLine = 0;

        textLines.add("Wake up.");
        textLines.add("Matrix has you.");
        textLines.add("We gonna help you out.");
        textLines.add("First, you need to tell us your name:");

        terminalView.initWithType(TerminalView.CHECKER_TYPE_LOGIN);

        terminalView.postDelayed(new Runnable() {
            @Override
            public void run() {
                terminalView.injectTextLine(textLines.get(0));
                indexLine++;
            }
        },600);

        terminalView.setTerminalCallback(new TerminalTextView.DisplayCallback() {
            @Override
            public void onEndDisplayed() {
                terminalView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(indexLine<textLines.size()){
                            terminalView.injectTextLine(textLines.get(indexLine));
                            indexLine++;
                        }
                    }
                },1000);
            }
        });


    }
}
