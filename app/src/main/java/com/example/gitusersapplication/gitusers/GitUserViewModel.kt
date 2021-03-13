package com.example.gitusersapplication.gitusers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitusersapplication.dataSource.ApiService
import com.example.gitusersapplication.dataSource.models.GitUsers
import com.example.gitusersapplication.dataSource.models.License
import com.example.gitusersapplication.dataSource.models.RepoDetail
import com.example.gitusersapplication.dataSource.models.UsersRepo
import com.example.gitusersapplication.remote.NetworkState
import com.example.gitusersapplication.remote.isSessionExpired
import com.example.gitusersapplication.remote.parseErrorBody
import com.example.gitusersapplication.utilities.Event
import kotlinx.coroutines.launch
import retrofit2.HttpException

class GitUserViewModel : ViewModel() {

    lateinit var repo: UsersRepo

    private val _getUser = MutableLiveData<Event<NetworkState<List<GitUsers>>>>()
    val getUser: LiveData<Event<NetworkState<List<GitUsers>>>>
        get() = _getUser

    private val _user = MutableLiveData<GitUsers>()
    val user: LiveData<GitUsers>
        get() = _user

    private val _repository = MutableLiveData<UsersRepo>()
    val repository: LiveData<UsersRepo>
        get() = _repository

    private val _getRepos = MutableLiveData<Event<NetworkState<List<UsersRepo>>>>()
    val getRepos: LiveData<Event<NetworkState<List<UsersRepo>>>>
        get() = _getRepos

    private val _repoDetail = MutableLiveData<Event<NetworkState<RepoDetail>>>()
    val repoDetail: LiveData<Event<NetworkState<RepoDetail>>>
        get() = _repoDetail

    fun getUser(keyword : String = ""){
        _getUser.value = Event(NetworkState.Loading())
        viewModelScope.launch {

            val result = ApiService.instance.getUser(keyword)
            try {
                when {
                     result.body() != null ->
                        _getUser.value =
                            Event(NetworkState.Success(result.body()))
                }
            } catch (e: HttpException) {
                val errorResult = parseErrorBody(e.response()?.errorBody()?.string())
                _getUser.value = Event(
                    NetworkState.Error(
                        errorResult?.errors!![0], errorResult.code.toString(),
                        isSessionExpired(errorResult.code)
                    )
                )
            }
        }
    }

    fun user(user : GitUsers){
        _user.value = user
    }

    fun repository(repo : UsersRepo){
        _repository.value = repo

    }

    fun getRepositories(username : String){
        _getRepos.value = Event(NetworkState.Loading())
        viewModelScope.launch {

            val result = ApiService.instance.getRepos(username)
            try {
                when {
                    result.body() != null ->
                        _getRepos.value =
                            Event(NetworkState.Success(result.body()))
                }
            } catch (e: HttpException) {
                val errorResult = parseErrorBody(e.response()?.errorBody()?.string())
                _getRepos.value = Event(
                    NetworkState.Error(
                        errorResult?.errors!![0], errorResult.code.toString(),
                        isSessionExpired(errorResult.code)
                    )
                )
            }
        }
    }

    fun repoDetail(username: String, reponame : String){
        _repoDetail.value = Event(NetworkState.Loading())
        viewModelScope.launch {

            val result = ApiService.instance.repoDetail(username, reponame)
            try {
                when {
                    result.body() != null ->
                        _repoDetail.value =
                            Event(NetworkState.Success(result.body()))
                }
            } catch (e: HttpException) {
                val errorResult = parseErrorBody(e.response()?.errorBody()?.string())
                _repoDetail.value = Event(
                    NetworkState.Error(
                        errorResult?.errors!![0], errorResult.code.toString(),
                        isSessionExpired(errorResult.code)
                    )
                )
            }
        }
    }
}