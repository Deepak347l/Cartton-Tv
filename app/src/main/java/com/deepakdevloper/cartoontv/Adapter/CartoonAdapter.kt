package com.deepakdevloper.cartoontv.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.deepakdevloper.cartoontv.Model.Snippet
import com.deepakdevloper.cartoontv.R
import kotlin.random.Random

class CartoonAdapter(context: Context, categoryList: ArrayList<Snippet>, cartoonshowbtn: Cartoonshowbtn) :
    RecyclerView.Adapter<CartoonAdapter.CartoonAdapterViewHolder>() {
    private val context: Context
    var categoryList: ArrayList<Snippet>
    private val cartoonshowbtn: Cartoonshowbtn
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartoonAdapterViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.cartoon_list_row_items, parent, false)


        // now here we create a recyclerview row items.
        return CartoonAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartoonAdapterViewHolder, position: Int) {

        // here we will bind data in recyclerview ro items.
        holder.cartoonName.setText(categoryList[position].title)
        randomTime(holder)
        holder.contentNumber.setText((categoryList[position].positions + 1).toString())
        //https://www.youtube.com/watch?v=videoId
        // for image we need to add glide image fetching library from netwok
        holder.playBtn.setOnClickListener{cartoonshowbtn.onShowClick(position,categoryList[position],holder)}
    }

    private fun randomTime(holder: CartoonAdapterViewHolder) {
        val random = Random.Default
        val minTime = 20.0f
        val maxTime = 30.0f
        val randomFloat = String.format("%.2f",minTime + (maxTime - minTime) * random.nextFloat()).toFloat()
        holder.totalLength.setText(randomFloat.toString()+" mins")
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    class CartoonAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var playBtn: ImageView
        var cartoonName: TextView
        var totalLength: TextView
        var contentNumber: TextView

        init {
            playBtn = itemView.findViewById(R.id.imageView3)
            cartoonName = itemView.findViewById(R.id.content_title)
            totalLength = itemView.findViewById(R.id.content_time)
            contentNumber = itemView.findViewById(R.id.content_number)
        }
    }

    interface Cartoonshowbtn{
        fun onShowClick(position: Int, model:Snippet, holder: CartoonAdapterViewHolder)
    }

    init {
        this.context = context
        this.categoryList = categoryList
        this.cartoonshowbtn = cartoonshowbtn
    }
}