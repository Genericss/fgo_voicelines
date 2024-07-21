#!/bin/bash
old_dir=$(pwd)
cd ../../input_data/

if [ -f ./nice_servant_lore.json ]; then
	last_modified=$(date -r ./nice_servant_lore.json "+%Y-%m-%d")
	mkdir -p $last_modified
	mv ./nice_servant_lore.json ./${last_modified}
fi

curl https://api.atlasacademy.io/export/NA/nice_servant_lore.json > ./nice_servant_lore.json

cd ${old_dir}
