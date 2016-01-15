package gamebuddy.game.hackshow.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.github.pwittchen.prefser.library.Prefser;

import butterknife.ButterKnife;

/**
 * describe
 * created by tindle
 * created time 15/12/10 下午3:28
 */
public abstract class BaseFragment extends Fragment {

    public Prefser prefser;

    @Override
    public void onCreate(Bundle bundle) {
        injectorPresenter();
        super.onCreate(bundle);
        prefser = new Prefser(getContext());
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

    }

    protected String getTitle() {
        return "";
    }

    protected void injectorPresenter() {}

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
