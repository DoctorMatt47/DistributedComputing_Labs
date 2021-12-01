package com.distributed.lab8.core.validator;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;

public class XmlValidator implements IXmlValidator {
    private final String xsdPath;

    public XmlValidator(String xsdPath) {
        this.xsdPath = xsdPath;
    }

    @Override
    public boolean validate(String xmlPath) {
        var xmlFile = new File(xmlPath);
        var xsdFile = new File(xsdPath);
        Schema schema;
        try {
            schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                    .newSchema(xsdFile);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }

        var validator = schema.newValidator();
        try {
            validator.validate(new StreamSource(xmlFile));
        } catch (SAXException | IOException e) {
            return false;
        }
        return true;
    }
}
