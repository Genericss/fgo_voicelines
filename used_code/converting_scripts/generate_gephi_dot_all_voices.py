import csv
import sys
import graphviz

#This is the same as ./generate_gephi_dot.py, the only difference is that it includes the grouped voicelines
#See
top_level = graphviz.Digraph('servant_voices', strict=True) 
def main():
	if len(sys.argv) != 3:
		print("Bad arguments.")
		exit()
	fp = open(sys.argv[1], "r", encoding="utf-8")
	
	csvreader = csv.reader(fp, delimiter=',', quotechar='"')
	current_node = ""
	viewNextLine = 0
	voiceline=""

	for line in csvreader:
		#Indicates current servant, current node
		if (line[1] == 'id:'):
			current_node = line[2]
			top_level.node(current_node, line[6], servant_class=line[16])
		
		#indicates voiceline to single servant (1) or to group (2)
		if(line[1] == '1' or line[1] == '2'):

			#The servant ids are stored on the next line, so set a flag to view
			if(not viewNextLine):
				viewNextLine = line[1]
				voiceline=line[3]
			else:
				#If single, create one edge between current node and target servant
				if(viewNextLine == '1'):
					top_level.edge(current_node, line[3], label=voiceline)
				else:
					#Otherwise, create an edge between every servant in the group and the current servant
					for i in range(6, len(line)):
						top_level.edge(current_node, line[i], label=voiceline)
				viewNextLine = 0


	fp.close()
	#Write out generated dot file
	with open(sys.argv[2], "w", encoding="utf-8") as fp:
		fp.write(top_level.source)	
if __name__ == "__main__":
	main()
