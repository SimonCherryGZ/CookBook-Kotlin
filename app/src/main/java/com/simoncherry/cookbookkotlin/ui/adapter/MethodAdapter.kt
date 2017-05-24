package com.simoncherry.cookbookkotlin.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.simoncherry.cookbookkotlin.R
import com.simoncherry.cookbookkotlin.loadUrl
import com.simoncherry.cookbookkotlin.model.MobRecipeMethod
import kotlinx.android.synthetic.main.item_method.view.*

/**
 * <pre>
 *     author : Donald
 *     e-mail : xxx@xx
 *     time   : 2017/05/24
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class MethodAdapter(
        val mContext: Context,
        val mData: List<MobRecipeMethod>) : RecyclerView.Adapter<MethodAdapter.MyViewHolder>(){

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(mContext.applicationContext)
                .inflate(R.layout.item_method, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val method = mData[position]
        holder.tvMethod.text = method.step ?: "不详"
        val url = method.img
        if (url != null) {
            holder.ivImg.visibility = View.VISIBLE
            holder.ivImg.loadUrl(url)
        } else {
            holder.ivImg.visibility = View.GONE
        }
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvMethod = view.tv_method!!
        val ivImg = view.iv_img!!
    }
}