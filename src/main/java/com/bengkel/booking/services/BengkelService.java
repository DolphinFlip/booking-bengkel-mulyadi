package com.bengkel.booking.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.bengkel.booking.models.Customer;
import com.bengkel.booking.models.MemberCustomer;
import com.bengkel.booking.models.ItemService;
import com.bengkel.booking.models.BookingOrder;
import com.bengkel.booking.models.Vehicle;
import com.bengkel.booking.models.Car;
import com.bengkel.booking.models.Motorcyle;

import com.bengkel.booking.repositories.CustomerRepository;
import com.bengkel.booking.repositories.ItemServiceRepository;

public class BengkelService {
	private static List<Customer> listAllCustomers = CustomerRepository.getAllCustomer();
	private static List<ItemService> listAllItemService = ItemServiceRepository.getAllItemService();
	private static PrintService printService = new PrintService();
	private static Scanner input = new Scanner(System.in);
	private static String currentAccount = "";
	// Silahkan tambahkan fitur-fitur utama aplikasi disini

	// Login
	public static boolean login(List<Customer> listAllCustomers, String tipe, String input) {
		//for (Customer customer : listAllCustomers) {
			if (tipe.equals("username")) {
				Customer customer = findCustomerById(input);
				if(customer == null) {
					return false;
				}
				if (input.equals(customer.getCustomerId())) {
					currentAccount = customer.getCustomerId();
					return true;
				}
				return false;
			} else if (tipe.equals("katasandi")) {
				Customer customer = findCustomerById(currentAccount);
				//if (customer.getCustomerId().equals(currentAccount) && input.equals(customer.getPassword())) {
				if(customer.getPassword().equals(input)) {
					return true;
				}
				//}
			}
		//}
		return false;
	}

	// Info Customer
	public static void infoCustomer(List<Customer> listAllCustomers) {
		String membership = "Non Member";
		//for (Customer customer : listAllCustomers) {
			//if (customer.getCustomerId().equals(currentAccount)) {
				Customer customer = findCustomerById(currentAccount);
				MemberCustomer memberCustomer = null;
				if (customer instanceof MemberCustomer) {
		            memberCustomer = (MemberCustomer) customer;
		            System.out.println("Member");
		            membership = "Member";
		            if (memberCustomer != null) {
			            
			        }
		        }
				else {
					System.out.println("Non Member");
					
				}
				System.out.printf("Customer Id: %13s\n",currentAccount);
				System.out.printf("Nama: %19s\n",customer.getName());			
				System.out.printf("Customer Status: %7s\n", membership);
		        System.out.printf("Alamat: %15s\n", customer.getAddress());
		        System.out.printf("Saldo Koin: %15.0f\n", memberCustomer.getSaldoCoin());
		        System.out.println("List Kendaraan:");
				PrintService.printVechicle(customer.getVehicles());
			//}
		//}
	}

	// Booking atau Reservation
	public static void createBooking(List<BookingOrder> bookingList) {
		System.out.println("Masukan Vehicle Id: ");
		String vehicleId = input.nextLine();
		boolean adaVehicle = false;
		String vehicleType="";
		//for (Customer customer : listAllCustomers) {
			//if (customer.getCustomerId().equals(currentAccount)) {
				Customer customer = findCustomerById(currentAccount);
				for(Vehicle vehicle : customer.getVehicles()) { //customer.getVehicles = listVehicle
					if(vehicle.getVehiclesId().equals(vehicleId)) {
						adaVehicle = true;
						vehicleType = vehicle.getVehicleType();
						PrintService.printService(listAllItemService, vehicleType);
					}
				  /*if (vehicle instanceof Car) {vehicleType = "Mobil";}
					else {vehicleType = "Motor";}*/	
				}
				if(adaVehicle==false) {
					System.out.println("Kendaraan Tidak Ditemukan");
					return;
				}
			//}
		//}
		int maksItem = customer instanceof MemberCustomer? 2 : 1;

		int jmlItem = 0;
		double totalHarga=0;
		List<ItemService> listService = new ArrayList<>();
		//ItemService itemService = null;
		boolean loop = true;
		while(loop) {
			System.out.println("Silahkan masukan Service id:\n");
			String serviceId = input.nextLine();
			//itemService = findServiceById("serviceId");
			for (ItemService itemService : listAllItemService) {
				if(itemService==null) {
					System.out.println("Service tidak ditemukan");
				}
				else if(serviceId.equalsIgnoreCase(itemService.getServiceId())){
					jmlItem++;
					listService.add(itemService);
					totalHarga+=itemService.getPrice();
					if(jmlItem < maksItem) {
						System.out.println("Apakah anda ingin menambah service lainnya? (Y/T)");
						String tmbService = input.nextLine();
						if(tmbService.equalsIgnoreCase("T")){
							loop = false;
						}
					}
					else {
						loop=false;
					}
				}
			}
		}
		double diskon = 0;
		String metode = "";
		
		do {
			System.out.println("Silahkan pilih metode pembayaran (Saldo Coin atau Cash)");
			metode = input.nextLine();
			if(!metode.equalsIgnoreCase("Saldo Coin") && !metode.equalsIgnoreCase("Cash") && metode == "") {
				System.out.println("Metode tidak valid. Silahkan pilih metode lainnya");
			}
			if(metode.equalsIgnoreCase("Saldo Coin")) {
				if (customer instanceof MemberCustomer) {
					diskon = (double)totalHarga*0.9;
				}
				else {
					System.out.println("Metode Saldo Coin hanya untuk member. Silahkan pilih metode lainnya");
				}
			}
		} while (!metode.equalsIgnoreCase("Saldo Coin") && !metode.equalsIgnoreCase("Saldo Coin"));
		
		double totalBayar = (double)totalHarga - diskon;
		String bookingId = "Book-" + String.format("%04d", bookingList.size()+1);

        BookingOrder bookingOrder = new BookingOrder();
	    bookingOrder.setBookingId(bookingId);
	    bookingOrder.setCustomer(customer);
	    bookingOrder.setServices(listService);
	    bookingOrder.setPaymentMethod(metode);
	    bookingOrder.setTotalServicePrice(totalHarga);
	    bookingOrder.calculatePayment();

        bookingList.add(bookingOrder);
        System.out.println("Booking berhasil!!!");
        System.out.println("Total Harga Service : Rp. " + bookingOrder.getTotalServicePrice());
        System.out.println("Total Pembayaran : Rp. " + bookingOrder.getTotalPayment());
        if (customer instanceof MemberCustomer) {
            MemberCustomer memberCustomer = (MemberCustomer)customer;
            memberCustomer.setSaldoCoin(memberCustomer.getSaldoCoin() - totalBayar);
        }

	}
	
	public static void topUp() {
		System.out.println("Masukkan besaran Top Up: ");
		double num = input.nextDouble();
		Customer customer = findCustomerById(currentAccount);
		if (customer instanceof MemberCustomer) {
            MemberCustomer memberCustomer = (MemberCustomer) customer;
            memberCustomer.setSaldoCoin(memberCustomer.getSaldoCoin() + num);
            System.out.println("Jumlah saldo sekarang: " + memberCustomer.getSaldoCoin());
        } 
		else{
            System.out.println("Bukan member");
        }
	}
		/*
		printService.printCustomers(listAllCustomers);
		System.out.println("Input ID customer untuk booking: ");
		String customerId = input.nextLine();

		Customer customer = findCustomerById(customerId);

		while (customer == null) {
			System.out.println("Customer is not available!");
			System.out.println("Enter customer ID that exist for reservation: ");
			customerId = input.nextLine();
			customer = findCustomerById(customerId);
		}*/
	

	// Top Up Saldo Coin Untuk Member Customer

	// Logout

	private static Customer findCustomerById(String customerId) {
		return listAllCustomers.stream().filter(customer -> customer.getCustomerId().equals(customerId)).findFirst()
				.orElse(null);
	}
	
	private static ItemService findServiceById(String serviceId) {
		return listAllItemService.stream().filter(service -> service.getServiceId().equals(serviceId)).findFirst()
				.orElse(null);
	}
	
}
