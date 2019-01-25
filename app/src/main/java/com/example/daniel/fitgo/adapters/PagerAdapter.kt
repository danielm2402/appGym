package com.example.daniel.fitgo.adapters


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class PagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm){

    private val fragmentList = ArrayList<Fragment>()

    override fun getItem(p0: Int): Fragment = fragmentList[p0]

    override fun getCount(): Int = fragmentList.size

    fun addFragment(fragment: Fragment) = fragmentList.add(fragment)
}