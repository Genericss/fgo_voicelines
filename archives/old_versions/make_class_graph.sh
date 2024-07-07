#!/bin/bash

python3 /home/ngjust14/fate_graphs/class_sort.py fixedOutput.csv
#sfdp -x -Goverlap=scale -Tsvg servant_voices.gv > servant_voices.svg
sfdp -Tsvg -q servant_voices.gv > servant_voices.svg
cp servant_voices.svg /mnt/c/Users/justn/Downloads/
