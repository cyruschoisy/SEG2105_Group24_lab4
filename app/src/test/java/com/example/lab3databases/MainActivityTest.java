package com.example.lab3databases;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class MainActivityTest {

    @Test
    public void testParameterizedConstructor() {
        // Creates a product using the constructor with parameters.
        Product newProduct = new Product("Laptop", 1250.50);

        assertEquals("Product name incorrect.", "Laptop", newProduct.getProductName());
    }

    @Test
    public void testGetAndSetId() {
        Product newProduct = new Product("Laptop", 1250.50);

        // Sets the ID and then gets it to verify it was set correctly.
        newProduct.setId(101);
        assertEquals("getId() should return the value set by setId().", 101, newProduct.getId());
    }

    @Test
    public void testGetAndSetIdFAIL() {
        Product newProduct = new Product("Laptop", 1250.50);

        // Sets the ID and then gets it to verify it was set correctly.
        newProduct.setId(101);
        assertEquals("getId() should return the value set by setId().", 1001, newProduct.getId());
    }



}