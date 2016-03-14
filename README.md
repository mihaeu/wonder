# 7 Wonders

## Project structure

```
.
├── build.gradle
├── console
│   ├── build.gradle
│   └── src
├── core
│   ├── build.gradle
│   └── src
└── settings.gradle

```

Project structure should be pretty straightforward, but basically there's the root project, which contains two subprojects (core and console for now). Console is the console based frontend and depends on the core. The core has no Main class or output.
