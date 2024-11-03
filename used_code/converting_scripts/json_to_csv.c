#include <stdio.h>
#include <stdlib.h>


int main(int argc, char *argv[]){

	FILE* fp = fopen(argv[1], "r");
	FILE* ofp = fopen(argv[2], "w");
	
	int buf_len = 100;
	char* buffer = (char*)malloc(sizeof(char) * buf_len);
	int counter = 0;

	//Here is the necessary structure:
	//Each value gets a new line
	//Members of a list share a line
	//If this is a substructure, add an empty comma
	//e.g.
	//id:500
	//list: [1,2,3,4]
	//substructure
	//	id:200
	//	list2: [1,2,3,4]
	//Endsubstucture: 2
	//

	//Honestly, the best way to figure out how this works is to see the input json and check the output csv
	//The idea was that for lists like [1, 2, 3] to determine if it was in the list by tracking left and right brackets
	//And then print them on a line if so
	//Simultaneously, we want to break elements of {a, b, c} into new lines
	//So, if a {} are encountered, we place a new line
	//We also want to "indent" when viewed on excel, so we add a corresponding amount of preceding commas
	

	//This is the worst json to csv converter ever
	
	int inList = 0;
	
	while(fread(buffer, sizeof(char), buf_len, fp)){
		for(int i = 0; i < buf_len; i++){
			if(buffer[i] == '['){
				inList++;
			}else if (buffer[i] == ']'){
				inList--;
			}
			if(buffer[i] == '{'){ 
				counter = counter + 1;
				fputc('\n', ofp);
				for(int j=0; j < counter; j++){
					fputc(',', ofp);
				}
			}else if(buffer[i] == '}'){
				counter = counter - 1;
			}

			if(buffer[i] == ',' && !inList){ //!inlist should always be the case
				fputc('\n', ofp);
				for(int j = 0; j < counter; j++){
					fputc(',', ofp);
				}
			}else if(buffer[i] != '{' && buffer[i] != '}' ){

				if(buffer[i] == ']'){
					fputc(',', ofp);
				}

				fputc(buffer[i], ofp);

				if(buffer[i] == ':'){
					fputc(',', ofp);
				}
				if(buffer[i] == '['){
					fputc(',', ofp);
				}

			}
			//  else{
			// 	//There is misaligned formatting somewhere here... If you do this, you see that inList keeps increasing?
			// 	// char out;
			// 	// out = 'a' + inList;
			// 	fputc(out, ofp);
			// 	fputc(buffer[i], ofp);
			// }
			
		}
				
	}
	fclose(fp);
	fclose(ofp);
	free(buffer);
    return 0;
}
