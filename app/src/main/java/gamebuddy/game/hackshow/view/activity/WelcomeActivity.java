package gamebuddy.game.hackshow.view.activity;

import android.content.Intent;
import android.os.Bundle;

import com.tapjoy.Tapjoy;
import com.tapjoy.TapjoyConnectFlag;

import java.util.Hashtable;

import gamebuddy.game.hackshow.R;

/**
 * describe
 * created by tindle
 * created time 15/12/21 下午1:08
 */
public class WelcomeActivity extends BaseActivity{

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 暂去
        // setupTapjoy();

        findViewById(R.id.view).postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(mContext, RootActivity.class));
                finish();
            }
        },800);

    }

    public void setupTapjoy(){
        Hashtable<String, Object> connectFlags = new Hashtable<String, Object>();
        connectFlags.put(TapjoyConnectFlag.ENABLE_LOGGING, "true");
        Tapjoy.connect(getApplicationContext(), "9lc3ES8lSLqF-V5jsyXApQECaYc9S0gN2XYPxKfefsHembpLJ2tk_RQWGS9g", connectFlags, null);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Tapjoy.onActivityStart(this);
    }

    @Override
    protected void onStop() {
        Tapjoy.onActivityStop(this);
        super.onStop();
    }
}
