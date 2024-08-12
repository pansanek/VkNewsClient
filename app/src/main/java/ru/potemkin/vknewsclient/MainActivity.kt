package ru.potemkin.vknewsclient

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
                val launcher = rememberLauncherForActivityResult(
                    contract = VK.getVKAuthActivityResultContract()
                ) {
                    when (it) {
                        is VKAuthenticationResult.Success -> {
                            Log.d("MAINACTIVITY", "Auth success")
                        }

                        is VKAuthenticationResult.Failed -> {
                            Log.d("MAINACTIVITY", "Auth failed")
                        }
                    }
                }
                launcher.launch(listOf(VKScope.WALL))
                ActivityResultTest()
            }
        }
    }
}
