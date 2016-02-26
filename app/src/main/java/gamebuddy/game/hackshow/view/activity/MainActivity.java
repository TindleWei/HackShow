package gamebuddy.game.hackshow.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import butterknife.Bind;
import de.greenrobot.event.EventBus;
import gamebuddy.game.hackshow.R;
import gamebuddy.game.hackshow.presenter.eventbus.EditClickEvent;
import gamebuddy.game.hackshow.view.fragment.BaseFragment;
import gamebuddy.game.hackshow.view.fragment.M0206Fragment;

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
                updateKeyboardStatus(isOpen);
            }
        });

        updateKeyboardStatus(KeyboardVisibilityEvent.isKeyboardVisible(this));

        initHeader();
    }

    public void initHeader(){
        ImageView iv_profile = (ImageView)findViewById(R.id.iv_profile);
        int rest = R.drawable.computer_help_me;
        Glide.with(mContext)
                .load(rest)
                .placeholder(R.drawable.computer_help_me)
                .into(iv_profile);
    }


    private void updateKeyboardStatus(boolean isOpen) {

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
        mFragment = new M0206Fragment();

        FragmentTransaction trans = fragmentManager.beginTransaction();
        trans.add(R.id.fragment_container, mFragment, "TerminalFragment");
        trans.commit();
    }

    public void onEvent(EditClickEvent event){
        fragment_container = (FrameLayout)findViewById(R.id.fragment_container);
        ViewGroup.LayoutParams lp = fragment_container.getLayoutParams();
        lp.height = mScreenHeight/2;
        fragment_container.setLayoutParams(lp);
    }
    /**
     * 获取屏幕的宽高密度
     */
    public void getScreenSize() {
        if (mScreenHeight != 0) return;

        DisplayMetrics metric = getBaseContext().getResources().getDisplayMetrics();
        mScreenWidth = metric.widthPixels;
        mScreenHeight = metric.heightPixels;
        mDensity = metric.density;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dp2px(Context context, float dpValue) {
        if(mDensity==0){
            mDensity = context.getResources().getDisplayMetrics().density;
        }
        return (int) (dpValue * mDensity + 0.5f);
    }

}
