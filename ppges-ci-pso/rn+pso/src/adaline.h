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
	uint32_t input_number;	// TODO: Isso n�o � necess�rio pois INPUT_NUMBER � fixo...
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

// A fun��o training gera um arquivo: input_pso.data, que ser�o todos os indiv�duos do PSO.
void adaline_forward(struct adaline *adaline, uint32_t *iteration);

// A fun��o testing le um arquivo de entrada: output_pso.data, que ser� o melhor indiv�duo do PSO.
void adaline_testing(struct adaline *adaline, FILE *input, FILE *output);

double adaline_generate_random (void);

double calculate_net(struct neuron *neuron, double *input);

double logistic_sigmoid_function (double *v);

//Imprime tanto os pesos quanto o erro.
void print_weights(struct adaline *adaline, double *error, char* stream);

void define_current_weight(struct adaline *adaline, double* weights);

double quadratic_error(struct adaline *adaline);

#endif /* ADALINE_H_ */

