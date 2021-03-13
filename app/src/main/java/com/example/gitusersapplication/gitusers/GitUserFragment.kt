package com.example.gitusersapplication.gitusers

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitusersapplication.ConnectivityReciever
import com.example.gitusersapplication.MainActivity
import com.example.gitusersapplication.PaginationScrollListener
import com.example.gitusersapplication.dataSource.models.GitUsers
import com.example.gitusersapplication.databinding.GitUserFragmentBinding
import com.example.gitusersapplication.remote.NetworkState
import com.google.android.material.snackbar.Snackbar


class GitUserFragment : Fragment(), ConnectivityReciever.ConnectivityReceiverListener {

    lateinit var activity: MainActivity
    private lateinit var _binding: GitUserFragmentBinding
    private val binding get() = _binding
    lateinit var adapter: GitUserAdapter
    private var query: String = ""
    lateinit var list: MutableList<GitUsers>

    val PAGE_START = 1
    private var currentPage: Int = PAGE_START
    private val isLastPage = false
    private val totalPage = 10
    private var isLoading = false
    var itemCount = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as MainActivity
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = GitUserFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       getUsers()

        attachObserver()

        binding.etSearchUser.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {

                val filteredList = list.filter {
                    val name = it.login
                    name.contains(s)

                }
                adapter.updateList(filteredList as MutableList<GitUsers>)
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

    override fun onResume() {
        super.onResume()

        ConnectivityReciever().setConnectivityListener(this)
    }

    private fun attachObserver() {
        activity.viewModel.getUser.observe(viewLifecycleOwner, Observer {

            val state = it.getContentIfNotHandled() ?: return@Observer

            if (state is NetworkState.Loading) {
                binding.progressBar.visibility = View.VISIBLE
                return@Observer
            }
            binding.progressBar.visibility = View.GONE

            when (state) {
                is NetworkState.Success -> {
                    val data = state.data ?: return@Observer
                    list = data as MutableList<GitUsers>
                    setRecyclerView(list)
                }

            }
        })
    }

    private fun setRecyclerView(data: MutableList<GitUsers>) {
        adapter = GitUserAdapter(data) {
            activity.viewModel.user(it)
            activity.navigateUp()
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)


        binding.recyclerView.addOnScrollListener(object :
            PaginationScrollListener((binding.recyclerView.getLayoutManager() as LinearLayoutManager)) {
            override fun loadMoreItems() {
                    currentPage++
                    binding.progressBar2.visibility = View.VISIBLE
                    getUsers()

            }

            override fun isLastPage(): Boolean {
                return isLastPage()
            }

            override fun isLoading(): Boolean {
                return isLoading
            }
        })
    }

    private fun getUsers() {

        if (ConnectivityReciever().isConnected(context ?: return)) {
            binding.recyclerView.visibility = View.VISIBLE
            isLoading = true
            activity.viewModel.getUser()
        } else {
            binding.progressBar.visibility = View.VISIBLE
            val snackbar: Snackbar = Snackbar
                .make(binding.root, "No internet connection", Snackbar.LENGTH_LONG)
            snackbar.show()
            binding.recyclerView.visibility = View.GONE
            binding.progressBar2.visibility = View.GONE
        }

    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        getUsers()
    }
}