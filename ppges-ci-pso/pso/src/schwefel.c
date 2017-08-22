/*
 * schwefel.c
 *
 *  Created on: 06/05/2013
 *      Author: carlos
 */
#include "schwefel.h"
#include "pso.h"

uint32_t use_counter;

double schwefel (struct pso *pso, double *v) {
	double ret;
	uint32_t i, j;

	use_counter ++;

	ret = 0.0f;
	for (i = 0; i < NUMBER_OF_DIMENSIONS; i++) {
		for (j = 0; j < i; j++) {
			ret += v[j];
		}
		ret *= ret;
	}

	return ret;
}

