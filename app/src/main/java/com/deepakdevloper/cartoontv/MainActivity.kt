package com.deepakdevloper.cartoontv

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.deepakdevloper.cartoontv.Adapter.TopCartoonAdapter
import com.deepakdevloper.cartoontv.Model.TopCatoon
import com.deepakdevloper.cartoontv.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.*
import com.google.firebase.database.annotations.Nullable
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), TopCartoonAdapter.Cartoonshowbtn {

    private lateinit var binding: ActivityMainBinding
    private lateinit var cartoonItems: ArrayList<TopCatoon>
    private lateinit var topCartoonAdapter: TopCartoonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cartoonItems = ArrayList()
        topCartoonAdapter = TopCartoonAdapter(this, cartoonItems, this)
        val layoutManager: RecyclerView.LayoutManager = StaggeredGridLayoutManager(2, 1)
        binding.courseRecycler.setLayoutManager(layoutManager)
        binding.courseRecycler.setAdapter(topCartoonAdapter)
        getData()
        //searching
        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do Nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Do Nothing
            }

            override fun afterTextChanged(s: Editable?) {
                filter(s.toString())
            }
        })
        //nav bar open
        binding.NavView.itemIconTintList = null
        //nav bar open by 3line
        binding.imageView.setOnClickListener {
            binding.Drawer.openDrawer(GravityCompat.START)
        }
        binding.NavView.setNavigationItemSelectedListener(object :
            NavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                val id = item.itemId
                _drawer.closeDrawer(GravityCompat.START)
                when (id) {
                    R.id.page_1A -> {
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=com.deepakdevloper.cartoontv")
                        )
                        startActivity(intent)
                    }
                    R.id.page_2A -> {
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://doc-hosting.flycricket.io/acasia-cartoon-tv-privacy-policy/532eb40f-818a-42ee-ac41-93ab87252ce0/privacy")
                        )
                        startActivity(intent)
                    }
                    //our profile click
                    R.id.page_8A -> {
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://t.me/cartoontvhindiindia")
                        )
                        startActivity(intent)
                    }
                    R.id.page_6A -> {
                        FirebaseDatabase.getInstance().getReference("appDetails").child("shareLink").addListenerForSingleValueEvent(object :
                            ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                try {
                                    val link = snapshot.child("allMessage").value.toString()
                                    val sendIntent = Intent().apply {
                                        action = Intent.ACTION_SEND
                                        putExtra(Intent.EXTRA_TEXT, link)
                                        type = "text/plain"
                                    }
                                    val shareIntent = Intent.createChooser(sendIntent, null)
                                    startActivity(shareIntent)
                                }catch(e:Exception){
                                    Log.e("finderror", e.message.toString())
                                }
                                //custom ad set up
                            }
                            override fun onCancelled(error: DatabaseError) {
                                Log.e("finderror", error.message.toString())
                            }
                        })
                    }
                    else -> {
                        return true
                    }
                }
                return true
            }
        })
    }

    private fun filter(text: String) {
        //new array list that will hold the filtered data
        val filteredNames = ArrayList<TopCatoon>()
        //looping through existing elements and adding the element to filtered list
        cartoonItems.filterTo(filteredNames) {
            //if the existing elements contains the search input
            it.cartoonName.toLowerCase().contains(text.toLowerCase())
        }
        //calling a method of the adapter class and passing the filtered list
        topCartoonAdapter.filterList(filteredNames)
    }

    private fun getData() {
        FirebaseDatabase.getInstance().getReference("shows")
            .addChildEventListener(object :
                ChildEventListener {
                override fun onChildAdded(
                    snapshot: DataSnapshot,
                    @Nullable previousChildName: String?
                ) {
                    // on below line we are hiding our progress bar.
                    binding.progressBar.visibility = View.GONE
                    snapshot.getValue(TopCatoon::class.java)?.let { cartoonItems.add(it) }
                    // notifying our adapter that data has changed.
                    topCartoonAdapter.notifyDataSetChanged()
                }
                override fun onChildChanged(
                    snapshot: DataSnapshot,
                    @Nullable previousChildName: String?
                ) {
                    // this method is called when new child is added
                    // we are notifying our adapter and making progress bar
                    // visibility as gone.
                    binding.progressBar.visibility = View.GONE
                    topCartoonAdapter.notifyDataSetChanged()
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    // notifying our adapter when child is removed.
                    topCartoonAdapter.notifyDataSetChanged()
                    binding.progressBar.visibility = View.GONE
                }

                override fun onChildMoved(
                    snapshot: DataSnapshot,
                    @Nullable previousChildName: String?
                ) {
                    // notifying our adapter when child is moved.
                    topCartoonAdapter.notifyDataSetChanged()
                    binding.progressBar.visibility = View.GONE
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        this@MainActivity,
                        "Fail to get the data" + error.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    override fun onShowClick(
        position: Int,
        model: TopCatoon,
        holder: TopCartoonAdapter.TopCartoonViewHolder
    )
    {
        val i = Intent(this, CartoonPage::class.java)
        i.putExtra("cartoonBaner",model.imgUrlbaner)
        i.putExtra("PLAY_LIST_ID",model.youtubeKey)
        startActivity(i)
    }
}
//just update name for need dialog box
//see all btn click work