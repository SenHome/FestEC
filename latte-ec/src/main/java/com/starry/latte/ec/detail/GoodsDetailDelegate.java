package com.starry.latte.ec.detail;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.ToxicBakery.viewpager.transforms.DefaultTransformer;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.daimajia.androidanimations.library.YoYo;
import com.joanzapata.iconify.widget.IconTextView;
import com.starry.latte.delegates.LatteDelegate;
import com.starry.latte.ec.R;
import com.starry.latte.ec.R2;
import com.starry.latte.net.RestClient;
import com.starry.latte.net.callback.ISuccess;
import com.starry.latte.ui.animation.BezierAnimation;
import com.starry.latte.ui.animation.BezierUtil;
import com.starry.latte.ui.banner.HolderCreator;
import com.starry.latte.ui.widget.CircleTextView;
import com.starry.latte.util.log.LatteLogger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by wangsen on 2018/4/22.
 * 点击后进入的页面
 */

public class GoodsDetailDelegate extends LatteDelegate implements AppBarLayout.OnOffsetChangedListener, BezierUtil.AnimationListener {

    @BindView(R2.id.goods_detail_toolbar)
    Toolbar mToolbar = null;
    @BindView(R2.id.tab_layout)
    TabLayout mTabLayout = null;
    @BindView(R2.id.view_pager)
    ViewPager mViewPager = null;
    @BindView(R2.id.detail_banner)
    ConvenientBanner<String> mBanner = null;
    @BindView(R2.id.collapsing_toolbar_detail)
    CollapsingToolbarLayout mCollapsingToolbarLayout = null;
    @BindView(R2.id.app_bar_detail)
    AppBarLayout mAppBar = null;

    //底部
    @BindView(R2.id.icon_favor)
    IconTextView mIconFavor = null;
    @BindView(R2.id.tv_shopping_cart_amount)
    CircleTextView mCircleTextView = null;
    @BindView(R2.id.rl_add_shop_cart)
    RelativeLayout mRlAddShopCart = null;
    @BindView(R2.id.icon_shop_cart)
    IconTextView mIconShopCart = null;

    private static final String ARG_GOODS_ID = "ARG_GOODS_ID";
    //传进来的goodsid是首页item的position
    private int mGoodsId = -1;

    //进行动画的图片
    private String mGoodsThumbUrl = null;
    private int mShopCount = 0;

    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate()
            .override(100, 100);

    @OnClick(R2.id.rl_add_shop_cart)
    void onClickAddShopCart(){
        final CircleImageView animImg = new CircleImageView(getContext());
        Glide.with(this)
                .load(mGoodsThumbUrl)
                .apply(OPTIONS)
                .into(animImg);

        BezierAnimation.addCart(this, mRlAddShopCart, mIconShopCart, animImg, this);
    }


    //获取动画缩略图URl
    private void setShopCartCount(JSONObject data){
        mGoodsThumbUrl = data.getString("thumb");
        if(mShopCount == 0){
            mCircleTextView.setVisibility(View.GONE);
        }
    }

    public static GoodsDetailDelegate create(int goodsId){
        final Bundle args = new Bundle();
        args.putInt(ARG_GOODS_ID, goodsId);
        final GoodsDetailDelegate delegate = new GoodsDetailDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if(args!= null){
            mGoodsId = args.getInt(ARG_GOODS_ID);
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_goods_detail;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mCollapsingToolbarLayout.setContentScrimColor(Color.WHITE);
        mAppBar.addOnOffsetChangedListener(this);
        //购物车
        mCircleTextView.setCricleBackground(Color.RED);

        initData();
        initTablayout();
    }

    //商品详情下部滑动Tab实现
    private void initTablayout(){
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        final Context context = getContext();

        mTabLayout.setSelectedTabIndicatorColor
                (ContextCompat.getColor(context, R.color.app_main));
        mTabLayout.setTabTextColors(ColorStateList.valueOf(Color.BLACK));
        mTabLayout.setBackgroundColor(Color.WHITE);
        mTabLayout.setupWithViewPager(mViewPager);
    }



    //返回的商品数据
    private void initData(){
        RestClient.builder()
                .url("goods_detail.php")
                .params("goods_id",mGoodsId)
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSucess(String response) {
                        LatteLogger.d("Goods_detail",response);
                        final JSONObject data = JSON.parseObject(response).getJSONObject("data");
                        initBanner(data);
                        initGoodsInfo(data);
                        initPager(data);

                        setShopCartCount(data);
                    }
                })
                .build()
                .get();
    }



    //详情轮播图
    private void initBanner(JSONObject data) {
        final JSONArray array = data.getJSONArray("banners");
        final List<String> images = new ArrayList<>();
        final int size = array.size();
        for (int i = 0; i < size; i++) {
            images.add(array.getString(i));
        }

        mBanner.setPages(new HolderCreator(),images)
                .setPageIndicator(new int[]{R.drawable.dot_normal,R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setPageTransformer(new DefaultTransformer())
                .startTurning(3000)
                .setCanLoop(true);
    }



    //商品信息
    private void initGoodsInfo(JSONObject data) {
        final String goodsData  = data.toJSONString();
        getSupportDelegate()
                .loadRootFragment(R.id.frame_goods_info,GoodsInfoDelegate.create(goodsData));
    }

    private void initPager(JSONObject data) {
        final PagerAdapter adapter = new TabPagerAdapter(getFragmentManager(),data);
        mViewPager.setAdapter(adapter);
    }

    //点击进入水平动画
    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

    }

    @Override
    public void onAnimationEnd() {
        //贝塞尔曲线动画几口
        YoYo.with(new ScaleUpAnimator())
                .duration(500)
                .playOn(mIconShopCart);

        RestClient.builder()
                .url("add_shop_cart_count.php")
                .success(new ISuccess() {
                    @Override
                    public void onSucess(String response) {
                        mShopCount++;
                        mCircleTextView.setVisibility(View.VISIBLE);
                        mCircleTextView.setText(String.valueOf(mShopCount));
                    }
                })
                .params("count",mShopCount)
                .build()
                .post();
    }
}
