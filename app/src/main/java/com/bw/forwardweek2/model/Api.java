package com.bw.forwardweek2.model;


import com.bw.forwardweek2.model.bean.ClassifyBean;
import com.bw.forwardweek2.model.bean.CommodityBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 注意 Observable 的导包， 必须是 io.
 */
public interface Api {
    @GET("small/commodity/v1/findCategory")
    Observable<ClassifyBean> getClassifyData();

    @GET("small/commodity/v1/findCommodityByCategory")
    Observable<CommodityBean> getCommodityData(@Query("categoryId") String categoryId, @Query("page") int page, @Query("count") int count);
}
