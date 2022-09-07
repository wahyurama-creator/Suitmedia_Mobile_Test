package com.way.suitmediamobile.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.way.suitmediamobile.R
import com.way.suitmediamobile.presentation.ui.adapter.EventAdapter
import com.way.suitmediamobile.presentation.ui.adapter.GuestAdapter
import com.way.suitmediamobile.presentation.viewmodel.MainViewModel
import com.way.suitmediamobile.presentation.viewmodel.ViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelFactory
    lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var guestAdapter: GuestAdapter

    @Inject
    lateinit var eventAdapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
    }
}