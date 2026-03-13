# MVVM Counter with History 

An extended counter app built with **Kotlin** and **Jetpack Compose** 
implementing the **MVVM architecture pattern** as part of a mobile 
development assignment.

## Features
- Counter with increment, decrement, and reset functionality
- Full action history log displayed in a scrollable list
- History entries colour-coded — green for increments, red for 
  decrements, grey for resets
- History clears when Reset is pressed
- Count and history **survive screen rotation** via ViewModel

## Tech Stack
- Kotlin
- Jetpack Compose
- Material 3
- Android ViewModel
- StateFlow / collectAsStateWithLifecycle

## Architecture
```
MVVM Pattern
├── Model       → CounterUiState (count + history)
├── ViewModel   → CounterViewModel (StateFlow + sealed events)
└── View        → CounterScreen (Composable, observes state)
```

## Project Structure
```
app/src/main/java/com/example/mvvmcounter/
├── MainActivity.kt         # Entry point
├── CounterViewModel.kt     # ViewModel, UiState, sealed CounterEvent
└── CounterScreen.kt        # UI — LazyColumn history + buttons
```

## How to Run
1. Clone the repo
2. Open in Android Studio
3. Run on an emulator or physical device (min SDK 24)

## Screen Rotation
The ViewModel survives configuration changes automatically. 
Rotate the device to verify count and history are preserved.
