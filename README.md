# fgo_voicelines
Graph to visualize FGO servant voicelines for each other

Each character in FGO may have direct voicelines for each other; this graph visualizes those direct connections. 

First, a downloaded JSON file from Atlas Academy is placed in the input_data folder.
Then, custom C and Awk scripts are used to parse the JSON (because I can't deal with javascript) into various csvs. 
The first C script (json_to_csv) converts the JSON to a csv format I like.
The awk script then prunes that csv for only servant ids and voicelines, also categorizes them into group voicelines and direct voicelines

From the list of voicelines, a python script is used to parse these into a DOT description (servant_voices.gv)

All the generated files are placed in the generated_data folder. 

From this, graphviz images can be generated (in archive/images) or gephi used, along with the sigmaJS plugin to generate a version visable online

The online version is viewable from the network folder, hosted on the following link:
	https://genericss.github.io/fgo_voicelines/network/
