/*
 * data.h
 *
 *  Created on: 14/12/2012
 *      Author: carlos
 */

#ifndef RN_H_
#define RN_H_

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>

struct data;
struct training_data;

struct data {
	char* header;
	double sepalLenght[114], sepalWidth[114], petalLenght[114], petalWidth[114], class[114];
};

struct training_data {
	char* header;
	double sepalLenght[36], sepalWidth[36], petalLenght[36], petalWidth[36], class[36];
};

void load_data(FILE *file, struct data *data);

#endif /* DATA_H_ */
