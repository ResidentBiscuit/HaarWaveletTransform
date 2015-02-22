package edu.washburn.HaarWaveletTransform;
public class HaarWaveletTransform
{
	public static double[] discreteHaarWaveletTransform(double[] input) {
	    // This function assumes that input.length=2^n, n>1
	    double[] output = new double[input.length];
	 
	    for (int length = input.length >> 1; ; length >>= 1) {
	        // length = input.length / 2^n, WITH n INCREASING to log_2(input.length)
	        for (int i = 0; i < length; ++i) {
	            double sum = input[i * 2] + input[i * 2 + 1];
	            double difference = input[i * 2] - input[i * 2 + 1];
	            output[i] = sum / 2;
	            output[length + i] = difference / 2;
	        }
	        if (length == 1) {
	            return output;
	        }
	 
	        //Swap arrays to do next iteration
	        System.arraycopy(output, 0, input, 0, length << 1);
	    }
	}
}