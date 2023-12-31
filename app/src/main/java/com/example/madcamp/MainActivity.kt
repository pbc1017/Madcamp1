package com.example.madcamp


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.madcamp.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator



class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var initTime = 0L

    // 뷰 페이저 어댑터
    class MyFragmentPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
        val fragments = listOf(OneFragment(), TwoFragment(), ThreeFragment())
        override fun getItemCount(): Int = fragments.size
        override fun createFragment(position: Int): Fragment = fragments[position]
    }

    fun tabSetting(tab: TabLayout.Tab, position: Int) {
        if (position == 0) {
            tab.text = "Contacts"
            tab.setIcon(R.drawable.contacts_icon)
        } else if (position == 1) {
            tab.text = "Gallery"
            tab.setIcon(R.drawable.gallery_icon)
        } else {
            tab.text = "GPT"
            tab.setIcon(R.drawable.gpt_icon)
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewpager.adapter = MyFragmentPagerAdapter(this)

        TabLayoutMediator(binding.tabs,binding.viewpager){
                tab, position-> tabSetting(tab, position)
        }.attach()

        // 툴바 적용
//        setSupportActionBar(binding.toolbar)
    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            val fragment = (binding.viewpager.adapter as MyFragmentPagerAdapter).fragments[binding.viewpager.currentItem]
            if (fragment is OneFragment) {
                if(fragment.backButtonPressed()==1) return true
            }
            else if (fragment is TwoFragment) {
                if(fragment.backButtonPressed()==1) return true
            }
            if(System.currentTimeMillis() - initTime > 3000){
                Toast.makeText(this, "종료하려면 한번 더 누르세요!",
                    Toast.LENGTH_SHORT).show()
                initTime = System.currentTimeMillis()
                return true // 키 이벤트 무시
            }
            else
            {
                ActivityCompat.finishAffinity(this)
                System.exit(0)
            }
        }
        return super.onKeyDown(keyCode, event) // 키 이벤트 처리
    }
}