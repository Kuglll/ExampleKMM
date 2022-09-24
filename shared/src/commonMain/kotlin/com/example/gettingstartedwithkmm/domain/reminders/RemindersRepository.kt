package com.example.gettingstartedwithkmm.domain.reminders

import com.example.gettingstartedwithkmm.domain.models.Reminder
import com.example.gettingstartedwithkmm.domain.utils.UUID

class RemindersRepository {

    private val _reminders: MutableList<Reminder> = mutableListOf()

    fun createReminder(title: String){
        val newReminder = Reminder(
            id = UUID().randomNumber,
            title = title,
            isCompleted = false
        )
        _reminders.add(newReminder)
    }

    fun markReminder(id: String, isCompleted: Boolean){
        val index = _reminders.indexOfFirst { it.id == id }
        if (index != -1) {
            _reminders[index] = _reminders[index].copy(isCompleted = isCompleted)
        }
    }

    val reminders: List<Reminder>
        get() = _reminders

}