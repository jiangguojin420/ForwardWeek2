package com.bw.forwardweek2.base;

/**
 * 1、 attach /  detach
 * 2、构造器中  initModel
 */
public abstract class BasePresenter<V> {
    protected V view;

    public void attach(V view) {
        this.view = view;
    }

    public void detach() {
        view = null;
    }

    public BasePresenter() {
        initModel();
    }

    protected abstract void initModel();
}
