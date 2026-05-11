package com.wms;

import com.wms.factory.ReportFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Factory Method Pattern (ReportFactory).
 * Verifies that the factory creates the correct report type.
 */
class FactoryPatternTest {

    @Test
    @DisplayName("TC-RPT-001: Factory creates Inventory Report with correct content")
    void testCreateInventoryReport() {
        String report = ReportFactory.createReport(ReportFactory.TYPE_INVENTORY, "admin");

        assertNotNull(report, "Report should not be null");
        assertTrue(report.contains("WAREHOUSE INVENTORY REPORT"),
                "Inventory report should contain correct title");
        assertTrue(report.contains("Total Products"),
                "Report should include total products count");
    }

    @Test
    @DisplayName("TC-RPT-002: Factory creates Order Report with correct content")
    void testCreateOrderReport() {
        String report = ReportFactory.createReport(ReportFactory.TYPE_ORDER, "admin");

        assertNotNull(report);
        assertTrue(report.contains("ORDER SUMMARY REPORT"),
                "Order report should contain correct title");
        assertTrue(report.contains("Pending"),
                "Report should include status breakdown");
    }

    @Test
    @DisplayName("TC-RPT-003: Factory creates Low Stock Report")
    void testCreateLowStockReport() {
        String report = ReportFactory.createReport(ReportFactory.TYPE_LOW_STOCK, "admin");

        assertNotNull(report);
        assertTrue(report.contains("LOW STOCK ALERT REPORT"),
                "Low stock report should contain correct title");
    }

    @Test
    @DisplayName("TC-PAT-003: Factory throws exception for unknown report type")
    void testFactoryThrowsExceptionForUnknownType() {
        assertThrows(IllegalArgumentException.class, () -> {
            ReportFactory.createReport("Unknown Report", "admin");
        }, "Factory should throw exception for unrecognized report type");
    }

    @Test
    @DisplayName("Factory includes generator name in report")
    void testReportContainsGeneratorName() {
        String report = ReportFactory.createReport(ReportFactory.TYPE_INVENTORY, "Alice Uwimana");
        assertTrue(report.contains("Alice Uwimana"),
                "Report should include the name of the person who generated it");
    }
}
