package com.bengkel.booking.services;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

import com.bengkel.booking.models.Customer;
import com.bengkel.booking.models.MemberCustomer;
import com.bengkel.booking.models.ItemService;
import com.bengkel.booking.models.BookingOrder;
import com.bengkel.booking.models.Vehicle;
import com.bengkel.booking.models.Car;
import com.bengkel.booking.models.Motorcyle;

import com.bengkel.booking.repositories.CustomerRepository;
import com.bengkel.booking.repositories.ItemServiceRepository;

public class MenuService {
	private static List<Customer> listAllCustomers = CustomerRepository.getAllCustomer();
	private static List<ItemService> listAllItemService = ItemServiceRepository.getAllItemService();
	private static List<BookingOrder> bookingList = new ArrayList<>();
	private static Scanner input = new Scanner(System.in);

	// private static String currentAccount = "";
	public static void run() {
		boolean isLooping = true;
		do {
			login();
			mainMenu();
		} while (isLooping);
	}

	public static void login() {
		int attempt = 0;
		System.out.printf("1.Login\n0.Exit\n");
		System.out.print("Input: ");
		int masuk = Integer.parseInt(input.nextLine());
		
		while (masuk != 1 && masuk != 0) {
			System.out.println("Perintah invalid. (1.Login 0.Exit)");
			System.out.print("Input: ");
			masuk = Integer.parseInt(input.nextLine());
			input.nextLine();
		}
		if (masuk == 0) {
			System.exit(0);
		}
		while(attempt<3){
			System.out.print("\nMasukkan Customer Id : \n");
			String custId = input.nextLine();
			while (BengkelService.login(listAllCustomers, "username", custId) == false) {
				attempt++;
				System.out.println("Customer Id Tidak Ditemukan atau Salah!");
				if(attempt>=3) {
					System.out.println("Terjadi tiga kali kesalahan. Keluar program.");
					System.exit(0);
				}
				System.out.print("\nMasukkan Customer Id : \n");
				custId = input.nextLine();
				
			}
			System.out.print("\nMasukkan Password dari " + custId + " : \n");
			String password = input.nextLine();
			while (BengkelService.login(listAllCustomers, "katasandi", password) == false) {
				attempt++;
				System.out.println("Password yang anda Masukan Salah!");
				if(attempt>=3) {
					System.out.println("Terjadi tiga kali kesalahan. Keluar program.");
					System.exit(0);
				}
				System.out.print("\nMasukkan Password dari " + custId + " : \n");
				password = input.nextLine();
				
			}
			break;
		}
	}

	public static void mainMenu() {
		String[] listMenu = { "Informasi Customer", "Booking Bengkel", "Top Up Bengkel Coin", "Informasi Booking",
				"Logout" };
		int menuChoice = 0;
		boolean isLooping = true;

		do {
			PrintService.printMenu(listMenu, "Booking Bengkel Menu");
			menuChoice = Validation.validasiNumberWithRange("Masukan Pilihan Menu:", "Input Harus Berupa Angka!",
					"^[0-9]+$", listMenu.length - 1, 0);
			System.out.println(menuChoice);

			switch (menuChoice) {
			case 1:
				// panggil fitur Informasi Customer
				BengkelService.infoCustomer(listAllCustomers);
				break;
			case 2:
				// panggil fitur Booking Bengkel
				BengkelService.createBooking(bookingList);

				break;
			case 3:
				// panggil fitur Top Up Saldo Coin
				BengkelService.topUp();
				break;
			case 4:
				// panggil fitur Informasi Booking Order
				PrintService.printBookingOrderList(bookingList);
				break;
			default:
				System.out.println("Logout");
				isLooping = false;
				break;
			}
		} while (isLooping);

	}

	// Silahkan tambahkan kodingan untuk keperluan Menu Aplikasi
}
