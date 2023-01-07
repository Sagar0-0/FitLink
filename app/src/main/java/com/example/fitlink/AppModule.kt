package com.example.fitlink

import androidx.compose.ui.res.stringResource
import com.auth0.android.Auth0
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFireStore() : FirebaseFirestore{
        return Firebase.firestore
    }

    @Provides
    @Singleton
    fun provideAuth0() : Auth0{
        return Auth0(
            "gs2kmhO6pIcsASFdPG1RWErxFG2hqjIz",
            "dev-s4chjvtt.us.auth0.com"
        )
    }
}