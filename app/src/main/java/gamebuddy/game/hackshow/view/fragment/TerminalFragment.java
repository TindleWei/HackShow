package gamebuddy.game.hackshow.view.fragment;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import de.greenrobot.event.EventBus;
import gamebuddy.game.hackshow.R;
import gamebuddy.game.hackshow.presenter.eventbus.EditClickEvent;
import gamebuddy.game.hackshow.view.vandor.HTextView;

/**
 * describe
 * created by tindle
 * created time 16/1/15 下午1:36
 */
public class TerminalFragment extends BaseFragment{

    @Bind(R.id.terminal_content)
    HTextView terminal_content;

    @Bind(R.id.terminal_edit)
    EditText terminal_edit;

    String text = "THis is hack show.\n\n\n\n\n\n\n\n\n\n\n\n";

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_terminal, container, false);
    }

    List<String> testLines = new ArrayList<>();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        terminal_content.setText(text);
        terminal_content.setMovementMethod(ScrollingMovementMethod.getInstance());

        testLines.add("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        testLines.add("bbbbb");
        testLines.add("bbbbb");
        testLines.add("bbbbb");
        testLines.add("bbbbb");
        testLines.add("bbbbb");
        testLines.add("cccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc"
                +"cccccccccccccccccc");

        terminal_content.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                terminal_content.reset();
                terminal_content.animateText(testLines);
            }
        });

        terminal_edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new EditClickEvent());

            }
        });
    }

}
