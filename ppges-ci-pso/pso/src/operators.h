#ifndef OPERATORS_H_
#define OPERATROS_H_

#include "pso.h"

void evaluate_gbest (struct pso *pso);
void evaluate_swarm_gbest (struct pso *pso);
void evaluate_pbest (struct pso *pso);	// TODO: Faz sentido?

#endif

