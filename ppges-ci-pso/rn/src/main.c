/*
 ============================================================================
 Name        : rn.c
 Author      : Carlos Timoteo
 Version     :
 Copyright   : Your copyright notice
 Description : Hello World in C, Ansi-style
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>

#include "data.h"
#include "adaline.h"

uint32_t load_data(FILE *iris, struct data *iris_data){

	char current_line[50];
	int i=0;
	char sepalLenght_current[3], sepalWidth_current[3], petalLenght_current[3], petalWidth_current[3], class_current[3];

	iris_data->header = (char*)malloc(sizeof(char)*100);

	printf("Escaneando arquivo de dados!\n");
	memset(iris_data->header, 0x00, sizeof(iris_data->header));
	iris = fopen ("iris.data" , "r");

	assert(iris_data->header != NULL);

	//Reading Header
	if (fgets (iris_data->header, 100, iris) != NULL ){
		fprintf (stdout, "%s\n", iris_data->header);
	}

	//Reading data
	//Legenda
	//001 - iris-setosa
	//010 - iris-versicolour
	//001 - iris-virginica
	
	fprintf(stdout, "Reading data...\n");
	for(;!feof(iris); i++){
		memset(current_line,0x00,sizeof(current_line));
		if (fgets (current_line, 50, iris) != NULL ){
			fprintf (stdout, "%s", current_line);

			sprintf(sepalLenght_current,"%c%c%c",current_line[0],current_line[1],current_line[2]);
			sprintf(sepalWidth_current,"%c%c%c",current_line[4],current_line[5],current_line[6]);
			sprintf(petalLenght_current,"%c%c%c",current_line[8],current_line[9],current_line[10]);
			sprintf(petalWidth_current,"%c%c%c",current_line[12],current_line[13],current_line[14]);
			sprintf(class_current,"%c%c%c",current_line[16],current_line[17],current_line[18]);

			iris_data->sepalLenght[i] = atof(sepalLenght_current);
			iris_data->sepalWidth[i] = atof(sepalWidth_current);
			iris_data->petalLenght[i] = atof(petalLenght_current);
			iris_data->petalWidth[i] = atof(petalWidth_current);
			iris_data->class[i] = atof(class_current);

		}
	}

	if (fclose(iris) != 0) {
		perror("Could not close data file.\n");
		return 0;
	}

	printf("Leu arquivo de dados!\n");
	return 1;	//TODO: Qual o propósito desse return aqui se a função não retorna algo diferente de 1?
}


int main(int argc, char ** argv) {

	FILE *iris, *pso_input, *pso_output;
	uint32_t ret;
	uint32_t it, i, j;

	// CZR: Que bosta é isso aqui?
	struct data iris_data = {
			header: NULL
	};

	struct adaline adaline = {
			neuron_number: 3,
			activation_function: logistic_sigmoid_function
	};

	ret = load_data(iris, &iris_data);
	if(!ret)	goto CLEANUP;

	ret = adaline_init(&adaline);
	if(!ret)	goto CLEANUP;

	printf("***Begin Execution***");

	// Para cada iteração, define a entrada correspondente e o valor desejado
	adaline.input[0] = 1.0f;
	for (it = 0; it < 150; ++it) {
		adaline.input[1] = iris_data.sepalLenght[it];
		adaline.input[2] = iris_data.sepalWidth[it];
		adaline.input[3] = iris_data.petalLenght[it];
		adaline.input[4] = iris_data.petalWidth[it];
		switch ((int32_t)(iris_data.class[it])) {
			case 1:
				adaline.neurons[0]->wished = 0.0f;
				adaline.neurons[1]->wished = 0.0f;
				adaline.neurons[2]->wished = 1.0f;
				break;
			case 10:
				adaline.neurons[0]->wished = 0.0f;
				adaline.neurons[1]->wished = 1.0f;
				adaline.neurons[2]->wished = 0.0f;
				break;
			case 100:
			default:
				adaline.neurons[0]->wished = 1.0f;
				adaline.neurons[1]->wished = 0.0f;
				adaline.neurons[2]->wished = 0.0f;
				break;
		}

		ret = adaline_training(&adaline, pso_input, &it);
		if(!ret)	goto CLEANUP;
	}

	ret = adaline_testing(&adaline, pso_output);
	if(!ret)	goto CLEANUP;

	printf("***End Execution***");
	goto CLEANUP;


	CLEANUP:
//		assert(iris_data.header == NULL);
		if (iris_data.header)
			free (iris_data.header);

	return 0;
}

