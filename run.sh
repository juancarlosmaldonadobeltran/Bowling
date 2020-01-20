#!/usr/bin/env bash

PATH_TO_FILE=$1
./mvnw exec:java -q -Dexec.args="$PATH_TO_FILE"