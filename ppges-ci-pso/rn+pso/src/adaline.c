/*
 * adaline.c
 *
 *  Created on: 14/12/2012
 *      Author: carlos
 */

#include <assert.h>
#include <string.h>
#include <math.h>
#include "adaline.h"
#include "data.h"

void adaline_init(struct adaline *adaline){
	//ADALINE com três neurônios na camada de saída
	uint32_t i,j;
	assert(adaline->neurons == NULL);

	adaline->input_number = 5;
	adaline->input[0] = 1.0f;
	adaline->neurons = (struct neurons **)malloc(adaline->neuron_number*sizeof(struct neuron *));
	assert(adaline->neurons != NULL);

	for (i = 0; i < adaline->neuron_number; ++i) {
		adaline->neurons[i] = (struct neuron *)malloc(sizeof(struct neuron));
		memset(adaline->neurons[i], 0x00, sizeof(struct neuron));
		adaline->neurons[i]->output = 0;
		adaline->neurons[i]->wished = 0;
	}
	return ;
}

void adaline_forward(struct adaline *adaline, uint32_t *iteration){
	uint32_t i,j;
	double current_alpha = 0.7f, neti = 0.0f;

	for (i = 0; i < adaline->neuron_number; ++i) {
		neti = calculate_net(adaline->neurons[i], adaline->input);
		adaline->neurons[i]->output = logistic_sigmoid_function(&neti);
	}

	//Regra: O vencedor leva tudo.
	i=0;
	struct neuron *winner = adaline->neurons[0];
	for (i = 1; i < adaline->neuron_number; ++i) {
		if(adaline->neurons[i]->output > winner->output){
			winner->output = 0.0f;
			winner = adaline->neurons[i];
		} else {
			adaline->neurons[i]->output = 0.0f;
		}
	}
	winner->output = 1.0f;

	return;
}

void adaline_testing(struct adaline *adaline, FILE *input, FILE *output){
	uint32_t h,i,j;
	double neti = 0.0f, class_wished=0.0f;
	char* current_line[50];
	struct training_data training = {
				header: NULL
		};

	output = fopen("result.txt","a");
	load_training_data(input,&training);

	fprintf(output,"** INICIO DOS RESULTADOS **\n");
	for(h=0;h<36;++h){
		adaline->input[0] = 1.0f;
		adaline->input[1] = training.sepalLenght[h];
		adaline->input[2] = training.sepalWidth[h];
		adaline->input[3] = training.petalLenght[h];
		adaline->input[4] = training.petalWidth[h];
		class_wished = training.class[h];
		for (i = 0; i < adaline->neuron_number; ++i) {
			for (j = 0; j < adaline->input_number; ++j) {
				neti = calculate_net(adaline->neurons[i], adaline->input);
				adaline->neurons[i]->output = logistic_sigmoid_function(&neti);
			}
		}
		fprintf(output, "%0.1f,%0.1f,%0.1f\t%f\n", adaline->neurons[0]->output, adaline->neurons[1]->output, adaline->neurons[2]->output, class_wished);
	}
	fprintf(output,"** FIM DOS RESULTADOS **");

	return;
}


double adaline_generate_random (void) {
	return ((double)random()/(double)RAND_MAX);
}


double calculate_net(struct neuron *neuron, double *input) {
	double ret = 0.0f;
	uint32_t i;
	for (i = 0; i < INPUT_NUMBER+1; ++i) //+1: Bias
		ret += (input[i]*neuron->weight[i]);

	return ret;
}

double logistic_sigmoid_function (double *v) {
	return (1 / (1 - exp(-*v)));
}

void print_weights(struct adaline *adaline, double *error, char* stream){
	int i, j;
	for (i = 0; i < adaline->neuron_number; ++i) {
		for (j = 0; j < adaline->input_number; ++j) {
			sprintf(stream, "%0.2f,", adaline->neurons[i]->weight[j]);
		}
	}

	sprintf(stream, "%0.2f\n", *error);

	return;
}

void define_current_weight(struct adaline *adaline, double* weights){
	int i, j, k=0;
	for (i = 0; i < adaline->neuron_number; ++i) {
		for (j = 0; j < adaline->input_number; ++j, ++k) {
			adaline->neurons[i]->weight[j] = weights[k];
		}
	}
	return;
}

double quadratic_error(struct adaline *adaline) {
	uint32_t i;
	double quadratic_error= 0.0f;
	for (i = 0; i < adaline->neuron_number; ++i) {
		quadratic_error += pow((adaline->neurons[i]->wished - adaline->neurons[i]->output),2);
	}

	return quadratic_error;
}

