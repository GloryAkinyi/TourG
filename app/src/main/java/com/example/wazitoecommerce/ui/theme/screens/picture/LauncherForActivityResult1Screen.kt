package com.example.wazitoecommerce.ui.theme.screens.picture

import android.graphics.Bitmap
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.wazitoecommerce.Manifest
import com.example.wazitoecommerce.R

private const val URL = "activity/LauncherForActivityResult1Screen.kt"
@Composable
fun LauncherForActivityResult1Screen(){
    DefaultScaffold(
        title = ActivityNavRoutes.LauncherForActivityResult1,
        link = URL,
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues = it),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LauncherForActivityResultExample()
        }
    }
}

@Composable
private fun LauncherForActivityResultExample() {
    val context = LocalContext.current
    val result = rememberSaveable { mutableStateOf<Bitmap?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
        result.value = it
    }
    val permissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                startActivitySafe(
                    startActivity = { launcher.launch() },
                        Toast.makeText(context,"Activity not found", Toast.LENGTH_LONG).show()
                )
            } else {
                context.shortToast(R.string.permission_not_granted)
            }
        }

    Button(
        onClick = {
            if (context.isCameraPermissionGranted) {
                launcher.launch()
            } else {
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    ) {
        Text(text = stringResource(id = R.string.take_picture))
    }

    result.value?.let { image ->
        Image(
            image.asImageBitmap(),
            null,
            modifier = Modifier
                .size(250.dp)
        )
    }
}