import csv
import sys
import graphviz

#This is a no go, I think the best bet is to get linux for windows, try to install graphviz onto there, and then proceed to generate the graph (port to bokeh)

#On the global level, set a graph

G = graphviz.Digraph('servant_voices')
G.attr(overlap='false',splines='true')
#G.attr(overlap='scale')
def string_to_hex_string(s):
	return hex(int(s))[2:]
def main():
	if len(sys.argv) != 2:
		print("Bad arguments.")
		exit()
	fp = open(sys.argv[1], "r", encoding="utf-8")
	
	csvreader = csv.reader(fp, delimiter=',', quotechar='"')

	#Due to some quirks in my c and awk scripts, we index from 1 (0 index is empty)
	current_node = ""
	viewNextLine = 0
	for line in csvreader:
		if (line[1] == 'id:'):
			current_node = line[2]
			G.node(current_node, line[6])
		
		if(line[1] == '1' or line[1] == '2'):
			
			if(not viewNextLine):
				viewNextLine = line[1]
			else:
				if(viewNextLine == '1'):
					G.edge(current_node, line[3])
					#pass
				viewNextLine = 0


	fp.close()
	with open('/home/ngjust14/fate_graphs/servant_voices.gv', "w", encoding="utf-8") as fp:
		fp.write(G.source)	
#	G.render(directory='/home/ngjust14/fate_graphs', view=False)
if __name__ == "__main__":
	main()
