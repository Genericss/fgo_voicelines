#!/bin/bash

java -cp "../../downloaded_libraries/gephi-toolkit-0.10.0-all.jar:../../generated_data/servant_voices.gv:../converting_scripts/" gephiDotToImage $1
