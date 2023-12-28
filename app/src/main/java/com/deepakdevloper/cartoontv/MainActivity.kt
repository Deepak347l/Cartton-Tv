package com.deepakdevloper.cartoontv

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.deepakdevloper.cartoontv.Adapter.TopCartoonAdapter
import com.deepakdevloper.cartoontv.Model.TopCatoon
import com.deepakdevloper.cartoontv.databinding.ActivityMainBinding
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.annotations.Nullable


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
                    snapshot.getValue(TopCatoon::class.java)
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
    ) {
        val i = Intent(this, CartoonPage::class.java)
        startActivity(i)
    }
}