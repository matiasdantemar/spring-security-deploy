package com.example.ordenadores.controller;

import com.example.ordenadores.entity.Laptop;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LaptopControllerTest {

    private TestRestTemplate testRestTemplate;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup(){
        restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:" + port);
        testRestTemplate = new TestRestTemplate(restTemplateBuilder);
    }

    
    @Test
    @Order(2)
    void findAll() {
        ResponseEntity<Laptop[]> response =testRestTemplate.getForEntity("/api/laptops",  Laptop[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getStatusCodeValue()); //son lo mismos

        List<Laptop> laptops = Arrays.asList(response.getBody());
        System.out.println(laptops.size());
    }


    @Test
    @Order(3)
    void findOneById() {
        ResponseEntity<Laptop> response =testRestTemplate.getForEntity("/api/laptops/1", Laptop.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    @Order(1)
    void create() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String json = """
                    {
                        "marca": "Lenovo",
                        "modelo": "2015",
                        "precio": 20.1,
                        "capacidadRam": 4,
                        "capacidadAlmacenamiento": 250,
                        "procesador": "i3",
                        "tamañoPantalla": 15.1,
                        "sistemaOperativo": "Windows 8"
                    }
                """;
        HttpEntity<String> request = new HttpEntity<>(json,headers);
        ResponseEntity<Laptop> response = testRestTemplate.exchange("/api/laptops", HttpMethod.POST, request, Laptop.class);
        //lo creo dos veces para que el test deleteById elimine el id 1 y el deleteAll() elimine el id 2 que es el unico que quedaria.
        ResponseEntity<Laptop> response2 = testRestTemplate.exchange("/api/laptops", HttpMethod.POST, request, Laptop.class);

        Laptop result = response.getBody();

        assertEquals(1, result.getId());
        assertEquals("Lenovo", result.getMarca());
    }


    @Test
    @Order(4)
    void update() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String json = """
                {
                    "id": 1,
                    "marca": "Dell",
                    "modelo": "2020",
                    "precio": 30.5,
                    "capacidadRam": 8,
                    "capacidadAlmacenamiento": 500,
                    "procesador": "i5",
                    "tamañoPantalla": 14.0,
                    "sistemaOperativo": "Windows 10"
                }
            """;
        HttpEntity<String> request = new HttpEntity<>(json, headers);
        ResponseEntity<Laptop> response = testRestTemplate.exchange("/api/laptops", HttpMethod.PUT, request, Laptop.class);

        Laptop updatedLaptop = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(updatedLaptop);
        assertEquals(1, updatedLaptop.getId());
        assertEquals("Dell", updatedLaptop.getMarca());
        assertEquals("2020", updatedLaptop.getModelo());
        // Resto de las aserciones para verificar la actualización de los campos deseados
    }


    @Test
    @Order(5)
    void delete() {
        // Realizar la solicitud de eliminación
        ResponseEntity<Void> response = testRestTemplate.exchange("/api/laptops/{id}", HttpMethod.DELETE, null, Void.class, 1);

        // Verificar el código de estado de la respuesta
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }


    @Test
    @Order(6)
    void deleteAll() {
        // Realizar la solicitud de eliminación de todos los laptops
        ResponseEntity<String> response = testRestTemplate.exchange("/api/laptops", HttpMethod.DELETE, null, String.class);

        // Verificar el código de estado de la respuesta
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

}