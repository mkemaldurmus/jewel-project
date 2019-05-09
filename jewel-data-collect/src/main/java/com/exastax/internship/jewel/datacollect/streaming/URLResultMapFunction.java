package com.exastax.internship.jewel.datacollect.streaming;

import com.exastax.internship.jewel.core.JewelItem;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.spark.api.java.function.Function;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.URL;

public class URLResultMapFunction implements Function<ConsumerRecord<String, String>, JewelItem> {

    @Override
    public JewelItem call(ConsumerRecord<String, String> record) throws Exception {
        final URL productURL = new URL(record.value());
        final Integer id = Integer.parseInt(record.key());
        final Document document = Jsoup.parse(productURL, 5000);

        /*
            Collect main attributes for product
         */
        final int price = Integer.parseInt(document
                .getElementsByClass("sale-price")
                .get(0).getElementsByClass("price").attr("data-default").replace(".", ""));

        final String[] mainAttributes = document.getElementsByAttribute("itemprop")
                .get(5)
                .text()
                .split(" ");

        final double weight = Double.parseDouble(mainAttributes[0]);
        final int carat = Integer.parseInt(mainAttributes[2]);

        /*
            Use table values
         */
        // Soru bunu farklı yerde nasıl çalıştırım

        final Elements otherAttributes = document
                .getElementsByClass("product-extras")
                .get(0)
                .getElementsByTag("table")
                .stream().filter(element -> element.attr("class").equals("hidden-xs")).findFirst().get()
                .getElementsByTag("tr").get(1).getElementsByTag("td");

        //TODO: Fill class by 'otherAttributes' variable

        String diamondType = String.valueOf(otherAttributes.get(0).text());
        //diamondType = ?
        double diamondWeight = Double.parseDouble(otherAttributes.get(1).text());
        //diamondWeight = ?
        String diamondColor =String.valueOf(otherAttributes.get(2).text()) ;
        //diamondColor = ?
        String diamondClarity = String.valueOf(otherAttributes.get(3).text());
        //diamondClarity = ?;
        String diamondShape = String.valueOf(otherAttributes.get(4).text());
        //diamondShape = ?
        return new JewelItem(id, price, weight, carat, diamondType, diamondWeight, diamondColor, diamondClarity, diamondShape);
    }



}

