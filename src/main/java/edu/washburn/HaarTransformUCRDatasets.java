package edu.washburn;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import edu.washburn.HaarWaveletTransform.HaarWaveletTransform;

import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffSaver;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class HaarTransformUCRDatasets {
	public static void main(String[] args) throws Exception {
		if(args.length != 1) {
			System.out.println("\nUsage: HaarTransformUCRDatasets <directory>");
			System.exit(1);
		}
		
		File newBaseDirectory = new File("UCR_ARFF_Haar");
		if(!newBaseDirectory.exists()) {
			try {
				newBaseDirectory.mkdir();
			} catch(SecurityException se) {
				System.out.println(se.getMessage());
			}
		}
		else {
			System.out.println("\nPlease delete/move directory $PWD/UCR_ARFF_Haar and rerun.");
			System.exit(1);
		}
		
		ArrayList<File> datasets = new ArrayList<File>();
		getFiles(args[0], datasets);
		for(File dataset : datasets) {
			String[] partsOfPath = dataset.getCanonicalPath().split("/");
			String currentDirectory = partsOfPath[partsOfPath.length - 2];
			File subDirectory = new File(newBaseDirectory, currentDirectory);
			if(!subDirectory.exists()) {
				subDirectory.mkdir();
			}
			
			ArffLoader loader = new ArffLoader();
			loader.setSource(dataset);
			Instances data = loader.getDataSet();
			data.setClassIndex(0);
			
			int numAttributesAfterTransformation = (data.numAttributes() - 1) / (int) Math.pow(2, 3);
			
			for(int i = 0; i < data.numInstances(); i++) {
				double classValue = data.get(i).classValue();
				double[] haarTransformedData = 
						Arrays.copyOfRange(HaarWaveletTransform.discreteHaarWaveletTransform(Arrays.copyOfRange(data.get(i).toDoubleArray(), 1, data.get(i).toDoubleArray().length), 3), 0, numAttributesAfterTransformation);
				double[] haarTransformedDataWithClass = new double[haarTransformedData.length + 1];
				haarTransformedDataWithClass[0] = classValue;
				for(int ii = 1; ii < haarTransformedDataWithClass.length; ii++) {
					haarTransformedDataWithClass[ii] = haarTransformedData[ii - 1];
				}
				
				data.set(i, new DenseInstance(1.0, haarTransformedDataWithClass));
			}
			
			// We are reducing the number of attributes by 2^resolution, where resolution is 3 in this case
			Remove remove = new Remove();
			remove.setAttributeIndices(new String("first-" + (numAttributesAfterTransformation + 1)));
			remove.setInvertSelection(true);
			remove.setInputFormat(data);
			data = Filter.useFilter(data, remove);
			
			ArffSaver saver = new ArffSaver();
		    saver.setInstances(data);
		    saver.setFile(new File(subDirectory, dataset.getName() + ".arff"));
		    saver.setDestination(new File(subDirectory, dataset.getName() + ".arff"));
		    saver.writeBatch();
		}
	}
	
	public static void getFiles(String directoryName, ArrayList<File> files) {
	    File directory = new File(directoryName);

	    // get all the files from a directory
	    ArrayList<File> fileList = new ArrayList<File>(Arrays.asList(directory.listFiles()));
	    for (File file : fileList) {
	        if (file.isFile()) {
	            files.add(file);
	        } else if (file.isDirectory()) {
	        	getFiles(file.getAbsolutePath(), files);
	        }
	    }
	}
	
}
