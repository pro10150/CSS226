#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/mman.h>
#include <fcntl.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <stdbool.h>

int word_count(char buff[]);

int main() {
   	FILE *fp;
   	char* filename = "test.txt";
   	int count = 0;
   	int c,lastChar;
   	int paragraph = 0;
   	char buff[1000000];
   	bool flag = false;
   	int countPerParagraph[1000];
   	int paragraphCount;
   	int characterCount = 0;

   	fp = fopen(filename, "r");
   	if (fp == NULL){
        	printf("Could not open file %s\n",filename);
        	return 1;
   	}
	do {
		c = fgetc(fp);
        	if( feof(fp) ) {
			//printf("end of file\n");
            		buff[characterCount] = ' ';
			printf("parent: %d\n",word_count(buff));
			int pid = fork();
			if(pid == 0){
				//child
				printf("child: %d\n",word_count(buff));
				sleep(1);
				break;
			}
			else if(pid != -1){
				//parent
				wait(NULL);
			}
			else perror("Error while calling the fork function");
            		countPerParagraph[paragraph] = word_count(buff)-1;
            		memset(buff, 0, sizeof(buff));
            		break ;
        	}
        	if( flag == true){
			//printf("here");
            		if(c == 32){
			//printf("check");
                	characterCount = 0;
			//printf("%d\n",word_count(buff));
			printf("parent: %d\n",word_count(buff));
			int pid = fork();
			if(pid == 0){
				//child
				printf("child: %d\n",word_count(buff));
				sleep(1);
				break;
			}
			else if(pid != -1){
				//parent
				wait(NULL);
			}
			else perror("Error while calling the fork function");
                	countPerParagraph[paragraph] = word_count(buff);
                	memset(buff, 0, sizeof(buff));
                	paragraph++;
            	}
            	else{
                	flag = false;
            	}
        	}

        	if( flag == false){
            		if(c == 10){
			
                	flag = true;
                	c = 32;
                	//count++;
                	//printf("%d",count);
            		}
        	}
        	if(c == 32){
            		//count++;
            		//printf("%d",count);
        	}
        	buff[characterCount] = c;
        	characterCount++;
   	} while(1);
    	fclose(fp);
	/*
    	for(int i=0;i<=paragraph;i++){
        	count += countPerParagraph[i];
        	printf("%d\n",countPerParagraph[i]);
    	}
    	printf("Paragraph = %d\n",paragraph+1);
    	printf("Word count = %d\n",count);
	*/

    	return 0;
}

int word_count(char buff[]){
	bool flag = false;
    	int count = 0,i=0,c;
    	//printf("\n");
    	/*
    	while(buff[i] != '\0'){
        	printf("%c",buff[i]);
            	i++;
    	}*/
    	do{
        	if(buff[i] == '\0') break;


        	if(buff[i] == 32){
            	count++;
            	//printf("%d",count);
        	}
        	i++;
    	}while(1);
    	i = 0;
    	//printf("%d",count);
    	//sleep(1);
    	return count;
}
