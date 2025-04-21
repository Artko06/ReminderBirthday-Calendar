package com.example.data

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.data.fake.FakeEventDao
import com.example.data.local.entity.settings.event.toData
import com.example.data.repository.ExportFileRepositoryImpl
import com.example.domain.models.event.Event
import com.example.domain.models.event.EventType
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import java.time.LocalDate

@RunWith(AndroidJUnit4::class)
class ExportFileRepositoryImplTest {

    private lateinit var context: Context
    private lateinit var dao: FakeEventDao
    private lateinit var repository: ExportFileRepositoryImpl

    private val mockEvents = listOf(
        Event(
            id = 1,
            eventType = EventType.BIRTHDAY,
            nameContact = "Alice",
            surnameContact = "Johnson",
            originalDate = LocalDate.of(1990, 1, 1),
            yearMatter = true,
            nextDate = LocalDate.of(2025, 1, 1),
            notes = "Best friend",
            image = null
        ).toData(),
        Event(
            id = 2,
            eventType = EventType.ANNIVERSARY,
            nameContact = "Bob",
            surnameContact = "Smith",
            originalDate = LocalDate.of(1995, 5, 15),
            yearMatter = false,
            nextDate = LocalDate.of(2025, 5, 15),
            notes = "Work",
            image = null
        ).toData()
    )

    @Before
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        dao = FakeEventDao(mockEvents)
        repository = ExportFileRepositoryImpl(context, dao)
    }

    @Test
    fun exportEventsToCsvToExternalDir_creates_CSV_file_with_correct_content() = runBlocking {
        repository.exportEventsToCsvToExternalDir()

        val file = File(context.getExternalFilesDir(null), "exported_events.csv")
        assertTrue("CSV file should exist", file.exists())

        val content = file.readText()
        assertTrue("CSV should contain Alice", content.contains("Alice"))
        assertTrue("CSV should contain Bob", content.contains("Bob"))
        assertTrue("CSV should contain event type BIRTHDAY", content.contains("BIRTHDAY"))
    }

    @Test
    fun exportEventsToJsonToExternalDir_creates_JSON_file_with_correct_content() = runBlocking {
        repository.exportEventsToJsonToExternalDir()

        val file = File(context.getExternalFilesDir(null), "exported_events.json")
        assertTrue("JSON file should exist", file.exists())

        val content = file.readText()
        assertTrue("JSON should contain surname Johnson", content.contains("Johnson"))
        assertTrue("JSON should contain note Work", content.contains("Work"))
        assertTrue("JSON should contain event type ANNIVERSARY", content.contains("ANNIVERSARY"))
    }

    @After
    fun tearDown() {
        File(context.getExternalFilesDir(null), "exported_events.csv").delete()
        File(context.getExternalFilesDir(null), "exported_events.json").delete()
    }
}