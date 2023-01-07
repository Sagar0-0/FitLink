package com.example.fitlink.ui.view

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fitlink.MainViewModel
import com.example.fitlink.R
import com.example.fitlink.model.FitLinkPost
import com.example.fitlink.model.WorkoutPlan
import com.example.fitlink.repository.ResultState
import com.example.fitlink.ui.theme.lightBlue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Home(
    viewModel: MainViewModel = hiltViewModel()
) {
    val isTypingPost = remember {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()
    val isDialog = remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    if (isDialog.value) {
        CircularProgressIndicator()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray.copy(0.8f))
    ) {
        Column {
            Box(
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
                    .background(Color.DarkGray)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomStart)
                        .padding(start = 10.dp, end = 10.dp, bottom = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        color = Color.White.copy(0.8f),
                        text = "HOME",
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 20.sp
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_baseline_notifications_24),
                        contentDescription = "Notifications",
                        colorFilter = ColorFilter.tint(Color.White.copy(0.8f))
                    )
                }
            }
            val allPosts = viewModel.posts.value
            if (allPosts.isLoading) {
                isDialog.value = true
            } else if (allPosts.data.isNotEmpty()) {
                isDialog.value = false
                LazyColumn(
                    modifier = Modifier.padding(
                        start = 10.dp,
                        end = 10.dp,
                        bottom = 56.dp
                    )
                ) {
                    items(
                        items = allPosts.data,
                        key = {
                            it.key
                        }) {
                        PostItem(fitLinkPost = it.post) {
                            //show details of post
                        }
                    }
                }
            } else if (allPosts.error.isNotBlank()) {
                isDialog.value = false
                Text(text = "ERROR")
            }

        }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 68.dp, end = 12.dp),
            onClick = {
                isTypingPost.value = true
            }) {
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_add_24),
                contentDescription = "Create new post",
                colorFilter = ColorFilter.tint(Color.White)
            )
        }

        var title by remember {
            mutableStateOf("")
        }
        var imageUrl by remember {
            mutableStateOf("")
        }
        var category by remember {
            mutableStateOf("")
        }
        var sunday by remember {
            mutableStateOf("")
        }
        var monday by remember {
            mutableStateOf("")
        }
        var tuesday by remember {
            mutableStateOf("")
        }
        var wednesday by remember {
            mutableStateOf("")
        }
        var thursday by remember {
            mutableStateOf("")
        }
        var friday by remember {
            mutableStateOf("")
        }
        var saturday by remember {
            mutableStateOf("")
        }

        if (isTypingPost.value) {
            Dialog(
                onDismissRequest = { isTypingPost.value = false },
                properties = DialogProperties(usePlatformDefaultWidth = false),
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(0.94f),
                    shape = RoundedCornerShape(12.dp),
                    color = Color.White
                ) {
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            modifier = Modifier.padding(top = 10.dp, bottom = 6.dp),
                            text = "Create a new FitLink Post",
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            fontFamily = FontFamily.SansSerif
                        )

                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp),
                            value = title,
                            onValueChange = {
                                title = it
                            },
                            label = {
                                Text(text = "Headline")
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = lightBlue,
                                cursorColor = Color.Black,
                                disabledLabelColor = lightBlue,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            trailingIcon = {
                                if (title.isNotEmpty()) {
                                    IconButton(onClick = { title = "" }) {
                                        Icon(
                                            imageVector = Icons.Outlined.Close,
                                            contentDescription = null
                                        )
                                    }
                                }
                            }
                        )


                        Text(
                            text = "Category(e.g. Bulk, Cut, etc.)",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 10.dp)
                        )
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp),
                            value = category,
                            onValueChange = {
                                category = it
                            },
                            label = {
                                Text(text = "Category")
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = lightBlue,
                                cursorColor = Color.Black,
                                disabledLabelColor = lightBlue,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            trailingIcon = {
                                if (category.isNotEmpty()) {
                                    IconButton(onClick = { category = "" }) {
                                        Icon(
                                            imageVector = Icons.Outlined.Close,
                                            contentDescription = null
                                        )
                                    }
                                }
                            }
                        )

                        Text(text = "Workout Routine", fontWeight = FontWeight.Bold)

                        TextField(
                            modifier = Modifier.fillMaxWidth().padding(bottom =10.dp),
                            value = monday,
                            onValueChange = {
                                monday = it
                            },
                            label = {
                                Text(text = "Monday")
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = lightBlue,
                                cursorColor = Color.Black,
                                disabledLabelColor = lightBlue,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            trailingIcon = {
                                if (monday.isNotEmpty()) {
                                    IconButton(onClick = { monday = "" }) {
                                        Icon(
                                            imageVector = Icons.Outlined.Close,
                                            contentDescription = null
                                        )
                                    }
                                }
                            }
                        )

                        TextField(
                            modifier = Modifier.fillMaxWidth().padding(bottom =10.dp),
                            value = tuesday,
                            onValueChange = {
                                tuesday = it
                            },
                            label = {
                                Text(text = "Tuesday")
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = lightBlue,
                                cursorColor = Color.Black,
                                disabledLabelColor = lightBlue,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            trailingIcon = {
                                if (tuesday.isNotEmpty()) {
                                    IconButton(onClick = { tuesday = "" }) {
                                        Icon(
                                            imageVector = Icons.Outlined.Close,
                                            contentDescription = null
                                        )
                                    }
                                }
                            }
                        )

                        TextField(
                            modifier = Modifier.fillMaxWidth().padding(bottom =10.dp),
                            value = wednesday,
                            onValueChange = {
                                wednesday = it
                            },
                            label = {
                                Text(text = "Wednesday")
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = lightBlue,
                                cursorColor = Color.Black,
                                disabledLabelColor = lightBlue,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            trailingIcon = {
                                if (wednesday.isNotEmpty()) {
                                    IconButton(onClick = { wednesday = "" }) {
                                        Icon(
                                            imageVector = Icons.Outlined.Close,
                                            contentDescription = null
                                        )
                                    }
                                }
                            }
                        )

                        TextField(
                            modifier = Modifier.fillMaxWidth().padding(bottom =10.dp),
                            value = thursday,
                            onValueChange = {
                                thursday = it
                            },
                            label = {
                                Text(text = "Thursday")
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = lightBlue,
                                cursorColor = Color.Black,
                                disabledLabelColor = lightBlue,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            trailingIcon = {
                                if (thursday.isNotEmpty()) {
                                    IconButton(onClick = { thursday = "" }) {
                                        Icon(
                                            imageVector = Icons.Outlined.Close,
                                            contentDescription = null
                                        )
                                    }
                                }
                            }
                        )

                        TextField(
                            modifier = Modifier.fillMaxWidth().padding(bottom =10.dp),
                            value = friday,
                            onValueChange = {
                                friday = it
                            },
                            label = {
                                Text(text = "Friday")
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = lightBlue,
                                cursorColor = Color.Black,
                                disabledLabelColor = lightBlue,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            trailingIcon = {
                                if (friday.isNotEmpty()) {
                                    IconButton(onClick = { friday = "" }) {
                                        Icon(
                                            imageVector = Icons.Outlined.Close,
                                            contentDescription = null
                                        )
                                    }
                                }
                            }
                        )

                        TextField(
                            modifier = Modifier.fillMaxWidth().padding(bottom =10.dp),
                            value = saturday,
                            onValueChange = {
                                saturday = it
                            },
                            label = {
                                Text(text = "Saturday")
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = lightBlue,
                                cursorColor = Color.Black,
                                disabledLabelColor = lightBlue,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            trailingIcon = {
                                if (saturday.isNotEmpty()) {
                                    IconButton(onClick = { saturday = "" }) {
                                        Icon(
                                            imageVector = Icons.Outlined.Close,
                                            contentDescription = null
                                        )
                                    }
                                }
                            }
                        )

                        TextField(
                            modifier = Modifier.fillMaxWidth().padding(bottom =10.dp),
                            value = sunday,
                            onValueChange = {
                                sunday = it
                            },
                            label = {
                                Text(text = "Sunday")
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = lightBlue,
                                cursorColor = Color.Black,
                                disabledLabelColor = lightBlue,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            trailingIcon = {
                                if (sunday.isNotEmpty()) {
                                    IconButton(onClick = { sunday = "" }) {
                                        Icon(
                                            imageVector = Icons.Outlined.Close,
                                            contentDescription = null
                                        )
                                    }
                                }
                            }
                        )

                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(onClick = {
                                isTypingPost.value = false
                                scope.launch(Dispatchers.Main) {
                                    viewModel.uploadPost(
                                        FitLinkPost(
                                            user = viewModel.user,
                                            title = title,
                                            category = category,
                                            imageUrl = imageUrl,
                                            workoutPlan = WorkoutPlan(
                                                monday,
                                                tuesday,
                                                wednesday,
                                                thursday,
                                                friday,
                                                saturday,
                                                sunday
                                            )
                                        )
                                    ).collect {
                                        when (it) {
                                            is ResultState.Success -> {
                                                isDialog.value = false
                                                Toast.makeText(context, it.data, Toast.LENGTH_LONG)
                                                    .show()
                                                viewModel.getPosts()
                                            }
                                            is ResultState.Failure -> {
                                                isDialog.value = false
                                                Toast.makeText(
                                                    context,
                                                    it.msg.message,
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            }
                                            is ResultState.Loading -> {
                                                isDialog.value = true
                                            }
                                        }
                                    }
                                }
                            }) {
                                Text("Post")
                            }
                        }
                    }

                }
            }

        }

    }

}


