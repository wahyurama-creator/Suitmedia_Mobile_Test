package com.way.suitmediamobile.presentation.ui.three

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.way.suitmediamobile.R
import com.way.suitmediamobile.data.local.model.Events
import com.way.suitmediamobile.databinding.FragmentThreeBinding
import com.way.suitmediamobile.presentation.ui.MainActivity
import com.way.suitmediamobile.presentation.ui.adapter.EventAdapter

class ThreeFragment : Fragment() {

    private var _binding: FragmentThreeBinding? = null
    private val binding get() = _binding!!

    private lateinit var eventAdapter: EventAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThreeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventAdapter = (activity as MainActivity).eventAdapter
        eventAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putParcelable("eventArgs", it)
            }
            findNavController().navigate(R.id.action_threeFragment_to_fiveFragment, bundle)
        }

        setOptionMenu()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.rvEvent.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = eventAdapter
            eventAdapter.setData(Events.listEvent)
        }
    }

    private fun setOptionMenu() {
        binding.toolbar2.apply {
            setNavigationOnClickListener {
                activity?.onBackPressed()
            }
            setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.mapsMenu -> {
                        val action = ThreeFragmentDirections.actionThreeFragmentToFiveFragment()
                        findNavController().navigate(action)
                        true
                    }
                    else -> false
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}