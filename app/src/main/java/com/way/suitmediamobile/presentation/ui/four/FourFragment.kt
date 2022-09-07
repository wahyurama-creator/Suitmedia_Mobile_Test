package com.way.suitmediamobile.presentation.ui.four

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.way.suitmediamobile.data.local.model.PersonEntity
import com.way.suitmediamobile.data.remote.network.NetworkResult
import com.way.suitmediamobile.databinding.FragmentFourBinding
import com.way.suitmediamobile.presentation.ui.MainActivity
import com.way.suitmediamobile.presentation.ui.adapter.GuestAdapter
import com.way.suitmediamobile.presentation.utils.observeOnce
import com.way.suitmediamobile.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FourFragment : Fragment() {

    private var _binding: FragmentFourBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private lateinit var guestAdapter: GuestAdapter

    private var currentPage = 1
    private lateinit var gridLayoutManager: GridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFourBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel = (activity as MainActivity).mainViewModel
        guestAdapter = (activity as MainActivity).guestAdapter

        setOptionMenu()
        setupRecyclerView()

        lifecycleScope.launchWhenStarted {
            readFromDB()
        }

        guestAdapter.setOnItemClickListener { data ->
            lifecycleScope.launch {
                try {
                    val personEntity =
                        PersonEntity("${data.firstName} ${data.lastName}", getBitmap(data.avatar))
                    mainViewModel.insertPerson(personEntity)
                    Toast.makeText(context, "Guest saved to profile", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private suspend fun getBitmap(imageData: String): Bitmap {
        val loading = ImageLoader(requireContext())
        val request = ImageRequest.Builder(requireContext())
            .data(imageData)
            .build()

        val result = (loading.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }

    private fun getUsers() {
        showLoading(true)
        mainViewModel.getUsers(setQueryParam())
        mainViewModel.userResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    showLoading(false)
                    response.data?.let {
                        guestAdapter.setData(it)
                    }
                }
                is NetworkResult.Error -> {
                    showLoading(false)
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()

                    loadOfflineData()
                }
                is NetworkResult.Loading -> {
                    showLoading(true)
                }
            }
        }
    }

    private fun readFromDB() = lifecycleScope.launch(Dispatchers.Main) {
        mainViewModel.readGuestsEntity.observeOnce(viewLifecycleOwner) { db ->
            if (db.isNotEmpty()) {
                guestAdapter.setData(db[0].userResponse)
                Log.e(FourFragment::class.simpleName, "Database called")
            } else {
                getUsers()
                Log.e(FourFragment::class.simpleName, "API called")
            }
        }
    }

    private fun loadOfflineData() = lifecycleScope.launch(Dispatchers.Main) {
        mainViewModel.readGuestsEntity.observe(viewLifecycleOwner) { db ->
            if (db.isNotEmpty()) {
                guestAdapter.setData(db[0].userResponse)
            }
        }
    }

    private fun setQueryParam(): HashMap<String, Int> {
        val queries: HashMap<String, Int> = HashMap()
        queries["page"] = currentPage
        queries["per_page"] = 10
        return queries
    }

    private fun setupRecyclerView() {
        gridLayoutManager = GridLayoutManager(context, 2)
        binding.rvGuest.apply {
            layoutManager = gridLayoutManager
            adapter = guestAdapter
        }
    }

    private fun showLoading(isShow: Boolean) {
        binding.progressBar.visibility = if (isShow) View.VISIBLE else View.INVISIBLE
    }

    private fun setOptionMenu() {
        binding.toolbar2.apply {
            setNavigationOnClickListener {
                activity?.onBackPressed()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}