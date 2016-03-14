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
├── web
│   ├── build.gradle
│   └── src
└── settings.gradle

```

Project structure should be pretty straightforward, but basically there's the root project, which contains three subprojects (core and console for now).
Console is the console based frontend and depends on the core. The core has no Main class or output. Web is the REST backend.

## Rules

### Base Game

 - [Rules](http://rprod.com/uploads/file/7WONDERS_RULES_US_COLOR.pdf)
 - [Quick Rules](http://rprod.com/uploads/file/7WONDERS_QUICKRULES_US_COLOR.pdf)
 - [Card List](http://rprod.com/uploads/file/7Wonders-CardsList-EN.pdf)
 - [FAQ](http://rprod.com/uploads/FAQ_USA_7W.pdf)

### Leaders Extension

 - [Leaders Rules](http://rprod.com/uploads/file/7WONDERS_LEADERS_RULES_EN_COLOR.pdf)
 - [Leaders Helpsheet](http://rprod.com/uploads/file/Helpsheet-Leaders-EN.pdf)

### Cities Extension

 - [Cities Rules](http://rprod.com/uploads/file/7WONDERS_CITIES_RULES_EN.pdf)
 - [Cities Helpsheet](http://rprod.com/uploads/file/Helpsheet-Cities-EN.pdf)

### Babel Extension

 - [Babel Rules](http://rprod.com/uploads/file/7W-Babel-Rules-En.pdf)
 - [Babel Quick Rules](http://rprod.com/uploads/file/7W-Babel-Helpsheet-En.pdf)

### Wonder Pack

 - [Rules](http://rprod.com/uploads/WONDERPACK_RULES_US.pdf)