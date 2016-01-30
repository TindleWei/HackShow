package gamebuddy.game.hackshow.view.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import gamebuddy.game.hackshow.R;

/**
 * describe
 * created by tindle
 * created time 16/1/29 上午11:36
 */
public class TestActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        init();
    }

    public void init(){

        final TextView tv = (TextView)findViewById(R.id.test_tv1);
        final TextView tv2 = (TextView)findViewById(R.id.test_tv2);

        Paint textPaint = tv.getPaint();
        float width = textPaint.measureText(tv.getText().toString());
        tv2.setText("" + width);


        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        final int mScreenWidth = metric.widthPixels;

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String test = "Hello World!Hello World!sadfasfasdfasdfasdfasdf";
//                tv.setText(test);

                Paint textPaint = tv.getPaint();
                float width = textPaint.measureText(tv.getText().toString());
                tv2.setText("tvW:"+ width
                        + "\nleft: "+ tv.getCompoundPaddingLeft()
                        + "\nright: "+ tv.getCompoundPaddingRight()
                        + "\nscreenW:"+mScreenWidth);
            }
        });
    }


}
