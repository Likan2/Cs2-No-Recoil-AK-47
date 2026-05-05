# Cs2 No-Recoil AK-47

AutoRecoil is a movement macro written in Java. This tool runs in the background and applies a predefined sequence of cursor offsets while the left mouse button is held down. It uses [JNativeHook](https://github.com/kwhat/jnativehook) to listen for global mouse events without requiring the Java application to be in focus, and `java.awt.Robot` to manipulate the cursor.

## Features
* **Global Event Listening:** Detects mouse clicks regardless of which application is currently in focus.
* **Predefined Offset Sequences:** Loads a custom set of 195 frames to map precise cursor movements.
* **Coordinate Inversion & Scaling:** Automatically inverts the loaded RAW coordinates and scales them by a multiplier ($$Multiplier = 3$$). 
* **Hot-Toggleable:** Easily enable or disable the macro on the fly using **Mouse Button 5**.
* **Smooth Execution:** Runs the coordinate loop in a separate thread at roughly 60 FPS ($17ms$ delay per frame) to prevent system UI stuttering.

## Prerequisites
To run or build this project, you will need:
* **Java Development Kit (JDK):** Version 8 or higher.
* **JNativeHook:** This project depends on the `com.github.kwhat:jnativehook` library.

If you are using Maven, add the following dependency to your `pom.xml`:
```xml
<dependency>
<groupId>com.github.kwhat</groupId>
<artifactId>jnativehook</artifactId>
<version>2.2.2</version>
</dependency>

## How It Works
1. **Initialization:** On startup, the program parses a raw string of coordinate data. It extracts the X and Y values, inverts them, and multiplies them by 3 to calculate the final offsets: $X_{offset} = -X_{raw} \times 3$ and $Y_{offset} = -Y_{raw} \times 3$. Depent's on your monitor resolution mine is 1920-1200 lower multiplication if you have lower resolution
2. **Listening:** The program attaches a native hook to the OS to listen for mouse states.
3. **Execution:** 
   - When **Left Click (Button 1)** is pressed and the macro is enabled, it captures the current mouse position.
   - A background thread starts iterating through the offset list, applying the new positions relative to the starting coordinates using `java.awt.Robot`.
   - The thread stops safely once the Left Click is released.

## Usage
1. Compile and run the `GlobalMouseMover` class.
2. The macro will start in the **ENABLED** state.
3. **Hold Left Click (Button 1)** to trigger the automated mouse movement.
4. **Press Mouse Button 5** (usually a side button on gaming mice) to toggle the macro ON or OFF. Console output will confirm the current state.

## Customizing the Data
You can modify the movement pattern by replacing the `RAW_DATA` string in the source code. Ensure the data follows the same space/tab-separated format:
`[FrameNumber] [X-Value] [Y-Value] [Unused]`

## Disclaimer
**Use at your own risk.** This tool simulates human mouse inputs and can be interpreted as a macro or automation software. If you intend to use this in competitive multiplayer games, be aware that automated movement scripts (such as anti-recoil macros) frequently violate End User License Agreements (EULAs) and Terms of Service (ToS), which may result in account bans. This repository is provided for educational purposes and standard desktop automation only.
