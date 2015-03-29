package edu.washburn.HaarWaveletTransform;
import org.junit.Assert;
import org.junit.Test;

public class HaarWaveletTransformTest
{
	@Test
	public void transformTest() {
		//Test Haar Wavelet Transform with data provided in Temporal Data Mining, Mitsa (36)
		double[] originalTimeSeries = {1,5,3,9,10,4,2,8};
		double[] expectedTransformedTimeSeries = {5.25,-0.75,-1.5,1,-2,-3,3,-3};
		double[] transformedTimeSeries = HaarWaveletTransform.discreteHaarWaveletTransform(originalTimeSeries, 3);
		Assert.assertArrayEquals(expectedTransformedTimeSeries, transformedTimeSeries, 0.001);
	}
}