package br.com.tryyourfood.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class PageAdapter(
    private val resultBundle: Bundle,
    private val fragments: ArrayList<Fragment>,
    private val title: ArrayList<String>,
    private val fragmentManager: FragmentManager,
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        fragments[position].arguments = resultBundle
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return title[position]
    }

}