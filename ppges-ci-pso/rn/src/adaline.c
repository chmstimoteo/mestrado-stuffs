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

#define UPPER_BOUND 1.0f
#define LOWER_BOUND -1.0f

uint32_t adaline_init(struct adaline *adaline){
	//ADALINE com três neurônios na camada de saída
	uint32_t i,j;

	assert(adaline->neurons == NULL);

	adaline->input_number = 5;	// TODO: Sem propósito...
	adaline->input[0] = 1.0f;

	adaline->neurons = (struct neurons **)malloc(adaline->neuron_number*sizeof(struct neuron *));
	assert (adaline->neurons != NULL);

	for (i = 0; i < adaline->neuron_number; ++i) {
		adaline->neurons[i] = (struct neuron *)malloc(sizeof(struct neuron));
		memset(adaline->neurons[i], 0x00, sizeof(struct neuron));
		adaline->input[i] = 0;
		adaline->neurons[i]->output = 0;

		for(j = 0; j < INPUT_NUMBER+1; j++){	//+1: Bias
			adaline->neurons[i]->weight[j] = ((UPPER_BOUND - LOWER_BOUND) * adaline_generate_random() + LOWER_BOUND);
		}

	}
	return 1;	// TODO: Sem propósito esse return aqui pois não há nenhum retorno diferente de 1
}

// A função training gera um arquivo: input_pso.data, que será todos os indivíduos do PSO.
uint32_t adaline_training(struct adaline *adaline, FILE *input_pso, uint32_t *iteration){
	uint32_t i,j;
	double current_alpha, quadratic_error = 0.0f, neti = 0.0f;

	//Manipular o arquivo de saida
	input_pso = fopen("pso_input.data", "w");

	for (i = 0; i < adaline->neuron_number; ++i) {
		for (j = 0; j < adaline->input_number; ++j) {
			neti = calculate_net(adaline->neurons[i], adaline->input);
			adaline->neurons[i]->output = logistic_sigmoid_function(&neti);
			quadratic_error += powf((adaline->neurons[i]->wished - adaline->neurons[i]->output), 2);
		}
	}

	assert(input_pso != NULL);

	char stream[100];
	print_weights(adaline, &quadratic_error, stream);
	stream[sizeof(stream)-1] = 0x00;
	//assert(fputs(stream,input_pso) != EOF);
	fprintf (stdout, "**%s**", stream);

	if(fclose(input_pso)){
		perror("Could not close pso_input file.\n");
		return 0;
	}

	return 1;
}

// A função testing le um arquivo de entrada: output_pso.data, que será o melhor indivíduo do PSO.
uint32_t adaline_testing(struct adaline *adaline, FILE *output_pso){
	return 1;
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

uint32_t print_weights(struct adaline *adaline, double *error, char* stream){
	int i, j;
	for (i = 0; i < adaline->neuron_number; ++i) {
		for (j = 0; j < adaline->input_number; ++j) {
			sprintf(stream, "%0.2f,", adaline->neurons[i]->weight[j]);
		}
	}

	sprintf(stream, "%0.2f\n", *error);

	return 1;
}

