#!/bin/bash

awk -F "," -f ./only_voicelines.awk < ../generated_data/nice_servant_lore.csv > ../generated_data/only_servant_voicelines.csv
