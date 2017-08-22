#ifndef PSO_BASE_H
#define PSO_BASE_H

#include <stdint.h>

#define NUMBER_OF_DIMENSIONS 30
#define NUMBER_OF_ITERATIONS 10000
#define INERTIA_UPPER_BOUND 0.9
#define INERTIA_LOWER_BOUND 0.4

struct pso;
struct particle;

struct pso {
	uint32_t dimension_num;	// TODO: Remover; inútil pois é fixo
	uint32_t particle_num;
	double c1, c2;
	double inertia;

	double swarm_g_best[NUMBER_OF_DIMENSIONS];
	double swarm_fitness;

	struct particle **particles;
	double (*fit_function)(struct pso *pso, double *v);
};

struct particle {
	uint32_t dimension_num;	// TODO: Remover; inútil pois é fixo
	uint32_t neighbor_num;
	double fitness;

	double position[NUMBER_OF_DIMENSIONS];
	double velocity[NUMBER_OF_DIMENSIONS];
	double p_best[NUMBER_OF_DIMENSIONS];
	double g_best[NUMBER_OF_DIMENSIONS];
	struct particle **neighborhood;
};

double default_fitness (const struct pso *pso, const double *v);
void update_inertia(struct pso *pso);
double pso_generate_random (void);
double pso_generate_urandom (void);
void pso_print_vector (const struct pso *pso);
void pso_init_particles (struct pso *pso, double lower_bound, double upper_bound);
void pso_create_particles (struct pso *pso);

void define_neighborhood_global (struct pso *pso);
void define_neighborhood_local (struct pso *pso);
void define_neighborhood_focal (struct pso *pso);

double clerk_constriction_factor();
#endif

