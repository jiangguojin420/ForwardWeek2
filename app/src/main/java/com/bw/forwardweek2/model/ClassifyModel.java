package com.bw.forwardweek2.model;

import com.bw.forwardweek2.contract.IClassifyContract;
import com.bw.forwardweek2.model.bean.ClassifyBean;
import com.bw.forwardweek2.model.bean.CommodityBean;
import com.bw.forwardweek2.util.NetUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ClassifyModel implements IClassifyContract.IModel {
    @Override
    public void getClassifyData(final IModelCallback iModelCallback) {
        // TODO: 2020/1/3  调用api中的 分类接口
        NetUtil.getInstance().getApi().getClassifyData()
                //子线程联网
                .subscribeOn(Schedulers.io())
                //主线程观察
                .observeOn(AndroidSchedulers.mainThread())
                //订阅，传 Observer 接口回调
                .subscribe(new Observer<ClassifyBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ClassifyBean classifyBean) {
                        iModelCallback.onClassifySuccess(classifyBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        iModelCallback.onClassifyFailure(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 请求商品接口
     */
    @Override
    public void getCommodityData(String categoryId, final IModelCallback iModelCallback) {
        // TODO: 2020/1/3  调用api中的 商品接口
        NetUtil.getInstance().getApi().getCommodityData(categoryId, 1, 10)
                //子线程联网
                .subscribeOn(Schedulers.io())
                //主线程观察
                .observeOn(AndroidSchedulers.mainThread())
                //订阅，传 Observer 接口回调
                .subscribe(new Observer<CommodityBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CommodityBean commodityBean) {
                        iModelCallback.onCommoditySuccess(commodityBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        iModelCallback.onCommodityBeanFailure(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
