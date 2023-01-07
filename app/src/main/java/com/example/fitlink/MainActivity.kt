package com.example.fitlink

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.auth0.android.result.UserProfile
import com.example.fitlink.ui.theme.FitLinkTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var account: Auth0
    private var accessToken = mutableStateOf<String?>(null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FitLinkTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    account = Auth0(
                        stringResource(id = R.string.client_id),
                        stringResource(id = R.string.com_auth0_domain)
                    )
                    if (accessToken.value == null) {
                        Column() {
                            Button(
                                onClick = { loginWithBrowser() }) {
                                Text(text = "Login")
                            }
                        }
                    } else {
                        Column() {
                            Button(
                                onClick = { showUserProfile(accessToken.value!!) }) {
                                Text(text = "ShowProfile")
                            }
                            Button(
                                onClick = { logout() }) {
                                Text(text = "Logout")
                            }
                        }
                    }


                }
            }
        }
    }

    private fun loginWithBrowser() {
        // Setup the WebAuthProvider, using the custom scheme and scope.

        WebAuthProvider.login(account)
            .withScheme("demo")
            .withScope("openid profile email")
            // Launch the authentication passing the callback where the results will be received
            .start(this, object : Callback<Credentials, AuthenticationException> {
                // Called when there is an authentication failure
                override fun onFailure(error: AuthenticationException) {
                    // Something went wrong!
                    Log.e(TAG, "Login Failure")
                }

                // Called when authentication completed successfully
                override fun onSuccess(result: Credentials) {
                    // Get the access token from the credentials object.
                    // This can be used to call APIs
                    accessToken.value = result.accessToken
                    Log.d(TAG, "Authentication completed successfully")
                }
            })
    }

    private fun logout() {
        WebAuthProvider.logout(account)
            .withScheme("demo")
            .start(this, object : Callback<Void?, AuthenticationException> {
                override fun onSuccess(result: Void?) {
                    // The user has been logged out!
                    Log.d(TAG, "The user has been logged out!")
                }

                override fun onFailure(error: AuthenticationException) {
                    // Something went wrong!
                    Log.e(TAG, "Logout Failure")
                }
            })
    }

    private fun showUserProfile(accessToken: String) {
        val client = AuthenticationAPIClient(account)

        // With the access token, call `userInfo` and get the profile from Auth0.
        client.userInfo(accessToken)
            .start(object : Callback<UserProfile, AuthenticationException> {
                override fun onFailure(error: AuthenticationException) {
                    // Something went wrong!
                    Log.e(TAG, "User profile not found ${error.message}")
                }

                override fun onSuccess(result: UserProfile) {
                    // We have the user's profile!
                    val email = result.email
                    val name = result.name
                    Log.d(TAG, "We have the user's profile! $email $name")
                }
            })
    }
}
