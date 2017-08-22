
#include "ga.h"
#include <stdio.h>
#include <stdint.h>
#include <stdlib.h>
#include <assert.h>
#include <math.h>

float default_fitness(struct ga_algorithm *ga, float *vector) {

	return 0.0f;
}

void ga_sort (struct ga_algorithm *ga) {
	uint32_t i, j;
	float *v, *temp_vector, temp_fitness;

	v = (float *)malloc(sizeof(float)*ga->population);
	assert (v != NULL);

	for (i = 0; i < ga->population; i++)
		v[i] = ga->fit_function(ga, ga->carac_vector[i]);

	for (i = ga->population - 1; i > 0; i--) {
		for (j = 0; j < i; j++) {
			if (v[j] > v[i]) {
				temp_fitness = v[i];
				temp_vector = ga->carac_vector[i];

				v[i] = v[j];
				ga->carac_vector[i] = ga->carac_vector[j];

				v[j] = temp_fitness;
				ga->carac_vector[j] = temp_vector;
			}
		}
	}

	free (v);
	return ;
}

void ga_init (struct ga_algorithm *ga) {

	uint32_t i;

	ga->carac_vector = (float **)malloc(sizeof(float *)*ga->population);
	assert (ga->carac_vector != NULL);

	for (i = 0; i < ga->population; i++) {
		ga->carac_vector[i] = (float *)malloc(sizeof(float)*ga->carac_number);
		assert (ga->carac_vector[i] != NULL);
	}

	return ;
}

void ga_clean (struct ga_algorithm *ga) {

	uint32_t i;

	assert (ga->carac_vector != NULL);

	for (i = 0; i < ga->population; i++) {
		assert (ga->carac_vector[i] != NULL);
		free (ga->carac_vector[i]);
	}

	free (ga->carac_vector);

	return ;
}

void ga_init_vector (struct ga_algorithm *ga, float lower_bound, float upper_bound) {

	uint32_t i, j;

	assert (ga->carac_vector != NULL);

	for (i = 0; i < ga->population; i++) {
		for (j = 0; j < ga->carac_number; j++) {
			ga->carac_vector[i][j] = ((upper_bound - lower_bound) * ga_generate_random() + lower_bound);
		}
	}

	return ;
}

void ga_print_vector (struct ga_algorithm *ga) {

	uint32_t i, j;

	assert (ga->carac_vector != NULL);

	for (i = 0; i < ga->population; i++) {
		printf ("ga->carac_vector[%u] = [ ", i);
		for (j = 0; j < ga->carac_number; j++) {
			printf ("%.4f ", ga->carac_vector[i][j]);
		}
		printf ("] - FITNESS: %.4f\n", ga->fit_function(ga, ga->carac_vector[i]));
	}

	return;
}

float ga_generate_random (void) {

	return ((float)random()/(float)RAND_MAX);
}

