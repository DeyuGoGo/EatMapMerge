package go.deyu.eatmapmerge.ui.login

sealed class LoginIntent {
    object SignInWithGoogle : LoginIntent()
}