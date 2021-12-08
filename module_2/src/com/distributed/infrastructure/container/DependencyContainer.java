package com.distributed.infrastructure.container;

import com.distributed.core.context.DatabaseContext;
import com.distributed.core.context.XmlContext;
import com.distributed.core.parser.SoftwareXmlParser;
import com.distributed.core.validator.XmlValidator;

public class DependencyContainer {
    public void buildController() {
        var context = new XmlContext(
                new XmlValidator("src/main/resources/software.xsd"),
                new SoftwareXmlParser("src/main/resources/software.xml"),
                "src/main/resources/software.xml");

        var context2 = new DatabaseContext();
    }
}
