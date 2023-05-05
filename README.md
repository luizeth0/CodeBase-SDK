***Code Base SDK***
Code Base SDK is a collection of utility classes, views, and view models that can be used in Android applications. It is intended to be a reusable library of code that can simplify development and improve consistency across multiple projects.

**Features**
The SDK includes the following features:
- A MainBaseViewModel that can be used as a base class for view models that manage data loading and state changes.
- A ResultState class that encapsulates the different states of a network request (loading, success, or error) and the associated data or error message.
- A SearchView that can be used to implement search functionality in an app.
- An AppAdapter that can be used as a base class for RecyclerView adapters.
- A SlidingPaneLayout that can be used to implement a sliding panel UI.
- An IMainSdk interface and MainSdk singleton object that provides a simple way to launch the base activity of the SDK.
- 
**Usage**
To use Code Base SDK in your Android application, you will need to add the following dependencies to your build.gradle file:
implementation project(':code-base-sdk')

Then, you can use the classes and views provided by the SDK in your own code. For example, to launch the base activity of the SDK, you can use the following code:
MainSdk.launchApplication(context, AppType.TYPE_A)
