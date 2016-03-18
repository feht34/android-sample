package com.miva.doorlock.widget;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miva.doorlock.R;

import java.util.Vector;

/**
 * 在 ViewPager 中懒创建Fragment
 * 把 Fragment 的创建放在 Fragment 变为可见之后，而不是相邻的 Fragment 可见后就创建。
 *
 * Created by tieh on 2015-12-31.
 */
public abstract class TabPagerAdapter extends FragmentPagerAdapter {

    private Vector<Fragment> mLoaded;
    private boolean mChanged = false;

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * 返回指定位置的真正的 Fragment，只有正在显示 (ViewPager滑到一半不算) 时才会去调用此方法来取真正的 Fragment
     *
     * @param position
     * @return
     */
    public abstract Fragment getLazyItem(int position);

    /**
     * 在取 Item 时才创建 LoadingFragment
     * 在显示 Fragment 时 {@link #setPrimaryItem(ViewGroup, int, Object)} 会去取真正的 Fragment 并替换到
     * {@link #mLoaded} 中，之后再取的就是真正的Fragment了
     *
     * @param position
     * @return
     */
    @Override
    public final Fragment getItem(int position) {
        if (mLoaded.get(position) == null) {
            mLoaded.set(position, new LoadingFragment());
        }
        return mLoaded.get(position);
    }

    /**
     * 在开始更新前初始化 Fragment Vector
     *
     * @param container
     */
    @Override
    public void startUpdate(ViewGroup container) {
        if (mLoaded == null) {
            mLoaded = new Vector<>(getCount());
            for(int i = 0; i < getCount(); i++) {
                mLoaded.add(null);
            }
        }
        super.startUpdate(container);
    }

    /**
     * 更新完之后检查是否需要更新真正的 Fragment
     * 这里加一个延时是为了更方便的演示真正的 Fragment 是在 LoadingFragment 显示之后才创建的。
     *
     * @param container
     */
    @Override
    public void finishUpdate(ViewGroup container) {
        super.finishUpdate(container);
        if (mChanged) {
            mChanged = false;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    notifyDataSetChanged();
                }
            }, 500);
        }
    }

    /**
     * 这是最关键的一个方法，如果当前显示的 Fragment 是 LoadingFragment，则创建真正的Fragment，
     * 并在 {@link #finishUpdate} 中去更新
     *
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        if (object instanceof LoadingFragment) {
            mLoaded.set(position, getLazyItem(position));
            mChanged = true;
        }
    }

    /**
     * {@linkd ViewPager} 会通过此方法来判断 Fragment 的位置是否有变化，是否需要 destroy Fragment
     *
     * @param object 之前已加载的 Fragment
     * @return 如果已经被真正的 Fragment 替代了，则返回 {@link #POSITION_NONE}
     */
    @Override
    public int getItemPosition(Object object) {
        if (object instanceof Fragment) {
            Fragment f = (Fragment) object;
            if (mLoaded.contains(f)) {
                return mLoaded.indexOf(f);
            }
        }
        return POSITION_NONE;
    }

    /**
     * 父类 {@link FragmentPagerAdapter} 通过此方法判断指定位置的 Item 是否有变化，
     * 所以这里返回 Fragment 对象的 hash code 来保证 Fragment 更新之后父类可以重新来取 Fragment。
     *
     * @param position Position within this adapter
     * @return Hash code of the Fragment object
     */
    @Override
    public long getItemId(int position) {
        if (mLoaded.get(position) == null) {
            return position;
        }
        return mLoaded.get(position).hashCode();
    }

    /**
     * 一个显示正在加载的 Fragment
     */
    public static class LoadingFragment extends Fragment {
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.loading, container, false);
        }
    }

}
