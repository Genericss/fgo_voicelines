

function strip_quotes(name){
	return substr(name, 2, length(name)-2);
}


BEGIN{
	inProfile = 0;
	viewNextLine = 0;	
	outStr = "";
	single = 0;
	subtitle = "";
}



{
	#Prints the servant id
	if($2 == "\"id\":"){
		print($0);

	}

	#Checks if this is the voiceline section
	if($3 == "\"cv\":" ){
		
		inProfile = 1;
	}
	
	#This variable is set later, it's because the voiceline and voiceline condition is disjointed
	if(viewNextLine == 1){

		if($7 == "\"svtGet\"" ||$7=="\"svtGroup\""){
			#This checks the condition
			if($7 == "\"svtGet\"" ){
				single = 1;
			}else{
				single = 2;
			}

			#And records the servants for whom this voiceline is for
			servants = "";
			for(i=(7+1);i<=NF;i++){
				if($i == "["){
					continue;
				}else if($i == "]"){
					break;
				}else{
					servants = (servants "," $i);
				}
			}
			print("," single subtitle);
			print("," single servants);
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

				#This section parses out the subtitle to use as a label
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

					}
					if($i=="\"conds\":"){
						
						if($(i+3) != "]"){
							viewNextLine = 1;
						}else{
							print("," subtitle);
						}
						
						break;
					}
				}


			}
		}
		
	}
}
