Easy DataStore
Easy DataStore is a lightweight Android library that replaces the traditional SharedPreferences with the new DataStore API. It uses the Factory pattern to create a singleton DataStore object, ensuring a simple and efficient way to store and retrieve values across your codebase.

Features
Replace SharedPreferences with DataStore.
Factory pattern implementation for creating DataStore instances.
Supports migration from SharedPreferences to DataStore.
Singleton pattern to ensure a single instance of DataStore throughout the application.
Asynchronous and synchronous methods for data storage and retrieval.
Installation
Add the JitPack repository to your project's build.gradle file:

groovy
Copy code
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
Then, add the dependency in your module's build.gradle file:

groovy
Copy code
dependencies {
    implementation 'com.github.YourUsername:easy-datastore:Tag'
}
Replace YourUsername with your GitHub username and Tag with the release tag.

Usage
Setting Up DataStore
Create an instance of DataStore using the DataStoreFactory:

kotlin
Copy code
import com.example.datastore.datastore.DataStoreFactory

val dataStore = DataStoreFactory.Creator()
    .context(applicationContext) // Provide the application context
    .dataStoreName("your_datastore_name") // Provide a name for your DataStore
    .sharedPrefMigration("your_shared_pref_name") // Optional: Provide the SharedPreferences name if you want to migrate
    .create()
Initialize the DataStoreHelper with the created DataStore instance:

kotlin
Copy code
import com.example.datastore.datastore.DataStoreHelper

DataStoreHelper.initialize(dataStore)
Storing Values
Asynchronously
To store a boolean value asynchronously:

kotlin
Copy code
DataStoreHelper.putBoolean("example_key", true)
To store an integer value asynchronously:

kotlin
Copy code
DataStoreHelper.putInt("example_int_key", 123)
Synchronously
To store a boolean value synchronously:

kotlin
Copy code
DataStoreHelper.putBooleanSync("example_key", true)
To store an integer value synchronously:

kotlin
Copy code
DataStoreHelper.putIntSync("example_int_key", 123)
Retrieving Values
Asynchronously
To retrieve a boolean value:

kotlin
Copy code
val exampleValue = DataStoreHelper.getBoolean("example_key", false)
To retrieve an integer value:

kotlin
Copy code
val exampleIntValue = DataStoreHelper.getInt("example_int_key", 0)
Other Data Types
The library also supports other data types like String, Float, and Long.

Storing and Retrieving String
To store a string value asynchronously:

kotlin
Copy code
DataStoreHelper.putString("example_string_key", "example_value")
To retrieve a string value synchronously:

kotlin
Copy code
val exampleStringValue = DataStoreHelper.getString("example_string_key", "default_value")
Storing and Retrieving Float
To store a float value asynchronously:

kotlin
Copy code
DataStoreHelper.putFloat("example_float_key", 123.45f)
To retrieve a float value synchronously:

kotlin
Copy code
val exampleFloatValue = DataStoreHelper.getFloat("example_float_key", 0.0f)
Storing and Retrieving Long
To store a long value asynchronously:

kotlin
Copy code
DataStoreHelper.putLong("example_long_key", 123456789L)
To retrieve a long value synchronously:

kotlin
Copy code
val exampleLongValue = DataStoreHelper.getLong("example_long_key", 0L)
Removing Values
To remove a key-value pair asynchronously:

kotlin
Copy code
DataStoreHelper.remove("example_key")
To remove a key-value pair synchronously:

kotlin
Copy code
DataStoreHelper.removeSync("example_key")
Checking Key Existence
To check if a key exists in the DataStore:

kotlin
Copy code
val containsKey = DataStoreHelper.contains("example_key")
Author
Periasamy G

License
This project is licensed under the MIT License - see the LICENSE file for details.

