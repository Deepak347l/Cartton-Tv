package com.deepakdevloper.cartoontv.Adapter

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.deepakdevloper.cartoontv.CartoonPage
import com.deepakdevloper.cartoontv.Model.TopCatoon
import com.deepakdevloper.cartoontv.R


class TopCartoonAdapter(context: Context, categoryList: ArrayList<TopCatoon>,cartoonshowbtn: Cartoonshowbtn) :
    RecyclerView.Adapter<TopCartoonAdapter.TopCartoonViewHolder>() {
    private val context: Context
    var categoryList: ArrayList<TopCatoon>
    private val cartoonshowbtn: Cartoonshowbtn
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopCartoonViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.cartoon_row_items, parent, false)


        // now here we create a recyclerview row items.
        return TopCartoonViewHolder(view)
    }

    override fun onBindViewHolder(holder: TopCartoonViewHolder, position: Int) {

        // here we will bind data in recyclerview ro items.
        holder.categoryName.setText(categoryList[position].cartoonName)
        holder.totalCategory.setText(categoryList[position].episodes)

        // for image we need to add glide image fetching library from netwok
        Glide.with(context).load(categoryList[position].imgUrl)
            .into(holder.categoryImage)
        holder.itemView.setOnClickListener{cartoonshowbtn.onShowClick(position,categoryList[position],holder)}
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    fun filterList(filteredNames: ArrayList<TopCatoon>) {
        this.categoryList = filteredNames
        notifyDataSetChanged()
    }

    class TopCartoonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var categoryImage: ImageView
        var categoryName: TextView
        var totalCategory: TextView

        init {
            categoryImage = itemView.findViewById(R.id.course)
            categoryName = itemView.findViewById(R.id.course_name)
            totalCategory = itemView.findViewById(R.id.total_course)
        }
    }

    interface Cartoonshowbtn{
        fun onShowClick(position: Int, model:TopCatoon , holder: TopCartoonViewHolder)
    }

    init {
        this.context = context
        this.categoryList = categoryList
        this.cartoonshowbtn = cartoonshowbtn
    }
}
