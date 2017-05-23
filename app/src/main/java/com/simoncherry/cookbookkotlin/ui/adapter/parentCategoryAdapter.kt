package com.simoncherry.cookbookkotlin.ui.adapter

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.simoncherry.cookbookkotlin.R
import com.simoncherry.cookbookkotlin.model.MobCategory
import kotlinx.android.synthetic.main.item_category.view.*

/**
 * <pre>
 *     author : Donald
 *     e-mail : xxx@xx
 *     time   : 2017/05/23
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class parentCategoryAdapter(
        var mContext: Context,
        var mData: List<MobCategory>) : RecyclerView.Adapter<parentCategoryAdapter.MyViewHolder>() {

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val mobCategory = mData[position]
        holder.tvName.text = mobCategory.name

        if (mobCategory.isSelected) {
            holder.layoutRoot.setBackgroundColor(Color.WHITE)
        } else {
            holder.layoutRoot.setBackgroundColor(ContextCompat.getColor(mContext, R.color.gray_inactive))
        }

        holder.layoutRoot.setOnClickListener {
            for (item in mData) {
                item.isSelected = item.ctgId == mobCategory.ctgId
            }
            notifyDataSetChanged()
            onItemClickListener?.onItemClick(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(mContext.applicationContext)
                .inflate(R.layout.item_category, parent, false))
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val layoutRoot = view.layout_root!!
        val tvName = view.tv_name!!
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }
}