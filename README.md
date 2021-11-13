# easy-datastore
It provides easy way to access datastore across an app. 
it requires app level one time datastore creation and initialization. 
After this, datastore can be accessed easily by calling the needed methods 
such as putInt(), getInt()...

DATA STORE CREATION:
====================
val dataStore = com.example.datastore.datastore.DataStoreFactory.Creator()
            .context(this)
            .dataStoreName("TestDataStore")
            .create()
            
INITIALIZATION:
===============
 DataStoreHelper.initialize(dataStore)
