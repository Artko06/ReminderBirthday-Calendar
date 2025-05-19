package com.example.reminderbirthday_calendar.presentation.viewModel

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.useCase.calendar.event.GetAllEventUseCase
import com.example.domain.useCase.calendar.event.ImportEventsFromContactsUseCase
import com.example.domain.useCase.exportFile.ExportEventsToCsvToExternalDirUseCase
import com.example.domain.useCase.exportFile.ExportEventsToJsonToExternalDirUseCase
import com.example.domain.useCase.google.GetAuthGoogleEmailUseCase
import com.example.domain.useCase.google.GetEventsFromRemoteUseCase
import com.example.domain.useCase.google.GetTimeLastUploadToRemoteUseCase
import com.example.domain.useCase.google.GoogleIsSignInUseCase
import com.example.domain.useCase.google.GoogleSignInUseCase
import com.example.domain.useCase.google.GoogleSignOutUseCase
import com.example.domain.useCase.google.UploadEventsToRemoteUseCase
import com.example.domain.useCase.importFile.ImportEventsFromCsvUseCase
import com.example.domain.useCase.importFile.ImportEventsFromJsonUseCase
import com.example.reminderbirthday_calendar.intents.shareIntent.TypeShareFile
import com.example.reminderbirthday_calendar.presentation.event.ImportExportEvent
import com.example.reminderbirthday_calendar.presentation.sharedFlow.ImportExportSharedFlow
import com.example.reminderbirthday_calendar.presentation.sharedFlow.ImportExportSharedFlow.ShowShareView
import com.example.reminderbirthday_calendar.presentation.sharedFlow.ImportExportSharedFlow.ShowToast
import com.example.reminderbirthday_calendar.presentation.sharedFlow.ImportExportSharedFlow.UpdateEventsAfterImport
import com.example.reminderbirthday_calendar.presentation.state.ImportExportState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImportExportViewModel @Inject constructor(
    private val exportEventsToJsonToExternalDirUseCase: ExportEventsToJsonToExternalDirUseCase,
    private val exportEventsToCsvToExternalDirUseCase: ExportEventsToCsvToExternalDirUseCase,
    private val importEventsFromJsonUseCase: ImportEventsFromJsonUseCase,
    private val importEventsFromCsvUseCase: ImportEventsFromCsvUseCase,
    private val importEventsFromContactsUseCase: ImportEventsFromContactsUseCase,

    private val googleSignInUseCase: GoogleSignInUseCase,
    private val googleIsSignInUseCase: GoogleIsSignInUseCase,
    private val googleSignOutUseCase: GoogleSignOutUseCase,
    private val getAuthGoogleEmailUseCase: GetAuthGoogleEmailUseCase,

    private val getEventsFromRemoteUseCase: GetEventsFromRemoteUseCase,
    private val getTimeLastUploadToRemoteUseCase: GetTimeLastUploadToRemoteUseCase,
    private val uploadEventsToRemoteUseCase: UploadEventsToRemoteUseCase,

    private val getAllEventUseCase: GetAllEventUseCase,
    @ApplicationContext private val appContext: Context
): ViewModel() {
    private val _importExportState = MutableStateFlow(ImportExportState())
    val importExportState = _importExportState.asStateFlow()

    private val _importExportSharedFlow = MutableSharedFlow<ImportExportSharedFlow>()
    val importExportSharedFlow = _importExportSharedFlow.asSharedFlow()

    init {
        _importExportState.update { it.copy(
            googleAuthEmail = getAuthGoogleEmailUseCase()
        ) }
    }

    fun onEvent(event: ImportExportEvent){
        when(event){
            ImportExportEvent.ExportEventsToJson -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _importExportState.update { it.copy(
                        isLoadingExportJson = true
                    ) }

                    _importExportState.update { it.copy(
                        statusExportJson = exportEventsToJsonToExternalDirUseCase()
                    ) }

                    _importExportSharedFlow.emit(
                        value = if (_importExportState.value.statusExportJson == true){
                            ShowToast(
                                message = "Successfully export json file"
                            )
                        }
                        else
                            ShowToast(
                                message = "Fail export json file"
                            )
                    )

                    _importExportState.update { it.copy(
                        isLoadingExportJson = false
                    ) }

                    if (_importExportState.value.statusExportJson == true){
                        _importExportSharedFlow.emit(value = ShowShareView(typeShareFile = TypeShareFile.JSON))
                    }
                }
            }

            ImportExportEvent.ExportEventsToCsv -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _importExportState.update { it.copy(
                        isLoadingExportCsv = true
                    ) }

                    _importExportState.update {
                        it.copy(
                            statusExportCsv = exportEventsToCsvToExternalDirUseCase()
                        )
                    }

                    _importExportSharedFlow.emit(
                        if (_importExportState.value.statusExportCsv == true)
                            ShowToast(
                                message = "Successfully export csv file"
                            )
                        else
                            ShowToast(
                                message = "Fail export csv file"
                            )
                    )

                    _importExportState.update { it.copy(
                        isLoadingExportCsv = false
                    ) }

                    if (_importExportState.value.statusExportCsv == true){
                        _importExportSharedFlow.emit(value = ShowShareView(typeShareFile = TypeShareFile.CSV))
                    }
                }
            }

            is ImportExportEvent.ImportEventsFromCsv -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _importExportState.update { it.copy(
                        isLoadingImportCsv = true
                    ) }

                    val importedEvents = importEventsFromCsvUseCase.invoke(strUri = event.uri.toString())

                    _importExportState.update { it.copy(
                        isLoadingImportCsv = false
                    ) }

                    if (importedEvents.isEmpty()) {
                        _importExportSharedFlow.emit(value = ShowToast(
                            message = "0 imported events"
                        )
                        )
                    } else {
                        _importExportSharedFlow.emit(value = UpdateEventsAfterImport(events = importedEvents))
                    }
                }
            }

            is ImportExportEvent.ImportEventsFromJson -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _importExportState.update { it.copy(
                        isLoadingImportJson = true
                    ) }

                    val importedEvents = importEventsFromJsonUseCase.invoke(strUri = event.uri.toString())

                    _importExportState.update { it.copy(
                        isLoadingImportJson = false
                    ) }

                    if (importedEvents.isEmpty()) {
                        _importExportSharedFlow.emit(value = ShowToast(
                            message = "0 imported events"
                        )
                        )
                    } else {
                        _importExportSharedFlow.emit(value = UpdateEventsAfterImport(events = importedEvents))
                    }
                }
            }

            ImportExportEvent.ImportEventsFromContacts -> {
                _importExportState.update { it.copy(
                    isLoadingReimportEvent = true
                ) }

                var permissionReadState =
                    ContextCompat.checkSelfPermission(appContext, Manifest.permission.READ_CONTACTS) ==
                            PackageManager.PERMISSION_GRANTED

                if (!permissionReadState){
                    _importExportState.update { it.copy(
                        isShowReadContactPermDialog = true,
                        isLoadingReimportEvent = false
                    ) }
                    return
                }

                viewModelScope.launch(Dispatchers.IO) {
                    val importedEvents = importEventsFromContactsUseCase.invoke()

                    if (importedEvents.isEmpty()){
                        _importExportState.update { it.copy(
                            isLoadingReimportEvent = false
                        ) }

                        _importExportSharedFlow.emit(value = ShowToast(message = "0 imported events"))
                    } else{
                        _importExportState.update { it.copy(
                            isLoadingReimportEvent = false
                        ) }

                        _importExportSharedFlow.emit(value = UpdateEventsAfterImport(events = importedEvents))
                    }
                }
            }

            ImportExportEvent.GoogleSignInOrOut -> {
                _importExportState.update { it.copy(
                    isLoadingSignInWithGoogle = true
                ) }

                if (!googleIsSignInUseCase()){
                    viewModelScope.launch {
                        val isSignIn = googleSignInUseCase()

                        if (!isSignIn) {
                            _importExportState.update { it.copy(
                                isLoadingSignInWithGoogle = false
                            ) }

                            _importExportSharedFlow.emit(
                                value = ShowToast(message = "Error sign-in")
                            )
                        }
                        else{
                            val email = getAuthGoogleEmailUseCase()

                            _importExportState.update { it.copy(
                                googleAuthEmail = email,
                                isLoadingSignInWithGoogle = false
                            ) }

                            _importExportSharedFlow.emit(
                                value = ShowToast(message = "Successfully sign-in to ${email ?: ""}")
                            )
                        }
                    }
                } else {
                    val email = getAuthGoogleEmailUseCase()

                    viewModelScope.launch {
                        googleSignOutUseCase()

                        _importExportState.update { it.copy(
                            googleAuthEmail = null,
                            isLoadingSignInWithGoogle = false
                        ) }

                        _importExportSharedFlow.emit(
                            value = ShowToast(message = "Successfully sign-out from ${email ?: ""}")
                        )
                    }
                }
            }

            ImportExportEvent.GetEventsFromRemote -> {
                _importExportState.update { it.copy(
                    isLoadingRemoteImport = true
                ) }

                viewModelScope.launch(Dispatchers.IO) {
                    if (!googleIsSignInUseCase()){
                        _importExportState.update { it.copy(
                            isLoadingRemoteImport = false
                        ) }

                        _importExportSharedFlow.emit(value = ShowToast(
                            message = "You should sigh-in with google"
                        )
                        )
                    }
                }

                viewModelScope.launch(Dispatchers.IO) {
                    if (!googleIsSignInUseCase()) {
                        _importExportState.update { it.copy(
                            isLoadingRemoteImport = false
                        ) }

                        return@launch
                    }

                    if (getTimeLastUploadToRemoteUseCase() == null){
                        _importExportState.update { it.copy(
                            isLoadingRemoteImport = false
                        ) }

                        _importExportSharedFlow.emit(value = ShowToast(
                            message = "You don't have backups on remote storage"
                        )
                        )

                        return@launch
                    }

                    val importedRemoteEvents = getEventsFromRemoteUseCase()
                    val backupTime = getTimeLastUploadToRemoteUseCase()

                    if (importedRemoteEvents.isEmpty()){
                        _importExportState.update { it.copy(
                            isLoadingRemoteImport = false
                        ) }

                        _importExportSharedFlow.emit(value = ShowToast(
                            message = "0 imported events"
                        )
                        )
                    } else{
                        _importExportState.update { it.copy(
                            isLoadingRemoteImport = false
                        ) }

                        _importExportSharedFlow.emit(value = ShowToast(
                            message = "Time this backup is $backupTime"
                        )
                        )

                        _importExportSharedFlow.emit(value = UpdateEventsAfterImport(events = importedRemoteEvents))
                    }
                }
            }

            ImportExportEvent.UploadEventsToRemote -> {
                _importExportState.update { it.copy(
                    isLoadingRemoteExport = true
                ) }

                viewModelScope.launch(Dispatchers.IO) {
                    if (!googleIsSignInUseCase()){
                        _importExportState.update { it.copy(
                            isLoadingRemoteExport = false
                        ) }

                        _importExportSharedFlow.emit(value = ShowToast(
                            message = "You should sigh-in with google"
                        )
                        )
                    }
                }
                if (!googleIsSignInUseCase()) return

                val email = getAuthGoogleEmailUseCase()

                viewModelScope.launch(Dispatchers.IO) {
                    if (getAllEventUseCase.invoke().first().isEmpty()){
                        _importExportState.update { it.copy(
                            isLoadingRemoteExport = false
                        ) }

                        _importExportSharedFlow.emit(value = ShowToast(
                            message = "You have 0 events!"))

                        return@launch
                    }

                    val statusUpload = uploadEventsToRemoteUseCase.invoke()

                    _importExportState.update { it.copy(
                        isLoadingRemoteExport = false
                    ) }

                    if(statusUpload){
                        _importExportSharedFlow.emit(value = ShowToast(
                            message = "Successfully upload from ${email ?: ""}"
                        )
                        )
                    } else{
                        _importExportSharedFlow.emit(value = ShowToast(
                            message = "Fail upload from ${email ?: ""}"
                        )
                        )
                    }
                }
            }

            ImportExportEvent.ShowReadContactPermDialog -> {
                _importExportState.update { it.copy(
                    isShowReadContactPermDialog = true
                ) }
            }

            ImportExportEvent.CloseReadContactPermDialog -> {
                _importExportState.update { it.copy(
                    isShowReadContactPermDialog = false
                ) }
            }


        }
    }
}