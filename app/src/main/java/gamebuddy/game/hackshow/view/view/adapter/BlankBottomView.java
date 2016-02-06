package gamebuddy.game.hackshow.view.view.adapter;

import android.content.Context;
import android.widget.LinearLayout;

import gamebuddy.game.hackshow.R;
import io.nlopez.smartadapters.views.BindableLinearLayout;

/**
 * describe
 * created by tindle
 * created time 16/1/14 上午11:56
 */
public class BlankBottomView extends BindableLinearLayout<Object>{

//    @Bind(R.id.tv_content)
//    TextView tv_content;

    public BlankBottomView(Context context){
        super(context);
    }

    @Override
    public int getOrientation() {
        return LinearLayout.VERTICAL;
    }

    @Override
    public int getLayoutId() {
        return R.layout.view_blank_bottom;
    }

    @Override
    public void bind(Object commandInfo) {

    }

}
