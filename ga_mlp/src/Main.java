
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		struct ga_algorithm ga = {
				population: 		30,
				carac_number:		15,
				mutation_index: 	0.01,
				crossover_index: 	0.9,

				fit_function: 		rastrigin_fitness,
				crossover_function:	crossover_one_point,
				mutation_function:	mutation_uniform,

				carac_vector: 		NULL
			};

			float *v, fit, p1;

			int iter, i;

			ga_init (&ga);
			ga_init_vector (&ga, -5.12, 5.12);

			for (iter = 0; iter < 100; iter++) {
				ga_sort(&ga);

				// Imprime o melhor fitness desta iteração
				printf ("%f\n", ga.fit_function (&ga, ga.carac_vector[0]));

				if (ga_generate_random() < ga.crossover_index) {
					v = ga.crossover_function(&ga, ga.carac_vector[0], ga.carac_vector[1]);
					fit = ga.fit_function(&ga, v);

					if (fit < ga.fit_function(&ga, ga.carac_vector[19])) {
						free (ga.carac_vector[19]);
						ga.carac_vector[19] = v;
					}
				}

				for (i = 0; i < ga.population; i++)
					ga.mutation_function(&ga, ga.carac_vector[i], -5.12, 5.12);

			}

			ga_clean (&ga);

			return 0;
		
	}

}
