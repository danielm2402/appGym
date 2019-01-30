package com.example.daniel.fitgo


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import com.example.daniel.fitgo.activities.login.LoginActivity
import com.example.daniel.fitgo.activities.maps.MapsActivity
import com.example.daniel.fitgo.adapters.PagerAdapter
import com.example.daniel.fitgo.fragments.AgregarRutinas
import com.example.daniel.fitgo.fragments.RutinaFragment
import com.example.mylibrary.ToolbarActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : ToolbarActivity() {

    private  var  prevBottomSelected: MenuItem?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbarToLoad(toolbarView as Toolbar)

        setUpViewPager(getPagerAdapter())
        setUpBottomNavigationVar()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.general_options,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_log_out->{
                FirebaseAuth.getInstance().signOut()
                goToActivity<LoginActivity> {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }

            }

            R.id.googlemaps->{
                goToActivity<MapsActivity>{}
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getPagerAdapter(): PagerAdapter{
       val adapter = PagerAdapter(supportFragmentManager)
        adapter.addFragment(RutinaFragment())
        adapter.addFragment(AgregarRutinas())
        return adapter

    }

    private fun setUpViewPager(adapter: PagerAdapter)
    {
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

            }

            override fun onPageSelected(position: Int) {

                if(prevBottomSelected == null){
                    bottomNavigation.menu.getItem(0).isChecked=false
                }else{
                    prevBottomSelected!!.isChecked = false
                }
               bottomNavigation.menu.getItem(position).isChecked=true
                prevBottomSelected = bottomNavigation.menu.getItem(position)
            }

        } )

    }
    private fun setUpBottomNavigationVar()
    {
        bottomNavigation.setOnNavigationItemSelectedListener{item->
            when(item.itemId){
                R.id.bottom_nav_rut -> {
                    viewPager.currentItem=0;true
                }
                R.id.bottom_nav_add -> {
                    viewPager.currentItem=1;true
                }
                else -> false
            }
        }
    }
}
