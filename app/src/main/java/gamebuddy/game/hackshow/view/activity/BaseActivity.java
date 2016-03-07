package gamebuddy.game.hackshow.view.activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.github.pwittchen.prefser.library.Prefser;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * describe
 * created by tindle
 * created time 15/12/9 下午3:23
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Context mContext;

    //Lib for SharedPreferences
    public Prefser prefser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        prefser = new Prefser(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        setContentView(getLayoutResId());

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        MobclickAgent.onResume(mContext);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPause(mContext);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
    }

    abstract protected
    @LayoutRes
    int getLayoutResId();

    // default eventbus methos
    public void onEvent(Object object) {

    }
}
