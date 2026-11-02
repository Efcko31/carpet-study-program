package com.example.carpet_study_program.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "carpets", schema = "carpet_study_program_schema")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarpetProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand")
    private String brand;

    @Column(name = "collection")
    private String collection;

    @Column(name = "country_of_brand")
    private String countryOfBrand;

    @Column(name = "country_of_manufacture")
    private String countryOfManufacture;

    @Column(name = "material")
    private String material;

    @Column(name = "pile_composition")
    private String pileComposition; //Состав ворса ковра

    @Column(name = "composition_base")
    private String compositionBase; //Состав основы

    @Column(name = "type_production")
    private String typeProduction; //тип производства

    @Column(name = "pile_density")
    private String pileDensity; //Плотность ворса

    @Column(name = "pile_height_or_thickness")
    private String pileHeightOrThickness; //Высота ворса или толщина

    @Column(name = "carpet_shape")
    private String carpetShape; //Форма ковра

    @Column(name = "features")
    private String features; //Особенности

    @Column(name = "weight_per_square_meter")
    private String weightPerSquareMeter; // вес на метр вкадратный

}
