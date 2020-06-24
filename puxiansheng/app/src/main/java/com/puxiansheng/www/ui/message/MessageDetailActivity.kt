package com.puxiansheng.www.ui.message


import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.puxiansheng.logic.api.API
import com.puxiansheng.www.R
import com.puxiansheng.www.app.MyBaseActivity
import com.puxiansheng.www.ui.business.BusinessListActivity
import com.puxiansheng.www.ui.info.InfoDetailActivity
import com.puxiansheng.www.ui.main.HomeActivity
import com.puxiansheng.www.ui.main.MainActivity
import com.puxiansheng.www.ui.mine.setting.AboutUsActivity
import com.puxiansheng.www.ui.order.*
import com.puxiansheng.www.ui.release.InsertOrUpdateTransferInOrderActivity
import com.puxiansheng.www.ui.release.InsertOrUpdateTransferOutOrderActivity
import com.puxiansheng.www.ui.release.fasttransfer.FastTransferInActivity
import com.puxiansheng.www.ui.release.fasttransfer.FastTransferOutActivity
import kotlinx.android.synthetic.main.fragment_message_detail.*
import kotlinx.coroutines.launch

class MessageDetailActivity() : MyBaseActivity() {
    private lateinit var viewModel: MessageDetailViewModel
    var noticeId =" "

    override fun getLayoutId(): Int {
        return R.layout.fragment_message_detail
    }

    @SuppressLint("Range")
    override fun business() {
        noticeId = intent.getStringExtra("noticeId")
        viewModel = ViewModelProvider(this)[MessageDetailViewModel::class.java]
        button_back.setOnClickListener {
            onBackPressed()
        }

        lifecycleScope.launch {
            viewModel.getMessageDetailFromRemote(noticeId)?.let {
                if (it.code == API.CODE_SUCCESS && it.data != null) {
                    sub_title.text = it?.data?.messageDetail?.title
                    content.text = it?.data?.messageDetail?.content
                    time.text = it?.data?.messageDetail?.view_time
                    if (it?.data?.messageDetail?.buttonList?.size == 0) {
                        button_one.visibility = View.GONE
                    } else {
                        var menuItem = it?.data?.messageDetail?.buttonList?.get(0)
                        button_one.visibility = View.VISIBLE
                        button_one.text = menuItem?.btText ?: "查看"
                        button_one.setTextColor(Color.parseColor(menuItem?.color))
                        button_one.setOnClickListener {
                            when (menuItem?.jump_type) {
                                1 -> {
                                    when (menuItem.jump_view) {

                                        "transfer_list" -> {
                                            val intent = Intent(
                                                this@MessageDetailActivity,
                                                NewTransferOutOrdersActivity::class.java
                                            )
                                            intent.putExtra("title", "*")
                                            startActivity(intent)
                                        }
                                        "find_list" -> {
                                            val intent = Intent(
                                                this@MessageDetailActivity,
                                                NewTransferInOrdersActivity::class.java
                                            )
                                            intent.putExtra("title", "*")
                                            startActivity(intent)
                                        }

                                        "activity_list" -> {//文章列表
                                            val intent = Intent(this@MessageDetailActivity, HomeActivity::class.java)
                                            intent.putExtra("name", "5")
                                            startActivity(intent)
                                        }
                                        "user_center" -> {

                                        }
                                        "shop_success" -> {//成功案例
                                            val intent = Intent(this@MessageDetailActivity, NewTransferSuccessOrdersActivity::class.java)
                                            startActivity(intent)
                                        }
                                        "join_list" ->{
                                            val intent = Intent(this@MessageDetailActivity, BusinessListActivity::class.java)
                                            startActivity(intent)
                                        }

                                        "quick_transfer" ->{
                                            val intent = Intent(this@MessageDetailActivity, FastTransferOutActivity::class.java)
                                            startActivity(intent)
                                        }

                                        "quick_find" ->{
                                            val intent = Intent(this@MessageDetailActivity, FastTransferInActivity::class.java)
                                            startActivity(intent)
                                        }
                                        "about_us" ->{
                                            val intent = Intent(this@MessageDetailActivity, AboutUsActivity::class.java)
                                            startActivity(intent)
                                        }

                                    }
                                }
                                2 -> {//打开链接
                                    val intent = Intent(Intent.ACTION_VIEW)
                                    intent.data = Uri.parse(menuItem.jump_param)
                                    startActivity(intent)
                                }
                                3 -> {//找店详情

                                    //TODO
                                  // val intent = Intent(this@MessageDetailActivity, TransferInOrdersActivity::class.java)
                                    val intent = Intent(this@MessageDetailActivity, TransferInOrderDetailActivity::class.java)
                                    intent.putExtra("shopID", menuItem.jump_param)
                                    startActivity(intent)
                                }
                                4 -> {//转铺详情
                                    val intent = Intent(this@MessageDetailActivity,
                                        TransferOutOrderDetailActivity::class.java
                                    )
                                    intent.putExtra("shopID", menuItem.jump_param)
                                    startActivity(intent)
                                }
                                5 -> {//文章详情
                                    val intent = Intent(this@MessageDetailActivity,
                                        InfoDetailActivity::class.java
                                    )
                                    intent.putExtra("url", menuItem.jump_param)
                                    startActivity(intent)
                                }
                                6 ->{
                                    val intent = Intent(this@MessageDetailActivity,
                                        InsertOrUpdateTransferOutOrderActivity::class.java
                                    )
                                    intent.putExtra("shopID", menuItem.jump_param)
                                    startActivity(intent)

                                }
                                7->{
                                    val intent = Intent(this@MessageDetailActivity,
                                        InsertOrUpdateTransferInOrderActivity::class.java
                                    )
                                    intent.putExtra("shopID", menuItem.jump_param)
                                    startActivity(intent)
                                }
                                8 ->{
                                    val intent = Intent(this@MessageDetailActivity,
                                        NewTransferSuccessOrdersActivity::class.java
                                    )
                                    intent.putExtra("shopID", menuItem.jump_param)
                                    startActivity(intent)
                                }
                            }
                        }
                    }
                }else{
                    Toast.makeText(this@MessageDetailActivity, it.msg, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}