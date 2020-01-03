package com.bw.forwardweek2.view;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bw.forwardweek2.R;
import com.bw.forwardweek2.base.BaseActivity;
import com.bw.forwardweek2.contract.IClassifyContract;
import com.bw.forwardweek2.model.bean.ClassifyBean;
import com.bw.forwardweek2.model.bean.CommodityBean;
import com.bw.forwardweek2.presenter.ClassifyPresenter;
import com.bw.forwardweek2.view.adapter.CassifyAdapter;
import com.bw.forwardweek2.view.adapter.CommodityAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.concurrent.ConcurrentNavigableMap;

import butterknife.BindView;

/**
 * 继承 BaseActivity
 * 指定泛型 P 为 ClassifyPresenter
 * 实现 IView
 * <p>
 * 1、指定布局
 * 2、new presenter
 * 3、请求数据
 */
public class MainActivity extends BaseActivity<ClassifyPresenter> implements IClassifyContract.IView {

    @BindView(R.id.recycler_classify)
    RecyclerView mRecyclerClassify;
    @BindView(R.id.recycler_commodity)
    RecyclerView mRecyclerCommodity;

    @Override
    protected void initData() {
        // TODO: 2020/1/3  请求分类数据
        mPresenter.getClassifyData();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected ClassifyPresenter providePresenter() {
        return new ClassifyPresenter();
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    /**
     * 左侧分类成功
     */
    @Override
    public void onClassifySuccess(ClassifyBean classifyBean) {
        final List<ClassifyBean.ResultBean.SecondCategoryVoBean> classifyList = classifyBean.getResult().get(0).getSecondCategoryVo();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerClassify.setLayoutManager(linearLayoutManager);

        CassifyAdapter cassifyAdapter = new CassifyAdapter(classifyList);
        cassifyAdapter.setOnItemClickListener(new CassifyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // TODO: 2020/1/3 把 ID 发出去
                EventBus.getDefault().post(classifyList.get(position).getId());
            }
        });
        mRecyclerClassify.setAdapter(cassifyAdapter);

        // TODO: 2020/1/3 调用第一分类的商品列表
        //左侧分类第一个条目对应的id
        String id = classifyList.get(0).getId();
        //默认去请求左侧第一条目对应的商品数据， 相当于你点击了左侧第一条目的效果
        mPresenter.getCommodityData(id);
    }

    @Override
    public void onClassifyFailure(Throwable throwable) {

    }

    //右侧商品接口成功
    @Override
    public void onCommoditySuccess(CommodityBean commodityBean) {
        List<CommodityBean.ResultBean> commodityList = commodityBean.getResult();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mRecyclerCommodity.setLayoutManager(gridLayoutManager);

        CommodityAdapter commodityAdapter = new CommodityAdapter(commodityList);
        mRecyclerCommodity.setAdapter(commodityAdapter);

    }

    @Override
    public void onCommodityBeanFailure(Throwable throwable) {

    }


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    // TODO: 2020/1/3 接受 id 的方法， 必须 public 必须类型统一
    public void onGetClassifyId(String categoryId) {
        // TODO: 2020/1/3 接受到id之后，重新请求 右侧商品的数据
        mPresenter.getCommodityData(categoryId);
    }

}
