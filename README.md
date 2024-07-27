
Graph to visualize FGO servant voicelines for each other. 
Each character in FGO may have direct voicelines for each other; this graph visualizes those direct connections. 
The online version is viewable from the interactive_graphs folder, hosted on the following links:
     https://genericss.github.io/fgo_voicelines/interactive_graphs/direct_voicelines
     https://genericss.github.io/fgo_voicelines/interactive_graphs/all_voices

First, a downloaded JSON file from Atlas Academy is placed in the input_data folder.
Then, custom C and Awk scripts are used to parse the JSON (because I can't deal with javascript) into various csvs. 
The first C script (json_to_csv) converts the JSON to a csv format I like.
The awk script then prunes that csv for only servant ids and voicelines, also categorizes them into group voicelines and direct voicelines.
From the list of voicelines, a python script is used to parse these into a DOT description (servant_voices.gv).
All the generated files are placed in the generated_data folder. 
Using the gephi-toolkit/java, images are automatically generated (this was a real pain). 

I've installed the JDK/JRE version 11 and am using that to run. I also installed graphviz and the graphviz python library. 

Also, sometimes the gephi java script gets stuck, I just restart the computer and rerun it. 
It also keeps throwing out illegal reflective access warning, which I assume is just a normal problem.
Downgrading to java 8 might help, but that's for another day. 
