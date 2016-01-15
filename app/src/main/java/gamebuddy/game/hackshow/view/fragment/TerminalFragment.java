package gamebuddy.game.hackshow.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import de.greenrobot.event.EventBus;
import gamebuddy.game.hackshow.R;
import gamebuddy.game.hackshow.presenter.eventbus.EditClickEvent;

/**
 * describe
 * created by tindle
 * created time 16/1/15 下午1:36
 */
public class TerminalFragment extends BaseFragment{

    @Bind(R.id.terminal_content)
    TextView terminal_content;

    @Bind(R.id.terminal_edit)
    EditText terminal_edit;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_terminal, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        terminal_content.setText("sdf");

        terminal_content.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

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
