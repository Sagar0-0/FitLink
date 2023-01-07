package com.example.fitlink

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.Auth0
import com.auth0.android.result.UserProfile
import com.example.fitlink.model.*
import com.example.fitlink.repository.FireStoreRepository
import com.example.fitlink.repository.ResultState
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObjects
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
    private val repository: FireStoreRepository,
    val account: Auth0
) : ViewModel() {

    var accessToken by mutableStateOf("")
    var user by mutableStateOf(User())
    private val _posts: MutableState<FireStoreState> = mutableStateOf(FireStoreState())
    val posts: State<FireStoreState> = _posts

    init {
        getPosts()
    }

    fun uploadPost(post: FitLinkPost) = repository.uploadPost(post)

    fun getPosts() = viewModelScope.launch {
        repository.getPosts().collect{
            when(it){
                is ResultState.Success->{
                    _posts.value = FireStoreState(data = it.data)
                }
                is ResultState.Failure->{
                    _posts.value = FireStoreState(error = it.msg.toString())
                }
                is ResultState.Loading->{
                    _posts.value = FireStoreState(isLoading = true)
                }
            }
        }
    }

    fun deletePost(key:String) = repository.deletePost(key)
    fun updatePost(response: FireStoreResponse) = repository.updatePost(item = response)
}

data class FireStoreState(
    val data: List<FireStoreResponse> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false
)