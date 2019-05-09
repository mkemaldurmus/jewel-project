package com.exastax.internship.jewel.datacollect.starter;

import org.apache.http.client.utils.URIBuilder;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

public class DataProducerMain {
    private static final int JSOUP_PARSE_TIMEOUT = 5000;
    public static void main(String[] args) throws IOException, URISyntaxException {

        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.serializer", StringSerializer.class);
        properties.put("value.serializer", StringSerializer.class);
        properties.put("client.id", "jewel_producer_producer");
        final String topic = "jewel_store";

        final KafkaProducer<String, String> kafkaProducer = new KafkaProducer(properties);

        final String mainURL = "https://www.altinbas.com/urunlerimiz.php?urunlist=&satan=&stok=1&indirim=&yeniler=&filtre=&fiyat1=0&fiyat2=1000000&grupID=&kategoriID=179&altkategoriID=181&koleksiyonID=&orderby=sira&orderbytype=ASC&type=&sayfax=1";

        /*
            Jewel Store URL = https://www.altinbas.com/urunlerimiz.php?urunlist=&satan=&stok=1&indirim=&yeniler=&filtre=&fiyat1=0&fiyat2=1000000&grupID=&kategoriID=179&altkategoriID=181&koleksiyonID=&orderby=sira&orderbytype=ASC&type=
            Look up total pages first
         */

        final Document page = Jsoup.parse(new URL(mainURL), JSOUP_PARSE_TIMEOUT);

        final Elements pagerItems = page.getElementsByClass("pagement_list").get(0).getElementsByTag("li");

        final int totalPageCount = pagerItems.size() - 2; // result is 6
        System.out.println(totalPageCount);
        /*
            Iterate pages 1 to 6
         */
        for (int currentPage = totalPageCount; currentPage > 0; currentPage--) {
            final URIBuilder sayfax = new URIBuilder(mainURL).addParameter("sayfax", Integer.toString(currentPage));
            final Document parse = Jsoup.parse(new URL(sayfax.toString()), JSOUP_PARSE_TIMEOUT);

            /*
                Find products anchors by css class 'product-box'
             */

            final Elements products = parse.getElementsByClass("product-box");

            for (Element product : products) {
                /*
                    final product's URL. Send to Kafka Broker.
                 */
                final String productId = product.attr("uid");
                final String productURL = "https://www.altinbas.com/urun" + product.getElementsByTag("a").get(0).attr("href");
                while (true) {
                    ProducerRecord<String, String> record = new ProducerRecord<>(topic, productId, productURL);
                    kafkaProducer.send(record);

                }
                /*
                    TODO: Send Key/Value pair to Kafka Broker
                    Key is product's ID
                    Value is product's URL
                    @see org.apache.kafka.clients.producer.ProducerRecord
                 */
            }
        }
    }
}
