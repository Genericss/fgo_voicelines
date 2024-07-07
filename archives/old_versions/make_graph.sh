#!/bin/bash

python3 $1 fixedOutput.csv
#sfdp -x -Goverlap=scale -Tsvg servant_voices.gv > servant_voices.svg
sfdp -Tsvg servant_voices.gv > servant_voices.svg
cp servant_voices.svg /mnt/c/Users/justn/Downloads/
