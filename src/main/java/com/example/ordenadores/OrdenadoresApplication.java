package com.example.ordenadores;

import com.example.ordenadores.entity.Laptop;
import com.example.ordenadores.repository.LaptopRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class OrdenadoresApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(OrdenadoresApplication.class, args);
		LaptopRepository repository = context.getBean(LaptopRepository.class);

		Laptop laptop1= new Laptop(null, "Hp", "2023", 50.5, 8, 250, "i5", 15.5, "Windows 10");
		Laptop laptop2= new Laptop(null, "Pavilon", "2020", 71.3, 16, 500, "i7", 17.0, "Windows 11");
		repository.save(laptop1);
		repository.save(laptop2);
		System.out.println("num libros en BD: " + repository.findAll().size());
	}
}
