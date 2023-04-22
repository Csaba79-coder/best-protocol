package com.csaba79coder.bestprotocol.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.assertThat;

class ImageUtilTest {
    
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