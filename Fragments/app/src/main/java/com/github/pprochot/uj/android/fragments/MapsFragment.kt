package com.github.pprochot.uj.android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.pprochot.uj.android.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment(R.layout.fragment_maps) {

    private val callback = OnMapReadyCallback { googleMap ->
        val krakow = LatLng(50.0647, 19.9450)
        val warsaw = LatLng(52.2297, 21.0122)
        val gdansk = LatLng(54.3520, 18.6466)
        val wroclaw = LatLng(51.1079, 17.0385)
        val poznan = LatLng(52.4064, 16.9252)
        googleMap.addMarker(MarkerOptions()
            .position(krakow)
            .title("Shop in Krakow"))
        googleMap.addMarker(MarkerOptions()
            .position(warsaw)
            .title("Shop in Warsaw"))
        googleMap.addMarker(MarkerOptions()
            .position(gdansk)
            .title("Shop in Gdansk"))
        googleMap.addMarker(MarkerOptions()
            .position(wroclaw)
            .title("Shop in Wroclaw"))
        googleMap.addMarker(MarkerOptions()
            .position(poznan)
            .title("Shop in Poznan"))

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(warsaw))
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}