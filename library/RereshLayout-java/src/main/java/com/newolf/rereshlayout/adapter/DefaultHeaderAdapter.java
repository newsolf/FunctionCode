package com.newolf.rereshlayout.adapter;

import android.content.Context;
import android.view.View;

/**
 * ================================================
 *
 * @author : NeWolf
 * @version : 1.0
 * @date :  2018/5/9
 * 描述:
 * 历史:<br/>
 * ================================================
 */
public class DefaultHeaderAdapter extends BaseHeaderAdapter {
    public DefaultHeaderAdapter(Context context) {
        super(context);
    }

    @Override
    public View getHeaderView() {
        return null;
    }

    @Override
    public void pullViewToRefresh(int distY) {

    }

    @Override
    public void releaseViewToRefresh(int distY) {

    }

    @Override
    public void headerRefreshing() {

    }

    @Override
    public void headerRefreshComplete() {

    }
}
