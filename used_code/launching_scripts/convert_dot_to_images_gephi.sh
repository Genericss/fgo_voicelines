#!/bin/bash

old_dir=$(pwd)
new_dir=../../output_images/$(date "+%Y-%m-%d")
mkdir -p $new_dir/direct_voicelines

java -cp "../../downloaded_libraries/gephi-toolkit-0.10.0-all.jar:../../generated_data/servant_voices.gv:../converting_scripts/" gephiDotToImage ../../generated_data/servant_voices.gv

#This java section generates servant_voices.svg and servant_voices.png in ../../output_images
#So, move these direct graphs to the new folder
mv ../../output_images/servant_voices* $new_dir/direct_voicelines

mkdir -p $new_dir/all_voicelines
java -cp "../../downloaded_libraries/gephi-toolkit-0.10.0-all.jar:../../generated_data/servant_voices.gv:../converting_scripts/" gephiDotToImage ../../generated_data/all_servant_voices.gv
mv ../../output_images/servant_voices* $new_dir/all_voicelines

#javac -Xlint -cp "./downloaded_libraries/gephi-toolkit-0.10.0-all.jar" ./interactGephi.java
#javac -cp "./downloaded_libraries/gephi-toolkit-0.10.0-all.jar" ./interactGephi.java
#java -cp ".:./downloaded_libraries/gephi-toolkit-0.10.0-all.jar:./generated_data/servant_voices.gv" ./Main.java
#java --illegal-access=permit -cp ".:./downloaded_libraries/gephi-toolkit-0.10.0-all.jar:./generated_data/servant_voices.gv" ./Main.java

