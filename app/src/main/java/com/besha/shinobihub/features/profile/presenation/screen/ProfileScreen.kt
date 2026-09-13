package com.besha.shinobihub.features.profile.presenation.screen

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.besha.shinobihub.R
import com.besha.shinobihub.appcore.navigation.ScreenResources
import com.besha.shinobihub.features.profile.presenation.viewmodel.ProfileAction
import com.besha.shinobihub.features.profile.presenation.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    rootController: NavController,
    childController: NavController,
) {
    val context = LocalContext.current
    val profileViewModel = hiltViewModel<ProfileViewModel>()
    val state by profileViewModel.viewStates.collectAsState()

    LaunchedEffect(Unit) {
        profileViewModel.executeAction(ProfileAction.GetAccount)
        profileViewModel.executeAction(ProfileAction.GetNotifications(context))
    }

    LaunchedEffect(state.loggedOut) {
        if (state.loggedOut.isSuccess) {
            rootController.navigate(ScreenResources.AuthScreenRoute) {
                popUpTo(ScreenResources.MainScreeRoute()) { inclusive = true }
                launchSingleTop = true
            }
        } else if (state.loggedOut.errorThrowable != null) {
            Toast.makeText(context, state.loggedOut.errorThrowable?.message ?: "", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(colorResource(R.color.white))
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        // Profile Header Section
        Text(
            text = "Profile",
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            color = colorResource(R.color.black),
        )

        Card(
            colors = CardDefaults.cardColors(containerColor = colorResource(R.color.light_gray).copy(alpha = 0.5f)),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(0.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val profilePic = state.account.data?.avatar?.tmdb?.avatar_path

                    if (profilePic != null) {
                        AsyncImage(
                            modifier =
                                Modifier
                                    .clip(CircleShape)
                                    .size(64.dp),
                            model = "https://image.tmdb.org/t/p/original$profilePic",
                            contentDescription = "profile pic",
                            contentScale = ContentScale.Crop,
                        )
                    } else {
                        Box(
                            modifier =
                                Modifier
                                    .size(64.dp)
                                    .clip(CircleShape)
                                    .background(colorResource(R.color.dark_blue)),
                            contentAlignment = Alignment.Center,
                        ) {
                            val initials = state.account.data?.username?.take(1)?.uppercase() ?: "?"
                            Text(
                                text = initials,
                                color = colorResource(R.color.white),
                                fontWeight = FontWeight.Bold,
                                fontSize = 26.sp,
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "Welcome back,",
                            color = colorResource(R.color.gray),
                            fontSize = 14.sp,
                        )
                        Text(
                            text = state.account.data?.username ?: "Guest",
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.black),
                            fontSize = 20.sp,
                        )
                    }
                }

                IconButton(
                    onClick = { profileViewModel.executeAction(ProfileAction.LogOut) },
                    modifier = Modifier.background(colorResource(R.color.white).copy(alpha = 0.8f), CircleShape),
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                        contentDescription = "Logout",
                        tint = colorResource(R.color.dark_blue),
                    )
                }
            }
        }

        // Settings Section
        ProfileSectionTitle("Settings")
        Card(
            colors = CardDefaults.cardColors(containerColor = colorResource(R.color.light_gray).copy(alpha = 0.5f)),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(0.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column {
                NotificationPermissionToggle(
                    isEnabled = state.notification,
                    onToggle = {
                        profileViewModel.executeAction(ProfileAction.ToggleNotifications(it, context))
                    },
                )
                HorizontalDivider(modifier = Modifier.padding(horizontal = 20.dp), color = colorResource(R.color.gray).copy(alpha = 0.1f))
                SettingRowSwitch(
                    title = "Dark Mode",
                    icon = Icons.Default.DarkMode,
                    checked = isSystemInDarkTheme(),
                    onToggle = { },
                )
                HorizontalDivider(modifier = Modifier.padding(horizontal = 20.dp), color = colorResource(R.color.gray).copy(alpha = 0.1f))
                SettingRow(
                    title = "About",
                    icon = Icons.Default.Info,
                ) {
                    childController.navigate(ScreenResources.AboutScreenRoute)
                }
/*                HorizontalDivider(modifier = Modifier.padding(horizontal = 20.dp), color = colorResource(R.color.gray).copy(alpha = 0.1f))
                SettingRow(
                    title = "Invite a friend",
                    icon = Icons.Default.PersonAdd
                ) {
                    // Logic for sharing can be added here
                }*/
            }
        }

        // My Collections Section
        if (state.account.data != null) {
            ProfileSectionTitle("My Collections")
            Card(
                colors = CardDefaults.cardColors(containerColor = colorResource(R.color.light_gray).copy(alpha = 0.5f)),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(0.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column {
                    SettingRow("Watchlist", icon = Icons.Default.Bookmark) {
                        childController.navigate(ScreenResources.WatchListScreenRoute)
                    }
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        color = colorResource(R.color.gray).copy(alpha = 0.1f),
                    )
                    SettingRow("Favorites", icon = Icons.Default.Favorite) {
                        childController.navigate(ScreenResources.FavouritesScreenRoute)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun ProfileSectionTitle(title: String) {
    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        color = colorResource(R.color.black),
        modifier = Modifier.padding(start = 4.dp, bottom = 4.dp),
    )
}

@Composable
fun SettingRow(
    title: String,
    icon: ImageVector? = null,
    value: String? = null,
    onClick: () -> Unit = {},
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(horizontal = 20.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = colorResource(R.color.dark_blue),
                    modifier = Modifier.size(22.dp),
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
            Text(
                text = title,
                color = colorResource(R.color.black),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (value != null) {
                Text(
                    text = value,
                    color = colorResource(R.color.gray),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(end = 8.dp),
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = null,
                tint = colorResource(R.color.gray).copy(alpha = 0.6f),
                modifier = Modifier.size(12.dp),
            )
        }
    }
}

@Composable
fun SettingRowSwitch(
    title: String,
    icon: ImageVector? = null,
    description: String = "",
    checked: Boolean,
    onToggle: (Boolean) -> Unit,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(modifier = Modifier.weight(1f), verticalAlignment = if (description.isEmpty()) Alignment.CenterVertically else Alignment.Top) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = colorResource(R.color.dark_blue),
                    modifier = Modifier.size(22.dp).padding(top = if (description.isNotEmpty()) 2.dp else 0.dp),
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
            Column {
                Text(
                    text = title,
                    color = colorResource(R.color.black),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                )
                if (description.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = description,
                        color = colorResource(R.color.gray),
                        fontSize = 13.sp,
                        lineHeight = 18.sp,
                    )
                }
            }
        }
        Switch(
            checked = checked,
            onCheckedChange = onToggle,
            colors =
                SwitchDefaults.colors(
                    checkedThumbColor = colorResource(R.color.white),
                    checkedTrackColor = colorResource(R.color.dark_blue),
                    uncheckedThumbColor = colorResource(R.color.white),
                    uncheckedTrackColor = colorResource(R.color.gray).copy(alpha = 0.5f),
                    uncheckedBorderColor = androidx.compose.ui.graphics.Color.Transparent,
                ),
        )
    }
}

@Composable
fun NotificationPermissionToggle(
    isEnabled: Boolean,
    onToggle: (Boolean) -> Unit,
) {
    val context = LocalContext.current
    val activity = LocalContext.current as? android.app.Activity

    val permissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
        ) { granted ->
            if (granted) {
                onToggle(true)
            } else {
                Toast.makeText(context, "Notifications are disabled", Toast.LENGTH_SHORT).show()
                onToggle(false)
            }
        }

    SettingRowSwitch(
        title = "Notifications",
        icon = Icons.Default.Notifications,
        description = "Enable notifications to receive daily trending movie reminders and stay updated with the latest releases.",
        checked = isEnabled,
        onToggle = { checked ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val granted =
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS,
                    ) == PackageManager.PERMISSION_GRANTED

                if (!granted && checked) {
                    val showRationale =
                        activity?.let {
                            androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale(
                                it,
                                Manifest.permission.POST_NOTIFICATIONS,
                            )
                        } ?: false

                    if (showRationale) {
                        Toast.makeText(context, "Please allow notifications to get daily trending updates", Toast.LENGTH_LONG).show()
                        permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    } else {
                        permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                } else {
                    onToggle(checked)
                }
            } else {
                val notificationsEnabled = NotificationManagerCompat.from(context).areNotificationsEnabled()

                if (!notificationsEnabled) {
                    Toast.makeText(context, "Please enable notifications in app settings", Toast.LENGTH_LONG).show()
                    val intent =
                        Intent(android.provider.Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                            putExtra("app_package", context.packageName)
                            putExtra("app_uid", context.applicationInfo.uid)
                        }
                    context.startActivity(intent)
                    onToggle(false)
                } else {
                    onToggle(checked)
                }
            }
        },
    )
}
