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
#include "pso.h"
#include "operators.h"
#include "adaline.h"


int main(int argc, char ** argv) {

	FILE *iris, *output, *training;
	uint32_t ret, it, h, i, j;
	double phi1, phi2, old_fit;

	struct data iris_data = {
			header: NULL
	};

	struct pso pso = {
		particle_num: 20,
		c1: 2.05f,
		c2: 2.05f,
		inertia: 0.1f,
		particles:    NULL,
		fit_function: quadratic_error
	};

	struct adaline adaline = {
			neuron_number: 3,
			activation_function: logistic_sigmoid_function
	};

	printf("***Begin Execution***\n");

	load_data(iris, &iris_data);

	pso_create_particles(&pso);
	pso_init_particles(&pso, LOWER_WEIGHT, UPPER_WEIGHT);
	define_neighborhood_global(&pso);

	adaline_init(&adaline);

	//PSO
	for(h=0;h<10000;++h){
		for (i = 0; i < pso.particle_num; i++) {
			// Para cada entrada, o fitness total é gerado
			adaline.input[0] = 1.0f;
			phi1 = pso_generate_random(); phi2 = pso_generate_random();
			for (j = 0; j < NUMBER_OF_DIMENSIONS; j++) {
				pso.particles[i]->velocity[j] +=
						(pso.inertia*pso.particles[i]->velocity[j] +
						 phi1 * pso.c1 * (pso.particles[i]->p_best[j] - pso.particles[i]->position[j]) +
						 phi2 * pso.c2 * (pso.particles[i]->g_best[j] - pso.particles[i]->position[j]));

				if (pso.particles[i]->velocity[j] > UPPER_WEIGHT/2)
					pso.particles[i]->velocity[j] = UPPER_WEIGHT/2;
				else if (pso.particles[i]->velocity[j] < LOWER_WEIGHT/2)
					pso.particles[i]->velocity[j] = LOWER_WEIGHT/2;
			}
			for (j = 0; j < NUMBER_OF_DIMENSIONS; j++) {
				pso.particles[i]->position[j] += pso.particles[i]->velocity[j];
				if (pso.particles[i]->position[j] > UPPER_WEIGHT || pso.particles[i]->position[j] < LOWER_WEIGHT) {
					pso.particles[i]->velocity[j] *= -1.0f;
					if (pso.particles[i]->position[j] > UPPER_WEIGHT)
						pso.particles[i]->position[j] = 0.99f;
					else
						pso.particles[i]->position[j] = -0.99f;
				}

			}

			//Calculo da funcao de fitness
			old_fit = pso.particles[i]->fitness;
			pso.particles[i]->fitness = 0;
			define_current_weight(&adaline, pso.particles[i]->position);
			for (it = 0; it < 114; ++it) {
				//Configurando a entrada e a saída
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
				adaline_forward(&adaline, &it);
				pso.particles[i]->fitness += pso.fit_function (&adaline);
			}

			if (pso.particles[i]->fitness < old_fit)
				memcpy (pso.particles[i]->p_best, pso.particles[i]->position, sizeof(pso.particles[i]->p_best));
			evaluate_gbest(&pso);
			evaluate_swarm_gbest(&pso);
		}
	}
	// END PSO

	// ADALINE TRAINING
	define_current_weight(&adaline, pso.swarm_g_best);
	adaline_testing(&adaline, training, output);
	if(!ret)	goto CLEANUP;

	printf("\n***End Execution***\n");
	goto CLEANUP;

	CLEANUP:
//		assert(iris_data.header == NULL);
		if (iris_data.header)
			free (iris_data.header);

	return 0;
}

