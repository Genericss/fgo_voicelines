import csv
import sys
import graphviz

#The point of this is to convert the direct_servant_voices csv into a dot file format


top_level = graphviz.Digraph('servant_voices', strict=True) 
def main():
	if len(sys.argv) != 3:
		print("Bad arguments.")
		exit()
	fp = open(sys.argv[1], "r", encoding="utf-8")
	
	csvreader = csv.reader(fp, delimiter=',', quotechar='"')

	#Due to some quirks in my c and awk scripts, we index from 1 (0 index is empty)
	current_node = ""
	viewNextLine = 0
	voiceline=""

	#The input format of the csv should be something along the lines of
	#id...
	#voiceline:
	#servant id
	#voiceline
	#servant id

	for line in csvreader:
		#This indicates the first line, which specifies the servant for whom the voicelines are being created for
		if (line[1] == 'id:'):
			current_node = line[2]
			top_level.node(current_node, line[6], servant_class=line[16])
			
		#This indicates a voicelines, where 1 is to a single servant and 2 is to multiple
		if(line[1] == '1' or line[1] == '2'):
			
			#The servant id for whom the voice is for is actually on the next line, so set a flag to view
			if(not viewNextLine):
				viewNextLine = line[1]
				voiceline=line[3]
			else:
				#And then create an edge between the current servant and their voicelines
				if(viewNextLine == '1'):
					top_level.edge(current_node, line[3], label=voiceline)
				viewNextLine = 0


	fp.close()
	#We just write this into a file format instead of rendering it
	with open(sys.argv[2], "w", encoding="utf-8") as fp:
		fp.write(top_level.source)	

if __name__ == "__main__":
	main()
