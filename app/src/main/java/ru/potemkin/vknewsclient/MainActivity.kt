package ru.potemkin.vknewsclient

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope
import ru.potemkin.vknewsclient.ui.theme.ActivityResultTest
import ru.potemkin.vknewsclient.ui.theme.MainScreen
import ru.potemkin.vknewsclient.ui.theme.VkNewsClientTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VkNewsClientTheme {
                val someState = remember{
                    mutableStateOf(true)
                }
                Log.d("MAINACTIVITYYY", "Recomposition:${someState.value}")
                val launcher = rememberLauncherForActivityResult(
                    contract = VK.getVKAuthActivityResultContract()
                ) {
                    when (it) {
                        is VKAuthenticationResult.Success -> {
                            Log.d("MAINACTIVITYYY", "Auth success")
                        }

                        is VKAuthenticationResult.Failed -> {
                            Log.d("MAINACTIVITYYY", "Auth failed")
                        }
                    }
                }
                LaunchedEffect(key1 = someState.value) {
                    Log.d("MAINACTIVITYYY", "Launched")
//                    launcher.launch(listOf(VKScope.WALL))
                }
                SideEffect {
                    Log.d("MAINACTIVITYYY", "Side")
//                    launcher.launch(listOf(VKScope.WALL))
                }
                Button(onClick = { someState.value = !someState.value }) {
                    Text(text = "Change State")
                }
//                MainScreen()
            }
        }
    }
}
