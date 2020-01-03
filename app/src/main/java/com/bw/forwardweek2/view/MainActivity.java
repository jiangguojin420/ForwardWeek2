package com.bw.forwardweek2.view;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bw.forwardweek2.R;
import com.bw.forwardweek2.base.BaseActivity;
import com.bw.forwardweek2.contract.IClassifyContract;
import com.bw.forwardweek2.database.ClassifyTableDao;
import com.bw.forwardweek2.database.CommodityTableDao;
import com.bw.forwardweek2.database.DaoMaster;
import com.bw.forwardweek2.database.DaoSession;
import com.bw.forwardweek2.model.bean.ClassifyBean;
import com.bw.forwardweek2.model.bean.CommodityBean;
import com.bw.forwardweek2.model.dao.ClassifyTable;
import com.bw.forwardweek2.model.dao.CommodityTable;
import com.bw.forwardweek2.presenter.ClassifyPresenter;
import com.bw.forwardweek2.util.NetUtil;
import com.bw.forwardweek2.view.adapter.CassifyAdapter;
import com.bw.forwardweek2.view.adapter.CommodityAdapter;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
    private ClassifyTableDao classifyTableDao;
    private CommodityTableDao commodityTableDao;

    @Override
    protected void initData() {
        //拿到DaoSession
        DaoSession daoSession = DaoMaster.newDevSession(this, "app.db");
        //拿到分类的dao
        classifyTableDao = daoSession.getClassifyTableDao();
        //拿到商品的dao
        commodityTableDao = daoSession.getCommodityTableDao();

        // TODO: 2020/1/3  请求分类数据
        if (NetUtil.getInstance().hasNet(this)) {
            mPresenter.getClassifyData();
        } else {
            //从数据库中获取分类数据
            getClassifyDataFromDatabase();
            //从数据库中获取商品数据
            getCommodityDataFromDatabase();
        }
    }

    // TODO: 2020/1/3 左侧分类的查询和展示
    private void getClassifyDataFromDatabase() {

        // 查询到分类bean
        ClassifyTable classifyTable = classifyTableDao.queryBuilder().unique();

        if (classifyTable != null) {
            //拿到分类的json
            String classifyJson = classifyTable.getClassifyJson();
            //解析
            ClassifyBean classifyBean = new Gson().fromJson(classifyJson, ClassifyBean.class);

            //设置布局管理器和适配
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

        }
    }
    // TODO: 2020/1/3 右侧商品的查询和展示
    private void getCommodityDataFromDatabase() {

        CommodityTable commodityTable = commodityTableDao.queryBuilder().unique();
        String commodityJson = commodityTable.getCommodityJson();
        CommodityBean commodityBean = new Gson().fromJson(commodityJson, CommodityBean.class);

        List<CommodityBean.ResultBean> commodityList = commodityBean.getResult();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mRecyclerCommodity.setLayoutManager(gridLayoutManager);

        CommodityAdapter commodityAdapter = new CommodityAdapter(commodityList);
        mRecyclerCommodity.setAdapter(commodityAdapter);
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

        // TODO: 2020/1/3 存储分类对应的json
        //先清除数据库中旧数据
        classifyTableDao.deleteAll();
        //存储分类的json
        String json = new Gson().toJson(classifyBean);
        ClassifyTable classifyTable = new ClassifyTable(json);
        classifyTableDao.insert(classifyTable);


        // TODO: 2020/1/3 调用第一分类的商品列表，防止第一次加载，右侧白板
        //左侧分类第一个条目对应的id
        String id = classifyList.get(0).getId();
        //默认去请求左侧第一条目对应的商品数据， 相当于你点击了左侧第一条目的效果
        mPresenter.getCommodityData(id);
    }

    @Override
    public void onClassifyFailure(Throwable throwable) {

    }

    /**
     * 右侧商品接口成功
     */
    @Override
    public void onCommoditySuccess(CommodityBean commodityBean) {
        List<CommodityBean.ResultBean> commodityList = commodityBean.getResult();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mRecyclerCommodity.setLayoutManager(gridLayoutManager);

        CommodityAdapter commodityAdapter = new CommodityAdapter(commodityList);
        mRecyclerCommodity.setAdapter(commodityAdapter);

        // TODO: 2020/1/3 存储商品对应的json
        //先清除表中数据
        commodityTableDao.deleteAll();
        //存储
        String json = new Gson().toJson(commodityBean);
        CommodityTable commodityTable = new CommodityTable(json);
        commodityTableDao.insert(commodityTable);

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
    @Subscribe
    public void onGetClassifyId(String categoryId) {
        // TODO: 2020/1/3 接受到id之后，重新请求 右侧商品的数据
        mPresenter.getCommodityData(categoryId);
    }

}
