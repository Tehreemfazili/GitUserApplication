package com.example.gitusersapplication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.gitusersapplication.dataSource.models.RepoDetail
import com.example.gitusersapplication.databinding.RepositoryDetailFragmentBinding
import com.example.gitusersapplication.remote.NetworkState

class RepoDetailFragment : Fragment(){

    lateinit var activity: MainActivity
    private lateinit var _binding: RepositoryDetailFragmentBinding
    private val binding get() = _binding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as MainActivity
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = RepositoryDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        activity.viewModel.repository.observe(viewLifecycleOwner, Observer {
            binding.titleRepoName.text = it.name
            activity.viewModel.repoDetail(it.owner.login, it.name)
        })
        attachObserver()
    }

    private fun attachObserver() {
        activity.viewModel.repoDetail.observe(viewLifecycleOwner, Observer {

            val state = it.getContentIfNotHandled() ?: return@Observer

            if (state is NetworkState.Loading) {
                binding.progressBar.visibility = View.VISIBLE
                return@Observer
            }
            binding.progressBar.visibility = View.GONE

            when (state) {
                is NetworkState.Success -> {
                    val data = state.data ?: return@Observer
                    setData(data)
                }

            }
        })
    }

    private fun setData(data: RepoDetail) {

        binding.titleRepoName.text = data.name
        binding.description.text = data.fullName
        binding.owner.text = data.owner.login
        binding.url.text = data.gitUrl
        binding.name.text = data.name
        binding.type.text = data.owner.type
        binding.subscriptionUrl.text = data.subscriptionUrl
        binding.id.text = data.id.toString()
        binding.size.text = data.size.toString()
        binding.openIssues.text = data.openIssues.toString()
        if(data.hasDownloads.equals("true")){
            binding.downloads.text = "True"
        }
        else{
            binding.downloads.text = "False"

        }
        binding.subscribersCount.text = data.subscribersCount.toString()

    }
}