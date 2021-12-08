package com.distributed.core.parser;

import com.distributed.domain.entity.Producer;
import com.distributed.domain.entity.Product;
import com.distributed.domain.valuetype.ProductType;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;

public class SoftwareXmlParser implements ISoftwareXmlParser {
    private String xmlPath;
    private Document document;

    public SoftwareXmlParser(String xmlPath) {
        this.xmlPath = xmlPath;
        init();
    }

    private void init() {
        try {
            document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(new File(xmlPath));
        } catch (Exception ignored) {
        }
    }

    @Override
    public ArrayList<Product> findProducts() {
        var products = new ArrayList<Product>();
        var productNodes = document.getElementsByTagName("product");
        for (int i = 0; i < productNodes.getLength(); i++)
        {
            var productNode = productNodes.item(i);
            var attributes = productNode.getAttributes();
            var id = Integer.parseInt(attributes.getNamedItem("id").getNodeValue());
            var producerId = Integer.parseInt(attributes.getNamedItem("producerId").getNodeValue());
            var name = attributes.getNamedItem("name").getNodeValue();
            var price = Integer.parseInt(attributes.getNamedItem("price").getNodeValue());
            var type = ProductType.valueOf(attributes.getNamedItem("type").getNodeValue());
            products.add(new Product(id, producerId, name, price, type));
        }
        return products;
    }

    @Override
    public ArrayList<Producer> findProducers() {
        var producers = new ArrayList<Producer>();
        var producerNodes = document.getElementsByTagName("producer");
        for (int i = 0; i < producerNodes.getLength(); i++)
        {
            var productNode = producerNodes.item(i);
            var attributes = productNode.getAttributes();
            var id = Integer.parseInt(attributes.getNamedItem("id").getNodeValue());
            var name = attributes.getNamedItem("name").getNodeValue();
            producers.add(new Producer(id, name));
        }
        return producers;
    }

    @Override
    public void save(ArrayList<Product> products, ArrayList<Producer> producers) {
        clean();

        var softwareNode = document.getElementsByTagName("Software").item(0);
        for (var producer : producers) {
            var producerNode = document.createElement("producer");
            producerNode.setAttribute("id", String.valueOf(producer.getId()));
            producerNode.setAttribute("name", producer.getName());
            for (var product : producer.getProducts()) {
                var productNode = document.createElement("product");
                productNode.setAttribute("id", String.valueOf(product.getId()));
                productNode.setAttribute("producerId", String.valueOf(product.getProducerId()));
                productNode.setAttribute("name", product.getName());
                productNode.setAttribute("price", String.valueOf(product.getPrice()));
                productNode.setAttribute("type", product.getType().name());
                producerNode.appendChild(productNode);
            }
            softwareNode.appendChild(producerNode);
        }

        transform();
    }

    private void clean() {
        var softwareNode = document.getElementsByTagName("Software").item(0);
        var producerNodes = softwareNode.getChildNodes();
        for (int i = 0; i < producerNodes.getLength(); i++) {
            var producerNode = producerNodes.item(i);
            if (!producerNode.getNodeName().equals("producer")) {
                continue;
            }
            var data = producerNode.getAttributes().getNamedItem("id").getNodeValue();
            var productNodes = producerNode.getChildNodes();
            for (int j = 0; j < productNodes.getLength(); j++) {
                var productNode = productNodes.item(j) ;
                if (!productNode.getNodeName().equals("product")) {
                    continue;
                }
                producerNode.removeChild(productNode);
            }
            softwareNode.removeChild(producerNode);
        }
    }

    private void transform() {
        var tf = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = tf.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        var src = new DOMSource(document);
        var res = new StreamResult(new File(xmlPath));
        try {
            if (transformer != null) {
                transformer.transform(src, res);
            }
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
