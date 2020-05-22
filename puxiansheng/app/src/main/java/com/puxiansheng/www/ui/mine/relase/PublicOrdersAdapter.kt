package com.puxiansheng.www.ui.mine.relase

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.puxiansheng.logic.bean.Order
import com.puxiansheng.www.R
import com.puxiansheng.www.common.url
import com.puxiansheng.www.databinding.*
import kotlinx.android.extensions.LayoutContainer


class PublicOrdersAdapter(
    //定义加载数据显示的几种状态
    var type: Int,
    private val TYPE_LOAD_ALL_COMPLETE: Int = 1003,
    private val onItemSelect: ((order: Order?) -> Unit)? = null,
    private val onEdit: ((order: Order?) -> Unit)? = null,
    private val onDelete: ((order: Order?) -> Unit)? = null,
    private val onRefresh: ((order: Order?) -> Unit)? = null
) : PagedListAdapter<Order, PublicOrdersAdapter.OrderViewHolder>(Order.DIFF) {
    private var dataList: PagedList<Order>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder =
        LayoutInflater.from(parent.context).inflate(viewType, parent, false).let {
            when (viewType) {
                R.layout.fragment_order_list_transfer_out_item -> TransferOutOrderViewHolder(it)
                R.layout.fragment_orders_mine_transfer_out_item -> MineTransferOutOrderViewHolder(it)
                R.layout.fragment_favor_order_list_transfer_out_item ->FavorTransferOutOrderViewHolder(it)
                R.layout.fragment_favor_order_list_transfer_in_item ->FavorTransferInOrderViewHolder(it)
                R.layout.fragment_order_list_transfer_in_item -> TransferInOrderViewHolder(it)
                R.layout.fragment_orders_mine_transfer_in_item -> MineTransferInOrderViewHolder(it)
                else -> EmptyOrderViewHolder(it)
            }
        }


    fun getDataCount(): Int {
        return dataList?.size ?: 0
    }

    override fun submitList(pagedList: PagedList<Order>?) {
        dataList = pagedList!!
        super.submitList(pagedList)
    }

    override fun getItemCount(): Int {
        if (type == Order.Type.EMPTY.value()) return 1 + super.getItemCount()
        return super.getItemCount()
    }

    override fun getItemViewType(position: Int): Int {

        if (type == Order.Type.EMPTY.value() && position == itemCount - 1)
            return R.layout.fragment_order_list_empty

        return when (type) {
            Order.Type.TRANSFER_OUT.value() -> R.layout.fragment_order_list_transfer_out_item
            Order.Type.TRANSFER_OUT_PRIVATE.value() -> R.layout.fragment_orders_mine_transfer_out_item

            Order.Type.TRANSFER_IN.value() -> R.layout.fragment_order_list_transfer_in_item
            Order.Type.TRANSFER_IN_PRIVATE.value() -> R.layout.fragment_orders_mine_transfer_in_item

            Order.Type.TRANSFER_OUT_HISTORY.value() -> R.layout.fragment_order_list_transfer_out_item
            Order.Type.TRANSFER_IN_HISTORY.value() -> R.layout.fragment_order_list_transfer_in_item

            Order.Type.TRANSFER_OUT_FAVORITE.value() -> R.layout.fragment_favor_order_list_transfer_out_item
            Order.Type.TRANSFER_IN_FAVORITE.value() -> R.layout.fragment_favor_order_list_transfer_in_item
            else -> R.layout.fragment_order_list_empty
        }
    }

    override fun onBindViewHolder(
        holder: OrderViewHolder,
        position: Int
    ) {
        if (type != Order.Type.EMPTY.value())
            holder.bind(getItem(position))
    }


    abstract inner class OrderViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        abstract fun bind(item: Order?)
    }

    inner class LoadingViewHolder(
        override val containerView: View
    ) : OrderViewHolder(containerView) {

        override fun bind(item: Order?) {

        }
    }


    inner class TransferOutOrderViewHolder(
        override val containerView: View
    ) : OrderViewHolder(containerView) {
        private val binding = FragmentOrderListTransferOutItemBinding.bind(containerView)

        @SuppressLint("SetTextI18n")
        override fun bind(item: Order?) {
            item?.shop?.title.let { title ->
                binding.title.text = title
            }


            item?.shop?.images?.get(0)?.let { url ->
                binding.shopIcon.url(url)
            }

            item?.shop?.formattedDate?.let { date ->
                binding.date.text = date
            }

            item?.shop?.formattedRent?.let { rent ->
                binding.rent.text = rent
            }

            item?.shop?.formattedSize?.let { size ->
                item.shop?.formattedFee?.let { fee ->
                    binding.size.text = "$size"
                }
            }

            item?.shop?.formattedArea?.let { area ->
                binding.area.text= area
            }

            item?.shop?.formattedFinalIndustry?.let { industry ->
                if (industry.isNotEmpty()) binding.industry.visibility = View.VISIBLE
                binding.industry.text = industry
            }

//            item?.shop?.formattedFinalLocationNode?.let { location ->
//                if (location.isNotEmpty()) binding.area.visibility = View.VISIBLE
//                binding.area.text = location
//            }

            binding.root.setOnClickListener {
                onItemSelect?.let { select -> select(item) }
            }
        }
    }

    inner class TransferInOrderViewHolder(
        override val containerView: View
    ) : OrderViewHolder(containerView) {
        private val binding = FragmentOrderListTransferInItemBinding.bind(containerView)

        @SuppressLint("SetTextI18n")
        override fun bind(item: Order?) {
            item?.shop?.title?.let {
                binding.title.text = it
            }
            Log.d("industy","formattedIndustry 22 = "+ item?.shop?.formattedIndustry+"   indusry = "+item?.shop?.industry+"  finalindustry = "+item?.shop?.formattedFinalIndustry)
            item?.shop?.formattedFinalLocationNode?.let { area ->
                binding.area.text = area
            }

            item?.shop?.formattedIndustry?.let { industry ->
                binding.industry.text = industry
            }

            item?.shop?.formattedRent?.let { rent ->
                binding.rent.text = rent
            }

            item?.shop?.formattedSize?.let { size ->
                binding.size.text = size
            }

            item?.shop?.formattedDate?.let { date ->
                binding.date.text = date
            }

            binding.root.setOnClickListener {
                onItemSelect?.let { select -> select(item) }
            }
        }
    }



    inner class FavorTransferOutOrderViewHolder(
        override val containerView: View
    ) : OrderViewHolder(containerView) {
        private val binding = FragmentFavorOrderListTransferOutItemBinding.bind(containerView)

        @SuppressLint("SetTextI18n")
        override fun bind(item: Order?) {
            item?.shop?.title.let { title ->
                binding.title.text = title
            }


            item?.shop?.images?.get(0)?.let { url ->
                binding.shopIcon.url(url)
            }

            item?.shop?.formattedDate?.let { date ->
                binding.date.text = date
            }

            item?.shop?.formattedRent?.let { rent ->
                binding.rent.text = rent
            }

            item?.shop?.formattedSize?.let { size ->
                item.shop?.formattedFee?.let { fee ->
                    binding.size.text = "$size"
                }
            }

            item?.shop?.formattedArea?.let { area ->
                binding.area.text= area
            }

            item?.shop?.formattedFinalIndustry?.let { industry ->
                if (industry.isNotEmpty()) binding.industry.visibility = View.VISIBLE
                binding.industry.text = industry
            }

//            item?.shop?.formattedFinalLocationNode?.let { location ->
//                if (location.isNotEmpty()) binding.area.visibility = View.VISIBLE
//                binding.area.text = location
//            }

            binding.root.setOnClickListener {
                onItemSelect?.let { select -> select(item) }
            }
        }
    }

    inner class FavorTransferInOrderViewHolder(
        override val containerView: View
    ) : OrderViewHolder(containerView) {
        private val binding = FragmentFavorOrderListTransferInItemBinding.bind(containerView)

        @SuppressLint("SetTextI18n")
        override fun bind(item: Order?) {
            item?.shop?.title?.let {
                binding.title.text = it
            }
            Log.d("industy","formattedIndustry 22 = "+ item?.shop?.formattedIndustry+"   indusry = "+item?.shop?.industry+"  finalindustry = "+item?.shop?.formattedFinalIndustry)
            item?.shop?.formattedFinalLocationNode?.let { area ->
                binding.area.text = area
            }

            item?.shop?.formattedIndustry?.let { industry ->
                binding.industry.text = industry
            }

            item?.shop?.formattedRent?.let { rent ->
                binding.rent.text = rent
            }

            item?.shop?.formattedSize?.let { size ->
                binding.size.text = size
            }

            item?.shop?.formattedDate?.let { date ->
                binding.date.text = date
            }

            binding.root.setOnClickListener {
                onItemSelect?.let { select -> select(item) }
            }
        }
    }

    inner class MineTransferOutOrderViewHolder(
        override val containerView: View
    ) : OrderViewHolder(containerView) {
        private val binding = FragmentOrdersMineTransferOutItemBinding.bind(containerView)

        @SuppressLint("SetTextI18n", "Range")
        override fun bind(item: Order?) {
            item?.shop?.title.let { title ->
                binding.title.text = title
            }

            item?.shop?.images?.get(0)?.let { url ->
                binding.shopIcon.url(url)
            }

            item?.shop?.formattedDate?.let { date ->
                binding.date.text = date
            }

            item?.shop?.formattedRent?.let { rent ->
                binding.rent.text = rent
            }

            item?.shop?.formattedSize?.let { size ->
                binding.size.text = size
            }



            item?.shop?.formattedFinalIndustry?.let { industry ->
                if (industry.isNotEmpty()) binding.industry.visibility = View.VISIBLE
                binding.industry.text = industry
            }

//            item?.shop?.formattedFinalLocationNode?.let { location ->
//                if (location.isNotEmpty()) binding.area.visibility = View.VISIBLE
//                binding.area.text = location
//            }
            item?.shop?.formattedArea?.let { area ->
                binding.area.text= area
            }


            binding.btEdit.setOnClickListener {
                onEdit?.let { onEdit -> onEdit(item) }
            }

            binding.btDelete.setOnClickListener {
                onDelete?.let { onDelete -> onDelete }
            }

            binding.root.setOnClickListener {
                onItemSelect?.let { select -> select(item) }
            }
        }
    }

    inner class MineTransferInOrderViewHolder(
        override val containerView: View
    ) : OrderViewHolder(containerView) {
        private val binding = FragmentOrdersMineTransferInItemBinding.bind(containerView)

        @SuppressLint("SetTextI18n", "Range")
        override fun bind(item: Order?) {
            item?.shop?.title?.let {
                binding.title.text = it
            }

            item?.shop?.formattedFinalLocationNode?.let { area ->
                binding.area.text = area
            }

            Log.d("industy","formattedIndustry = "+ item?.shop?.formattedIndustry+"   indusry = "+item?.shop?.industry+"  finalindustry = "+item?.shop?.formattedFinalIndustry)
            item?.shop?.formattedIndustry?.let { industry ->
                binding.industry.text = industry
            }

            item?.shop?.formattedRent?.let { rent ->
                binding.rent.text = rent
            }

            item?.shop?.formattedSize?.let { size ->
                binding.size.text = size
            }

            item?.shop?.formattedDate?.let { date ->
                binding.date.text = date
            }

            binding.btDelete.setOnClickListener {
                onDelete?.let { onDelete -> onDelete(item) }
            }
            binding.btEdit.setOnClickListener {
                onEdit?.let { onEdit -> onEdit(item) }
            }

            binding.root.setOnClickListener {
                onItemSelect?.let { select -> select(item) }
            }
        }
    }

    inner class EmptyOrderViewHolder(
        override val containerView: View
    ) : OrderViewHolder(containerView) {

        override fun bind(item: Order?) {

        }
    }
//
//    fun drawLableBg(){
//        val mutableBitmap: Bitmap = imageBitmap.copy(Bitmap.Config.ARGB_8888, true)
//        val canvas = Canvas(mutableBitmap)
//        var paint = Paint
//        paint.setStyle(Paint.Style.STROKE)//空心矩形框
//        paint.setColor(Color.RED);
//        canvas.drawRect(RectF(10F, 10F, 300F, 100F), paint)
//    }
}
