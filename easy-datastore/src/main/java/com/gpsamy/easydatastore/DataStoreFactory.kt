package com.example.datastore.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

/**
 * Description: DataStoreFactory
 * Author:Periasamy G
 */
class DataStoreFactory {

    class Creator {
        private lateinit var applicationContext: Context
        private lateinit var dataStoreName: String
        private var sharedPrefMigrationName: String? = null

        fun context(context: Context) = apply {
            this.applicationContext = context.applicationContext
        }

        fun dataStoreName(name: String) = apply {
            this.dataStoreName = name
        }

        fun sharedPrefMigration(migrationName: String) = apply {
            this.sharedPrefMigrationName = migrationName
        }

        fun create(): DataStore<Preferences> {
            val dataStore: DataStore<Preferences>
            if (sharedPrefMigrationName != null) { //sharedPreference to be migrated to DataStore
                dataStore = PreferenceDataStoreFactory.create(
                    null,
                    listOf(
                        SharedPreferencesMigration(
                            applicationContext,
                            sharedPrefMigrationName!!
                        )
                    ),
                    CoroutineScope(Dispatchers.IO + SupervisorJob())
                ) { applicationContext.preferencesDataStoreFile(dataStoreName) }
            } else { //dataStore to be created
                dataStore = PreferenceDataStoreFactory.create(
                    null,
                    listOf(),
                    CoroutineScope(Dispatchers.IO + SupervisorJob())
                ) { applicationContext.preferencesDataStoreFile(dataStoreName) }
            }
            return dataStore
        }
    }
}
