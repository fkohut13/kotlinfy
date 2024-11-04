package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class Main : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusicList()
        }
    }
}

@Composable
fun MusicList() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF3d3d3a),
                        Color(0xFF232321),
                        Color(0xFF000000)
                    ),

                )
            )
    ) {
        Column {
            // Top Bar
            TopBar()

            // Music List
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(3) {
                    MusicItem()
                }
            }

            // Bottom Bar
            BottomBar()
        }
    }
}

@Composable
fun TopBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "PLAYLIST", style = MaterialTheme.typography.headlineMedium, color = Color.White)
    }
}

@Composable
fun MusicItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.androidparty), // Replace with actual image resource
            contentDescription = "Album Cover",
            modifier = Modifier
                .size(50.dp)
                .fillMaxWidth()
                .padding(1.dp)
        )
        Column {
            Text(text = "Musicname", style = MaterialTheme.typography.bodyLarge, color = Color.White)
            Text(text = "Album", style = MaterialTheme.typography.bodySmall, color = Color.White)
        }
    }
}

@Composable
fun BottomBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xff7a4f5f))
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.androidparty), // Replace with actual image resource
                contentDescription = "Album Cover",
                modifier = Modifier
                    .size(50.dp)
                    .padding(5.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = "Current Music", style = MaterialTheme.typography.bodyLarge, color = Color.White)
                Text(text = "Album", style = MaterialTheme.typography.bodySmall, color = Color.White)
            }
        }
        IconButton(onClick = { /* Play action */ }) {
            Icon(
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = "Play",
                tint = Color.White
            )
        }
    }
}

@Preview
@Composable
fun PreviewMusicList() {
    MusicList()
}
