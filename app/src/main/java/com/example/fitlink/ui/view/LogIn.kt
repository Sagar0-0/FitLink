package com.example.fitlink.ui.view

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.example.fitlink.MainActivity
import com.example.fitlink.MainViewModel

@Composable
fun Login(context: MainActivity) {
    val viewModel: MainViewModel = hiltViewModel()
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { loginWithBrowser(viewModel.account, viewModel, context) }) {
            Text(text = "Login")
        }
    }
}

private fun loginWithBrowser(account: Auth0, viewModel: MainViewModel, context: MainActivity) {
    // Setup the WebAuthProvider, using the custom scheme and scope.

    WebAuthProvider.login(account)
        .withScheme("demo")
        .withScope("openid profile email")
        // Launch the authentication passing the callback where the results will be received
        .start(context, object : Callback<Credentials, AuthenticationException> {
            // Called when there is an authentication failure
            override fun onFailure(error: AuthenticationException) {
                // Something went wrong!
                Log.e(ContentValues.TAG, "Login Failure")
            }

            // Called when authentication completed successfully
            override fun onSuccess(result: Credentials) {
                // Get the access token from the credentials object.
                // This can be used to call APIs
                viewModel.accessToken.value = result.accessToken
                Log.d(ContentValues.TAG, "Authentication completed successfully")
            }
        })
}

