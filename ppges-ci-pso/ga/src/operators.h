#ifndef OPERATORS_H
#define OPERATORS_H

#include "ga.h"

float * crossover_one_point (struct ga_algorithm *ga, float *ind1, float *ind2);
float * crossover_two_points (struct ga_algorithm *ga, float *ind1, float *ind2);
float * crossover_random_points (struct ga_algorithm *ga, float *ind1, float *ind2);

void mutation_uniform (struct ga_algorithm *ga, float *ind, float lower_bound, float upper_bound);
void mutation_gaussian (struct ga_algorithm *ga, float *ind, float lower_bound, float upper_bound);
void mutation_cauchy (struct ga_algorithm *ga, float *ind, float lower_bound, float upper_bound);

#endif
