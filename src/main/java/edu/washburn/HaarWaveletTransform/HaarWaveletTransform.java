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
	
	public static double[] mirrorToPowerOfTwo(double[] input) {
		// If input.length is 0, what are you even doing here?
		if(input.length == 0) {
			return input;
		}
		// Checks if input.length is power of two
		// Refer, http://en.wikipedia.org/wiki/Power_of_two#Fast_algorithm_to_check_if_a_positive_number_is_a_power_of_two
		if((input.length & (input.length - 1)) == 0) {
			return input;
		}
		//Need to get the next power of two, that is the next integer n > input.length such that n = 2^k for some positive
		//integer k
		int powerOfTwo = 1;
		while(powerOfTwo < input.length) {
			powerOfTwo <<= 1;
		}
		double[] output = new double[powerOfTwo];
		System.arraycopy(input, 0, output, 0, input.length);
		//Now we can mirror
		for(int i = input.length, j = input.length - 1; i < powerOfTwo; i++, j--) {
			output[i] = input[j];
		}
		
		return output;
	}
}