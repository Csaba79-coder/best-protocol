package com.csaba79coder.bestprotocol.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.assertThat;

/**
 * This class contains test cases for the ImageUtil class.
 */
class ImageUtilTest {

    /**
     * This test provide a JUnit test for ImageUtil class.
     * Tests the compressImage and decompressImage methods of the ImageUtil class.
     *
     * <p>Given a test string, the method first converts it to a byte array. The byte array is then compressed
     * using the compressImage method and decompressed using the decompressImage method. The resulting decompressed
     * byte array is converted back to a string and compared with the original test string to ensure that the
     * compression and decompression operations did not modify the data.</p>
     *
     * <p>The test asserts that the compressed data is not equal to the original data, and that the decompressed
     * string is equal to the original string.</p>
     */
    @Test
    @DisplayName("testCompressAndDecompressImage")
    void testCompressAndDecompressImage(){

        // Given
        String testString = "Hello, world!";
        byte[] testData = testString.getBytes();
        
        // When
        byte[] compressedData = ImageUtil.compressImage(testData);
        byte[] decompressedData = ImageUtil.decompressImage(compressedData);
        String decompressedString = new String(decompressedData);
        
        // Then
        assertThat(compressedData).isNotEqualTo(testData); // the compressed data should be different from the original data
        assertThat(decompressedString).isEqualTo(testString); // the decompressed string should be equal to the original string
    }
}