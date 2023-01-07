package com.pes.meetcatui.feature_user.presentation.screen_login

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.pes.meetcatui.SavedPreference

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val _warning = mutableStateOf("")
    val warning: State<String> = _warning
    private val context = getApplication<Application>().applicationContext

    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("818069305025-5vhb5ef5ddlekrhrnqa866fkum6cjbdn.apps.googleusercontent.com")
        .requestEmail()
        .build()
    var mGoogleSignInClient = GoogleSignIn.getClient(context, gso)


    fun tancarSessio() {
        mGoogleSignInClient.signOut();
        FirebaseAuth.getInstance().signOut();
        SavedPreference.setEmail(context, "")
        SavedPreference.setUsername(context, "")
    }

    fun getGoogleClient() : GoogleSignInClient {
        return mGoogleSignInClient
    }


    fun Login(username: String, password: String) {
        if (username.isEmpty() || password.isEmpty()) {
            _warning.value = "El nom d'usuari o la contrassenya son buïts."
            return;
        }
        if (!authenticate(username = username, password = password)) {
            _warning.value = "El nom d'usuari o la contrassenya son incorrectes."
            return;
        }
        //else navegar a la seguent pantalla
    }

    private fun authenticate(username: String, password: String): Boolean {
        return (username == "user" && password == "password")
    }

    fun tancarCompte() {
        TODO("Not yet implemented")
    }

}