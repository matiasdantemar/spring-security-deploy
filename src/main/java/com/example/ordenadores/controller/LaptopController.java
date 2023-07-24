package com.example.ordenadores.controller;

import com.example.ordenadores.entity.Laptop;
import com.example.ordenadores.repository.LaptopRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class LaptopController {

    /*cuando se produce un error podemos imprimirlo en los logs, implementar framework de logging que guarda esos datos en un archivo que se lee
    constantemente y se envia a un sistema como Elastic Stack, se guarda en un BD y luego se muestra con un dashboard y se puede ver errores en tiempo real*/
    private final Logger log =  LoggerFactory.getLogger(LaptopController.class); //permite mostrar errores con colores, niveles de error, hora etc

    LaptopRepository laptopRepository;
    public LaptopController(LaptopRepository laptopRepository) {
        this.laptopRepository = laptopRepository;
    }


    /**
     * Busca todas las Laptops que existen en la base de datos (retorna un Lista de Laptops)
     * http://localhost:8080/api/laptops
     * @return
     * @param headers
     */
    //paginacion a findALl() especificar el numero de paginas y la cantidad de elementos por paginas que se mostraran, mejorando el rendimiento
    @GetMapping("/api/laptops")
    public ResponseEntity<List<Laptop>> findAll(@RequestHeader HttpHeaders headers) {
        System.out.println(headers.get("User-Agent")); //User-Agent nos dice quien esta enviando la peticion si firefox con windows, o con linux, postman. python etc
        List<Laptop> laptops = laptopRepository.findAll();
        if (laptops.isEmpty()) {
            log.warn("trying to display an empty list");
            return ResponseEntity.notFound().build();
        } else
            return ResponseEntity.ok(laptops);
    }


    /**
     * Buscar una Laptop por id en la base de datos (retorna un unico elemento)
     * http://localhost:8080/api/laptops/1
     * @param id
     * @param headers
     * @return
     */
    @GetMapping("/api/laptops/{id}")
    public ResponseEntity<Laptop> findOneById(@PathVariable Long id, @RequestHeader HttpHeaders headers){
        System.out.println(headers.get("User-Agent"));
        Optional<Laptop> laptopOpt = laptopRepository.findById(id);
        if(!laptopOpt.isPresent()) {
            log.warn("trying to list by id that doesn't exist");
            return ResponseEntity.notFound().build();
        }else
            return ResponseEntity.ok(laptopOpt.get());
    }


    /**
     * Crea una nueva laptops en la base de datos
     * http://localhost:8080/api/laptops
     * @param laptop
     * @param headers
     * @return
     */
    //validar campos requeridos en la creación, Si faltan campos devolver una respuesta HTTP 400 (Bad Request)
    @PostMapping("/api/laptops")
    public ResponseEntity<Laptop> create(@RequestBody Laptop laptop, @RequestHeader HttpHeaders headers){
        System.out.println(headers.get("User-Agent"));
        if(laptop.getId() != null){
            log.warn("trying to create a laptop with id");
            return ResponseEntity.badRequest().build();
        }
        Laptop result = laptopRepository.save(laptop);
        return ResponseEntity.ok(result);
    }


    /**
     * Actualiza una laptop existente de la base de datos
     * http://localhost:8080/api/laptops
     * @param laptop
     * @param headers
     * @return
     */
    //validar que solo se actualicen los cambios que realice y no todos los campos
    @PutMapping("/api/laptops")
    public ResponseEntity<Laptop> update(@RequestBody Laptop laptop, @RequestHeader HttpHeaders headers){
        System.out.println(headers.get("User-Agent"));
        if(laptop.getId() == null){
            log.warn("trying to update with null id");
            return ResponseEntity.badRequest().build();
        }
        if(!laptopRepository.existsById(laptop.getId())){
            log.warn("trying to update id that doesn't exist");
            return ResponseEntity.notFound().build();
        }
        Laptop result = laptopRepository.save(laptop);
        return ResponseEntity.ok(result);
    }


    /**
     * Elimina una laptop existente de la base de datos
     * http://localhost:8080/api/laptops/1
     * @param id
     * @param headers
     * @return
     */
    @DeleteMapping("/api/laptops/{id}")
    public ResponseEntity<Laptop> delete(@PathVariable Long id, @RequestHeader HttpHeaders headers){
        System.out.println(headers.get("User-Agent"));
        if(!laptopRepository.existsById(id)){
            log.warn("trying to remove id that doesn't exist");
            return ResponseEntity.notFound().build();
        }
        laptopRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    /**
     * Elimina todos las laptopos los registros de la base de datos
     * http://localhost:8080/api/laptops
     * @return
     * @param headers
     */
    @DeleteMapping("/api/laptops")
    public ResponseEntity<String> deleteAll(@RequestHeader HttpHeaders headers){
        System.out.println(headers.get("User-Agent"));
        if(laptopRepository.count() == 0){
            log.warn("trying to remove empty list");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron registros para eliminar");
        }
        laptopRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
