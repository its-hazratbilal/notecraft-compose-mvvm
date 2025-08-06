package com.hazratbilal.notecraft.compose.ui.notes

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import com.hazratbilal.notecraft.compose.R
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hazratbilal.notecraft.compose.remote.NetworkResult
import com.hazratbilal.notecraft.compose.theme.BlackTextColor
import com.hazratbilal.notecraft.compose.theme.DefaultTextColor
import com.hazratbilal.notecraft.compose.theme.ItemBackground
import com.hazratbilal.notecraft.compose.theme.ItemBorder
import androidx.compose.runtime.getValue
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.hazratbilal.notecraft.compose.model.NoteRequest
import com.hazratbilal.notecraft.compose.model.NotesResponse
import com.hazratbilal.notecraft.compose.theme.DarkGrayTextColor
import com.hazratbilal.notecraft.compose.theme.WhiteColor
import com.hazratbilal.notecraft.compose.ui.components.Loading
import com.hazratbilal.notecraft.compose.utils.SharedPrefs
import com.hazratbilal.notecraft.compose.utils.sdp
import com.hazratbilal.notecraft.compose.utils.showToast
import com.hazratbilal.notecraft.compose.utils.ssp
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.hilt.navigation.compose.hiltViewModel
import com.hazratbilal.notecraft.compose.theme.DefaultColor


@Composable
fun NotesScreen(navController: NavHostController, sharedPrefs: SharedPrefs) {

    val context = LocalContext.current

    val notesViewModel: NotesViewModel = hiltViewModel()
    val notesResponse by notesViewModel.notesResponseStateFlow.collectAsState()

    val savedStateHandle = remember(navController) {
        navController.currentBackStackEntry?.savedStateHandle
    }
    val refreshNotes = savedStateHandle?.get<Boolean>("refreshNotes") ?: false
    val isNotesFetched = rememberSaveable(navController.currentBackStackEntry?.id) {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        if (!isNotesFetched.value) {
            notesViewModel.getNotes()
            isNotesFetched.value = true
        }
    }

    LaunchedEffect(refreshNotes) {
        if (refreshNotes) {
            notesViewModel.getNotes()
            savedStateHandle?.remove<Boolean>("refreshNotes")
        }
    }

    Loading(isLoading = notesResponse is NetworkResult.Loading)

    LaunchedEffect(notesResponse) {
        when (notesResponse) {
            is NetworkResult.AuthError -> {
                context.showToast("Authentication failed, please login again")
                notesViewModel.clearState()

                sharedPrefs.clearAll()
                navController.navigate("login") {
                    popUpTo("login") { inclusive = true }
                }
            }

            is NetworkResult.Error -> {
                context.showToast(notesResponse.message ?: "Unknown error")
                notesViewModel.clearState()
            }

            else -> Unit
        }

    }


    Box(modifier = Modifier.fillMaxSize()) {

        when (notesResponse) {
            is NetworkResult.Success -> {
                val notesData = notesResponse.data
                if (notesData?.success == true) {
                    if (notesData.notes.toList().isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No Note Found",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.ssp,
                                color = DarkGrayTextColor
                            )
                        }
                    } else {
                        val notes = notesData.notes
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(top = 6.sdp, bottom = 6.sdp)
                        ) {
                            items(notes) { item ->
                                NoteItem(
                                    navController = navController,
                                    notesViewModel = notesViewModel,
                                    note = item
                                )
                            }
                        }
                    }
                } else {
                    context.showToast(notesResponse.message ?: "Unknown error")
                    notesViewModel.clearState()
                }
            }

            else -> Unit
        }

        FloatingActionButton(
            onClick = {
                navController.navigate("create_note")
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.sdp),
            shape = CircleShape,
            containerColor = DefaultColor,
            contentColor = WhiteColor
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = "Add Note",
                colorFilter = ColorFilter.tint(WhiteColor),
                modifier = Modifier.size(28.sdp)
            )
        }

    }
}

@Composable
fun NoteItem(navController: NavHostController, notesViewModel: NotesViewModel, note: NotesResponse.Note) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 12.sdp,
                vertical = 6.sdp
            )
            .border(
                width = 1.dp,
                color = ItemBorder,
                shape = RoundedCornerShape(4.sdp)
            )
            .background(
                color = ItemBackground,
                shape = RoundedCornerShape(4.sdp)
            )
    ) {
        Column(
            modifier = Modifier
                .padding(12.sdp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = note.title,
                style = TextStyle(
                    color = BlackTextColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.ssp,
                    lineHeight = 18.ssp,
                )
            )

            Spacer(modifier = Modifier.height(8.sdp))

            Text(
                text = note.description,
                style = TextStyle(
                    color = DefaultTextColor,
                    fontSize = 12.ssp,
                    lineHeight = 18.ssp,
                )
            )

            Spacer(modifier = Modifier.height(12.sdp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_calendar),
                        contentDescription = "Calendar icon",
                        modifier = Modifier
                            .size(13.sdp),
                        colorFilter = ColorFilter.tint(DefaultTextColor)
                    )

                    Spacer(modifier = Modifier.width(4.sdp))

                    Text(
                        text = note.created_at,
                        style = TextStyle(
                            color = DefaultTextColor,
                            fontSize = 10.ssp
                        )
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedButton(
                        onClick = {
                            val gson = Gson()
                            val noteJson = Uri.encode(gson.toJson(note))
                            navController.navigate("edit_note/$noteJson")
                        },
                        modifier = Modifier
                            .height(22.sdp),
                        shape = RoundedCornerShape(2.sdp),
                        contentPadding = PaddingValues(horizontal = 11.sdp, vertical = 0.dp),
                        border = BorderStroke(1.sdp, ItemBorder),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = BlackTextColor,
                            containerColor = Color.Transparent
                        ),
                        elevation = null
                    ) {
                        Text(
                            text = "Edit",
                            style = TextStyle(
                                color = BlackTextColor,
                                fontSize = 10.ssp
                            )
                        )
                    }

                    Spacer(modifier = Modifier.width(8.sdp))

                    OutlinedButton(
                        onClick = {
                            notesViewModel.deleteNote(NoteRequest(note.id, "", ""))
                        },
                        modifier = Modifier
                            .height(22.sdp),
                        shape = RoundedCornerShape(2.sdp),
                        contentPadding = PaddingValues(horizontal = 11.sdp, vertical = 0.dp),
                        border = BorderStroke(1.sdp, ItemBorder),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = BlackTextColor,
                            containerColor = Color.Transparent
                        ),
                        elevation = null
                    ) {
                        Text(
                            text = "Delete",
                            style = TextStyle(
                                color = BlackTextColor,
                                fontSize = 10.ssp
                            )
                        )
                    }

                }

            }
        }
    }
}
