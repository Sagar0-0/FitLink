package com.example.fitlink.repository

import com.example.fitlink.model.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class FireStoreRepoImpl
@Inject constructor(
    private val db: FirebaseFirestore
) : FireStoreRepository {

    override fun uploadPost(post: FitLinkPost): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)
        db.collection("posts").add(post)
            .addOnSuccessListener {
                trySend(ResultState.Success("Insertion ID ${it.id}"))
            }.addOnFailureListener {
                trySend(ResultState.Failure(it))
            }
        awaitClose {
            close()
        }
    }

    override fun getPosts(): Flow<ResultState<List<FireStoreResponse>>> = callbackFlow {
        trySend(ResultState.Loading)
        db.collection("posts")
            .get()
            .addOnSuccessListener { it ->
                val posts = it.map { data ->
                    FireStoreResponse(
                        post = FitLinkPost(
                            title = data["title"] as String,
                            user = User(),
                            category = data["category"] as String,
                            imageUrl = data["imageUrl"] as String,
                            workoutPlan = WorkoutPlan()
                        ),
                        key = data.id
                    )
                }
                trySend(ResultState.Success(posts))
            }.addOnFailureListener {
                trySend(ResultState.Failure(it))
            }
        awaitClose {
            close()
        }
    }

    override fun deletePost(key: String): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)
        db.collection("posts").document(key)
            .delete().addOnCompleteListener {
                trySend(ResultState.Success("Deleted"))
            }.addOnFailureListener {
                trySend(ResultState.Failure(it))
            }
        awaitClose {
            close()
        }
    }

    override fun updatePost(item: FireStoreResponse): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)
        val map = HashMap<String,Any>()
        //Update post manually here
    }
}