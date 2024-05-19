package com.example.appassignment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPager: ViewPager = findViewById(R.id.viewPager)
        val tabLayout: TabLayout = findViewById(R.id.tabLayout)

        val fragmentAdapter = object : FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            override fun getCount(): Int {
                return 3 // Number of fragments
            }

            override fun getItem(position: Int): androidx.fragment.app.Fragment {
                return when (position) {
                    0 -> DiaryEntry()
                    1 -> DiaryNote()
                    2 -> DiaryDisplay()
                    else -> throw IllegalArgumentException("Invalid position")
                }
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return when (position) {
                    0 -> "Entry"
                    1 -> "Note"
                    2 -> "Display"
                    else -> null
                }
            }
        }

        viewPager.adapter = fragmentAdapter
        tabLayout.setupWithViewPager(viewPager)
    }
}
