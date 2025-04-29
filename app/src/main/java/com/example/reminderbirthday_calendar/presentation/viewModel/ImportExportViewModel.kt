package com.example.reminderbirthday_calendar.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.useCase.calendar.event.ImportEventsFromContactsUseCase
import com.example.domain.useCase.exportFile.ExportEventsToCsvToExternalDirUseCase
import com.example.domain.useCase.exportFile.ExportEventsToJsonToExternalDirUseCase
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ImportExportViewModel @Inject constructor(
    private val exportEventsToJsonToExternalDirUseCase: ExportEventsToJsonToExternalDirUseCase,
    private val exportEventsToCsvToExternalDirUseCase: ExportEventsToCsvToExternalDirUseCase,
    private val importEventsFromJsonUseCase: ImportEventsFromJsonUseCase,
    private val importEventsFromCsvUseCase: ImportEventsFromCsvUseCase,
    private val importEventsFromContactsUseCase: ImportEventsFromContactsUseCase
): ViewModel() {
    private val _importExportState = MutableStateFlow(ImportExportState())
    val importExportState = _importExportState.asStateFlow()

    private val _importExportSharedFlow = MutableSharedFlow<ImportExportSharedFlow>()
    val importExportSharedFlow = _importExportSharedFlow.asSharedFlow()


    fun onEvent(event: ImportExportEvent){
        when(event){
            ImportExportEvent.ExportEventsToJson -> {
                viewModelScope.launch {
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

                    if (_importExportState.value.statusExportJson == true){
                        _importExportSharedFlow.emit(value = ShowShareView(typeShareFile = TypeShareFile.JSON))
                    }
                }
            }

            ImportExportEvent.ExportEventsToCsv -> {
                viewModelScope.launch {
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

                    if (_importExportState.value.statusExportCsv == true){
                        _importExportSharedFlow.emit(value = ShowShareView(typeShareFile = TypeShareFile.CSV))
                    }
                }
            }

            is ImportExportEvent.ImportEventsFromCsv -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val importedEvents = importEventsFromCsvUseCase.invoke(strUri = event.uri.toString())

                    withContext(Dispatchers.Main) {
                        if (importedEvents.isEmpty()) {
                            _importExportSharedFlow.emit(value = ShowToast(
                                message = "0 imported events"
                            ))
                        } else {
                            _importExportSharedFlow.emit(value = UpdateEventsAfterImport(events = importedEvents))
                        }
                    }
                }
            }

            is ImportExportEvent.ImportEventsFromJson -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val importedEvents = importEventsFromJsonUseCase.invoke(strUri = event.uri.toString())

                    withContext(Dispatchers.Main) {
                        if (importedEvents.isEmpty()) {
                            _importExportSharedFlow.emit(value = ShowToast(
                                message = "0 imported events"
                            ))
                        } else {
                            _importExportSharedFlow.emit(value = UpdateEventsAfterImport(events = importedEvents))
                        }
                    }
                }
            }

            ImportExportEvent.ImportEventsFromContacts -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val importedEvents = importEventsFromContactsUseCase.invoke()

                    withContext(Dispatchers.Main) {
                        if (importedEvents.isEmpty()){
                            _importExportSharedFlow.emit(value = ShowToast(
                                message = "0 imported events"
                            ))
                        } else{
                            _importExportSharedFlow.emit(value = UpdateEventsAfterImport(events = importedEvents))
                        }
                    }
                }
            }
        }
    }
}