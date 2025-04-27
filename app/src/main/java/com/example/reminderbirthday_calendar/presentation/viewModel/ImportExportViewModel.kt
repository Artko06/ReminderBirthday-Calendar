package com.example.reminderbirthday_calendar.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.useCase.exportFile.ExportEventsToCsvToExternalDirUseCase
import com.example.domain.useCase.exportFile.ExportEventsToJsonToExternalDirUseCase
import com.example.reminderbirthday_calendar.presentation.event.ImportExportEvent
import com.example.reminderbirthday_calendar.presentation.sharedFlow.ImportExportSharedFlow
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
    private val exportEventsToCsvToExternalDirUseCase: ExportEventsToCsvToExternalDirUseCase
): ViewModel() {
    private val _importExportState = MutableStateFlow(ImportExportState())
    val importExportState = _importExportState.asStateFlow()

    private val _eventToast = MutableSharedFlow<ImportExportSharedFlow>()
    val eventToast = _eventToast.asSharedFlow()


    fun onEvent(event: ImportExportEvent){
        when(event){
            ImportExportEvent.EventsToJsonToExternalDirExport -> {
                viewModelScope.launch {
                    _importExportState.update { it.copy(
                        statusExportJson = exportEventsToJsonToExternalDirUseCase()
                    ) }

                    _eventToast.emit(
                        if (_importExportState.value.statusExportJson == true)
                            ImportExportSharedFlow.ShowToast("Successfully export json file")
                        else
                            ImportExportSharedFlow.ShowToast("Fail export json file")
                    )
                }
            }

            ImportExportEvent.EventsToCsvToExternalDirExport -> {
                viewModelScope.launch {
                    _importExportState.update {
                        it.copy(
                            statusExportCsv = exportEventsToCsvToExternalDirUseCase()
                        )
                    }

                    _eventToast.emit(
                        if (_importExportState.value.statusExportJson == true)
                            ImportExportSharedFlow.ShowToast("Successfully export csv file")
                        else
                            ImportExportSharedFlow.ShowToast("Fail export csv file")
                    )
                }
            }


        }
    }
}