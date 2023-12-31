package com.deepakdevloper.cartoontv

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.deepakdevloper.cartoontv.Adapter.CartoonAdapter
import com.deepakdevloper.cartoontv.Model.ResourceId
import com.deepakdevloper.cartoontv.Model.Snippet
import com.deepakdevloper.cartoontv.databinding.ActivityCartoonPageBinding

class CartoonPage : AppCompatActivity(), CartoonAdapter.Cartoonshowbtn {
    private lateinit var binding: ActivityCartoonPageBinding
    private var mRequestQueue: RequestQueue? = null
    private var jsonObjectRequest: JsonObjectRequest? = null
    var BASE_URL = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&maxResults=50&playlistId="
    val MY_API_KEY = "AIzaSyC77qzwtRwBjob5nQ4PFwaf9322skB0qgw"
    private lateinit var cartoonItems: ArrayList<Snippet>
    private lateinit var topCartoonAdapter: CartoonAdapter
    //PLAY_LIST_ID&key=MY_API_KEY
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartoonPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val banerImage = intent.getStringExtra("cartoonBaner")
        val PLAY_LIST_ID = intent.getStringExtra("PLAY_LIST_ID").toString()
        BASE_URL = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&maxResults=50&playlistId=$PLAY_LIST_ID&key=$MY_API_KEY"
        Glide.with(this).load(banerImage).into(binding.imageView6)
        cartoonItems = ArrayList()
        topCartoonAdapter = CartoonAdapter(this, cartoonItems, this)
        binding.courseRecycler.layoutManager = LinearLayoutManager(this)
        binding.courseRecycler.setAdapter(topCartoonAdapter)
        getData()
        
        binding.imageView7.setOnClickListener { 
            val i = Intent(this,MainActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    private fun getData() {
        mRequestQueue = Volley.newRequestQueue(this)
        jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            BASE_URL,
            null,
            Response.Listener
            { response ->
                try {
                    val res = response.getJSONArray("items")
                    for (i in 0 until res.length()){
                        val newRes = res.getJSONObject(i)
                        val title = newRes.getJSONObject("snippet").getString("title")
                        val positionOfvideo = newRes.getJSONObject("snippet").getInt("position")
                        val resource = newRes.getJSONObject("snippet").getJSONObject("resourceId").getString("videoId")
                        cartoonItems.add(Snippet(positionOfvideo, ResourceId(resource),title))
                        binding.progressBar2.visibility = View.GONE
                    }
                    topCartoonAdapter.notifyDataSetChanged()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener
            {
                binding.progressBar2.visibility = View.GONE
                Log.d("error", it.message.toString())
            })
        mRequestQueue!!.add(jsonObjectRequest)
    }

    override fun onShowClick(
        position: Int,
        model: Snippet,
        holder: CartoonAdapter.CartoonAdapterViewHolder
    ) {
        val i = Intent(this, VideoPlayActicity::class.java)
        i.putExtra("VIDEO_ID",model.resourceId.videoId.toString())
        startActivity(i)
    }
}