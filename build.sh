#!/usr/bin/env bash
gradle build

adb install -r ./app/build/outputs/apk/app-debug.apk

adb shell am start -n "tv.ismar.daisy/tv.ismar.daisy.MainActivity" -a android.intent.action.MAIN -c android.intent.category.LAUNCHER
