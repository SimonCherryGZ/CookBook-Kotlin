package com.simoncherry.cookbookkotlin.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.simoncherry.cookbookkotlin.R
import com.simoncherry.cookbookkotlin.loadUrl
import com.simoncherry.cookbookkotlin.model.RealmCollection
import kotlinx.android.synthetic.main.item_collection.view.*

/**
 * <pre>
 *     author : Donald
 *     e-mail : xxx@xx
 *     time   : 2017/05/24
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class CollectionAdapter(
        var mContext: Context,
        var mData: List<RealmCollection>)  : RecyclerView.Adapter<CollectionAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(mContext.applicationContext)
                .inflate(R.layout.item_collection, parent, false))
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val recipe : RealmCollection = mData[position]
        holder.ivThumbnail.loadUrl(recipe.thumbnail)
        holder.tvName.text = recipe.name
        holder.tvSummary.text = recipe.summary
        holder.tvIngredients.text = recipe.ingredients

        holder.layoutRoot.setOnClickListener {
            onItemClickListener?.onItemClick(holder.ivThumbnail, position)
        }
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val layoutRoot = view.layout_root!!
        val ivThumbnail = view.iv_thumbnail!!
        val tvName = view.tv_name!!
        val tvSummary = view.tv_summary!!
        val tvIngredients = view.tv_ingredients!!
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }
}