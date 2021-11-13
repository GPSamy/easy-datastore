# easy-datastore
It provides easy way to access datastore across an app. 
it requires app level one time datastore creation and initialization. 
After this, datastore can be accessed easily by calling the needed methods 
such as putInt(), getInt()...
Steps:
======
Step 1: 
      include maven{url 'https://jitpack.io'}  in repository of project level gradle file
Step 2:
       add this dependecny  implementation 'com.github.GPSamy:easy-datastore:1.0.0'
Step 3:
DATA STORE CREATION:
====================
val dataStore = com.example.datastore.datastore.DataStoreFactory.Creator()
            .context(this)
            .dataStoreName("TestDataStore")
            .create()
Step 4: 
INITIALIZATION:
===============
 DataStoreHelper.initialize(dataStore)
