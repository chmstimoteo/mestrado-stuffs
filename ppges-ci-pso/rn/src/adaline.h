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


uint32_t adaline_init(struct adaline *adaline);

// A fun��o training gera um arquivo: input_pso.data, que ser�o todos os indiv�duos do PSO.
uint32_t adaline_training(struct adaline *adaline, FILE *input_pso, uint32_t *iteration);

// A fun��o testing le um arquivo de entrada: output_pso.data, que ser� o melhor indiv�duo do PSO.
uint32_t adaline_testing(struct adaline *adaline, FILE *output_pso);

double adaline_generate_random (void);

double calculate_net(struct neuron *neuron, double *input);

double logistic_sigmoid_function (double *v);

//Imprime tanto os pesos quanto o erro.
uint32_t print_weights(struct adaline *adaline, double *error, char* stream);

#endif /* ADALINE_H_ */

