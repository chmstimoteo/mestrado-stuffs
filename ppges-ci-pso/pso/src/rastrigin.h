#ifndef RASTRIGIN_H_
#define RASTRIGIN_H_

#include <stdint.h>
#include <math.h>
#include "pso.h"

#define RASTRIGIN_LOWER_BOUND -5.12f
#define RASTRIGIN_UPPER_BOUND 5.12f

double rastrigin (struct pso *pso, double *v);

extern uint32_t use_counter;

#endif

