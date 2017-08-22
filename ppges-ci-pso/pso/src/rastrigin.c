#include "rastrigin.h"
#include "pso.h"

uint32_t use_counter;
const double A = 10.0f;

double rastrigin (struct pso *pso, double *v) {
	double ret;
	uint32_t i;

	use_counter ++;

	ret = 0.0f;
	for (i = 0; i < NUMBER_OF_DIMENSIONS; i++)
		ret += (v[i]*v[i] - A*cos(2.0f * M_PI * v[i]));

	ret += A*NUMBER_OF_DIMENSIONS;

	return ret;
}

