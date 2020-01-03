package com.bw.forwardweek2.presenter;

import com.bw.forwardweek2.base.BasePresenter;
import com.bw.forwardweek2.contract.IClassifyContract;
import com.bw.forwardweek2.model.ClassifyModel;
import com.bw.forwardweek2.model.bean.ClassifyBean;
import com.bw.forwardweek2.model.bean.CommodityBean;

/**
 * 继承 BasePresenter
 * 指定 V 类型 为IView
 * 实现 IPrensenter
 */
public class ClassifyPresenter extends BasePresenter<IClassifyContract.IView> implements IClassifyContract.IPresenter {

    private ClassifyModel classifyModel;

    @Override
    protected void initModel() {
        classifyModel = new ClassifyModel();
    }

    @Override
    public void getClassifyData() {
        classifyModel.getClassifyData(new IClassifyContract.IModel.IModelCallback() {
            @Override
            public void onClassifySuccess(ClassifyBean classifyBean) {
                view.onClassifySuccess(classifyBean);
            }

            @Override
            public void onClassifyFailure(Throwable throwable) {
                view.onClassifyFailure(throwable);
            }

            @Override
            public void onCommoditySuccess(CommodityBean commodityBean) {
                view.onCommoditySuccess(commodityBean);
            }

            @Override
            public void onCommodityBeanFailure(Throwable throwable) {
                view.onCommodityBeanFailure(throwable);
            }
        });
    }

    @Override
    public void getCommodityData(String categoryId) {
        classifyModel.getCommodityData(categoryId, new IClassifyContract.IModel.IModelCallback() {
            @Override
            public void onClassifySuccess(ClassifyBean classifyBean) {
                view.onClassifySuccess(classifyBean);
            }

            @Override
            public void onClassifyFailure(Throwable throwable) {
                view.onClassifyFailure(throwable);
            }

            @Override
            public void onCommoditySuccess(CommodityBean commodityBean) {
                view.onCommoditySuccess(commodityBean);
            }

            @Override
            public void onCommodityBeanFailure(Throwable throwable) {
                view.onCommodityBeanFailure(throwable);
            }
        });
    }
}
