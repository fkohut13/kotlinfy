package com.example.myapplication

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var mediaPlayer: MediaPlayer
    private var mediaPlayerPreparado = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try{
            mediaPlayer = MediaPlayer.create(this, R.raw.example)
            mediaPlayerPreparado = true
        }
        catch (e: Exception) {
            println(e.message)
        }


        setContent {
            TocarMusica(mediaPlayer, mediaPlayerPreparado)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::mediaPlayer.isInitialized) {
            mediaPlayer.release()
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TocarMusica(mediaPlayer: MediaPlayer, estaPronto: Boolean, modifier: Modifier = Modifier) {
    var estaTocando by remember { mutableStateOf(false) }
    var botaoDeslizantePosicao by remember { mutableStateOf(0f) }
    var duracao by remember { mutableStateOf(0) }


    val scope = rememberCoroutineScope()


    if (estaPronto) {
        duracao = mediaPlayer.duration
    }


    LaunchedEffect(estaTocando) {
        if (estaTocando && estaPronto) {
            mediaPlayer.start()
            scope.launch {
                while (estaTocando && mediaPlayer.isPlaying) {
                    botaoDeslizantePosicao = mediaPlayer.currentPosition.toFloat() / duracao
                    delay(1000L)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF3d3d3a),
                    titleContentColor = Color.White,
                ),
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "PLAYING FROM PLAYLIST")
                        Text(text = "PLAYLIST")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowDown,
                            tint = Color.White,
                            contentDescription = "Close"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            tint = Color.White,
                            contentDescription = "More options"
                        )
                    }
                },
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .background(Color.Black)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {


                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Menu",
                        tint = Color.White
                    )
                }
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Filled.Share,
                        contentDescription = "Share",
                        tint = Color.White
                    )
                }
            }
        }
    ) { content ->
        Column(
            modifier
                .padding(content)
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF3d3d3a),
                            Color(0xFF232321),
                            Color(0xFF000000)
                        ),
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.img),
                contentDescription = "Album Cover",
                modifier = Modifier
                    .size(400.dp)
                    .padding(30.dp),
                contentScale = ContentScale.Crop
            )
            Text(text = "Chamber of reflection", style = MaterialTheme.typography.headlineSmall, color = Color.White)
            Text(text = "Mac Demarco", style = MaterialTheme.typography.bodyLarge, color = Color.White)
            Row(
                modifier
                    .padding(0.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = { }) {
                    Icon(painter = painterResource(id = R.drawable.baseline_skip_previous_24), contentDescription = "Previous", tint = Color.White)
                }
                PlayPauseButton(
                    estaTocando = estaTocando,
                    onPlayPauseToggle = {
                        if (estaPronto) {
                            estaTocando = !estaTocando
                            if (estaTocando) {
                                mediaPlayer.start()
                            } else {
                                mediaPlayer.pause()
                            }
                        }
                    }
                )
                IconButton(onClick = {  }) {
                    Icon(painter = painterResource(id = R.drawable.baseline_skip_next_24), contentDescription = "Next", tint = Color.White)
                }


            }

            if (estaPronto) {
                Slider(
                    value = botaoDeslizantePosicao,
                    onValueChange = { newValue -> botaoDeslizantePosicao = newValue
                        if (mediaPlayer.isPlaying) {
                            mediaPlayer.seekTo((newValue * duracao).toInt())
                        }
                    },
                    modifier = Modifier.fillMaxWidth(0.87f)
                )
                Text(
                    text = "${(botaoDeslizantePosicao * duracao / 1000).toInt()}s / ${duracao / 1000}s",
                    color = Color.White
                )
            } else {
                Text("Loading...", color = Color.White)
            }
        }
    }
}

@Composable
fun PlayPauseButton(estaTocando: Boolean, onPlayPauseToggle: () -> Unit) {
    IconButton(onClick = onPlayPauseToggle) {
        if (estaTocando) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_pause_24),
                contentDescription = "Pause",
                tint = Color.White,
                modifier = Modifier.size(60.dp)
            )
        } else {
            Icon(
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = "Play",
                tint = Color.White,
                modifier = Modifier.size(60.dp)
            )
        }
    }
}


