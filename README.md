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

Using the gephi-toolkit/java, images are automatically generated as well (this was a real pain). 

The online version is viewable from the network folder, hosted on the following links:
	https://genericss.github.io/fgo_voicelines/interactive_graphs/direct_voicelines
	https://genericss.github.io/fgo_voicelines/interactive_graphs/all_voices

Gephi project files will also be provided somewhere

TODO: the python script to parse csv into dot file using graphviz can likely be phased out, since it doesn't actually render. It just writes out the dot source code and exits. Originally, it was going to do more interactions and pass it to other files, but changes had to be made (read: none of them were working)
making an edit to force commit



Installations:
	The majority of this was done on Linux, so I have:
		A lot of shell scripts to run the various programs I have
		gcc compiled the c executable
		awk parses the csv
		Graphviz is installed, although this can be phased out since it's only used to pass to Gephi
		Gephi is installed on Windows, although this will likely be changed.
		EDIT: 
			I downloaded the gephi-toolkit for java, which is hanging out in the downloaded_libraries directory
			I also had to download the java development kit for linux (version 11). 

