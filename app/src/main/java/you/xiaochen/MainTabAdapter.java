package you.xiaochen;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by you on 2017/3/15.
 */

public class MainTabAdapter extends FragmentPagerAdapter {

    static final String[] titles = {"水平兼垂直滑动", "兼容下拉刷新", "不嵌套ScrollView"};

    public MainTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return Fragment1.newInstance();
            case 1:
                return Fragment2.newInstance();
            case 2:
                return Fragment3.newInstance();
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

}
