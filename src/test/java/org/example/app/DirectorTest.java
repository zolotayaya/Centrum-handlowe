package org.example.app;

import org.example.dao.*;
import org.example.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DirectorTest {
    private PrintStream originalOut;
    private InputStream originalIn;

    @BeforeEach
    void setUp() {
        originalOut = System.out;
        originalIn = System.in;
        Window.setTestMode(true);
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
        Window.setTestMode(false);
    }
    @Test
    void testShowEmployees_displaysCorrectOutput() throws SQLException {
        Manager mockManager = new Manager(1, "Anna", new Department("HR"), 4500, 12, 5);
        Seller mockSeller = new Seller(2, "Bohdan", new Department("Sales"), 120, 13, 4, 2, 3);
        ManagerDB mockManagerDB = mock(ManagerDB.class);
        SellerDB mockSellerDB = mock(SellerDB.class);
        ProductDB mockProductDB = mock(ProductDB.class);
        BrandDB mockBrandDB = mock(BrandDB.class);
        SaleSystem mockSaleSystem = mock(SaleSystem.class);
        ReportingService mockReportingService = mock(ReportingService.class);
        DataExporter mockDataExporter = mock(DataExporter.class);
        DirectorMethods directorMethods = new DirectorMethods(mockManagerDB, mockSellerDB, mockProductDB, mockBrandDB, mockSaleSystem, mockReportingService, mockDataExporter);
        when(mockManagerDB.getManagers()).thenReturn(List.of(mockManager));
        when(mockSellerDB.getSellers()).thenReturn(List.of(mockSeller));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        directorMethods.showEmployees(mockManagerDB, mockSellerDB);

        String output = outContent.toString();
        String cleanOutput = output.replaceAll("\\u001B\\[[;\\d]*[mGKH]", "")
                .replaceAll("\033\\[[;\\d]*[mGKH]", "")
                .replaceAll("\r", "")
                .replaceAll("\033\\[H", "")
                .replaceAll("\033\\[2J", "");

        assertTrue(cleanOutput.contains("List of employees"));
        assertTrue(cleanOutput.contains("Anna"));
        assertTrue(cleanOutput.contains("HR"));
        assertTrue(cleanOutput.contains("4500"));
        assertTrue(cleanOutput.contains("Bohdan"));
    }
    @Test
    void testShowBrands_displaysCorrectOutput() throws SQLException {
        Department department = new Department("Technology");
        Brand brand = new Brand("Apple", department);

        Seller expert1 = new Seller(1, "John Doe", department, 50, 5, 4, 3, 2);
        Seller expert2 = new Seller(2, "Jane Smith", department, 40, 7, 4, 4, 1);
        brand.setExpert(expert1);
        brand.setExpert(expert2);

        Product product1 = new Product("iPhone 15", 999,12, "Phone",brand,1);
        Product product2 = new Product("MacBook Pro", 1999,13 ,"Mac",brand,2);
        brand.addProduct(product1);
        brand.addProduct(product2);
        ManagerDB mockManagerDB = mock(ManagerDB.class);
        SellerDB mockSellerDB = mock(SellerDB.class);
        ProductDB mockProductDB = mock(ProductDB.class);
        BrandDB mockBrandDB = mock(BrandDB.class);
        SaleSystem mockSaleSystem = mock(SaleSystem.class);
        ReportingService mockReportingService = mock(ReportingService.class);
        DataExporter mockDataExporter = mock(DataExporter.class);
        DirectorMethods directorMethods = new DirectorMethods(mockManagerDB, mockSellerDB, mockProductDB, mockBrandDB, mockSaleSystem, mockReportingService, mockDataExporter);

        when(mockBrandDB.getBrands()).thenReturn(List.of(brand));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        directorMethods.showBrands(mockBrandDB);

        String cleanOutput = outContent.toString()
                .replaceAll("\\u001B\\[[;\\d]*[mGKH]", "")
                .replaceAll("\r", "");

        assertTrue(cleanOutput.contains("Brands"));
        assertTrue(cleanOutput.contains("▶ Apple"));
        assertTrue(cleanOutput.contains("(Department: Technology)"));

        assertTrue(cleanOutput.contains("Experts:"));
        assertTrue(cleanOutput.contains("    • John Doe (Experience: 2 years, Rating: 3,0)"));
        assertTrue(cleanOutput.contains("    • Jane Smith (Experience: 1 years, Rating: 4,0)"));

        assertTrue(cleanOutput.contains("Products:"));
        assertTrue(cleanOutput.contains("    • iPhone 15 (Price: 999,00, Quantity: 12)"));
        assertTrue(cleanOutput.contains("    • MacBook Pro (Price: 1999,00, Quantity: 13)"));

    }

    }