package go.deyu.eatmapmerge.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val googleSignInClient: GoogleSignInClient
) : ViewModel() {

    private val _state = MutableStateFlow<LoginState>(LoginState.Idle)
    val state: StateFlow<LoginState> = _state

    fun handleIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.SignInWithGoogle -> signInWithGoogle()
        }
    }

    private fun signInWithGoogle() {
        viewModelScope.launch {
            _state.value = LoginState.Loading
            try {
                val account = googleSignInClient.silentSignIn().await()
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                val authResult = firebaseAuth.signInWithCredential(credential).await()
                _state.value = LoginState.Success(authResult.user!!)
            } catch (e: Exception) {
                Logger.e("signInWithGoogle Error $e")
                _state.value = LoginState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }
}