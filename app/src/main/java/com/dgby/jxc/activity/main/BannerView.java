//package com.dgby.jxc.activity.main;
//
//import android.content.Context;
//import android.content.res.TypedArray;
//import android.util.AttributeSet;
//import android.view.Gravity;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//
//import androidx.viewpager.widget.PagerAdapter;
//import androidx.viewpager.widget.ViewPager;
//
//import com.dgby.jxc.R;
//
//import java.util.List;
//
//public class BannerView extends ViewGroup implements ViewPager.OnPageChangeListener {
//
//    private ViewPager mViewPager;
//    private LinearLayout mIndicatorLayout;
//    private List<BannerItem> mDataList;
//    private int mIndicatorSelectedResId;
//    private int mIndicatorUnselectedResId;
//    private int mCurrentPosition;
//    private int mAutoPlayDuration = 3000;
//    private boolean mIsAutoPlay = true;
//
//    public BannerView(Context context) {
//        this(context, null);
//    }
//
//    public BannerView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        init(context, attrs);
//    }
//
//    private void init(Context context, AttributeSet attrs) {
//        // 初始化 ViewPager
//        mViewPager = new ViewPager(context);
//        mViewPager.addOnPageChangeListener(this);
//        addView(mViewPager);
//
//        // 初始化指示器布局
//        mIndicatorLayout = new LinearLayout(context);
//        mIndicatorLayout.setOrientation(LinearLayout.HORIZONTAL);
//        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        //layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
//        mIndicatorLayout.setLayoutParams(layoutParams);
//        addView(mIndicatorLayout);
//
//        // 获取指示器选中和未选中的资源 id
//        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BannerView);
//        mIndicatorSelectedResId = typedArray.getResourceId(R.styleable.BannerView_indicator_selected, R.drawable.indicator_selected);
//        mIndicatorUnselectedResId = typedArray.getResourceId(R.styleable.BannerView_indicator_unselected, R.drawable.indicator_unselected);
//        typedArray.recycle();
//    }
//
//    public void setDataList(List<BannerItem> dataList) {
//        mDataList = dataList;
//        if (mDataList != null && mDataList.size() > 0) {
//            // 初始化指示器
//            for (int i = 0; i < mDataList.size(); i++) {
//                ImageView imageView = new ImageView(getContext());
//                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//                layoutParams.leftMargin = 10;
//                layoutParams.rightMargin = 10;
//                imageView.setLayoutParams(layoutParams);
//                imageView.setImageResource(mIndicatorUnselectedResId);
//                mIndicatorLayout.addView(imageView);
//            }
//            // 默认选中第一项
//            mCurrentPosition = 0;
//            ImageView firstIndicatorView = (ImageView) mIndicatorLayout.getChildAt(0);
//            firstIndicatorView.setImageResource(mIndicatorSelectedResId);
//
//            // 初始化 Adapter
//            mViewPager.setAdapter(new BannerAdapter());
//            mViewPager.setCurrentItem(mCurrentPosition, false);
//
//            // 开始自动轮播
//            startAutoPlay();
//        }
//    }
//
//    public void setAutoPlayDuration(int duration) {
//        mAutoPlayDuration = duration;
//    }
//
//    public void setIsAutoPlay(boolean isAutoPlay) {
//        mIsAutoPlay = isAutoPlay;
//        if (mIsAutoPlay) {
//            startAutoPlay();
//        } else {
//            stopAutoPlay();
//        }
//    }
//
//    private void startAutoPlay() {
//        stopAutoPlay();
//        postDelayed(mAutoPlayTask, mAutoPlayDuration);
//    }
//
//    private void stopAutoPlay() {
//        removeCallbacks(mAutoPlayTask);
//    }
//
//    private final Runnable mAutoPlayTask = new Runnable() {
//        @Override
//        public void run() {
//            mCurrentPosition = (mCurrentPosition + 1) % mDataList.size();
//            mViewPager.setCurrentItem(mCurrentPosition, true);
//            postDelayed(this, mAutoPlayDuration);
//        }
//    };
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        // 测量 ViewPager 的大小
//        int viewPagerWidthMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.EXACTLY);
//        int viewPagerHeightMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec), MeasureSpec.EXACTLY);
//        mViewPager.measure(viewPagerWidthMeasureSpec, viewPagerHeightMeasureSpec);
//
//        // 测量指示器布局的大小
//        int indicatorWidthMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.AT_MOST);
//        int indicatorHeightMeasureSpec = MeasureSpec.makeMeasureSpec(50, MeasureSpec.EXACTLY);
//        mIndicatorLayout.measure(indicatorWidthMeasureSpec, indicatorHeightMeasureSpec);
//
//        // 设置整个 BannerView 的大小
//        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), mViewPager.getMeasuredHeight() + mIndicatorLayout.getMeasuredHeight());
//    }
//
//    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        mViewPager.layout(0, 0, mViewPager.getMeasuredWidth(), mViewPager.getMeasuredHeight());
//        mIndicatorLayout.layout(0, mViewPager.getMeasuredHeight(), mIndicatorLayout.getMeasuredWidth(), mViewPager.getMeasuredHeight() + mIndicatorLayout.getMeasuredHeight());
//    }
//
//    @Override
//    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//    }
//
//    @Override
//    public void onPageSelected(int position) {
//        // 更新当前选中的位置，并更新指示器状态
//        ImageView currentIndicatorView = (ImageView) mIndicatorLayout.getChildAt(mCurrentPosition);
//        currentIndicatorView.setImageResource(mIndicatorUnselectedResId);
//        mCurrentPosition = position % mDataList.size();
//        ImageView indicatorView = (ImageView) mIndicatorLayout.getChildAt(mCurrentPosition);
//        indicatorView.setImageResource(mIndicatorSelectedResId);
//    }
//
//    @Override
//    public void onPageScrollStateChanged(int state) {
//        // 根据 ViewPager 的滑动状态，更新自动轮播的状态
//        switch (state) {
//            case ViewPager.SCROLL_STATE_IDLE:
//                startAutoPlay();
//                break;
//            case ViewPager.SCROLL_STATE_DRAGGING:
//                stopAutoPlay();
//                break;
//            case ViewPager.SCROLL_STATE_SETTLING:
//                break;
//        }
//    }
//
//    private class BannerAdapter extends PagerAdapter {
//
//        @Override
//        public int getCount() {
//            return Integer.MAX_VALUE;
//        }
//
//        @Override
//        public boolean isViewFromObject(View view, Object object) {
//            return view == object;
//        }
//
//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//            // 创建 BannerItemView，并添加到容器中
//            BannerItemView bannerItemView = new BannerItemView(getContext());
//            bannerItemView.bindData(mDataList.get(position % mData
//                    List.size()), mBannerItemViewCreator);
//            container.addView(bannerItemView);
//            return bannerItemView;
//        }
//
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            // 销毁 BannerItemView
//            container.removeView((View) object);
//        }
//    }
//
//    public interface BannerItemViewCreator<T> {
//        View createView(Context context, int position, T data);
//
//        void updateView(View view, int position, T data);
//    }
//
//    public class BannerItemView<T> extends FrameLayout {
//        private View mView;
//
//        public BannerItemView(Context context) {
//            super(context);
//        }
//
//        public void bindData(T data, BannerItemViewCreator<T> creator) {
//            if (mView == null) {
//                mView = creator.createView(getContext(), 0, data);
//                addView(mView);
//            } else {
//                creator.updateView(mView, 0, data);
//            }
//        }
//
//        public BannerItemView(Context context) {
//            super(context);
//        }
//
//        public void bindData(T data, BannerItemViewCreator<T> creator) {
//            if (mView == null) {
//                mView = creator.createView(getContext(), 0, data);
//                addView(mView);
//            } else {
//                creator.updateView(mView, 0, data);
//            }
//        }
//
//        @Override
//        public View createView(Context context, int position, BannerData data) {
//            ImageView imageView = new ImageView(context);
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            Glide.with(context).load(data.imageUrl).into(imageView);
//            return imageView;
//        }
//
//        @Override
//        public void updateView(View view, int position, BannerData data) {
//            if (view instanceof ImageView) {
//                Glide.with(view).load(data.imageUrl).into((ImageView) view);
//            }
//        }
//    }
//
//    public class BannerData {
//        public String imageUrl;
//    }
//}
//
//
