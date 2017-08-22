/*
 * rosenbrock.h
 *
 *  Created on: 06/05/2013
 *      Author: carlos
 */

#ifndef ROSENBROCK_H_
#define ROSENBROCK_H_

#include <stdint.h>
#include "pso.h"

#define ROSENBROCK_LOWER_BOUND -30.00f
#define ROSENBROCK_UPPER_BOUND 30.00f

double rosenbrock (struct pso *pso, double *v);

extern uint32_t use_counter;

#endif /* ROSENBROCK_H_ */
