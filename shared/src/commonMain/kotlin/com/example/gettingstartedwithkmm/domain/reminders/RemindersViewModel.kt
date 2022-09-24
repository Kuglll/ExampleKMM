package com.example.gettingstartedwithkmm.domain.reminders

import com.example.gettingstartedwithkmm.domain.models.Reminder
import com.example.gettingstartedwithkmm.ui.shared.base.PlatformViewModel

class RemindersViewModel(
    private val repository: RemindersRepository
) : PlatformViewModel() {

    internal val reminders: List<Reminder>
        get() = repository.reminders

    var onRemindersUpdated: ((List<Reminder>) -> Unit)? = null
        set(value) {
            field = value
            onRemindersUpdated?.invoke(reminders)
        }

    fun createReminder(title: String) {
        val trimmed = title.trim()
        if (trimmed.isNotEmpty()) {
            repository.createReminder(title = trimmed)
            onRemindersUpdated?.invoke(reminders)
        }
    }

    fun markReminder(id: String, isCompleted: Boolean) {
        repository.markReminder(id = id, isCompleted = isCompleted)
        onRemindersUpdated?.invoke(reminders)
    }

}