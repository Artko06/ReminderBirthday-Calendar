package com.example.reminderbirthday_calendar.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImportExportViewModel @Inject constructor(
    private val exportEventsToJsonToExternalDirUseCase: ExportEventsToJsonToExternalDirUseCase,
    private val exportEventsToCsvToExternalDirUseCase: ExportEventsToCsvToExternalDirUseCase,
    private val importEventsFromJsonUseCase: ImportEventsFromJsonUseCase,
    private val importEventsFromCsvUseCase: ImportEventsFromCsvUseCase
): ViewModel() {
    private val _importExportState = MutableStateFlow(ImportExportState())
    val importExportState = _importExportState.asStateFlow()

    private val _eventToast = MutableSharedFlow<ImportExportSharedFlow>()
    val eventToast = _eventToast.asSharedFlow()


    fun onEvent(event: ImportExportEvent){
        when(event){
            ImportExportEvent.ExportEventsToJson -> {
                viewModelScope.launch {
                    _importExportState.update { it.copy(
                        statusExportJson = exportEventsToJsonToExternalDirUseCase()
                    ) }

                    _eventToast.emit(
                        value = if (_importExportState.value.statusExportJson == true){
                            ShowToast("Successfully export json file")
                        }
                        else
                            ShowToast("Fail export json file")
                    )

                    if (_importExportState.value.statusExportJson == true){
                        _eventToast.emit(value = ShowShareView(typeShareFile = TypeShareFile.JSON))
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

                    _eventToast.emit(
                        if (_importExportState.value.statusExportCsv == true)
                            ShowToast("Successfully export csv file")
                        else
                            ShowToast("Fail export csv file")
                    )

                    if (_importExportState.value.statusExportCsv == true){
                        _eventToast.emit(value = ShowShareView(typeShareFile = TypeShareFile.CSV))
                    }
                }
            }

            is ImportExportEvent.ImportEventsFromCsv -> {
                viewModelScope.launch {
                    viewModelScope.launch {
                        val importedEvents = importEventsFromCsvUseCase.invoke(strUri = event.uri.toString())

                        if (importedEvents.isEmpty()){
                            _eventToast.emit(value = ShowToast("0 imported evetns"))
                        } else{
                            _eventToast.emit(value = ShowToast("${importedEvents.size} imported evetns"))
                            _eventToast.emit(value = UpdateEventsAfterImport(events = importedEvents))
                        }
                    }
                }
            }

            is ImportExportEvent.ImportEventsFromJson -> {
                viewModelScope.launch {
                    val importedEvents = importEventsFromJsonUseCase.invoke(strUri = event.uri.toString())

                    if (importedEvents.isEmpty()){
                        _eventToast.emit(value = ShowToast("0 imported evetns"))
                    } else{
                        _eventToast.emit(value = ShowToast("${importedEvents.size} imported evetns"))
                        _eventToast.emit(value = UpdateEventsAfterImport(events = importedEvents))
                    }
                }
            }


        }
    }
}