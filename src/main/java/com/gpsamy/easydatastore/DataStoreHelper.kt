package com.example.datastore.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.*
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * Description: DataStore
 * Author:Periasamy G
 */
class DataStoreHelper {
    companion object {
        private lateinit var dataStore: DataStore<Preferences>
        private val coroutineException = CoroutineExceptionHandler { _, throwable ->
            run {
                Log.d("DataStoreHelper", ": " + throwable.stackTraceToString())
            }
        }
        private val scopeJob =
            SupervisorJob() // Exception is isolated instead of propagating to all jobs in same CoroutineScope
        private val uiScope = CoroutineScope(scopeJob + Dispatchers.IO + coroutineException)

        @JvmStatic
        fun initialize(dataStore: DataStore<Preferences>) {
            if (!this::dataStore.isInitialized) {
                this.dataStore = dataStore
            }
        }

        /**
         * Method to Save Integer to dateStore
         *
         * @param key    Name of the key used to store the value
         * @param value  Int value to be stored in datastore
         */
        private suspend fun putIntInternal(key: String, value: Int) {
            val dataStoreKey = intPreferencesKey(key)
            dataStore.edit {
                it[dataStoreKey] = value
            }
        }

        /**
         * Method to retrieve integer from dateStore
         *
         * @param key  Name of the key used to identify the value in datastore
         * @return     Int value to be retrieved from datastore
         */
        private suspend fun getIntInternal(key: String): Int? {
            val dataStoreKey = intPreferencesKey(key)
            val preferences = dataStore.data.first()
            return if (preferences.contains(dataStoreKey)) {
                preferences[dataStoreKey]
            } else {
                return null
            }
        }

        /**
         * Method to Save Integer to Asynchronously
         *
         * @param key    Name of the key used to store the value
         * @param value  Int value to be stored in datastore
         */
        @JvmStatic
        fun putInt(key: String, value: Int) {
            uiScope.launch {
                putIntInternal(key, value)
            }
        }

        /**
         * Method to Save Integer to Synchronously
         *
         * @param key    Name of the key used to store the value
         * @param value  Int value to be stored in datastore
         */
        @JvmStatic
        fun putIntSync(key: String, value: Int) {
            runBlocking {
                putIntInternal(key, value)
            }
        }

        /**
         * Method to retrieve Integer Synchronously
         *
         * @param key  Name of the key used to store the value
         * @return     Int value retrieved from datastore
         */
        @JvmStatic
        fun getInt(key: String, defaultValue: Int): Int {
            var tempInt: Int
            runBlocking {
                tempInt = getIntInternal(key) ?: defaultValue
            }
            return tempInt
        }

        /**
         * Method to Save Boolean to DataStore
         *
         * @param key    Name of the key used to store the value
         * @param value  Boolean value to be stored in datastore
         */
        private suspend fun putBooleanInternal(key: String, value: Boolean) {
            val dataStoreKey = booleanPreferencesKey(key)
            dataStore.edit { setting ->
                setting[dataStoreKey] = value
            }
        }

        /**
         * Method to Save Boolean to Synchronously
         *
         * @param key    Name of the key used to store the value
         * @param value  Boolean value to be stored in datastore
         */
        @JvmStatic
        fun putBooleanSync(key: String, value: Boolean) {
            runBlocking {
                putBooleanInternal(key, value)
            }
        }

        /**
         * Method to Save Boolean to Asynchronously
         *
         * @param key    Name of the key used to store the value
         * @param value  Boolean value to be stored in datastore
         */
        @JvmStatic
        fun putBoolean(key: String, value: Boolean) {
            uiScope.launch {
                putBooleanInternal(key, value)
            }
        }

        /**
         * Method to retrieve Boolean from DataStore
         *
         * @param key  Name of the key used to store the value
         * @return     Boolean value to be retrieved from  datastore
         */
        private suspend fun getBooleanInternal(key: String): BooleanArray {
            val booleanArray = BooleanArray(2)
            val dataStoreKey = booleanPreferencesKey(key)
            val preferences = dataStore.data.first()
            if (preferences.contains(dataStoreKey)) {
                booleanArray[0] = true
                preferences[dataStoreKey]?.let { booleanArray[1] = it }

            } else {
                booleanArray[0] = false
                booleanArray[1] = false
            }
            return booleanArray
        }

        /**
         * Method to read Boolean to Synchronously
         *
         * @param key Name of the key used to store the value
         * @return    Boolean value to be retrieved
         */
        @JvmStatic
        fun getBoolean(key: String, defaultValue: Boolean): Boolean {
            var tempBoolean: Boolean
            runBlocking {
                val booleanArray = getBooleanInternal(key)
                tempBoolean = if (booleanArray[0]) {
                    booleanArray[1]
                } else {
                    defaultValue
                }
            }
            return tempBoolean
        }

        /**
         * Method to Save String to DataStore
         *
         * @param key    Name of the key used to store the value
         * @param value  String value to be stored in datastore
         */
        private suspend fun putStringInternal(key: String, value: String?) {
            val dataStoreKey = stringPreferencesKey(key)
            dataStore.edit { setting ->
                setting[dataStoreKey] = value ?: ""
            }
        }

        /**
         * Method to Save String to Synchronously
         *
         * @param key    Name of the key used to store the value
         * @param value  String value to be stored
         */
        fun putStringSync(key: String, value: String?) {
            runBlocking {
                putStringInternal(key, value)
            }
        }

        /**
         * Method to Save String to Asynchronously
         *
         * @param key    Name of the key used to store the value
         * @param value  String value to be stored
         */
        @JvmStatic
        fun putString(key: String, value: String?) {
            uiScope.launch {
                putStringInternal(key, value)
            }
        }

        /**
         * Method to retrieve String from DataStore
         *
         * @param key    Name of the key used to retrieve the value
         * @return       String value to be retrieve from datastore
         */
        private suspend fun getStringInternal(key: String): Map<String, String> {
            val map = HashMap<String, String>()
            val dataStoreKey = stringPreferencesKey(key)
            val preferences = dataStore.data.first()
            if (preferences.contains(dataStoreKey)) {
                map["valueType"] = "prefValue"
                map["value"] = preferences[dataStoreKey] as String
            } else {
                map["valueType"] = "default"
            }
            return map
        }

        /**
         * Method to retrieve String
         *
         * @param key    Name of the key used to retrieve the value
         * @return       String value to be retrieved
         */
        @JvmStatic
        fun getString(key: String, defaultValue: String?): String? {
            var tempMap: Map<String, String>
            runBlocking {
                tempMap = getStringInternal(key)
            }
            return if (tempMap["valueType"].equals("prefValue")) {
                if (tempMap["value"].equals("")) {
                    return null
                } else {
                    return tempMap["value"]
                }
            } else {
                if (defaultValue == "") {
                    null
                } else {
                    defaultValue
                }
            }
        }

        /**
         * Method to delete key entry from datastore
         *
         * @param key    Name of the key to be deleted
         */
        private suspend fun removeInternal(key: String) {
            val dataStoreKey = stringPreferencesKey(key)
            dataStore.edit { setting ->
                setting.remove(dataStoreKey)
            }
        }

        /**
         * Method to delete key entry Synchronously
         *
         * @param key    Name of the key to be deleted
         */
        @JvmStatic
        fun removeSync(key: String) {
            runBlocking {
                removeInternal(key)
            }
        }

        /**
         * Method to delete key entry Asynchronously
         *
         * @param key    Name of the key to be deleted
         */
        @JvmStatic
        fun remove(key: String) {
            uiScope.launch {
                removeInternal(key)
            }
        }

        /**
         * Method to save Float to DataStore
         *
         * @param key    Name of the key used to store the value
         * @param value  Float value to be stored in datastore
         */
        private suspend fun putFloatInternal(key: String, value: Float) {
            val dataStoreKey = floatPreferencesKey(key)
            dataStore.edit { setting ->
                setting[dataStoreKey] = value
            }
        }

        /**
         * Method to Save Float  Asynchronously
         *
         * @param key    Name of the key used to store the value
         * @param value  Float value to be stored
         */
        @JvmStatic
        fun putFloat(key: String, value: Float) {
            uiScope.launch {
                putFloatInternal(key, value)
            }
        }

        /**
         * Method to Save Float  Synchronously
         *
         * @param key    Name of the key used to store the value
         * @param value  Float value to be stored
         */
        @JvmStatic
        fun putFloatSync(key: String, value: Float) {
            runBlocking {
                putFloatInternal(key, value)
            }
        }

        /**
         * Method to retrieve Float from DataStore
         *
         * @param key    Name of the key used to retrieve the value
         * @return       Float value to be retrieved from datastore
         */
        private suspend fun getFloatInternal(key: String): Float? {
            val dataStoreKey = floatPreferencesKey(key)
            val preferences = dataStore.data.first()
            return preferences[dataStoreKey]
        }

        /**
         * Method to retrieve Float Synchronously
         *
         * @param key    Name of the key used to retrieve the value
         * @return       Float value to be retrieved
         */
        @JvmStatic
        fun getFloat(key: String, defaultValue: Float): Float {
            val tempFloat: Float
            runBlocking {
                tempFloat = getFloatInternal(key) ?: defaultValue
            }
            return tempFloat
        }

        /**
         * Method to save Long to DataStore
         *
         * @param key    Name of the key used to store the value
         * @param value  Long value to be stored in datastore
         */
        private suspend fun putLongInternal(key: String, value: Long) {
            val dataStoreKey = longPreferencesKey(key)
            dataStore.edit { setting ->
                setting[dataStoreKey] = value
            }
        }

        /**
         * Method to Save Long  Asynchronously
         *
         * @param key    Name of the key used to store the value
         * @param value  Long value to be stored
         */
        @JvmStatic
        fun putLong(key: String, value: Long) {
            uiScope.launch {
                putLongInternal(key, value)
            }
        }

        /**
         * Method to retrieve Double from DataStore
         *
         * @param key    Name of the key used to retrieve the value
         * @return       Double value to be retrieved from datastore
         */
        private suspend fun getLongInternal(key: String): Long? {
            val dataStoreKey = longPreferencesKey(key)
            val preferences = dataStore.data.first()
            return if (preferences.contains(dataStoreKey)) {
                preferences[dataStoreKey]
            } else {
                null
            }
        }

        /**
         * Method to Save Long  Synchronously
         *
         * @param key    Name of the key used to store the value
         * @param value  Long value to be stored
         */
        @JvmStatic
        fun putLongSync(key: String, value: Long) {
            runBlocking {
                putLongInternal(key, value)
            }
        }

        /**
         * Method to retrieve Long Synchronously
         *
         * @param key    Name of the key used to retrieve the value
         * @return       Long value to be retrieved
         */
        @JvmStatic
        fun getLong(key: String, defaultValue: Long): Long {
            var tempLong: Long
            runBlocking {
                tempLong = getLongInternal(key) ?: defaultValue
            }
            return tempLong
        }

        /**
         * Method to check if preferences have the key or not in Datastore
         *
         * @param key    Name of the key used to check in DataStore
         * @return       True if the key is exists in DataStore or False
         */
        private suspend fun containsInternal(key: String): Boolean {
            val preferences = dataStore.data.first()
            val map = preferences.asMap()
            for ((prefKey, value) in map.entries) {
                if (prefKey.name == key) {
                    return true
                }
            }
            return false
        }

        /**
         * Method to check generally if preferences have the key ,without key type T, or not
         * this operation is heavy.Instead, prefer to use specific contains method ([containsStringKey],
         * [containsIntkey],[containsBooleanKey],[containsLongkey],[containsFloatkey])
         *
         * @param key    Name of the key used to check
         * @return       True if key is exists or False
         */
        @JvmStatic
        fun contains(key: String): Boolean {
            var result: Boolean
            runBlocking {
                result = containsInternal(key)
            }
            return result
        }

        /**
         * Method to check if preferences have string preference key or not in Datastore
         *
         * @param key    Name of the key used to check in DataStore
         * @return       True if the key is exists in DataStore or False
         */
        private suspend fun containsStringInternal(key: String): Boolean {
            return dataStore.data.first().contains(stringPreferencesKey(key))
        }

        /**
         * Method to check if preferences have string preference key or not
         *
         * @param key    Name of the key used to check
         * @return       True if the key is exists  or False
         */
        @JvmStatic
        fun containsString(key: String): Boolean {
            var result: Boolean
            runBlocking {
                result = containsStringInternal(key)
            }
            return result
        }

        /**
         * Method to check if preferences have int preference key or not in Datastore
         *
         * @param key    Name of the key used to check in DataStore
         * @return       True if the key is exists in DataStore or False
         */
        private suspend fun containsIntInternal(key: String): Boolean {
            return dataStore.data.first().contains(intPreferencesKey(key))
        }

        /**
         * Method to check if preferences have int preference key or not
         *
         * @param key    Name of the key used to check
         * @return       True if the key is exists  or False
         */
        @JvmStatic
        fun containsInt(key: String): Boolean {
            var result: Boolean
            runBlocking {
                result = containsIntInternal(key)
            }
            return result
        }

        /**
         * Method to check if preferences have long preference key or not in Datastore
         *
         * @param key    Name of the key used to check in DataStore
         * @return       True if the key is exists in DataStore or False
         */
        private suspend fun containsLongInternal(key: String): Boolean {
            return dataStore.data.first().contains(longPreferencesKey(key))
        }

        /**
         * Method to check if preferences have long preference key or not
         *
         * @param key    Name of the key used to check
         * @return       True if the key is exists  or False
         */
        @JvmStatic
        fun containsLong(key: String): Boolean {
            var result: Boolean
            runBlocking {
                result = containsLongInternal(key)
            }
            return result
        }

        /**
         * Method to check if preferences have float preference key or not in Datastore
         *
         * @param key    Name of the key used to check in DataStore
         * @return       True if the key is exists in DataStore or False
         */
        private suspend fun containsFloatInternal(key: String): Boolean {
            return dataStore.data.first().contains(floatPreferencesKey(key))
        }

        /**
         * Method to check if preferences have float preference key or not
         *
         * @param key    Name of the key used to check
         * @return       True if the key is exists  or False
         */
        @JvmStatic
        fun containsFloat(key: String): Boolean {
            var result: Boolean
            runBlocking {
                result = containsFloatInternal(key)
            }
            return result
        }

        /**
         * Method to check if preferences have boolean preference key or not in Datastore
         *
         * @param key    Name of the key used to check in DataStore
         * @return       True if the key is exists in DataStore or False
         */
        private suspend fun containsBooleanInternal(key: String): Boolean {
            return dataStore.data.first().contains(booleanPreferencesKey(key))
        }

        /**
         * Method to check if preferences have boolean preference key or not
         *
         * @param key    Name of the key used to check
         * @return       True if the key is exists  or False
         */
        @JvmStatic
        fun containsBoolean(key: String): Boolean {
            var result: Boolean
            runBlocking {
                result = containsBooleanInternal(key)
            }
            return result
        }

        /**
         * Method to get all the preferences in a string from DataStore
         * @return    string of preference values from DataStore
         */
        private suspend fun toStringDataStoreInternal(): String {
            return dataStore.data.first().toString()
        }

        /**
         * Method to get all the preferences  in a string
         * @return    string of preference values
         */
        @JvmStatic
        fun toStringDataStore(): String {
            var result: String
            runBlocking {
                result = toStringDataStoreInternal()
            }
            return result
        }

        /**
         * Method to get all the preferences from DataStore as mutable map. Note that
         * this map should  not be modified , if so, then DataStore functionality will be affected
         *
         * @return   All the keys and values from DataStore
         */
        private suspend fun getAllInternal(): Map<Preferences.Key<*>, Any> {
            return dataStore.data.first().asMap()
        }

        /**
         * Method to get all the preferences  as mutable map. Note that
         * this map should  not be modified , if so, then DataStore functionality will be affected
         *
         * @return    All the keys and values
         */
        @JvmStatic
        fun getAll(): Map<Preferences.Key<*>, Any> {
            val map: Map<Preferences.Key<*>, Any>
            runBlocking {
                map = getAllInternal()
            }
            return map
        }
    }
}