package gamebuddy.game.hackshow.view.activity;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import gamebuddy.game.hackshow.R;
import gamebuddy.game.hackshow.view.view.MatrixView;
import gamebuddy.game.hackshow.view.view.StarwarView;

/**
 * describe
 * created by tindle
 * created time 16/1/29 上午11:36
 */
public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Calculate
        setContentView(R.layout.activity_test);
        init();

        // Matrix
//        setContentView(R.layout.activity_test2);
//        init2();

//        setContentView(R.layout.activity_test3);
//        init3();
    }

    public void init3() {
        final StarwarView starwarView = (StarwarView) findViewById(R.id.starwar_view);
        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,1).setDuration(20000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                starwarView.setScrollPosition((float)animation.getAnimatedValue());
            }
        });
        starwarView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                valueAnimator.start();
            }
        });
    }

    public void init2() {
        final MatrixView matrix_view = (MatrixView) findViewById(R.id.matrix_view);

        matrix_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matrix_view.animateStart("hackshow");
            }
        });
    }

    public void init() {

        final TextView tv = (TextView) findViewById(R.id.test_tv1);
        final TextView tv2 = (TextView) findViewById(R.id.test_tv2);

        int color = android.R.color.holo_green_light;
        Paint textPaint = tv.getPaint();
        textPaint.setColor(getResources().getColor(color));
        // 设定阴影(柔边, X 轴位移, Y 轴位移, 阴影颜色)
        textPaint.setShadowLayer(25, 0, 0, 0xff99cc00);
        tv.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        float width = textPaint.measureText(tv.getText().toString());
        tv2.setText("" + width);

        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        final int mScreenWidth = metric.widthPixels;

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Paint textPaint = tv.getPaint();
                float width = textPaint.measureText(tv.getText().toString());
                tv2.setText("tvW:" + width
                        + "\nleft: " + tv.getCompoundPaddingLeft()
                        + "\nright: " + tv.getCompoundPaddingRight()
                        + "\nscreenW:" + mScreenWidth);

                float mDensity = getBaseContext().getResources().getDisplayMetrics().density;
                float mScreenHeight = getBaseContext().getResources().getDisplayMetrics().heightPixels;
                float mScreenWidth = getBaseContext().getResources().getDisplayMetrics().widthPixels;

                tv2.setText("desity: " + mDensity
                        + "\nheight: " + mScreenHeight
                        + "\nwidth: " + mScreenWidth);

                int contentViewTop = getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
                tv2.setText("contentViewTop: " + contentViewTop
                        + "\nstatusBar height: " + getStatusBarHeight(getBaseContext())
                        + "\nnavigation height: " + getNavigationBarHeight(getBaseContext()));

            }
        });
    }

    //获取statusBar高度
    public int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    //获取底部navigationBar高度
    public int getNavigationBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
