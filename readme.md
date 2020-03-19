# LoTool

LoTool decrypts certain files in HiTool

## Building

You need at least Java 8 to build this project.

```
./gradlew build
```

This yields an application archive in `build/distributions` 

## Running

Before running LoTool, unzip HiTool in some user-writable location.

During development you can run LoTool with `./gradlew run --args="<HiTool directory>"`
Alternatively, you can install the app with `./gradlew installDist`. Then you can run
the application like this: `./build/install/LoTool/bin/LoTool <HiTool directory>`

As a result, you get decrypted files with a proper extension in
the same directory as the original encrypted files.
