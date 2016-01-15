package gamebuddy.game.hackshow.view.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.Bind;
import gamebuddy.game.hackshow.R;
import gamebuddy.game.hackshow.view.fragment.MachineFragment;

/**
 * describe
 * created by tindle
 * created time 16/1/13 上午11:40
 */
public class RootActivity extends BaseActivity {

    @Bind(R.id.viewpagertab)
    SmartTabLayout viewpagerTab;

    @Bind(R.id.viewpager)
    ViewPager viewPager;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_root;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void init(){
        FragmentPagerItems.Creator creator = FragmentPagerItems.with(this);

        creator.add("Machine",MachineFragment.class);
        creator.add("One",MachineFragment.class);
        FragmentPagerItems pages = creator.create();

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(),
                pages);

        viewPager.setOffscreenPageLimit(pages.size());
        viewPager.setAdapter(adapter);
        viewpagerTab.setViewPager(viewPager);
    }
}
