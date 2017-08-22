/*
 * rosenbrock.c
 *
 *  Created on: 06/05/2013
 *      Author: carlos
 */
#include "rosenbrock.h"
#include "pso.h"

uint32_t use_counter;

double rosenbrock (struct pso *pso, double *v) {
	double ret;
	uint32_t i;

	use_counter ++;

	ret = 0.0f;
	for (i = 0; i < NUMBER_OF_DIMENSIONS-1; i++)
		ret += (100*pow((v[i+1]-pow(v[i],2)),2)+pow((v[i]-1),2));

	return ret;
}
