package gamebuddy.game.hackshow.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import gamebuddy.game.hackshow.R;
import gamebuddy.game.hackshow.view.view.TerminalView;

/**
 * describe
 * created by tindle
 * created time 16/2/6 上午10:48
 */
public class M0206Fragment extends BaseFragment {

    @Bind(R.id.terminal_view)
    TerminalView terminalView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_m0206, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        terminalView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            }
        });
    }
}
