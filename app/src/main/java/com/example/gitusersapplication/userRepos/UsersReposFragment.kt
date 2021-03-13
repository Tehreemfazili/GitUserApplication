package com.example.gitusersapplication.userRepos

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitusersapplication.MainActivity
import com.example.gitusersapplication.dataSource.models.UsersRepo
import com.example.gitusersapplication.databinding.UserReposFragmentBinding
import com.example.gitusersapplication.remote.NetworkState

class UsersReposFragment : Fragment(){


    lateinit var activity: MainActivity
    private lateinit var _binding: UserReposFragmentBinding
    private val binding get() = _binding

    val PAGE_START = 1
    private var currentPage = PAGE_START
    private val totalPages = 0
    var isLoading: Boolean = false


    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = UserReposFragmentBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        attachObserver()
        activity.viewModel.user.observe(viewLifecycleOwner, Observer {
            binding.titleUsername.text = it.login
            activity.viewModel.getRepositories(it.login)
        })

    }

    private fun attachObserver(){

        activity.viewModel.getRepos.observe(viewLifecycleOwner, Observer {
            val state = it.getContentIfNotHandled() ?: return@Observer

            if (state is NetworkState.Loading) {
                binding.progressBar.visibility = View.VISIBLE
                return@Observer
            }
            binding.progressBar.visibility = View.GONE

            when (state) {
                is NetworkState.Success -> {
                    val data = state.data ?: return@Observer
                    setRecyclerView(data)
                }

            }
        })
    }

    private fun setRecyclerView(data : List<UsersRepo>){
        binding.recyclerView.adapter = UserReposAdapter(data){
           activity.viewModel.repository(it)
            activity.navigateUp()
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }
}