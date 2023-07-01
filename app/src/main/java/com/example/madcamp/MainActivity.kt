package com.example.madcamp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.madcamp.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity() {
    // 뷰 페이저 어댑터
    class MyFragmentPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
        val fragments = listOf(OneFragment(), TwoFragment(), ThreeFragment())
        override fun getItemCount(): Int = fragments.size
        override fun createFragment(position: Int): Fragment = fragments[position]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewpager.adapter = MyFragmentPagerAdapter(this)

        TabLayoutMediator(binding.tabs,binding.viewpager){
                tab, position-> tab.text = if(position==0) "Contacts" else if(position==1) "Gallery" else "TAB3"
        }.attach()

        // 툴바 적용
//        setSupportActionBar(binding.toolbar)
    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        var initTime = 0L
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(System.currentTimeMillis() - initTime > 3000){
                Toast.makeText(this, "종료하려면 한번 더 누르세요!",
                    Toast.LENGTH_SHORT).show()
                initTime = System.currentTimeMillis()
                return true // 키 이벤트 무시
            }
        }
        return super.onKeyDown(keyCode, event) // 키 이벤트 처리
    }
}