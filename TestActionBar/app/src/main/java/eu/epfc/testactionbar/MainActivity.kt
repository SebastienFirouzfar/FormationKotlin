package eu.epfc.testactionbar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var buttonInformation : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = MyView(supportFragmentManager)
        adapter.addFragment(HomeFragment(), "HOME")
        adapter.addFragment(PocketFragment(), "Pocket")
        view_pager.adapter = adapter
         activity_mainTab.setupWithViewPager(view_pager)
    }

    fun buttonInformationPage(view: View){
        buttonInformation = findViewById(R.id.imageButton)
        val intent = Intent(this, NewActivityTest::class.java)
        startActivity(intent)
    }


    class MyView(manager: FragmentManager): FragmentPagerAdapter(manager){
        val fragmentList : MutableList<Fragment> = ArrayList()
        val titleString : MutableList<String> = ArrayList()

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return titleString.size
        }

        fun addFragment(fragment: Fragment, title : String){
            fragmentList.add(fragment)
            titleString.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titleString[position]
        }

    }


}
