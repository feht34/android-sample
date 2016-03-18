# Lazy Creat Fragment in ViewPager / 在 ViewPager 中懒创建Fragment

## 概述
比较常见的懒加载是重写 setUserVisibleHint 方法，由此判断 Fragment 是否可见，并在 onCreateView 方法中设置 View 已创建的标志位，可见且已创建 View 时去调用 lazyload 方法。
这里介绍一种新的方法，懒到极至，把 Fragment 的创建放在 Fragment 变为可见之后，而不是相邻的 Fragment 可见后就创建。

## 原理
重写 PagerAdapter.setPrimaryItem() 方法，ViewPager 通过此方法通知 Adpater 当前显示的 Fragment，此时才创建真正的 Fragment。

## 步骤
在 ViewPager 最初通过 PagerAdapter.getItem() 取 Fragment 时不给真正的 Fragment，只给一个显示正在加载的空 Fragment，在调用 PagerAdapter.setPrimaryItem() 通知当前正在显示的 Fragment 时才创建真正的 Fragment，通过 notifyDataSetChanged 通知 ViewPager 重新来取 Fragment。

接下直接看代码吧。

## 懒到 detach/destroy 也不想做
如果你想要切换 Page 时，相隔一个以上的 Page 不被 detach/destroy，可以调用 ViewPager.setOffscreenPageLimit(int limit)，默认是缓存左右各一页，想多缓存几页limit就取几。这样就不会在切回来的时候又去调 onCreateView 了。
 