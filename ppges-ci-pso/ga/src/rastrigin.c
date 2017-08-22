/*
 * rastrigin.c
 *
 *  Created on: 31/10/2012
 *      Author: cristovao
 */

#include <math.h>
#include "ga.h"
#include "rastrigin.h"

static const float A = 10.0f;

float rastrigin_fitness (struct ga_algorithm *ga, float *vector) {
	uint32_t i;
	float r = 0.0f;

	for (i = 0; i < ga->carac_number; i++)
		r += (vector[i]*vector[i] - A*cos(2*M_PI*vector[i]));

	r += A*ga->carac_number;

	return r;
}
