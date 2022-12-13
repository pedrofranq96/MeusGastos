package br.edu.infnet.meusgastos

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import br.edu.infnet.meusgastos.databinding.ActivityHomeBinding
import br.edu.infnet.meusgastos.login.ui.LoginActivity
import br.edu.infnet.meusgastos.main.ui.*
import br.edu.infnet.meusgastos.utils.navUp
import br.edu.infnet.meusgastos.utils.toast
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    val viewModel by viewModels<MainViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.logoutBtn.setOnClickListener{
            viewModel.logout()
            startLoginActivity()
        }

        binding.tvUserEmail.text = viewModel.getCurrentUserEmail()

        MobileAds.initialize(this) {}
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

//        setupViewPager(binding.tabViewpager)
//        setSupportActionBar()

    }

//    // This function is used to add items in arraylist and assign
//    // the adapter to view pager
//    private fun setupViewPager(viewpager: ViewPager) {
//        var adapter: ViewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
//
//        // LoginFragment is the name of Fragment and the Login
//        // is a title of tab
//        adapter.addFragment(DashboardFragment(), "Dashboard")
//        adapter.addFragment(EditarDespesaFragment(), "Editar")
//
//        // setting adapter to view pager.
//        viewpager.setAdapter(adapter)
//    }
//
//    class ViewPagerAdapter : FragmentPagerAdapter {
//
//        // objects of arraylist. One is of Fragment type and
//        // another one is of String type.*/
//        private final var fragmentList1: ArrayList<Fragment> = ArrayList()
//        private final var fragmentTitleList1: ArrayList<String> = ArrayList()
//
//        // this is a secondary constructor of ViewPagerAdapter class.
//        public constructor(supportFragmentManager: FragmentManager)
//                : super(supportFragmentManager)
//
//        // returns which item is selected from arraylist of fragments.
//        override fun getItem(position: Int): Fragment {
//            return fragmentList1.get(position)
//        }
//
//        // returns which item is selected from arraylist of titles.
//        override fun getPageTitle(position: Int): CharSequence {
//            return fragmentTitleList1.get(position)
//        }
//
//        // returns the number of items present in arraylist.
//        override fun getCount(): Int {
//            return fragmentList1.size
//        }
//
//        // this function adds the fragment and title in 2 separate  arraylist.
//        fun addFragment(fragment: Fragment, title: String) {
//            fragmentList1.add(fragment)
//            fragmentTitleList1.add(title)
//        }
//    }





    private fun startLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

}