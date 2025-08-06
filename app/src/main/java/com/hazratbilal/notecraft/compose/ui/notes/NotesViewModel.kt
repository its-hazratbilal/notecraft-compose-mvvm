package com.hazratbilal.notecraft.compose.ui.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hazratbilal.notecraft.compose.model.NoteRequest
import com.hazratbilal.notecraft.compose.model.NotesResponse
import com.hazratbilal.notecraft.compose.remote.NetworkResult
import com.hazratbilal.notecraft.compose.repository.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NotesViewModel @Inject constructor(private val notesRepository: NotesRepository) : ViewModel() {

    private val _notesResponseStateFlow = MutableStateFlow<NetworkResult<NotesResponse>>(NetworkResult.Idle())
    val notesResponseStateFlow: StateFlow<NetworkResult<NotesResponse>> get() = _notesResponseStateFlow

    fun clearState() {
        _notesResponseStateFlow.value = NetworkResult.Idle()
    }

    fun addNote(noteRequest: NoteRequest) {
        viewModelScope.launch {
            _notesResponseStateFlow.value = NetworkResult.Loading()
            val result = notesRepository.addNote(noteRequest)
            _notesResponseStateFlow.value = result
        }
    }

    fun getNotes() {
        viewModelScope.launch {
            _notesResponseStateFlow.value = NetworkResult.Loading()
            val result = notesRepository.getNotes()
            _notesResponseStateFlow.value = result
        }
    }

    fun updateNote(noteRequest: NoteRequest) {
        viewModelScope.launch {
            _notesResponseStateFlow.value = NetworkResult.Loading()
            val result = notesRepository.updateNote(noteRequest)
            _notesResponseStateFlow.value = result
        }
    }

    fun deleteNote(noteRequest: NoteRequest) {
        viewModelScope.launch {
            _notesResponseStateFlow.value = NetworkResult.Loading()
            val result = notesRepository.deleteNote(noteRequest)
            _notesResponseStateFlow.value = result
        }
    }

}
