package com.anton.url_shortener.logic;

import org.junit.jupiter.api.*;

@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
public class Base62EncoderTest {

    private final Base62Encoder encoder = new Base62Encoder();

    @Test
    // @DisplayName("Basic encoder test")
    void shouldEncodeSpecifiedId() {
        Assertions.assertEquals("a", encoder.encode(0));
        Assertions.assertEquals("z", encoder.encode(25));
    }

    @Test
    // @DisplayName("Full test")
    void shouldDecodeBackToOriginal() {
        long originalId = 99999L;
        String shortCode = encoder.encode(originalId);
        long decodedId = encoder.decode(shortCode);

        Assertions.assertEquals(originalId, decodedId, "Decoded ID should match original");
    }

    @Test
    void shouldHandleZero() {
        String shortCode = encoder.encode(0);
        Assertions.assertEquals("a", shortCode);
        Assertions.assertEquals(0, encoder.decode("a"));
    }

}
