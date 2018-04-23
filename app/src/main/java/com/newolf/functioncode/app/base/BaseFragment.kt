package com.newolf.functioncode.app.base

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.newolf.functioncode.R
import kotlinx.android.synthetic.main.fragment_base.*

/**
 * ================================================
 * @author : NeWolf
 * @version : 1.0
 * @date :  2018/3/23
 * 描述:
 * 历史:<br/>
 * ================================================
 */
abstract class BaseFragment : Fragment(), IBaseView {
    var mContext: Context? = null
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mContext = context
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(bindLayout(), container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        loadData()
        initListener()
    }

    override fun bindLayout(): Int {
        return R.layout.fragment_base
    }

    final override fun getExtras() {
    }

    override fun initView() {
        tvShow?.setText(javaClass.simpleName)
    }

    override fun loadData() {
    }

    override fun initListener() {
    }
}