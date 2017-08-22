/*
 * operators.c
 *
 *  Created on: 31/10/2012
 *      Author: cristovao
 */

#include <stdio.h>
#include <stdint.h>
#include <stdlib.h>
#include <assert.h>

#include "ga.h"
#include "operators.h"

float * crossover_one_point (struct ga_algorithm *ga, float *ind1, float *ind2) {
	uint32_t pos, i;
	float *ret;

	pos = random() % ga->carac_number;
	ret = (float *)malloc(sizeof(float)*ga->carac_number);
	assert (ret != NULL);

	for (i = 0; i < pos; ret[i] = ind1[i], i++);
	for (i = pos; i < ga->carac_number; ret[i] = ind2[i], i++);

	return ret;
}

float * crossover_two_points (struct ga_algorithm *ga, float *ind1, float *ind2) {
	uint32_t pos1, pos2, i;
	float *ret;

	pos1 = pos2 = 0;
	while (pos1 >= pos2) {
		pos1 = random() % ga->carac_number;
		pos2 = random() % ga->carac_number;
	}

	ret = (float *)malloc(sizeof(float)*ga->population);
	assert (ret != NULL);

	for (i = 0; i < pos1; ret[i] = ind1[i], i++);
	for (i = pos1; i < pos2; ret[i] = ind2[i], i++);
	for (i = pos2; i < ga->carac_number; ret[i] = ind1[i], i++);

	return ret;
}

float * crossover_random_points (struct ga_algorithm *ga, float *ind1, float *ind2) {
	uint32_t i;
	float *ret;

	ret = (float *)malloc(sizeof(float)*ga->carac_number);
	assert (ret != NULL);

	for (i = 0; i < ga->carac_number; i++) {
		if (random() % 2)
			ret[i] = ind1[i];
		else
			ret[i] = ind2[i];
	}

	return ret;
}

void mutation_uniform (struct ga_algorithm *ga, float *ind, float lower_bound, float upper_bound)
{
	uint32_t i;
	float r, p_m;

	if (ga->mutation_index < 0)
		return;

	p_m = 1.0f/(float)ga->population;
	for (i = 0; i < ga->carac_number; i++) {
		r = ga_generate_random();
		if (r < p_m)
			ind[i] = (upper_bound - lower_bound) * ga_generate_random() + lower_bound;
	}

}

void mutation_gaussian (struct ga_algorithm *ga, float *ind, float lower_bound, float upper_bound)
{
	// TODO: Implementar
	return ;
}

void mutation_cauchy (struct ga_algorithm *ga, float *ind, float lower_bound, float upper_bound)
{
	// TODO: Implementar
	return ;
}

