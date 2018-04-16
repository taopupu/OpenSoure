package com.open.source.mvp.base.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.open.source.MainActivity;
import com.open.source.R;
import com.open.source.base.BaseActivity;
import com.open.source.data.Constants;
import com.open.source.mvp.home.HomeActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class WelcomeActivity extends BaseActivity<WelcomeContract.View, WelcomeContract.Presenter> implements WelcomeContract.View {
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    private final int[] imageIds = {R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initWithPageGuideMode();
    }

    @Override
    protected WelcomeContract.Presenter initPresenter() {
        return new WelcomePresenter();
    }

    @Override
    public void welcome(Constants.START_MODE start_mode) {
        switch (start_mode) {
            case LOGIN:
//                startActivity(new Intent(this, LoginActivity.class));
                break;
            case NORMAL:
                startActivity(new Intent(this, HomeActivity.class));
                break;
        }
        finish();
    }

    private void initWithPageGuideMode() {
        List<View> mList = new ArrayList<View>();
        LinearLayout.LayoutParams ivParam = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        for (int index : imageIds) {
            ImageView item = new ImageView(this);
            item.setScaleType(ImageView.ScaleType.CENTER_CROP);
            item.setLayoutParams(ivParam);
            item.setImageResource(index);
            mList.add(item);
        }

        MyPagerAdapter adapter = new MyPagerAdapter(mList);
        viewpager.setAdapter(adapter);
        viewpager.setOnPageChangeListener(adapter);
    }

    private class MyPagerAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {

        private List<View> mViewList;

        public MyPagerAdapter(List<View> views) {
            mViewList = views;
        }

        @Override
        public int getCount() {
            return mViewList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position), 0);
            return mViewList.get(position);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            if (view == object) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            System.out.println("destroyItem  ::" + position);
            container.removeView(mViewList.get(position));
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int position) {
            if (position == mViewList.size() - 1) {
                mViewList.get(mViewList.size() - 1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPresenter.welcome();
                    }
                });
            }
//            if (position == mViewList.size() - 1) {
//                iv_start.setVisibility(View.VISIBLE);
//            } else {
//                iv_start.setVisibility(View.GONE);
//            }
//            pointGroup.getChildAt(position).setBackground(getResources().getDrawable(R.drawable.point_1));
//            pointGroup.getChildAt(lastPosition).setBackground(getResources().getDrawable(R.drawable.point_2));
//            lastPosition = position;
        }
    }

}
