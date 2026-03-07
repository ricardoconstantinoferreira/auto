package com.ferreira.auto.infra.upload;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConfigTest {

    @Test
    void uploadsConstantIsStable() {
        assertEquals("uploads", Config.UPLOADS);
    }
}
