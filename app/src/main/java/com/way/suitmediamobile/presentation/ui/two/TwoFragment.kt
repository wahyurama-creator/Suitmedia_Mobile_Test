package com.way.suitmediamobile.presentation.ui.two

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.way.suitmediamobile.databinding.FragmentTwoBinding

class TwoFragment : Fragment() {

    private var _binding: FragmentTwoBinding? = null
    private val binding get() = _binding!!

    private val personNameArgs by navArgs<TwoFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTwoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            textPersonName.text = personNameArgs.personName
            btnEvent.setOnClickListener {
                val action = TwoFragmentDirections.actionTwoFragmentToThreeFragment()
                findNavController().navigate(action)
            }
            btnGuest.setOnClickListener {
                val action = TwoFragmentDirections.actionTwoFragmentToFourFragment()
                findNavController().navigate(action)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}