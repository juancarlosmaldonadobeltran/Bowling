#!/usr/bin/env bash

DEMO_FILE_PATH="./players_rolls.txt"
printf "\nPlayers rolls inputs:\n\n"
cat $DEMO_FILE_PATH
printf "\n\nOutput:\n\n"
sh ./run.sh $DEMO_FILE_PATH