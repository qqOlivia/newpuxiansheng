package com.puxiansheng.www.ui.search

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.puxiansheng.logic.api.API
import com.puxiansheng.logic.bean.BusinessBean
import com.puxiansheng.logic.bean.InfoItem
import com.puxiansheng.logic.bean.Order
import com.puxiansheng.util.ext.SharedPreferencesUtil
import com.puxiansheng.www.R
import com.puxiansheng.www.app.MyBaseActivity
import com.puxiansheng.www.ui.business.*
import com.puxiansheng.www.ui.info.InfoDetailActivity
import com.puxiansheng.www.ui.info.InfoListAdapter
import com.puxiansheng.www.ui.info.InfoListViewModel
import com.puxiansheng.www.ui.order.*
import kotlinx.android.synthetic.main.activity_order_list.*
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.fragment_invest_business.*
import kotlinx.coroutines.launch


class SearchActivity : MyBaseActivity() {

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var orderOutViewModel: TransferOutOrdersViewModel
    private lateinit var orderInViewModel: TransferInOrdersViewModel
    private lateinit var businessViewModel: InvestBusnessViewModel
    var typeList = listOf("转铺", "找店", "加盟")

    override fun getLayoutId(): Int {
        return R.layout.activity_search
    }

    override fun business() {
        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        orderOutViewModel = ViewModelProvider(this)[TransferOutOrdersViewModel::class.java]
        orderInViewModel = ViewModelProvider(this)[TransferInOrdersViewModel::class.java]
        businessViewModel = ViewModelProvider(this)[InvestBusnessViewModel::class.java]

        searchViewModel.usrId = SharedPreferencesUtil.get(API.LOGIN_USER_ID,0).toString()

        lifecycleScope.launch {
//            history_list.removeAllViews()
//            searchViewModel.getHistorySearch()?.let { it ->
//                it.forEach { menuItem ->
//                    history_list.addView(Chip(history_list.context).apply {
//                        text = menuItem.keyword
//                    })
//                }
//            }

            searchViewModel.getRecommendSearch()?.let {
                it.forEach { menuItem ->
                    recommend_list.addView(Chip(recommend_list.context).apply {
                        text = menuItem.name

                        //TODO 2020.6.8
                        setOnClickListener {
                            search_content.setText(text)
                            hideKeyboard(search_content)
//                            layout_recommend.visibility = View.GONE
//                            search_refresh.visibility = View.VISIBLE
                            list.removeAllViews()
//                                1 -> orderOutViewModel.refresh(
//                                    SharedPreferencesUtil.get(API.USER_CITY_ID, 0).toString()
//                                )
//                                2-> orderInViewModel.refresh(
//                                    SharedPreferencesUtil.get(API.USER_CITY_ID, 0).toString()
//                                )
//                                3 -> businessViewModel.refresh()
//                            }
//                            getDateList()
                            when (searchViewModel.type) {
                                1 -> {
                                    val intent = Intent(this@SearchActivity, NewTransferOutOrdersActivity::class.java)
                                    intent.putExtra("title", searchViewModel.searchTitle)
                                    startActivity(intent)
                                }

                                2 -> {
                                    val intent = Intent(this@SearchActivity, NewTransferInOrdersActivity::class.java)
                                    intent.putExtra("title", searchViewModel.searchTitle)
                                    startActivity(intent)
                                }

                                4 -> {
                                    val intent = Intent(this@SearchActivity, InvestBusinessActivity::class.java)
                                    intent.putExtra("title", searchViewModel.searchTitle)
                                    startActivity(intent)
                                }
                            }
                        }
                    })

                }
//                Chip(recommend_list.context).apply {
//                    text = menuItem.name
//                }.setOnClickListener {
//                    Log.d("---Chip","=====OnClickListener "+menuItem.name)
//                    search_content.setText(menuItem.name)
//                }

//                recommend_list.setOnCheckedChangeListener { chipGroup, selectedId ->
//                    search_content.setText(it.get(selectedId).name)
//                    Toast.makeText(this@SearchActivity, hintStr, Toast.LENGTH_SHORT).show()
//                }
            }
        }




        bt_cancel.setOnClickListener {
            search_content.setText("")
            orderOutViewModel.title = ""
            orderInViewModel.title = ""
            businessViewModel.title = ""
            if (list.isVisible) {
                list.removeAllViews()
                layout_recommend.visibility = View.VISIBLE
                search_refresh.visibility = View.GONE
            }
            finish()
        }

        search_content.addTextChangedListener {
            when (searchViewModel.type) {
                1 -> orderOutViewModel.title = it.toString()
                2 -> orderInViewModel.title = it.toString()
                4 -> businessViewModel.title = it.toString()
            }

            searchViewModel.searchTitle = it.toString()
        }

        search_content.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyboard(search_content)
//                layout_recommend.visibility = View.GONE
//                search_refresh.visibility = View.VISIBLE
                list.removeAllViews()
                when (searchViewModel.type) {
                    1 -> {
                        val intent = Intent(this, NewTransferOutOrdersActivity::class.java)
                        intent.putExtra("title", searchViewModel.searchTitle)
                        startActivity(intent)
                    }

                    2 -> {
                        val intent = Intent(this, NewTransferInOrdersActivity::class.java)
                        intent.putExtra("title", searchViewModel.searchTitle)
                        startActivity(intent)
                    }

                    4 -> {
                        val intent = Intent(this, InvestBusinessActivity::class.java)
                        intent.putExtra("title", searchViewModel.searchTitle)
                        startActivity(intent)
                    }

//                    0 -> orderOutViewModel.refresh(
//                        SharedPreferencesUtil.get(API.USER_CITY_ID, 0).toString()
//                    )
//                    1 -> orderInViewModel.refresh(
//                        SharedPreferencesUtil.get(API.USER_CITY_ID, 0).toString()
//                    )
//                    2 -> businessViewModel.refresh()
                }
//                getDateList()
                return@OnEditorActionListener true
            }
            false
        })

        DividerItemDecoration(this, DividerItemDecoration.VERTICAL).let {
            it.setDrawable(resources.getDrawable(R.drawable.recyclerview_divider_order, null))
            list.addItemDecoration(it)
        }

//        search_refresh.setOnRefreshListener {
//            list.removeAllViews()
//            when (searchViewModel.type) {
//                1 -> orderOutViewModel.refresh(
//                    SharedPreferencesUtil.get(API.USER_CITY_ID, 0).toString()
//                )
//                2 -> orderInViewModel.refresh(
//                    SharedPreferencesUtil.get(API.USER_CITY_ID, 0).toString()
//                )
//                3 -> businessViewModel.refresh()
//            }
//            search_refresh.isRefreshing = false
//            getDateList()
//        }

        list.layoutManager = LinearLayoutManager(this@SearchActivity)

        var arrayAdapter = ArrayAdapter(this, R.layout.item_select, typeList)
        type_spinner.adapter = arrayAdapter
        arrayAdapter.setDropDownViewResource(R.layout.item_pulldown_select)

        bt_delete.setOnClickListener {
            searchViewModel.deleteHistorySearch()
        }

        searchViewModel.toastMsg.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        searchViewModel.deleteResult.observe(this, Observer {
            if (it == API.CODE_SUCCESS) {
                history_list.removeAllViews()
            }
        })

        type_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                //切换选择
                if(position == 2){
                    searchViewModel.type = position + 2
                }else {
                    searchViewModel.type = position + 1
                }
                Log.d("---search", "  searchViewModel.type = " + searchViewModel.type)
                list.removeAllViews()
                layout_recommend.visibility = View.VISIBLE
                lifecycleScope.launch {
                    history_list.removeAllViews()
                    searchViewModel.getHistorySearch()?.let { it ->
                        it.forEach { menuItem ->
                            history_list.addView(Chip(history_list.context).apply {
                                text = menuItem.keyword
                            })
                        }
                    }
                }


            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }


//    fun getDateList() {
//        if (searchViewModel.type == 0) {
//            list.adapter = OrdersAdapter(
//                type = Order.Type.TRANSFER_OUT.value(),
//                onItemSelect = {
//                    val intent =
//                        Intent(this@SearchActivity, TransferOutOrderDetailActivity::class.java)
//                    intent.putExtra("shopID", it?.shop?.jump_param)
//                    startActivity(intent)
//
//                }
//            ).apply {
//                LivePagedListBuilder<Int, Order>(
//                    orderOutViewModel.getTransferOutOrdersFromLocal(),
//                    PagedList.Config.Builder()
//                        .setEnablePlaceholders(true)
//                        .setPageSize(10)
//                        .setInitialLoadSizeHint(20)
//                        .build()
//                ).let { pageBuilder ->
//                    pageBuilder.setBoundaryCallback(object : PagedList.BoundaryCallback<Order>() {
//                        override fun onItemAtEndLoaded(itemAtEnd: Order) {
//                            super.onItemAtEndLoaded(itemAtEnd)
//                            orderOutViewModel.loadMore()
//                        }
//                    })
//                    addLoadStateListener { loadType, _, _ ->
//                        if (loadType == PagedList.LoadType.END) {
//                            if (getDataCount() == 0) {
//                                type = Order.Type.EMPTY.value()
//                                notifyDataSetChanged()
//                                orderOutViewModel.refresh(
//                                    SharedPreferencesUtil.get(API.USER_CITY_ID, 0).toString()
//                                )
//                            }
//                        }
//                        if (loadType == PagedList.LoadType.REFRESH) {
//                            if (type != Order.Type.TRANSFER_OUT.value()) {
//                                type = Order.Type.TRANSFER_OUT.value()
//                                notifyDataSetChanged()
//                            }
//                        }
//                    }
//
//                    pageBuilder.build().observe(this@SearchActivity, Observer {
//                        submitList(it)
//                        notifyDataSetChanged()
//                    })
//                }
//            }
//        } else if (searchViewModel.type == 1) {
//            list.adapter = OrdersAdapter(
//                type = Order.Type.TRANSFER_IN.value(),
//                onItemSelect = {
//                    val intent = Intent(this, TransferInOrderDetailActivity::class.java)
//                    intent.putExtra("shopID", it?.shop?.jump_param)
//                    startActivity(intent)
//                }
//            ).apply {
//                LivePagedListBuilder<Int, Order>(
//                    orderInViewModel.getTransferInOrdersFromLocal(),
//                    PagedList.Config.Builder()
//                        .setEnablePlaceholders(true)
//                        .setPageSize(10)
//                        .setInitialLoadSizeHint(20)
//                        .build()
//                ).let { pageBuilder ->
//                    pageBuilder.setBoundaryCallback(object : PagedList.BoundaryCallback<Order>() {
//                        override fun onItemAtEndLoaded(itemAtEnd: Order) {
//                            super.onItemAtEndLoaded(itemAtEnd)
//                            orderInViewModel.loadMore()
//                        }
//                    })
//                    addLoadStateListener { loadType, _, _ ->
//                        if (loadType == PagedList.LoadType.END) {
//                            if (getDataCount() == 0) {
//                                type = Order.Type.EMPTY.value()
//                                notifyDataSetChanged()
//                                orderInViewModel.refresh(
//                                    SharedPreferencesUtil.get(API.USER_CITY_ID, 0).toString()
//                                )
//                            }
//                        }
//                        if (loadType == PagedList.LoadType.REFRESH) {
//                            if (type != Order.Type.TRANSFER_IN.value()) {
//                                type = Order.Type.TRANSFER_IN.value()
//                                notifyDataSetChanged()
//                            }
//                        }
//                    }
//
//                    pageBuilder.build().observe(this@SearchActivity, Observer {
//                        submitList(it)
//                        notifyDataSetChanged()
//                    })
//                }
//            }
//        } else if (searchViewModel.type == 2) {
//            BusinessAdapter(onItemSelect = { info ->
//                val intent = Intent(
//                    this@SearchActivity,
//                    InvestBusinessDetailActivity::class.java
//                )
//                intent.putExtra("id", info?.id)
//                startActivity(intent)
//            },
//                onItemCall = {
//                    ConsultDialog(it?.id.toString()).show(
//                        supportFragmentManager,
//                        ConsultDialog::class.java.name
//                    )
//                }).let { adapter ->
//                list.adapter = adapter
//                lifecycleScope.launch {
//                    LivePagedListBuilder<Int, BusinessBean>(
//                        businessViewModel.getBusinesssFromLocal(),
//                        5
//                    ).apply {
//                        setBoundaryCallback(object : PagedList.BoundaryCallback<BusinessBean>() {
//                            override fun onItemAtEndLoaded(itemAtEnd: BusinessBean) {
//                                super.onItemAtEndLoaded(itemAtEnd)
//                                businessViewModel.loadMore(
//                                )
//                            }
//                        })
//                    }.build().observe(this@SearchActivity, Observer {
//                        adapter.submitList(it)
//                    })
//                }
//            }
//        }
//    }

    fun hideKeyboard(view: View) {
        val manager: InputMethodManager =
            view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}