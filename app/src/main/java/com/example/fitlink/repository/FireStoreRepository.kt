package com.example.fitlink.repository

import com.example.fitlink.model.FireStoreResponse
import com.example.fitlink.model.FitLinkPost
import kotlinx.coroutines.flow.Flow

interface FireStoreRepository {

    fun uploadPost(
        post : FitLinkPost
    ) : Flow<ResultState<String>>

    fun getPosts() : Flow<ResultState<List<FireStoreResponse>>>

    fun deletePost(key:String) : Flow<ResultState<String>>

    fun updatePost(
        item: FireStoreResponse
    ) : Flow<ResultState<String>>
}