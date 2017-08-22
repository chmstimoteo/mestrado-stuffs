#include <stdint.h>
#include <string.h>
#include "operators.h"
#include "pso.h"

void evaluate_gbest (struct pso *pso) {

	double g_best[NUMBER_OF_DIMENSIONS];
	double fit;
	int i, j;

	for (i = 0; i < pso->particle_num; i++) {

		memcpy (g_best, pso->particles[i]->g_best, sizeof(g_best));
		fit = pso->particles[i]->fitness;

		for (j = 0; j < pso->particles[i]->neighbor_num; j++) {
			if (pso->particles[i]->neighborhood[j]->fitness < fit) {
				memcpy (g_best, pso->particles[i]->neighborhood[j]->position, sizeof(g_best));
				fit = pso->particles[i]->neighborhood[j]->fitness;
			}
		}

		memcpy (pso->particles[i]->g_best, g_best, sizeof(g_best));
	}

}

void evaluate_swarm_gbest (struct pso *pso) {
	int i;

	for (i = 0; i < pso->particle_num; i++) {
		if (pso->particles[i]->fitness < pso->swarm_fitness) {
			pso->swarm_fitness = pso->particles[i]->fitness;
			memcpy (pso->swarm_g_best, pso->particles[i]->g_best, sizeof(pso->swarm_g_best));
		}
	}
}

// TODO: Faz sentido?
void evaluate_pbest (struct pso *pso) {

}

