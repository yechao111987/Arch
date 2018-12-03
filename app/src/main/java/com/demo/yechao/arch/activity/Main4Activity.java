//package com.demo.yechao.arch.activity;
//
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.support.v7.widget.Toolbar;
//import com.ashokvarma.bottomnavigation.BottomNavigationBar;
//import com.ashokvarma.bottomnavigation.BottomNavigationItem;
//import com.demo.yechao.arch.R;
//
//public class Main4Activity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {
//
//    private BottomNavigationBar bottomNavigationBar;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main4);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
//        int lastSelectedPosition = 0;
//        bottomNavigationBar
//                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "位置").setActiveColor(R.color.colorPrimary))
//                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher_round, "发现").setActiveColor(R.color.colorAccent))
//                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher_round, "爱好").setActiveColor(R.color.colorPrimary))
//                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher_round, "图书").setActiveColor(R.color.colorAccent))
//                .setFirstSelectedPosition(lastSelectedPosition)
//                .initialise();
//
//        bottomNavigationBar.setTabSelectedListener(this);
////        setDefaultFragment();
//    }
//
////    /**
////     * 设置默认的
////     */
////    private void setDefaultFragment() {
////        FragmentManager fm = getFragmentManager();
////        FragmentTransaction transaction = fm.beginTransaction();
////        mLocationFragment = LocationFragment.newInstance("位置");
////        transaction.replace(R.id.tabs, mLocationFragment);
////        transaction.commit();
////    }
//
//    /**
//     * Called when a tab enters the selected state.
//     *
//     * @param position The position of the tab that was selected
//     */
//    @Override
//    public void onTabSelected(int position) {
//
//    }
//
//    /**
//     * Called when a tab exits the selected state.
//     *
//     * @param position The position of the tab that was unselected
//     */
//    @Override
//    public void onTabUnselected(int position) {
//
//    }
//
//    /**
//     * Called when a tab that is already selected is chosen again by the user. Some applications
//     * may use this action to return to the top level of a category.
//     *
//     * @param position The position of the tab that was reselected.
//     */
//    @Override
//    public void onTabReselected(int position) {
//
//    }
//}
