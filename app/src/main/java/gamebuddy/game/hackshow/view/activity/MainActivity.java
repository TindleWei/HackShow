package gamebuddy.game.hackshow.view.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import butterknife.Bind;
import de.greenrobot.event.EventBus;
import gamebuddy.game.hackshow.R;
import gamebuddy.game.hackshow.presenter.eventbus.EditClickEvent;
import gamebuddy.game.hackshow.view.fragment.BaseFragment;
import gamebuddy.game.hackshow.view.fragment.TerminalFragment;

public class MainActivity extends BaseActivity {

    /**
     * 屏幕的宽度、高度、密度
     */
    protected int mScreenWidth;
    protected int mScreenHeight;
    protected float mDensity;

    @Bind(R.id.fragment_container)
    FrameLayout fragment_container;

    @Bind(R.id.layout_top)
    View layout_top;

    @Bind(R.id.layout_bottom)
    View layout_bottom;

    private BaseFragment mFragment;
    private FragmentManager fragmentManager;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);

        getScreenSize();

        initFragments();

        KeyboardVisibilityEvent.setEventListener(this, new KeyboardVisibilityEventListener() {
            @Override
            public void onVisibilityChanged(boolean isOpen) {
                updateKeyboardStatusText(isOpen);
            }
        });

        updateKeyboardStatusText(KeyboardVisibilityEvent.isKeyboardVisible(this));
    }


    private void updateKeyboardStatusText(boolean isOpen) {

        if(isOpen){
            layout_top.setVisibility(View.GONE);
            layout_bottom.setVisibility(View.GONE);
        }else{
            layout_top.setVisibility(View.VISIBLE);
            layout_bottom.setVisibility(View.VISIBLE);
        }

//        Toast.makeText(this,String.format("keyboard is %s", (isOpen ? "visible" : "hidden")),1000).show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void initFragments(){
        fragmentManager = getSupportFragmentManager();
        mFragment = new TerminalFragment();

        FragmentTransaction trans = fragmentManager.beginTransaction();
        trans.add(R.id.fragment_container, mFragment, "TerminalFragment");
        trans.commit();
    }

    public void onEvent(EditClickEvent event){
        fragment_container = (FrameLayout)findViewById(R.id.fragment_container);
        ViewGroup.LayoutParams lp = fragment_container.getLayoutParams();
        lp.height = mScreenHeight/2;
        fragment_container.setLayoutParams(lp);
//        fragment_container.requestLayout();
//        layout_top.setVisibility(View.GONE);
//        layout_bottom.setVisibility(View.GONE);
    }
    /**
     * 获取屏幕的宽高密度
     */
    public void getScreenSize() {
        if (mScreenHeight != 0) return;

        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) this).getWindowManager().getDefaultDisplay().getMetrics(metric);
        mScreenWidth = metric.widthPixels;
        mScreenHeight = metric.heightPixels;
        mDensity = metric.density;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


}