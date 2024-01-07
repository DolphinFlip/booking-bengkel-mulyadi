package com.bengkel.booking.services;

import java.util.List;

import com.bengkel.booking.models.BookingOrder;
import com.bengkel.booking.models.Car;
import com.bengkel.booking.models.Customer;
import com.bengkel.booking.models.ItemService;
import com.bengkel.booking.models.Vehicle;

public class PrintService {
	
	public static void printMenu(String[] listMenu, String title) {
		String line = "+---------------------------------+";
		int number = 1;
		String formatTable = " %-2s. %-25s %n";
		
		System.out.printf("%-25s %n", title);
		System.out.println(line);
		
		for (String data : listMenu) {
			if (number < listMenu.length) {
				System.out.printf(formatTable, number, data);
			}else {
				System.out.printf(formatTable, 0, data);
			}
			number++;
		}
		System.out.println(line);
		System.out.println();
	}
	
	public static void printVechicle(List<Vehicle> listVehicle) {
		String formatTable = "| %-2s | %-15s | %-10s | %-15s | %-15s | %-5s | %-15s |%n";
		String line = "+----+-----------------+------------+-----------------+-----------------+-------+-----------------+%n";
		System.out.format(line);
	    System.out.format(formatTable, "No", "Vechicle Id", "Warna", "Brand", "Transmisi", "Tahun", "Tipe Kendaraan");
	    System.out.format(line);
	    int number = 1;
	    String vehicleType = "";
	    for (Vehicle vehicle : listVehicle) {
	    	if (vehicle instanceof Car) {
				vehicleType = "Mobil";
			}else {
				vehicleType = "Motor";
			}
	    	System.out.format(formatTable, number, vehicle.getVehiclesId(), vehicle.getColor(), vehicle.getBrand(), vehicle.getTransmisionType(), vehicle.getYearRelease(), vehicleType);
	    	number++;
	    }
	    System.out.printf(line);
	}
	
	public static void printService(List<ItemService> listAllItemService, String tipe) {
		System.out.println("List Service yang tersedia:");
		String formatTable = "| %-2s | %-15s | %-15s | %-15s | %-15s |%n";
		String line = "+----+-----------------+------------+-----------------+-----------------+-------+-----------------+%n";
		System.out.format(line);
	    System.out.format(formatTable, "No", "Service Id", "Nama Service", "Tipe Kendaraan", "Harga");
	    System.out.format(line);
	    int number = 1;
	    //String vehicleType = "";
	    for (ItemService itemService : listAllItemService) {
	    	if(tipe.equals("Car")) {
	    		if (itemService.getVehicleType().equals("Car")) {
					System.out.format(formatTable, number, itemService.getServiceId(), itemService.getServiceName(), itemService.getVehicleType(), itemService.getPrice());
					number++;
	    		}
	    		
	    	}
	    	else {
	    		if (itemService.getVehicleType().equals("Motorcyle")) {
					System.out.format(formatTable, number, itemService.getServiceId(), itemService.getServiceName(), itemService.getVehicleType(), itemService.getPrice());
					number++;
	    		}		
	    	}
	    	//number++;
	    }
	    System.out.printf(line);
	}

	
	//Silahkan Tambahkan function print sesuai dengan kebutuhan.
	/*
	public static void printAllCustomers(List<Customer> listAllCustomers){
        int num = 1;
        System.out.printf("| %-3s | %-8s | %-8s | %-8s |\n",
                "No.", "ID", "Nama", "Alamat");
        System.out.println("+===================================================================================+");
        for (Customer customer : listAllCustomers) {
        	System.out.printf("| %-3s | %-8s | %-8s | %-8s |\n",
                    num, customer.getCustomerId(), customer.getName(), customer.getAddress());
        	System.out.println("List kendaraan " + customer.getName() + ":");
        	printVechicle(customer.getVehicles());
            num++;
            System.out.println();
        }
    }*/
	
	public static void printBookingOrderList(List<BookingOrder> listBooking) {
	    String formatTable = "| %-2s | %-20s | %-20s | %-15s | %-15s | %-50s |%n";
	    String line = "+----+----------------------+----------------------+-----------------+-----------------+----------------------------------------------------+%n";
	    System.out.format(line);
	    System.out.format(formatTable, "No", "Booking Id", "Nama Customer", "Payment Method", "Total Service", "List Service");
	    System.out.format(line);
	    int number = 1;
	    for (BookingOrder bookingOrder : listBooking) {
	        System.out.format(formatTable, number, bookingOrder.getBookingId(), bookingOrder.getCustomer().getName(), 
	        		bookingOrder.getPaymentMethod(), bookingOrder.getTotalServicePrice(), 
	        		printServiceList(bookingOrder.getServices()));
	        number++;
	    }
	    System.out.printf(line);
	}
	
	public static String printServiceList(List<ItemService> serviceList) {
        String result = "";
        // Bisa disesuaikan kembali
        for (ItemService itemService : serviceList) {
            result += itemService.getServiceName() + ", ";
        }
        return result;
    }
	
}
