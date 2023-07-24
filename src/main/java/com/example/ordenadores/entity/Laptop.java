package com.example.ordenadores.entity;


import jakarta.persistence.*;

@Entity
public class Laptop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String marca;
    private String modelo;
    private Double precio;
    private Integer capacidadRam;
    private Integer capacidadAlmacenamiento;
    private String procesador;
    private Double tamañoPantalla;
    private String sistemaOperativo;

    public Laptop() {
    }

    public Laptop(Long id, String marca, String modelo, Double precio, Integer capacidadRam, Integer capacidadAlmacenamiento, String procesador, Double tamañoPantalla, String sistemaOperativo) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.precio = precio;
        this.capacidadRam = capacidadRam;
        this.capacidadAlmacenamiento = capacidadAlmacenamiento;
        this.procesador = procesador;
        this.tamañoPantalla = tamañoPantalla;
        this.sistemaOperativo = sistemaOperativo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getCapacidadRam() {
        return capacidadRam;
    }

    public void setCapacidadRam(Integer capacidadRam) {
        this.capacidadRam = capacidadRam;
    }

    public Integer getCapacidadAlmacenamiento() {
        return capacidadAlmacenamiento;
    }

    public void setCapacidadAlmacenamiento(Integer capacidadAlmacenamiento) {
        this.capacidadAlmacenamiento = capacidadAlmacenamiento;
    }

    public String getProcesador() {
        return procesador;
    }

    public void setProcesador(String procesador) {
        this.procesador = procesador;
    }

    public Double getTamañoPantalla() {
        return tamañoPantalla;
    }

    public void setTamañoPantalla(Double tamañoPantalla) {
        this.tamañoPantalla = tamañoPantalla;
    }

    public String getSistemaOperativo() {
        return sistemaOperativo;
    }

    public void setSistemaOperativo(String sistemaOperativo) {
        this.sistemaOperativo = sistemaOperativo;
    }
}
