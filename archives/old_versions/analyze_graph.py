import networkx as nx
import matplotlib.pyplot as plt
import pygraphviz as pgv

def main():
	to_pgv = pgv.AGraph('/home/ngjust14/fate_graphs/servant_voices.gv')
	#G = nx.nx_agraph.read_dot('/home/ngjust14/fate_graphs/servant_voices.gv')
	G  = nx.nx_agraph.from_agraph(to_pgv)
	#to_pgv.write('test.dot')
	#for node_id in G.nodes:
	#	print(node_id, G._node[node_id]['label'])
	plt.figure(1, figsize=(100,100))
	#nx.draw_circular(G)
	pos = nx.nx_agraph.graphviz_layout(G, prog='sfdp')

	nx.draw(G, pos, with_labels=True)
	plt.savefig('test.svg')


if __name__ == "__main__":
	main()
