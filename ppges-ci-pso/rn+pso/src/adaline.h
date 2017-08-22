/*
 * adaline.h
 *
 *  Created on: 14/12/2012
 *      Author: carlos
 */

#ifndef ADALINE_H_
#define ADALINE_H_

#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>
#include "data.h"

#define INPUT_NUMBER 4
#define LOWER_WEIGHT -1.0f
#define UPPER_WEIGHT 1.0f

struct adaline;
struct neuron;

struct adaline {
	uint32_t neuron_number;
	uint32_t input_number;	// TODO: Isso não é necessário pois INPUT_NUMBER é fixo...
	double input[INPUT_NUMBER+1]; //+1: Bias
	struct neuron **neurons;
	double (*activation_function) (double *net_index);
};

struct neuron {
	double weight[INPUT_NUMBER+1]; //+1: Bias
	double output;
	double wished;
};


void adaline_init(struct adaline *adaline);

// A função training gera um arquivo: input_pso.data, que serão todos os indivíduos do PSO.
void adaline_forward(struct adaline *adaline, uint32_t *iteration);

// A função testing le um arquivo de entrada: output_pso.data, que será o melhor indivíduo do PSO.
void adaline_testing(struct adaline *adaline, FILE *input, FILE *output);

double adaline_generate_random (void);

double calculate_net(struct neuron *neuron, double *input);

double logistic_sigmoid_function (double *v);

//Imprime tanto os pesos quanto o erro.
void print_weights(struct adaline *adaline, double *error, char* stream);

void define_current_weight(struct adaline *adaline, double* weights);

double quadratic_error(struct adaline *adaline);

#endif /* ADALINE_H_ */

