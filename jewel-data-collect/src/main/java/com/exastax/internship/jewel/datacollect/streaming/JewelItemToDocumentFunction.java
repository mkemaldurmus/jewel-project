package com.exastax.internship.jewel.datacollect.streaming;

import com.exastax.internship.jewel.core.JewelItem;
import org.apache.spark.api.java.function.Function;
import org.bson.Document;


public class JewelItemToDocumentFunction implements Function<JewelItem, Document> {

    @Override
    public Document call(JewelItem jewelItem) throws Exception {
        final Document document = new Document();

        //TODO: Build document object by jewel item.

        document.append("productId", jewelItem.getId())
                .append("price", jewelItem.getPrice())
                .append("weight", jewelItem.getWeight())
                .append("carat", jewelItem.getCarat())
                .append("diamondType", jewelItem.getDiamondType())
                .append("diamondWeight", jewelItem.getDiamondWeight())
                .append("diamondColor", jewelItem.getDiamondColor())
                .append("diamondClarity", jewelItem.getDiamondClarity())
                .append("diamondShape", jewelItem.getDiamondShape());

        return document;
    }
}
