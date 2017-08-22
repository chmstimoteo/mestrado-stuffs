/*
 * sphere.h
 *
 *  Created on: 06/05/2013
 *      Author: carlos
 */

#ifndef SPHERE_H_
#define SPHERE_H_

#include <stdint.h>
#include <math.h>
#include "pso.h"

#define SPHERE_LOWER_BOUND -100,00f
#define SPHERE_UPPER_BOUND 100.00f

double sphere (struct pso *pso, double *v);

extern uint32_t use_counter;

#endif /* SPHERE_H_ */
