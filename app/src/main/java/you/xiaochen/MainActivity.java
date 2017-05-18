package you.xiaochen;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.HorizontalScrollView;

public class MainActivity extends AppCompatActivity {

    private TabLayout pager_tabs;

    private ViewPager vp_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("游阿游的博客");
        pager_tabs = (TabLayout) findViewById(R.id.pager_tabs);
        vp_list = (ViewPager) findViewById(R.id.vp_list);
        vp_list.setAdapter(new MainTabAdapter(getSupportFragmentManager()));
        pager_tabs.setupWithViewPager(vp_list);
    }

}
