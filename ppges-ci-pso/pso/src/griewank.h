/*
 * griewank.h
 *
 *  Created on: 06/05/2013
 *      Author: carlos
 */

#ifndef GRIEWANK_H_
#define GRIEWANK_H_

#include <stdint.h>
#include <math.h>
#include "pso.h"

#define GRIEWANK_LOWER_BOUND -600.00f
#define GRIEWANK_UPPER_BOUND 600.00f

double griewank (struct pso *pso, double *v);

extern uint32_t use_counter;

#endif /* GRIEWANK_H_ */
