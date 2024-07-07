#This is a no go, I think the best bet is to get linux for windows, try to install graphviz onto there, and then proceed to generate the graph (port to bokeh)
import networkx as nx
import pygraphviz as pgv

#On the global level, set a graph

def main():
	G = nx.nx_agraph.read_dot('/home/ngjust14/fate_graphs/servant_voices.gv')
	nx.write_gml(G, '/mnt/c/Users/justn/Downloads/servant_voices.gv')
if __name__ == "__main__":
	main()
