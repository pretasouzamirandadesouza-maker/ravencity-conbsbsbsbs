#!/usr/bin/env sh

DIR="$(cd "$(dirname "$0")"; pwd)"

java -classpath "$DIR/android/gradle/wrapper/gradle-wrapper.jar" org.gradle.wrapper.GradleWrapperMain "$@"
