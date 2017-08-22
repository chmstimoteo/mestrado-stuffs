#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "pso.h"
#include "operators.h"
#include "rastrigin.h"
#include "griewank.h"
#include "rosenbrock.h"
#include "schwefel.h"
#include "sphere.h"

int main (int argc, char ** argv) {

	struct pso pso = {
		particle_num: 30,
		c1: 2.05f,
		c2: 2.05f,
		inertia: 0.8f,
		particles:    NULL,
		fit_function: sphere
	};

	uint32_t i, j;
	double phi1, phi2, old_fit;

	pso_create_particles (&pso);
	pso_init_particles (&pso, RASTRIGIN_LOWER_BOUND, RASTRIGIN_UPPER_BOUND);

	//Definir a vizinhança
	//define_neighborhood_focal(&pso);
	//define_neighborhood_local(&pso);
	define_neighborhood_global(&pso);

	while (use_counter < NUMBER_OF_ITERATIONS) {
		//Decaimento do fator de inercia
		//update_inertia(&pso);
		for (i = 0; i < pso.particle_num; i++) {
			phi1 = pso_generate_urandom(); phi2 = pso_generate_urandom();
			for (j = 0; j < NUMBER_OF_DIMENSIONS; j++) {
				pso.particles[i]->velocity[j] +=
						(pso.inertia*pso.particles[i]->velocity[j] +
						 phi1 * pso.c1 * (pso.particles[i]->p_best[j] - pso.particles[i]->position[j]) +
						 phi2 * pso.c2 * (pso.particles[i]->g_best[j] - pso.particles[i]->position[j]));//*clerk_constriction_factor();

				if (pso.particles[i]->velocity[j] > RASTRIGIN_UPPER_BOUND/2)
					pso.particles[i]->velocity[j] = RASTRIGIN_UPPER_BOUND/2;
				else if (pso.particles[i]->velocity[j] < RASTRIGIN_LOWER_BOUND/2)
					pso.particles[i]->velocity[j] = RASTRIGIN_LOWER_BOUND/2;
			}

			for (j = 0; j < NUMBER_OF_DIMENSIONS; j++) {
				pso.particles[i]->position[j] += pso.particles[i]->velocity[j];

				if (pso.particles[i]->position[j] > RASTRIGIN_UPPER_BOUND ||
					pso.particles[i]->position[j] < RASTRIGIN_LOWER_BOUND)
					pso.particles[i]->velocity[j] *= -1.0f;
			}

			old_fit = pso.particles[i]->fitness;
			pso.particles[i]->fitness = pso.fit_function (&pso, pso.particles[i]->position);

			if (pso.particles[i]->fitness < old_fit)
				memcpy (pso.particles[i]->p_best, pso.particles[i]->position, sizeof(pso.particles[i]->p_best));

			evaluate_gbest(&pso);
			evaluate_swarm_gbest(&pso);
		}
		if (use_counter % 200 == 0) {
			printf ("%u, %lf\n", use_counter, pso.swarm_fitness);
		}
	}

	return 0;
}

