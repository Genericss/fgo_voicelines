#!/bin/bash

#javac -Xlint -cp "./downloaded_libraries/gephi-toolkit-0.10.0-all.jar" ./interactGephi.java
java -cp "../../downloaded_libraries/gephi-toolkit-0.10.0-all.jar:../../generated_data/servant_voices.gv:../converting_scripts/" gephiDotToImage

#javac -cp "./downloaded_libraries/gephi-toolkit-0.10.0-all.jar" ./interactGephi.java
#java -cp ".:./downloaded_libraries/gephi-toolkit-0.10.0-all.jar:./generated_data/servant_voices.gv" ./Main.java
#java --illegal-access=permit -cp ".:./downloaded_libraries/gephi-toolkit-0.10.0-all.jar:./generated_data/servant_voices.gv" ./Main.java

