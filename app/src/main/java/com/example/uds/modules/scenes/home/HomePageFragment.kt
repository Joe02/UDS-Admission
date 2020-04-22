package com.example.uds.modules.scenes.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.uds.R
import com.example.uds.databinding.FragmentHomePageBinding

class HomePageFragment : Fragment() {

    private lateinit var homeBinding : FragmentHomePageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        homeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_page, container, false)

        return homeBinding.root
    }
}