#include <stdlib.h>
#include <stdio.h>
#include <assert.h>
#include <string.h>
#include "pso.h"
#include "adaline.h"

double default_fitness (const struct pso *pso, const double *v) {

	return 0.0f;
}

double pso_generate_random (void) {

	return ((double)random()/(double)RAND_MAX);
}

void pso_print_vector(const struct pso *pso) {

	uint32_t i, j;
	assert (pso->particles != NULL);

	for (i = 0; i < pso->particle_num; i++) {
		printf ("pso->particles[%u] = [ ", i);
		for (j = 0; j < NUMBER_OF_DIMENSIONS; j++)
			printf ("%.4f ", pso->particles[i]->position[j]);

		printf ("] - FITNESS: %.4f\n", pso->particles[i]->fitness);
	}

	printf ("Fitness do enxame: %lf\n", pso->swarm_fitness);
}

void pso_init_particles (struct pso *pso, double lower_bound, double upper_bound) {

	uint32_t i, j;
	assert (pso->particles != NULL);


	for (i = 0; i < pso->particle_num; i++) {
		for (j = 0; j < NUMBER_OF_DIMENSIONS; j++) {
			pso->particles[i]->position[j] = ((upper_bound - lower_bound) * pso_generate_random() + lower_bound);
			pso->particles[i]->velocity[j] = ((upper_bound/2.0f - lower_bound/2.0f) * pso_generate_random() + lower_bound/2.0f);
		}
		pso->particles[i]->fitness = 10000.0f;

		memcpy (pso->particles[i]->g_best, pso->particles[i]->position, sizeof(pso->particles[i]->g_best));
		memcpy (pso->particles[i]->p_best, pso->particles[i]->position, sizeof(pso->particles[i]->p_best));

		if (i == 0 || (pso->particles[i]->fitness < pso->swarm_fitness)) { 
			pso->swarm_fitness = pso->particles[i]->fitness;
			memcpy (pso->swarm_g_best, pso->particles[i]->position, sizeof(pso->swarm_g_best));
		}
	}
}

void pso_create_particles (struct pso *pso) {

	uint32_t i;
	assert (pso->particles == NULL);

	pso->particles = (struct particle **)malloc(pso->particle_num*sizeof(struct particle *));
	for (i = 0; i < pso->particle_num; i++) {
		pso->particles[i] = (struct particle *)malloc(sizeof(struct particle));
		memset (pso->particles[i], 0x00, sizeof(struct particle));
	}
}

void define_neighborhood_global (struct pso *pso) {

	uint32_t i, j;

	for (i = 0; i < pso->particle_num; i++) {
		pso->particles[i]->neighborhood = (struct particle **)malloc((pso->particle_num)*sizeof (struct particle *));
		for (j = 0; j < pso->particle_num; j++) {

			pso->particles[i]->neighborhood[j] = pso->particles[j];
		}
		pso->particles[i]->neighbor_num = pso->particle_num;
	}
}

void define_neighborhood_local (struct pso *pso) {

	int32_t i;

	for (i = 0; i < pso->particle_num; i++) {
		pso->particles[i]->neighborhood = (struct particle **)malloc(3*sizeof(struct particle *));
		pso->particles[i]->neighborhood[0] = pso->particles[i];
		pso->particles[i]->neighborhood[1] = pso->particles[(i - 1) % pso->particle_num];
		pso->particles[i]->neighborhood[2] = pso->particles[(i + 1) % pso->particle_num];
		pso->particles[i]->neighbor_num = 3;
	}
}

void define_neighborhood_focal (struct pso *pso) {
	return ;
}
