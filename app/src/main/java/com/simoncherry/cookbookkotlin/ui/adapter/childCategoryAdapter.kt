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
import kotlinx.android.synthetic.main.item_tag.view.*

/**
 * <pre>
 *     author : Donald
 *     e-mail : xxx@xx
 *     time   : 2017/05/23
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class ChildCategoryAdapter(
        var mContext: Context,
        var mData: List<MobCategory>) : RecyclerView.Adapter<ChildCategoryAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(mContext.applicationContext)
                .inflate(R.layout.item_tag, parent, false))
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val mobCategory = mData[position]
        holder.tvName.text = mobCategory.name

        holder.layoutRoot.setOnClickListener {
            onItemClickListener?.onItemClick(position)
        }
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