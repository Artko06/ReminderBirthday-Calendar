package com.artkotlindev.reminderbirthday_calendar.presentation.viewModel

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artkotlindev.domain.models.event.Event
import com.artkotlindev.domain.useCase.calendar.event.GetAllEventUseCase
import com.artkotlindev.domain.useCase.calendar.event.ImportEventsFromContactsUseCase
import com.artkotlindev.domain.useCase.calendar.event.UpsertEventsUseCase
import com.artkotlindev.domain.useCase.exportFile.ExportEventsToCsvToExternalDirUseCase
import com.artkotlindev.domain.useCase.exportFile.ExportEventsToJsonToExternalDirUseCase
import com.artkotlindev.domain.useCase.importFile.ImportEventsFromCsvUseCase
import com.artkotlindev.domain.useCase.importFile.ImportEventsFromJsonUseCase
import com.artkotlindev.domain.useCase.settings.notification.ScheduleAllEventsUseCase
import com.artkotlindev.reminderbirthday_calendar.R
import com.artkotlindev.reminderbirthday_calendar.intents.shareIntent.TypeShareFile
import com.artkotlindev.reminderbirthday_calendar.presentation.event.ImportExportEvent
import com.artkotlindev.reminderbirthday_calendar.presentation.sharedFlow.ImportExportSharedFlow
import com.artkotlindev.reminderbirthday_calendar.presentation.sharedFlow.ImportExportSharedFlow.ShowShareView
import com.artkotlindev.reminderbirthday_calendar.presentation.sharedFlow.ImportExportSharedFlow.ShowToast
import com.artkotlindev.reminderbirthday_calendar.presentation.state.ImportExportState
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

    private val getAllEventUseCase: GetAllEventUseCase,
    private val upsertEventsUseCase: UpsertEventsUseCase,
    private val scheduleAllEventsUseCase: ScheduleAllEventsUseCase,
    @ApplicationContext private val appContext: Context
): ViewModel() {
    private val _importExportState = MutableStateFlow(ImportExportState())
    val importExportState = _importExportState.asStateFlow()

    private val _importExportSharedFlow = MutableSharedFlow<ImportExportSharedFlow>()
    val importExportSharedFlow = _importExportSharedFlow.asSharedFlow()

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
                                messageResId = R.string.export_json_success
                            )
                        }
                        else
                            ShowToast(
                                messageResId = R.string.export_json_fail
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
                                messageResId = R.string.export_csv_success
                            )
                        else
                            ShowToast(
                                messageResId = R.string.export_csv_fail
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
                            messageResId = R.string.import_zero_events
                        )
                        )
                    } else {
                        updateEventsAfterImport(events = importedEvents)
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
                            messageResId = R.string.import_zero_events
                        )
                        )
                    } else {
                        updateEventsAfterImport(events = importedEvents)
                    }
                }
            }

            ImportExportEvent.ImportEventsFromContacts -> {
                viewModelScope.launch(Dispatchers.IO) {
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
                        return@launch
                    }

                    val importedEvents = importEventsFromContactsUseCase.invoke()

                    _importExportState.update { it.copy(
                        isLoadingReimportEvent = false
                    ) }

                    if (importedEvents.isEmpty()){
                        _importExportSharedFlow.emit(value = ShowToast(
                            messageResId = R.string.import_zero_events
                        ))
                    } else{
                        updateEventsAfterImport(events = importedEvents)
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

    private fun updateEventsAfterImport(events: List<Event>){
        viewModelScope.launch(Dispatchers.IO) {
            val importEventsId0 = events.map { event ->
                event.copy(id = 0)
            }

            val eventsDbBeforeAdding = getAllEventUseCase.invoke().first()

            upsertEventsUseCase(events = importEventsId0)

            val eventsDbAfterAdding = getAllEventUseCase.invoke().first()

            scheduleAllEventsUseCase()

            _importExportSharedFlow.emit(value = ShowToast(
                messageResId = R.string.events_import_result,
                formatArgs = listOf(
                    events.size.toString(),
                    (eventsDbAfterAdding.size - eventsDbBeforeAdding.size).toString()
                )
            ))
        }
    }


}
