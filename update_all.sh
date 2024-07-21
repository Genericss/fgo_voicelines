#!/bin/bash

old_dir=$(pwd)
cd ./used_code/top_level_scripts/
echo "Acquiring input data."
./get_input_data.sh

echo "Converting input data to dot file."
./regenerate_data.sh

echo "Converting dot file to output images."
./generate_output_images.sh 
#> /dev/null 2>&1

cd $old_dir
