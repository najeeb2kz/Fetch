package com.chaudhry.najeeb.fetch

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.chaudhry.najeeb.fetch.databinding.ActivityMainBinding
import com.chaudhry.najeeb.fetch.ui.adapter.ItemAdapter
import com.chaudhry.najeeb.fetch.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        adapter = ItemAdapter() //adapter is an instance of ItemAdapter, which is a RecyclerView adapter

        //Set the LayoutManager for recyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        viewModel.items.observe(this) { items ->
            //submitList(items) is a method provided by ListAdapter (which ItemAdapter extends) to update the adapter's data
            //When submitList(items) is called, it calculates the differences between the current list and the new list (items) and updates the RecyclerView accordingly
            //This ensures that the RecyclerView displays the latest data from the items LiveData
            //when you see the word "list" in the context of submitList(items), it refers to the list of items that the RecyclerView adapter (ItemAdapter) is managing, not the old ListAdapter
            //Submits a new list to be diffed, and displayed.
            //If a list is already being displayed, a diff will be computed on a background thread, which will dispatch Adapter. notifyItem events on the main thread.
            adapter.submitList(items)
            binding.recyclerView.scrollToPosition(0) // Scroll to the top when new items are loaded
        }

        viewModel.loading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(this) { error ->
            error?.let {
                // Handle error (e.g., show a Toast)
            }
        }

        viewModel.text.observe(this) { newText ->
            binding.descriptionTextView.text = newText
        }

        binding.btnFetchFromDb.setOnClickListener {
            viewModel.fetchItemsFromDb()
        }

        binding.btnFetchFromApi.setOnClickListener {
            viewModel.fetchItemsFromApi()
        }

        binding.btnReset.setOnClickListener {
            viewModel.clearItems()
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}