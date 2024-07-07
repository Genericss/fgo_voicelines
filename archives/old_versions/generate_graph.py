import csv
import sys



#This is a no go, I think the best bet is to get linux for windows, try to install graphviz onto there, and then proceed to generate the graph (port to bokeh)
import matplotlib.pyplot as plt
import networkx as nx

#On the global level, set a graph

G = nx.Graph()
id_to_name = {}

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
			G.add_node(line[2])
			current_node = line[2]
			id_to_name[line[2]] = line[6]
		
		if(line[1] == '1'):
			if(not viewNextLine):
				viewNextLine = 1
			else:
				G.add_edge(current_node, line[3])
				viewNextLine = 0


	fp.close()
	nx.draw_shell(G, with_labels=True, labels=id_to_name)
	
	plt.show()
	
if __name__ == "__main__":
	main()
