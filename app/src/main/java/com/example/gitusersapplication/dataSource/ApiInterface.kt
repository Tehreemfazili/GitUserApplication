package com.example.gitusersapplication.dataSource

import com.example.gitusersapplication.dataSource.models.GitUsers
import com.example.gitusersapplication.dataSource.models.RepoDetail
import com.example.gitusersapplication.dataSource.models.UsersRepo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface{


    @GET("users")
    suspend fun getUser(
        @Query("since") since : Int = 0,
        @Query("keyword") keyword: String = ""
    ) : Response<List<GitUsers>>


    @GET("users/{username}/repos")
    suspend fun getRepos(
        @Path("username") username : String
    ) : Response<List<UsersRepo>>

    @GET("repos/{username}/{reponame}")
    suspend fun repoDetail(
        @Path("username") username : String,
        @Path("reponame") reponame : String
    ) : Response<RepoDetail>
}