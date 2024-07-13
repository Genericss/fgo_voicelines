#!C:\Program Files (x86)\GnuWin32\bin\awk.exe

function strip_quotes(name){
	return substr(name, 2, length(name)-2);
}


#please run the program with something like this
#"C:\Program Files (x86)\GnuWin32\bin\awk.exe" -F "," -f "C:\Users\justn\Downloads\onlyVoice.awk" < "C:\Users\justn\Downloads\nice_servant_lore.csv" > "C:\Users\justn\Downloads\fixedOutput.csv"   


BEGIN{
	inProfile = 0;
	viewNextLine = 0;	
	outStr = "";
	single = 0;
	subtitle = "";
}



{
	if($2 == "\"id\":"){
		print($0);

	}

	if($3 == "\"cv\":" ){
		
		inProfile = 1;
	}

	if(viewNextLine == 1){
		if($7 == "\"svtGet\"" ||$7=="\"svtGroup\""){
			if($7 == "\"svtGet\"" ){
				single = 1;
			}else{
				single = 2;
			}
			#print($0);
			servants = "";
			#printf(",,%d,%s", single,$7);
			for(i=(7+1);i<=NF;i++){
				if($i == "["){
					continue;
				}else if($i == "]"){
					break;
				}else{
					#printf(",%s", $i);
					servants = (servants "," $i);
				}
			}
			print("," single subtitle);
			print("," single servants);
			#printf("\n");
		}
		viewNextLine = 0;
	}
	
	


	if(inProfile==1){
		if($3 != "\"cv\":" && $3 != ""){ 
			inProfile = 0;	
		}else {
			if($6 ~ "Conversation"){
				#print($0)
				subtitle = ("," $6 ",") 

				#printf(",,%s,", $6);
				for(i=1;i<=NF;i++){
					if($i ~ "subtitle"){
						
						#This doesn't work if the string has commas in it, as set by the field separator
						#The planned workaround is just to see if the last character of the string is an end quote...should work
						#print(substr($(i+1), length($(i+1))-2, length($(i+1))));
						#I am pretty sure this part is meant to read everything after subtitle: "" and end at the last quote
						
						#this shouldn't work, but somehow still does?
						#for(j=i+1; substr($j, length($j)-1, 1) != "\""; j++){
						#it was breaking at altera-rama transition, b/c altera also had a voiceline with escaped quotes
						#changed just to check for conds
						for(j=i+1; $j !="\"conds\":"; j++){

						#for(j=i+1; substr($j, length($j), 1) != "\""; j++){
							subtitle = (subtitle $j ",");
							#printf("%s,", $j);
						}
						
						i = j;

						#print($j)
						#subtitle = (subtitle "\n");
						#print(subtitle);
						#printf("\n");
						#printf(" %s\n", $(i+1));
					}
					if($i=="\"conds\":"){
						
						if($(i+3) != "]"){
							viewNextLine = 1;
						}else{
							print("," subtitle);
						}
						
						
	
						#print($i, $(i+1), $(i+2), $(i+3))
						break;
					}
				}

#				if($0 ~ "\"conds\":"){
#					viewNextLine = 1;
#				}

			}
		}
		
	}
}
