#!/bin/bash
cd ./used_code/
./convert_json_lore_to_csv.sh
./get_only_voicelines_from_csv.sh
./convert_voiceline_csv_to_dot.sh

