package com.way.suitmediamobile.presentation.ui.five

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import coil.load
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.way.suitmediamobile.R
import com.way.suitmediamobile.data.local.model.EventEntity
import com.way.suitmediamobile.databinding.FragmentFiveBinding

class FiveFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentFiveBinding? = null
    private val binding get() = _binding!!

    private var currentLocation: Location? = null
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFiveBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())

        fetchLocation()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOptionMenu()
    }

    private fun fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE
            )
            return
        }

        val task = fusedLocationProviderClient!!.lastLocation
        task.addOnSuccessListener { location ->
            if (location != null) {
                currentLocation = location
                val supportMapFragment =
                    childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
                supportMapFragment!!.getMapAsync(this)
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        var eventEntity: EventEntity? = null
        eventEntity = if (arguments!!.isEmpty) {
            null
        } else {
            val args = arguments?.getParcelable<EventEntity>("eventArgs") as EventEntity
            setCardViewData(args)
            args
        }

        val latLng: LatLng = if (eventEntity == null) {
            LatLng(currentLocation!!.latitude, currentLocation!!.longitude)
        } else {
            LatLng(eventEntity.latitude, eventEntity.longitude)
        }
        val markerOptions = MarkerOptions().position(latLng).title("I Am Here!")
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
        googleMap.addMarker(markerOptions)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLocation()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun setCardViewData(event: EventEntity) {
        binding.cardView.visibility = View.VISIBLE
        binding.ivEvent.load(event.image) {
            placeholder(R.drawable.ic_error_placeholder)
        }
        binding.tvTitle.text = event.title
        binding.tvDescription.text = event.subTitle
        binding.tvDate.text = event.eventDate
        binding.tvTime.text = event.time
    }

    private fun setOptionMenu() {
        binding.toolbar2.apply {
            setNavigationOnClickListener {
                activity?.onBackPressed()
            }
            setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.listMenu -> {
                        val action = FiveFragmentDirections.actionFiveFragmentToThreeFragment()
                        findNavController().navigate(action)
                        true
                    }
                    else -> false
                }
            }
        }
    }

    companion object {
        private const val REQUEST_CODE = 101
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}