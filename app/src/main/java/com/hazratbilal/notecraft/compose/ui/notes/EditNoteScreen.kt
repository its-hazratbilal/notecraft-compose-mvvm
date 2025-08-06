package com.hazratbilal.notecraft.compose.ui.notes

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.hazratbilal.notecraft.compose.model.NoteRequest
import com.hazratbilal.notecraft.compose.model.NotesResponse
import com.hazratbilal.notecraft.compose.remote.NetworkResult
import com.hazratbilal.notecraft.compose.ui.components.CustomButton
import com.hazratbilal.notecraft.compose.ui.components.CustomLabel
import com.hazratbilal.notecraft.compose.ui.components.CustomMultilineTextField
import com.hazratbilal.notecraft.compose.ui.components.CustomTextField
import com.hazratbilal.notecraft.compose.ui.components.Loading
import com.hazratbilal.notecraft.compose.utils.SharedPrefs
import com.hazratbilal.notecraft.compose.utils.sdp
import com.hazratbilal.notecraft.compose.utils.showToast


@Composable
fun EditNoteScreen(
    navController: NavHostController,
    note: NotesResponse.Note,
    sharedPrefs: SharedPrefs

) {
    val context = LocalContext.current

    val notesViewModel: NotesViewModel = hiltViewModel()
    val notesResponse by notesViewModel.notesResponseStateFlow.collectAsState()

    var noteId by rememberSaveable { mutableStateOf(note.id) }
    var title by rememberSaveable { mutableStateOf(note.title) }
    var description by rememberSaveable { mutableStateOf(note.description) }

    Loading(isLoading = notesResponse is NetworkResult.Loading)

    LaunchedEffect(notesResponse) {
        when (notesResponse) {
            is NetworkResult.Success -> {
                val notesData = notesResponse.data
                if (notesData?.success == true) {

                    context.showToast("Note updated successfully")

                    navController.previousBackStackEntry?.savedStateHandle?.apply {
                        set("refreshNotes", true)
                    }
                    navController.popBackStack()

                } else {
                    context.showToast(notesResponse.message ?: "Unknown error")
                }
            }

            is NetworkResult.AuthError -> {
                context.showToast("Authentication failed, please login again")

                sharedPrefs.clearAll()
                navController.navigate("login") {
                    popUpTo("login") { inclusive = true }
                }
            }

            is NetworkResult.Error -> {
                context.showToast(notesResponse.message ?: "Unknown error")
            }

            else -> Unit
        }

        if (notesResponse !is NetworkResult.Idle && notesResponse !is NetworkResult.Loading) {
            notesViewModel.clearState()
        }

    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 12.sdp),
        horizontalAlignment = Alignment.Start
    ) {

        Spacer(modifier = Modifier.height(10.sdp))
        CustomLabel("Title")
        Spacer(modifier = Modifier.height(4.sdp))
        CustomTextField(hint = "Title", value = title,  onValueChange = { title = it })

        Spacer(modifier = Modifier.height(16.sdp))
        CustomLabel("Description")
        Spacer(modifier = Modifier.height(4.sdp))
        CustomMultilineTextField(hint = "Description", value = description, onValueChange = { description = it })

        Spacer(modifier = Modifier.height(26.sdp))
        CustomButton("Update Note") {
            if (title.trim().isEmpty() || description.trim().isEmpty()) {
                context.showToast("Title and Description cannot be empty")
            } else {
                notesViewModel.updateNote(NoteRequest(noteId, title, description))
            }
        }

    }
}
