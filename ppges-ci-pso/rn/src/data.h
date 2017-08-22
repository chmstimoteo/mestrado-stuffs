/*
 * data.h
 *
 *  Created on: 14/12/2012
 *      Author: carlos
 */

#ifndef RN_H_
#define RN_H_

struct data;

struct data {
	char* header;
	double sepalLenght[150], sepalWidth[150], petalLenght[150], petalWidth[150], class[150];
};

#endif /* DATA_H_ */
