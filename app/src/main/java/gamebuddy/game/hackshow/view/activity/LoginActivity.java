package gamebuddy.game.hackshow.view.activity;

import android.os.Bundle;

import java.util.ArrayList;

import butterknife.Bind;
import gamebuddy.game.hackshow.R;
import gamebuddy.game.hackshow.view.view.TerminalEditView;
import gamebuddy.game.hackshow.view.view.TerminalTextView;
import gamebuddy.game.hackshow.view.view.TerminalView;

/**
 * describe
 * created by tindle
 * created time 16/2/26 下午4:49
 */
public class LoginActivity extends BaseActivity {

    @Bind(R.id.terminal_view)
    TerminalView terminalView;

    ArrayList<String> textLines = new ArrayList<>();

    int indexLine = 0;

    // 初始化步骤计数
    // 0 等待输入用户名
    // 1 用户名输入完成，等待输入密码
    // 2 密码输入完成，建立Terminal
    // 3 成功创建Terminal, 进入游戏界面
    int setup = 0;

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
        }, 600);

        terminalView.setTextCallback(new TerminalTextView.DisplayCallback() {
            @Override
            public void onEndDisplayed() {
                terminalView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (indexLine < textLines.size()) {
                            terminalView.injectTextLine(textLines.get(indexLine));
                            indexLine++;
                        }
                    }
                }, 1000);
            }
        });

        terminalView.setEditCallback(new TerminalEditView.EditCallback() {
            @Override
            public void onActionDone(String content) {

                terminalView.injectTextLine(content);
                terminalView.startShow();

                if (setup == 0) {
                    //当输入名称后
                    username = content;
                    //检测用户明是否存在
                    terminalView.injectTextLine("User name is in checking...");
                    terminalView.injectTextLine("User name access successful.");
                    terminalView.injectTextLine("Input your terminal password:");

                } else if (setup == 1) {
                    //当输入密码后
                    terminalView.injectTextLine("Your password is checking...");
                    terminalView.injectTextLine("Access Success!");
                    //服务器 创建新的Terminal
                    terminalView.injectTextLine("Your terminal is found.");

                }
                setup++;
            }
        });
    }

    String username = "";
    String userpwd = "";
}
