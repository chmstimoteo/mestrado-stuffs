/*
 * schwefel.h
 *
 *  Created on: 06/05/2013
 *      Author: carlos
 */

#ifndef SCHWEFEL_H_
#define SCHWEFEL_H_

#include <stdint.h>
#include <math.h>
#include "pso.h"

#define SCHWEFEL_LOWER_BOUND -100.00f
#define SCHWEFEL_UPPER_BOUND 100.00f

double schwefel (struct pso *pso, double *v);

extern uint32_t use_counter;

#endif /* SCHWEFEL_H_ */
