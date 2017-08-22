import java.io.File;
import java.util.Iterator;
import java.util.Random;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;
import weka.filters.unsupervised.instance.Resample;


public class LibSVM_Experiment {

	public static void main(String[] args) {
		Instances peril, peril_train, peril_crossvalidation, peril_test;
		Instances blackswam, blackswam_train, blackswam_crossvalidation, blackswam_test;
		Random random = new Random();
		random.setSeed(System.currentTimeMillis());
		try {
			//Loading dataset
			blackswam = DataSource.read("blackswam_gamma.arff");
			peril = DataSource.read("peril_gamma.arff");
			//Numerical
			//numerical_impact(peril);
			//numerical_impact(blackswam);
			//blackswam = DataSource.read("blackswam_gamma_nominal.arff");
			//Setting class {true, false}
			if(peril.classIndex() == -1) 
				peril.setClassIndex(peril.numAttributes()-1);	
			if(blackswam.classIndex() == -1)
				blackswam.setClassIndex(blackswam.numAttributes()-1);
			//Ramdomize dataset
			peril.randomize(random);
			blackswam.randomize(random);
			//Split dataset
			int trainSize = 325;
			int testSize = 162;
			peril_train = new Instances(peril, 0, trainSize);
			peril_crossvalidation = new Instances(peril, 50, testSize);
			peril_test = new Instances(peril,75, testSize);
			trainSize = 63;
			testSize = 32; 
			blackswam_train = new Instances(blackswam, 0, trainSize);
			blackswam_crossvalidation = new Instances(blackswam, 50, testSize);
			blackswam_test = new Instances(blackswam,75, testSize);
			//Salvando em arquivos
			ArffSaver saver = new ArffSaver();
			saver.setInstances(peril_train);
			saver.setFile(new File("./peril_train.arff"));
			saver.writeBatch();
			saver.setInstances(peril_test);
			saver.setFile(new File("./peril_test.arff"));
			saver.writeBatch();
			saver.setInstances(peril_crossvalidation);
			saver.setFile(new File("./peril_crossvalidation.arff"));
			saver.writeBatch();
			saver = new ArffSaver();
			saver.setInstances(blackswam_train);
			saver.setFile(new File("./blackswam_train.arff"));
			saver.writeBatch();
			saver.setInstances(blackswam_test);
			saver.setFile(new File("./blackswam_test.arff"));
			saver.writeBatch();
			saver.setInstances(blackswam_crossvalidation);
			saver.setFile(new File("./blackswam_crossvalidation.arff"));
			saver.writeBatch();
			//Previsao dos dados
			WLSVM lsvm = new WLSVM();
			String seed = String.valueOf(random.nextInt());
			String opts[] =
				{
					new String("-t"),
					new String("peril_train.arff"),
					new String("-T"),
					new String("peril_crossvalidation.arff"),
					new String("-S"),
					new String("3"),  //0 = C-SVC, 1 = nu-SVC, 2 = one-class SVM, 3 = epsilon-SVR, 4 = nu-SVR
					new String("-K"),
					new String("0"), //0 = linear: u'*v, 1 = polynomial: (gamma*u'*v + coef0)^degree, 2 = radial basis function: exp(-gamma*|u-v|^2), 3 = sigmoid: tanh(gamma*u'*v + coef0)
					new String("-D"),
					new String("3"), 
					new String("-G"),
					new String("0.0"),
					new String("-R"),
					new String("0.0"),
					new String("-N"),
					new String("0.5"),
					new String("-M"),
					new String("40.0"),
					new String("-C"),
					new String("1.0"),
					new String("-E"),
					new String("0.001"),
					new String("-P"),
					new String("0.1"),
					new String("-H"),
					new String("0")
				};
			
			WLSVM.runClassifier(lsvm, opts);
			
			Iterator<Instance> it = peril_test.iterator();
			double EMA = 0.0;
			double n = peril_test.size();
			while (it.hasNext()) {
				Instance instance = (Instance)it.next();
				double wished = instance.classValue();
				double calculated = lsvm.classifyInstance(instance);
				System.out.println(wished +";"+ calculated);
				EMA += (Math.abs(wished - calculated) / Math.abs(wished));
			}
			EMA = EMA/n;
			System.out.println("EMA Test = " + EMA);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void numerical_impact(Instances peril) throws Exception {
		//Setting numeric impact to Nominal
		String options[] =
			{
				new String("-R"),
				new String("last")
			};
		NumericToNominal num2nominal = new NumericToNominal();
		num2nominal.setInputFormat(peril);
		num2nominal.setOptions(options);
		Instances peril_nominal = Filter.useFilter(peril, num2nominal);


		ArffSaver saver = new ArffSaver();
		saver.setInstances(peril_nominal);
		saver.setFile(new File("./blackswam_gamma_nominal.arff"));
		saver.writeBatch();

	}

	private static Instances sample(Instances peril) throws Exception {
		Resample resample = new Resample();
		String options[] = new String[5];
		options[0] = "-S";
		options[1] = "1";
		options[2] = "-Z";
		options[3] = "50";
		options[4] = "-no-replacement";
		resample.setOptions(options);
		resample.setInputFormat(peril);
		resample.setRandomSeed((int)System.currentTimeMillis());
		return Filter.useFilter(peril, resample);

	}

}









//LibSVM lsvm = new LibSVM();
//String seed = String.valueOf(random.nextInt());
//String opts[] =
//	{
//		new String("-t"),
//		new String("peril_train.arff"),
//		new String("-T"),
//		new String("peril_crossvalidation.arff"),
//		new String("-S"),
//		new String("3"),  //0 = C-SVC, 1 = nu-SVC, 2 = one-class SVM, 3 = epsilon-SVR, 4 = nu-SVR
//		new String("-K"),
//		new String("0"), //0 = linear: u'*v, 1 = polynomial: (gamma*u'*v + coef0)^degree, 2 = radial basis function: exp(-gamma*|u-v|^2), 3 = sigmoid: tanh(gamma*u'*v + coef0)
//		new String("-D"),
//		new String("3"), 
//		new String("-G"),
//		new String("0.0"),
//		new String("-R"),
//		new String("0.0"),
//		new String("-N"),
//		new String("0.5"),
//		new String("-M"),
//		new String("40.0"),
//		new String("-C"),
//		new String("1.0"),
//		new String("-E"),
//		new String("0.001"),
//		new String("-P"),
//		new String("0.1"),
//		new String("-H"),
//		new String("-V"),
//		new String("-model"),
//		new String("C:\\Program Files\\Weka-3-7"),
//		new String("-seed"),
//		seed,
//	};
//lsvm.setOptions(opts);
//LibSVM.runClassifier(lsvm, opts);
//Evaluation.evaluateModel((LibSVM)lsvm,opts);

//Iterator<Instance> it = peril_test.iterator();
//double EMA = 0.0;
//double n = peril_test.size();
//while (it.hasNext()) {
//	Instance instance = (Instance)it.next();
//	double wished = instance.classValue();
//	double calculated = lsvm.classifyInstance(instance);
//	EMA += Math.abs(wished - calculated);
//}
//EMA = EMA/n;
//System.out.println("EMA Test = " + EMA);