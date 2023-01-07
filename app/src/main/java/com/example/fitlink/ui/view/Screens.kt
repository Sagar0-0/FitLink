package com.example.fitlink.ui.view

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.UserProfile
import com.example.fitlink.MainActivity
import com.example.fitlink.MainViewModel

@Composable
fun Home() {
    Text(text = "HOME")
}

@Composable
fun Search() {

    Text(text = "Search")
}

@Composable
fun BookMarks() {

    Text(text = "Bookmarks")
}

@Composable
fun Profile(context: MainActivity) {
    val viewModel: MainViewModel = hiltViewModel()
    Column {
        Button(
            onClick = { showUserProfile(viewModel.accessToken.value!!, viewModel.account) }) {
            Text(text = "ShowProfile")
        }
        Button(
            onClick = { logout(viewModel, context) }) {
            Text(text = "Logout")
        }
    }
}

private fun logout(viewModel: MainViewModel, context: MainActivity) {
    WebAuthProvider.logout(viewModel.account)
        .withScheme("demo")
        .start(context, object : Callback<Void?, AuthenticationException> {
            override fun onSuccess(result: Void?) {
                // The user has been logged out!
                Log.d(ContentValues.TAG, "The user has been logged out!")
                viewModel.accessToken.value = null
            }

            override fun onFailure(error: AuthenticationException) {
                // Something went wrong!
                Log.e(ContentValues.TAG, "Logout Failure")
            }
        })
}

private fun showUserProfile(accessToken: String, account: Auth0) {
    val client = AuthenticationAPIClient(account)

    // With the access token, call `userInfo` and get the profile from Auth0.
    client.userInfo(accessToken)
        .start(object : Callback<UserProfile, AuthenticationException> {
            override fun onFailure(error: AuthenticationException) {
                // Something went wrong!
                Log.e(ContentValues.TAG, "User profile not found ${error.message}")
            }

            override fun onSuccess(result: UserProfile) {
                // We have the user's profile!
                val email = result.email
                val name = result.name
                Log.d(ContentValues.TAG, "We have the user's profile! $email $name")
            }
        })
}