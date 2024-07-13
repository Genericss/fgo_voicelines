#!/bin/bash

old_dir=$(pwd)
cd ./used_code/launching_scripts/

#These two convert the input JSON to a csv format I like
./convert_json_lore_to_csv.sh
./get_only_voicelines_from_csv.sh

#These generate the dot files that will be fed into the gephi analysis later. 
./convert_voiceline_csv_to_dot.sh
./convert_all_voiceline_csv_to_dot.sh

cd $old_dir

