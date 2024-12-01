package com.example.userlookup.common

import android.content.Context
import com.example.userlookup.BaseApplication
import com.example.userlookup.UserDB
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

class DriverFactory(private val context: Context) {
     fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(UserDB.Schema, context, "User.db")
    }
}

fun createDatabase(driverFactory: DriverFactory): UserDB {
    val driver = driverFactory.createDriver()
    return UserDB(driver)
}


object SqlDelightInitializer {

    private var database: UserDB? = null
    fun initialize(context: Context) {
        val dataBaseFactory = DriverFactory(context)
        database = createDatabase(dataBaseFactory)
    }
    fun getDatabase(): UserDB {
        return database!!
    }
}
