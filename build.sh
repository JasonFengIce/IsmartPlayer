#!/usr/bin/env bash
gradle build

adb install -r ./app/build/outputs/apk/app-debug.apk

adb shell am start -n "cn.ismartv.ismartplayer/cn.ismartv.ismartplayer.MainActivity" -a android.intent.action.MAIN -c android.intent.category.LAUNCHER
