package com.example.gettingstartedwithkmm.db

import com.example.gettingstartedwithkmm.OrganizeDb
import com.squareup.sqldelight.db.SqlDriver

class DatabaseHelper(
    sqlDriver: SqlDriver,
) {

    private val dbRef: OrganizeDb = OrganizeDb(sqlDriver)

    fun fetchAllItems(): List<ReminderDb> =
        dbRef.tableQueries
            .selectAll()
            .executeAsList()

    fun insertReminder(id: String, title: String) {
        dbRef.tableQueries.insertReminder(id, title)
    }

    fun updateIsCompleted(id: String, isCompleted: Long) {
        dbRef.tableQueries
            .updateIsCompleted(isCompleted, id)
    }

}

fun ReminderDb.isCompleted(): Boolean
    = this.isCompleted != 0L