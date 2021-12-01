package com.distributed.lab8.infrastructure.container;

import com.distributed.lab8.core.context.XmlContext;
import com.distributed.lab8.core.parser.SoftwareXmlParser;
import com.distributed.lab8.core.service.producer.ProducerService;
import com.distributed.lab8.core.service.product.ProductService;
import com.distributed.lab8.core.validator.XmlValidator;
import com.distributed.lab8.domain.entity.ProductType;
import com.distributed.lab8.infrastructure.dto.product.AddProductRequest;
import com.distributed.lab8.infrastructure.dto.product.UpdateProductRequest;

public class DependencyContainer {
    public void buildController() {
        var context = new XmlContext(
                new XmlValidator("src/main/resources/software.xsd"),
                new SoftwareXmlParser("src/main/resources/software.xml"),
                "src/main/resources/software.xml");

        var productService = new ProductService(context);
        var producerService = new ProducerService(context);
        var dbg2 = productService.addProduct(new AddProductRequest(100, "123", 123, ProductType.OTHER));
        productService.updateProduct(new UpdateProductRequest(dbg2.id(), "200", 421, ProductType.DESKTOP));
        context.save();
    }
}
