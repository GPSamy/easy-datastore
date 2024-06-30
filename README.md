# Easy DataStore

Easy DataStore is a lightweight Android library that replaces the traditional SharedPreferences with the new DataStore API. It uses the Factory pattern to create a singleton DataStore object, ensuring a simple and efficient way to store and retrieve values across your codebase.

## Features
- Replace SharedPreferences with DataStore.
- Factory pattern implementation for creating DataStore instances.
- Supports migration from SharedPreferences to DataStore.
- Singleton pattern to ensure a single instance of DataStore throughout the application.
- Asynchronous and synchronous methods for data storage and retrieval.

## Installation

### Step 1: Add JitPack Repository
Add the JitPack repository to your project's `build.gradle` file:

```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

### Step 2: Add Dependency
Then, add the dependency in your module's build.gradle file:
```groovy
dependencies {
    implementation 'com.github.YourUsername:easy-datastore:Tag'
}

```


## Usage
### Setting Up DataStore
Create an instance of DataStore using the DataStoreFactory:

```groovy
import com.example.datastore.datastore.DataStoreFactory

val dataStore = DataStoreFactory.Creator()
    .context(applicationContext) // Provide the application context
    .dataStoreName("your_datastore_name") // Provide a name for your DataStore
    .sharedPrefMigration("your_shared_pref_name") // Optional: Provide the SharedPreferences name if you want to migrate
    .create()

Initialize the DataStoreHelper with the created DataStore instance:
import com.example.datastore.datastore.DataStoreHelper

DataStoreHelper.initialize(dataStore)

```




