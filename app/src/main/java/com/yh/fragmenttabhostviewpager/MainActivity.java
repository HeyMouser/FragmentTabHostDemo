package com.yh.fragmenttabhostviewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {

    @BindView(R.id.tabHost)
    FragmentTabHost tabHost;
    @BindView(R.id.viewPage)
    ViewPager viewPage;

    private int mIndex = 0;//tab标签索引
    private LayoutInflater mLayoutInflater;
    private TextView mLastTextView;

    private String[] mTitles = {"友家/整租", "自如寓", "民宿", "自如驿", "生活服务"};
    private int[] mTabbgImg = {R.drawable.rent_select, R.drawable.ziruyu_select, R.drawable.minsu_select,
            R.drawable.ziruyi_select, R.drawable.life_select};
    private Class<?>[] fragmentArray = {RentFragment.class, ZiruyuFragment.class, MinsuFragment.class, ZiruyiFragment.class, LifeFragment.class};
    private List<Fragment> fragments;
    private RentFragment rentFragment;
    private ZiruyuFragment ziruyuFragment;
    private MinsuFragment minsuFragment;
    private ZiruyiFragment ziruyiFragment;
    private LifeFragment lifeFragment;

    private MyViewPageAdapter mViewPageAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initViewPage();
    }

    private void initView() {
        mLayoutInflater = LayoutInflater.from(this);
        mLastTextView = new TextView(this);
        tabHost.setup(this, getSupportFragmentManager(), R.id.viewPage);
        //去掉分割线
        tabHost.getTabWidget().setDividerDrawable(null);
        tabHost.setOnTabChangedListener(this);

        int count = mTitles.length;
        for (int i = 0; i < count; i++) {
            TabHost.TabSpec tabSpec = tabHost.newTabSpec(mTitles[i]).setIndicator(setItemView(i));
            tabHost.addTab(tabSpec, fragmentArray[i], null);
        }
        tabHost.setCurrentTab(mIndex);
    }

    private void initViewPage() {
        fragments = new ArrayList<>();
        rentFragment = RentFragment.newInstance("", "");
        ziruyuFragment = ZiruyuFragment.newInstance("", "");
        minsuFragment = MinsuFragment.newInstance("", "");
        ziruyiFragment = ZiruyiFragment.newInstance("", "");
        lifeFragment = LifeFragment.newInstance("", "");

        fragments.add(rentFragment);
        fragments.add(ziruyuFragment);
        fragments.add(minsuFragment);
        fragments.add(ziruyiFragment);
        fragments.add(lifeFragment);

        mViewPageAdapter = new MyViewPageAdapter(getSupportFragmentManager());
        viewPage.setAdapter(mViewPageAdapter);
        viewPage.setOnPageChangeListener(this);
    }

    @Override
    public void onTabChanged(String s) {
        mLastTextView.setTextColor(getResources().getColor(R.color.text_gray));
        LinearLayout view = (LinearLayout) tabHost.getCurrentTabView();
        TextView text = (TextView) view.getChildAt(1);
        text.setTextColor(getResources().getColor(R.color.text_black));
        mLastTextView = text;

        int position = tabHost.getCurrentTab();
        viewPage.setCurrentItem(position);
    }

    //给tab标签设置文字、图片
    private View setItemView(int index) {
        View view = mLayoutInflater.inflate(R.layout.main_tab_item, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.main_tab_item_imageview);
        imageView.setBackgroundResource(mTabbgImg[index]);

        TextView textView = (TextView) view.findViewById(R.id.main_tab_item_textview);
        textView.setText(mTitles[index]);
        return view;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        tabHost.setCurrentTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public class MyViewPageAdapter extends FragmentPagerAdapter {


        public MyViewPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments == null ? 0 : fragments.size();
        }
    }
}
