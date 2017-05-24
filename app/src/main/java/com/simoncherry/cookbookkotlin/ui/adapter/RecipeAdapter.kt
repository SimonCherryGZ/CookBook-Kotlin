package com.simoncherry.cookbookkotlin.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.simoncherry.cookbookkotlin.R
import com.simoncherry.cookbookkotlin.model.MobRecipe
import kotlinx.android.synthetic.main.item_recipe.view.*


/**
 * <pre>
 *     author : Donald
 *     e-mail : xxx@xx
 *     time   : 2017/05/23
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class RecipeAdapter(
        var mContext: Context,
        var mData: List<MobRecipe>) : RecyclerView.Adapter<RecipeAdapter.MyViewHolder>() {

    private val TXT_DEFAULT = "Null"

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val recipe = mData[position]
        val thumbnail = recipe.thumbnail
        if (thumbnail != null) {
            Glide.with(mContext).load(thumbnail)
                    .placeholder(R.drawable.default_img)
                    .error(R.drawable.default_img)
                    .into(holder.ivThumbnail)
        } else {
            holder.ivThumbnail.setImageResource(R.drawable.default_img)
        }

        val name = recipe.name
        holder.tvName.text = name ?: TXT_DEFAULT

        val detail = recipe.recipe
        if (detail != null) {
            val summary = detail.sumary
            holder.tvSummary.text = summary ?: TXT_DEFAULT

            var ingredients = detail.ingredients
            if (ingredients != null && !TextUtils.isEmpty(ingredients)) {
                ingredients = ingredients.replace("[", "")
                ingredients = ingredients.replace("]", "")
                ingredients = ingredients.replace("\"", "")
                holder.tvIngredients.text = ingredients
            }
        } else {
            holder.tvSummary.text = TXT_DEFAULT
            holder.tvIngredients.text = TXT_DEFAULT
        }

        holder.layoutRoot.setOnClickListener {
            onItemClickListener?.onItemClick(holder.ivThumbnail, position)
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(mContext.applicationContext)
                .inflate(R.layout.item_recipe, parent, false))
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