package gamebuddy.game.hackshow.view.activity;

import android.os.Bundle;
import android.view.View;

import com.tapjoy.Tapjoy;
import com.tapjoy.TapjoyConnectFlag;

import java.util.Hashtable;

import gamebuddy.game.hackshow.R;
import gamebuddy.game.hackshow.view.view.MatrixAnimView;

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

        final MatrixAnimView matrix_view = (MatrixAnimView) findViewById(R.id.matrix_view);

        matrix_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matrix_view.animateStart("hackshow");
            }
        });
        matrix_view.animateStart("hackshow");

//        matrix_view.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                startActivity(new Intent(mContext, MainActivity.class));
//                finish();
//            }
//        }, 5000);

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
