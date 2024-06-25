package com.gpsamy.easydatastore

import android.content.Context
import android.os.Build
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.robolectric.annotation.Config
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.junit.JUnitAsserter.assertNotNull

private const val EASY_DATASTORE1 = "easy-datastore3"

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

class DataStoreHelperTest {

    private lateinit var mContext: Context
    private lateinit var dataStore: DataStore<Preferences>


    @Before
    fun setUp() {
        mContext = ApplicationProvider.getApplicationContext()
        dataStore = DataStoreFactory.Creator().context(mContext).dataStoreName(EASY_DATASTORE1).create()
        DataStoreHelper.initialize(dataStore)
    }

    @Test
    fun datastore_creation_success() {
        assertNotNull("DataStore should not be null", dataStore)
    }

    @Test
    fun datastore_initialization_failed() {
        assertThrows(Exception::class.java) {
            DataStoreHelper.initialize(null)
        }
    }

    @Test
    fun datastore_initialization_success() {
        DataStoreHelper.initialize(dataStore)
    }

    @Test
    fun datastore_getDefaultValueForLong_retrieved() {
        val defaultLongValue = DataStoreHelper.getInt("No_key", 0)
        assertEquals(0, defaultLongValue)
    }

    @Test
    fun datastore_getDefaultValueForInt_retrieved() {
        val defaultIntValue = DataStoreHelper.getInt("No_key", 0)
        assertEquals(0, defaultIntValue)
    }

    @Test
    fun datastore_getDefaultValueForFloat_retrieved() {
        val defaultFloatValue = DataStoreHelper.getFloat("No_key", 0f)
        assertEquals(0f, defaultFloatValue)
    }

    @Test
    fun datastore_getDefaultValueForString_retrieved() {
        val defaultStringValue = DataStoreHelper.getString("No_key", null)
        assertEquals(null, defaultStringValue)
    }

    @Test
    fun datastore_getDefaultValueForBoolean_retrieved() {
        val defaultBooleanValue = DataStoreHelper.getBoolean("No_key", false)
        assertEquals(false, defaultBooleanValue)
    }


    @Test
    fun datastore_getStoredIntValue_retrieved() {
        val key = "age"
        val value = 36
        DataStoreHelper.putIntSync(key, value)
        val retrievedValue = DataStoreHelper.getInt(key, 0)
        assertEquals(value, retrievedValue)
    }

    @Test
    fun datastore_getStoredLongValue_retrieved() {
        val key = "age_in_long"
        val value = 36L
        DataStoreHelper.putLongSync(key, value)
        val retrievedValue = DataStoreHelper.getLong(key, 0)
        assertEquals(value, retrievedValue)
    }

    @Test
    fun datastore_getStoredFloatValue_retrieved() {
        val key = "age_in_float"
        val value = 36f
        DataStoreHelper.putFloatSync(key, value)
        val retrievedValue = DataStoreHelper.getFloat(key, 0f)
        assertEquals(value, retrievedValue)
    }

    @Test
    fun datastore_getStoredStringValue_retrieved() {
        val key = "age_in_string"
        val value = "36"
        DataStoreHelper.putStringSync(key, value)
        val retrievedValue = DataStoreHelper.getString(key, null)
        assertEquals(value, retrievedValue)
    }

    @Test
    fun datastore_containsStoredKey_true() {
        val key = "height_in_int"
        DataStoreHelper.putIntSync(key, 163)
        val boolean = DataStoreHelper.containsInt(key)
        assertEquals(true, boolean)
    }

    @Test
    fun datastore_containsStoredKey_false() {
        val key = "int_stored_key"
        DataStoreHelper.putIntSync(key, 100)
        val boolean = DataStoreHelper.contains("Not_stored_key")
        assertEquals(false, boolean)
    }

    @Test
    fun datastore_containsIntKey_true() {
        val key = "int_key_contains"
        val value = 36
        DataStoreHelper.putIntSync(key, value)
        val boolean = DataStoreHelper.containsInt(key)
        assertEquals(true, boolean)
    }

    @Test
    fun datastore_containsIntKey_false() {
        val boolean = DataStoreHelper.containsInt("No_Key")
        assertEquals(false, boolean)
    }

    @Test
    fun datastore_containsIntFloat_true() {
        val key = "float_key_contains"
        val value = 36f
        DataStoreHelper.putFloatSync(key, value)
        val boolean = DataStoreHelper.containsFloat(key)
        assertEquals(true, boolean)
    }

    @Test
    fun datastore_containsIntFloat_false() {
        val boolean = DataStoreHelper.containsFloat("No_float_Key")
        assertEquals(false, boolean)
    }

    @Test
    fun datastore_containsStringKey_true() {
        val key = "string_key_contains"
        val value = "36"
        DataStoreHelper.putStringSync(key, value)
        val boolean = DataStoreHelper.containsString(key)
        assertEquals(true, boolean)
    }

    @Test
    fun datastore_containsStringKey_false() {
        val boolean = DataStoreHelper.containsString("No_String_Key")
        assertEquals(false, boolean)
    }

    @Test
    fun datastore_containsLongKey_true() {
        val key = "Long_key_contains"
        val value = 36L
        DataStoreHelper.putLongSync(key, value)
        val boolean = DataStoreHelper.containsLong(key)
        assertEquals(true, boolean)
    }

    @Test
    fun datastore_containsLongKey_false() {
        val boolean = DataStoreHelper.containsLong("No_Long_Key")
        assertEquals(false, boolean)
    }

    @Test
    fun datastore_containsBooleanKey_true() {
        val key = "boolean_key_contains"
        val value = true
        DataStoreHelper.putBooleanSync(key, value)
        val boolean = DataStoreHelper.containsBoolean(key)
        assertEquals(true, boolean)
    }

    @Test
    fun datastore_containsBooleanKey_false() {
        val boolean = DataStoreHelper.containsBoolean("No_boolean_Key")
        assertEquals(false, boolean)
    }

    @Test
    fun datastore_storeIntSynchronously_true() {
        val key = "syn_int_key"
        val value = 100
        DataStoreHelper.putIntSync(key, value)
        assertEquals(value, DataStoreHelper.getInt(key, 0))
    }

    @Test
    fun datastore_storeIntSynchronously_false() {
        val key = "not_syn_int_key"
        val value = 100
        DataStoreHelper.putInt(key, value)
        assertNotEquals(value, DataStoreHelper.getInt(key, 0))
    }

    @Test
    fun datastore_storeLongSynchronously_true() {
        val key = "syn_long_key"
        val value = 100L
        DataStoreHelper.putLongSync(key, value)
        assertEquals(value, DataStoreHelper.getLong(key, 0))
    }

    @Test
    fun datastore_storeLongSynchronously_false() {
        val key = "not_syn_long_key"
        val value = 100L
        DataStoreHelper.putLong(key, value)
        assertNotEquals(value, DataStoreHelper.getLong(key, 0))
    }

    @Test
    fun datastore_storeFloatSynchronously_true() {
        val key = "syn_float_key"
        val value = 100f
        DataStoreHelper.putFloatSync(key, value)
        assertEquals(value, DataStoreHelper.getFloat(key, 0f))
    }

    @Test
    fun datastore_storeFloatSynchronously_false() {
        val key = "not_syn_float_key"
        val value = 100f
        DataStoreHelper.putFloat(key, value)
        assertNotEquals(value, DataStoreHelper.getFloat(key, 0f))
    }

    @Test
    fun datastore_storeBooleanSynchronously_true() {
        val key = "syn_boolean_key"
        val value = true
        DataStoreHelper.putBooleanSync(key, value)
        assertEquals(value, DataStoreHelper.getBoolean(key, false))
    }

    @Test
    fun datastore_storeBooleanSynchronously_false() {
        val key = "not_syn_boolean_key"
        val value = true
        DataStoreHelper.putBoolean(key, value)
        assertNotEquals(value, DataStoreHelper.getBoolean(key, false))
    }

    @Test
    fun datastore_getAllKeys_retrieved() {
        val intKey = "int_key"
        val valueInt = 120
        val floatKey = "floatKey"
        val valueFloat = 125f
        val stringKey = "stringKey"
        val valueString = "periasamy"
        val longKey = "long_key"
        val valueLong = 1235L
        DataStoreHelper.putIntSync(intKey, valueInt)
        DataStoreHelper.putFloatSync(floatKey, valueFloat)
        DataStoreHelper.putStringSync(stringKey, valueString)
        DataStoreHelper.putLongSync(longKey, valueLong)

        val map = DataStoreHelper.getAll()
        if (map.containsKey(intPreferencesKey(intKey)) && map.containsKey(floatPreferencesKey(floatKey)) && map.containsKey(longPreferencesKey(longKey)) &&
            map.containsKey(stringPreferencesKey(stringKey))
        ) {
            assert(true)
        }
    }
}