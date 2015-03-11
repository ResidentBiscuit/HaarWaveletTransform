package edu.washburn.HaarWaveletTransform;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class MirrorArrayTest {

	@Test
	public void testMirrorArray() {
		double[] input = {1,2,3,4,5};
		double[] expected_output = {1,2,3,4,5,5,4,3};
		
		Assert.assertArrayEquals(expected_output, HaarWaveletTransform.mirrorToPowerOfTwo(input), 0.00001);
	}

}
