import csv
import sys
import graphviz

#This is a no go, I think the best bet is to get linux for windows, try to install graphviz onto there, and then proceed to generate the graph (port to bokeh)

#On the global level, set a graph

top_level = graphviz.Digraph('servant_voices') 
def main():
	if len(sys.argv) != 2:
		print("Bad arguments.")
		exit()
	fp = open(sys.argv[1], "r", encoding="utf-8")
	
	csvreader = csv.reader(fp, delimiter=',', quotechar='"')

	#Due to some quirks in my c and awk scripts, we index from 1 (0 index is empty)
	current_node = ""
	viewNextLine = 0
	voiceline=""
	for line in csvreader:
		if (line[1] == 'id:'):
			current_node = line[2]
			#with top_level.subgraph(name='cluster_' + line[18]) as G:
			font_col = 'black'
			fill_col = line[16]
			if(int(line[16]) > 12):
				font_col = 'white'	
				fill_col = 'black'
			top_level.node(current_node, line[6], fillcolor=fill_col, fontcolor=font_col)
			
		if(line[1] == '1' or line[1] == '2'):
			
			if(not viewNextLine):
				viewNextLine = line[1]
				voiceline=line[3]
			else:
				if(viewNextLine == '1'):
					top_level.edge(current_node, line[3], label=voiceline)
					#pass
#				else:
#					#for collections
#					#Groups are identified as a 4 digit (1023)..
#					#So, i am arbitrarily going to give it
#					group_id = line[3]
#					#colour = "#" + string_to_hex_string(group_id[1]) + '0' + string_to_hex_string(group_id[2]) + '0' + string_to_hex_string(group_id[3]) + '0'
#					for i in range(6, len(line)):
#						top_level.edge(current_node, line[i], label='group_'+voiceline)
				viewNextLine = 0


	fp.close()
	with open('/home/ngjust14/fate_graphs/servant_voices.gv', "w", encoding="utf-8") as fp:
		fp.write(top_level.source)	
#	G.render(directory='/home/ngjust14/fate_graphs', view=False)
if __name__ == "__main__":
	main()
