package com.bw.forwardweek2.contract;

import com.bw.forwardweek2.model.bean.ClassifyBean;
import com.bw.forwardweek2.model.bean.CommodityBean;

public interface IClassifyContract {
    interface IView {

        //左侧分类的成功
        void onClassifySuccess(ClassifyBean classifyBean);

        void onClassifyFailure(Throwable throwable);

        //右侧商品的成功
        void onCommoditySuccess(CommodityBean commodityBean);

        void onCommodityBeanFailure(Throwable throwable);
    }

    interface IPresenter {
        //第一个分类接口
        void getClassifyData();

        //第二个商品接口
        void getCommodityData(String categoryId);
    }

    interface IModel {
        //第一个分类接口
        void getClassifyData(IModelCallback iModelCallback);
        //第二个商品接口
        void getCommodityData(String categoryId, IModelCallback iModelCallback);

        interface IModelCallback {

            //左侧分类的成功
            void onClassifySuccess(ClassifyBean classifyBean);

            void onClassifyFailure(Throwable throwable);

            //右侧商品的成功
            void onCommoditySuccess(CommodityBean commodityBean);

            void onCommodityBeanFailure(Throwable throwable);
        }
    }
}
