/*
 * sphere.c
 *
 *  Created on: 06/05/2013
 *      Author: carlos
 */
#include "sphere.h"
#include "pso.h"

uint32_t use_counter;

double sphere (struct pso *pso, double *v) {
	double ret;
	uint32_t i;

	use_counter ++;

	ret = 0.0f;
	for (i = 0; i < NUMBER_OF_DIMENSIONS; i++)
		ret += v[i]*v[i];

	return ret;
}

