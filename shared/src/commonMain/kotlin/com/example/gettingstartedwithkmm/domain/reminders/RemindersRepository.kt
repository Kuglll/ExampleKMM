package com.example.gettingstartedwithkmm.domain.reminders

import com.example.gettingstartedwithkmm.db.DatabaseHelper
import com.example.gettingstartedwithkmm.db.ReminderDb
import com.example.gettingstartedwithkmm.domain.models.Reminder
import com.example.gettingstartedwithkmm.domain.utils.UUID

class RemindersRepository(
    private val databaseHelper: DatabaseHelper
) {

    val reminders: List<Reminder>
        get() = databaseHelper.fetchAllItems().map(ReminderDb::map)

    fun createReminder(title: String){
        databaseHelper.insertReminder(UUID().randomNumber, title)
    }

    fun markReminder(id: String, isCompleted: Long){
        databaseHelper.updateIsCompleted(id, isCompleted)
    }
}

fun ReminderDb.map() = Reminder(
    id = this.id,
    title = this.title,
    isCompleted = this.isCompleted,
)