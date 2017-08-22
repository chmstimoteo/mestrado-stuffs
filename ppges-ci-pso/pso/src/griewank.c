/*
 * griewank.c
 *
 *  Created on: 06/05/2013
 *      Author: carlos
 */

#include "griewank.h"
#include "pso.h"

uint32_t use_counter;

double griewank (struct pso *pso, double *v) {
	double ret, prod, sum;
	uint32_t i;

	use_counter ++;

	sum = 0.0f;
	prod = 1.0f;
	ret = 0.0f;
	for (i = 0; i < NUMBER_OF_DIMENSIONS; i++) {
		sum += v[i]*v[i];
		prod *= cos(v[i]/ sqrt(i));
	}

	ret = sum/4000 - prod + 1;

	return ret;
}
