package com.wms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Warehouse Management System.
 * 
 * This Spring Boot application follows the MVC architecture
 * and demonstrates several design patterns including:
 * - Singleton Pattern (DatabaseConnection)
 * - Factory Method Pattern (ReportFactory)
 * - Observer Pattern (Inventory stock alerts)
 * - Repository/DAO Pattern (Data Access Objects)
 * - MVC Pattern (Spring Boot Controllers + Thymeleaf Views)
 * 
 * @author Blaise BYIRINGIRO
 * @version 1.0
 *          Case Study: Magerwa Ltd, Rwanda
 */
@SpringBootApplication
public class WmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(WmsApplication.class, args);
        System.out.println("===========================================");
        System.out.println(" Magerwa WMS is running!");
        System.out.println(" Open: http://localhost:8085");
        System.out.println("===========================================");
    }
}
