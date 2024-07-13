#include <stdio.h>
#include <stdlib.h>


int main(int argc, char *argv[]){

	FILE* fp = fopen(argv[1], "r");
	FILE* ofp = fopen(argv[2], "w");
	
	int buf_len = 100;
	char* buffer = (char*)malloc(sizeof(char) * buf_len);
	int counter = 0;

	//functionlly, the idea here is to read in buf_len sized chunks from the file
	//The main goal of this is to sort by csv, which is already mostly being done
	//Functionlly, we simply need to print everything [{a,b,c={}}]
	//This will be done by keeping a counter...
	//If the counter encounters a left bracket, it increments by one
	//If the counter encounters a right bracket, it decrements by one
	//Only print a bracket if the counter is greater or equal to 2 (ge 1)
	//
	

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


	

	//This is the worst json to csv converter ever
	//In the end, there is some kind of unbounded formatting...
	
	int inList = 0;
	
	while(fread(buffer, sizeof(char), buf_len, fp)){
		for(int i = 0; i < buf_len; i++){
//			if(buffer[i] == '{' || buffer[i] == '['){
//				counter = counter + 1;
//				fputc('\n', ofp);
//				for(int j=0; j < counter; j++){
//					fputc(',', ofp);
//				}
//			}else if(buffer[i] == '}' || buffer[i] == ']'){
//				counter = counter - 1;
//			}

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
				
//			if(counter>=2){
//				fputc(buffer[i], ofp);
//			}else if(counter == 1 && buffer[i] ==','){
//				for(int j=0; j < 10; j++)
//				fputc('\n', ofp);
//				
//			}
			
		}
				
	}
	fclose(fp);
	fclose(ofp);
    return 0;
}
