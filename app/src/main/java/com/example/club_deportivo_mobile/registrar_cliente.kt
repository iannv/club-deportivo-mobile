package com.example.club_deportivo_mobile

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout

class registrar_cliente : AppCompatActivity() {

    private lateinit var volver: ImageView
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPage2: ViewPager2
    private lateinit var adapter: FragmentRegisterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrar_cliente)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        volver = findViewById(R.id.img_volver)
        tabLayout = findViewById(R.id.tabLayout)
        viewPage2 = findViewById(R.id.viewPager2)

        adapter = FragmentRegisterAdapter(supportFragmentManager, lifecycle)
        viewPage2.adapter = adapter

        tabLayout.addTab(tabLayout.newTab().setText("DATOS PERSONALES"))
        tabLayout.addTab(tabLayout.newTab().setText("CONTACTO"))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    viewPage2.currentItem = it.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        viewPage2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })

        volver.setOnClickListener {
            startActivity(Intent(this, menuPrincipal::class.java))
        }
    }
}
