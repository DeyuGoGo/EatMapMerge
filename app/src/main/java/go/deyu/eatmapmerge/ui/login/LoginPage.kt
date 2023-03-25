package go.deyu.eatmapmerge.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun LoginPage(viewModel: LoginViewModel ) {
    val state = viewModel.state.collectAsState().value

    Surface(color = MaterialTheme.colors.background) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (state) {
                is LoginState.Idle -> {
                    GoogleSignInButton {
                        viewModel.handleIntent(LoginIntent.SignInWithGoogle)
                    }
                }
                is LoginState.Loading -> {
                    CircularProgressIndicator()
                }
                is LoginState.Success -> {
                    Text(text = "Welcome, ${state.user.displayName}")
                }
                is LoginState.Error -> {
                    Text(text = "Error: ${state.message}")
                }
            }
        }
    }
}

@Composable
fun GoogleSignInButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.padding(16.dp),
        shape = RoundedCornerShape(50),
        elevation = ButtonDefaults.elevation(defaultElevation = 0.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
//            Icon(
//                painter = painterResource(id = R.drawable.ic_google_logo),
//                contentDescription = "Google logo"
//            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Sign in with Google")
        }
    }
}