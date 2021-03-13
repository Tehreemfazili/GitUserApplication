package com.example.gitusersapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.ViewModelProvider
import com.example.gitusersapplication.databinding.ActivityMainBinding
import com.example.gitusersapplication.gitusers.GitUserFragment
import com.example.gitusersapplication.gitusers.GitUserViewModel
import com.example.gitusersapplication.userRepos.UsersReposFragment

class MainActivity : AppCompatActivity() {



    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

     val viewModel : GitUserViewModel by lazy {
        ViewModelProvider(this).get(GitUserViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.toolbar.arrowBack.visibility= View.GONE
        binding.toolbar.image.visibility = View.GONE
        binding.viewPager.adapter = SwipeAdapter(supportFragmentManager, getFragmentList())

    }

    class SwipeAdapter(
        fm: FragmentManager, private var items: ArrayList<Fragment>
    ) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getItem(position: Int) = items[position]

        override fun getCount() = items.size

    }

    private fun getFragmentList() = arrayListOf(
        GitUserFragment(),
        UsersReposFragment(),
        RepoDetailFragment()
    )

    fun navigateUp() {
        binding.viewPager.currentItem = binding.viewPager.currentItem + 1
    }

    override fun onBackPressed() {
        if (binding.viewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            binding.viewPager.currentItem = binding.viewPager.currentItem - 1
        }
    }

}