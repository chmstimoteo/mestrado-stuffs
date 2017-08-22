#ifndef GA_H
#define GA_H

#include <stdint.h>

struct ga_algorithm;

struct ga_algorithm {
	uint32_t population;
	uint32_t carac_number;
	float mutation_index;
	float crossover_index;

	float (*fit_function)(struct ga_algorithm *ga, float *vector);
	float *(*crossover_function)(struct ga_algorithm *ga, float *ind1, float *ind2);
	void (*mutation_function)(struct ga_algorithm *ga, float *ind, float lower_bound, float upper_bound);

	float **carac_vector;
};

float default_fitness(struct ga_algorithm *ga, float *vector);
void ga_init (struct ga_algorithm *ga);
void ga_clean (struct ga_algorithm *ga);
void ga_init_vector (struct ga_algorithm *ga, float lower_bound, float upper_bound);
void ga_print_vector (struct ga_algorithm *ga);
float ga_generate_random(void);
void ga_sort (struct ga_algorithm *ga);
#endif

