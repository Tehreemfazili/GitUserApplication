package com.example.gitusersapplication.dataSource

import com.example.gitusersapplication.RetrofitInstance
import com.example.gitusersapplication.dataSource.models.GitUsers
import com.example.gitusersapplication.dataSource.models.License
import com.example.gitusersapplication.dataSource.models.RepoDetail
import com.example.gitusersapplication.dataSource.models.UsersRepo
import retrofit2.Response

class ApiService() {

    companion object {
        val instance: ApiService by lazy { ApiService() }
    }

    suspend fun getUser( keyword : String) : Response<List<GitUsers>>{

        val service = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
        return service.getUser(keyword = "")
    }

    suspend fun getRepos(username : String) : Response<List<UsersRepo>> {
        val service = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
        return service.getRepos(username)
    }

    suspend fun repoDetail(username : String, reponame : String) : Response<RepoDetail> {
        val service = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
        return service.repoDetail(username, reponame)
    }
}