package com.example.practice1.ui.screens.map

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

@Composable
fun UserLocationMapScreen(){
   val context = LocalContext.current
   var hasLocationPermission by remember { mutableStateOf(false) }
   var permissionRequested by remember { mutableStateOf(false) }

   val permissionLauncher = rememberLauncherForActivityResult(
      contract = ActivityResultContracts.RequestMultiplePermissions()
   ) { permissions ->
      hasLocationPermission = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
              permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
      permissionRequested = true
   }

   LaunchedEffect(Unit) {
      Configuration.getInstance().userAgentValue = context.packageName
      permissionLauncher.launch(
         arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
         )
      )
   }

   if(hasLocationPermission){
      AndroidView(
         modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(16.dp))
         , factory = { ctx ->
            MapView(ctx).apply {
               setTileSource(TileSourceFactory.MAPNIK)
               setMultiTouchControls(true)
               controller.setZoom(18.0)

               val locationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(ctx), this)
               locationOverlay.enableMyLocation()
               locationOverlay.enableFollowLocation()

               overlays.add(locationOverlay)
            }
         }
      )
   } else {
      Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
         if(!permissionRequested){
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
               Text("Location is required to view map....")
               Button(
                  onClick = {
                     permissionLauncher.launch(
                        arrayOf(
                           Manifest.permission.ACCESS_FINE_LOCATION,
                           Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                     )
                  }
               ) {
                  Text("Request Permission")
               }
            }
         }
      }
   }
}