/*
 * data.c
 *
 *  Created on: 26/12/2012
 *      Author: carlos
 */

#include "data.h"

void load_data(FILE *file, struct data *data){

	char current_line[50];
	int i=0;
	char sepalLenght_current[3], sepalWidth_current[3], petalLenght_current[3], petalWidth_current[3], class_current[3];

	data->header = (char*)malloc(sizeof(char)*100);

	memset(data->header, 0x00, sizeof(data->header));
	file = fopen ("iris.data" , "r");

	assert(data->header != NULL);

	//Reading Header
	if (fgets (data->header, 100, file) != NULL ){
		fprintf (stdout, "%s\n", data->header);
	}

	//Reading data
	//Legenda
	//001 - iris-setosa
	//010 - iris-versicolour
	//001 - iris-virginica

	for(;!feof(file); i++){
		memset(current_line,0x00,sizeof(current_line));
		if (fgets (current_line, 50, file) != NULL ){
			fprintf (stdout, "%s", current_line);

			sprintf(sepalLenght_current,"%c%c%c",current_line[0],current_line[1],current_line[2]);
			sprintf(sepalWidth_current,"%c%c%c",current_line[4],current_line[5],current_line[6]);
			sprintf(petalLenght_current,"%c%c%c",current_line[8],current_line[9],current_line[10]);
			sprintf(petalWidth_current,"%c%c%c",current_line[12],current_line[13],current_line[14]);
			sprintf(class_current,"%c%c%c",current_line[16],current_line[17],current_line[18]);

			data->sepalLenght[i] = atof(sepalLenght_current);
			data->sepalWidth[i] = atof(sepalWidth_current);
			data->petalLenght[i] = atof(petalLenght_current);
			data->petalWidth[i] = atof(petalWidth_current);
			data->class[i] = atof(class_current);

		}
	}

	if (fclose(file) != 0)
		perror("Could not close data file.\n");

	return ;
}

void load_training_data(FILE *file, struct training_data *training_data){

	char current_line[50];
	int i=0;
	char sepalLenght_current[3], sepalWidth_current[3], petalLenght_current[3], petalWidth_current[3], class_current[3];

	training_data->header = (char*)malloc(sizeof(char)*100);

	memset(training_data->header, 0x00, sizeof(training_data->header));
	file = fopen ("training.data" , "r");

	assert(training_data->header != NULL);

	//Reading Header
	if (fgets (training_data->header, 100, file) != NULL ){
		fprintf (stdout, "%s\n", training_data->header);
	}

	//Reading training_data
	//Legenda
	//001 - iris-setosa
	//010 - iris-versicolour
	//001 - iris-virginica

	for(;!feof(file); i++){
		memset(current_line,0x00,sizeof(current_line));
		if (fgets (current_line, 50, file) != NULL ){
			fprintf (stdout, "%s", current_line);

			sprintf(sepalLenght_current,"%c%c%c",current_line[0],current_line[1],current_line[2]);
			sprintf(sepalWidth_current,"%c%c%c",current_line[4],current_line[5],current_line[6]);
			sprintf(petalLenght_current,"%c%c%c",current_line[8],current_line[9],current_line[10]);
			sprintf(petalWidth_current,"%c%c%c",current_line[12],current_line[13],current_line[14]);
			sprintf(class_current,"%c%c%c",current_line[16],current_line[17],current_line[18]);

			training_data->sepalLenght[i] = atof(sepalLenght_current);
			training_data->sepalWidth[i] = atof(sepalWidth_current);
			training_data->petalLenght[i] = atof(petalLenght_current);
			training_data->petalWidth[i] = atof(petalWidth_current);
			training_data->class[i] = atof(class_current);

		}
	}

	if (fclose(file) != 0)
		perror("Could not close training_data file.\n");

	return ;
}
