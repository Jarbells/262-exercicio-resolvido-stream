package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Product;

public class Program {

	public static void main(String[] args) {
	
		Locale.setDefault(Locale.US);
		List<Product> productsList = new ArrayList<>(); 
		
		try (Scanner sc = new Scanner(System.in)) {
			System.out.print("Enter full file path: ");
			String path = sc.next();
			
			try (BufferedReader br = new BufferedReader(new FileReader(path))) {
				String line = br.readLine();
				
				while (line != null) {
					String[] fields = line.split(",");
					productsList.add(new Product(fields[0], Double.parseDouble(fields[1])));
					line = br.readLine();
				}
				
				double average = productsList.stream()
						.mapToDouble(Product::getPrice) // .map(p -> p.getPrice) 
						.reduce(0.0, Double::sum) / productsList.size(); // .reduce(0.0, (x,y) -> x + y) / list.size();
				
				if (!productsList.isEmpty()) {
					Comparator<String> comparator = (str1, str2) -> str1.toUpperCase().compareTo(str2.toUpperCase());
					
					System.out.printf("Average price: %.2f%n", average);
					
					List<String> productNames = productsList.stream()
							.filter(x -> x.getPrice() <= average)
							.map(x -> x.getName()).sorted(comparator.reversed()).collect(Collectors.toList());
					productNames.forEach(System.out::println);
				}				
			}
			catch (IOException e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
	}
}
