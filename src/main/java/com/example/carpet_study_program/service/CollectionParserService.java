package com.example.carpet_study_program.service;


import com.example.carpet_study_program.entity.CarpetProduct;
import com.example.carpet_study_program.repository.CarpetRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class CollectionParserService {
    private final CarpetRepository carpetRepository;

    public CollectionParserService(CarpetRepository carpetRepository) {
        this.carpetRepository = carpetRepository;
    }
    @Transactional
    public void getElementsFromSite() throws IOException {
        log.info("Запуск парсинга каталога amikovry.ru...");

        Document doc = Jsoup.connect("https://amikovry.ru/catalog/?from=menu_catalog").get();
        Elements links = doc.select(".catalog-sections__section-link");

        carpetRepository.deleteAll();
        log.info("Старые данные успешно очищены перед обновлением.");

        for (Element e : links) {
            String link = e.attr("abs:href");
            System.out.println(link);
            getProductFromCollection(link);
        }

        log.info("Парсинг успешно завершен!");
    }

    private void getProductFromCollection(String link) throws IOException {
        Document doc = Jsoup.connect(link).get();
        Element firstProduct = doc.select(".product-tile__image-link").first();
        if (firstProduct == null) {
            return;
        }
        String firstProductLink = firstProduct.attr("abs:href");

        Document firstProductLinkDoc = Jsoup.connect(firstProductLink).get();
        Elements descriptionContainer = firstProductLinkDoc.select(".catalog-product__params-container > div");

        CarpetProduct product = new CarpetProduct();

        for (int i = 0; i < descriptionContainer.size(); i += 2) {
            Element e = descriptionContainer.get(i);
            String paramName = e.text().trim();
            String paramValue = descriptionContainer.get(i + 1).text().trim();

            switch (paramName) {
                case "Бренд":
                    product.setBrand(paramValue);
                    break;
                case "Коллекция":
                    product.setCollection(paramValue);
                    break;
                case "Страна бренда":
                    product.setCountryOfBrand(paramValue);
                    break;
                case "Страна производства":
                    product.setCountryOfManufacture(paramValue);
                    break;
                case "Материал":
                    product.setMaterial(paramValue);
                    break;
                case "Состав ворса":
                    product.setPileComposition(paramValue);
                    break;
                case "Состав основы":
                    product.setCompositionBase(paramValue);
                    break;
                case "Тип производства":
                    product.setTypeProduction(paramValue);
                    break;
                case "Плотность ворса":
                    product.setPileDensity(paramValue);
                    break;
                case "Высота ворса", "Толщина":
                    product.setPileHeightOrThickness(paramValue);
                    break;
                case "Форма":
                    product.setCarpetShape(paramValue);
                    break;
                case "Особенности":
                    product.setFeatures(paramValue);
                    break;
                case "Вес на м2":
                    product.setWeightPerSquareMeter(paramValue);
                    break;
            }
        }
        carpetRepository.save(product);
    }
}
