package com.distributed.core.handler;

import com.distributed.core.context.XmlContext;
import com.distributed.core.service.producer.DbProducerService;
import com.distributed.core.service.producer.XmlProducerService;
import com.distributed.core.service.product.DbProductService;
import com.distributed.core.service.product.XmlProductService;
import com.distributed.domain.valuetype.EntityCode;
import com.distributed.domain.valuetype.OperationCode;
import com.distributed.domain.valuetype.SourceCode;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class RequestHandlerBuilder implements IRequestHandlerBuilder {
    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    private final XmlProducerService xmlProducerService;
    private final XmlProductService xmlProductService;
    private final DbProducerService dbProducerService;
    private final DbProductService dbProductService;

    public RequestHandlerBuilder(ObjectInputStream in,
                                 ObjectOutputStream out,
                                 XmlProducerService xmlProducerService,
                                 XmlProductService xmlProductService,
                                 DbProducerService dbProducerService,
                                 DbProductService dbProductService) {
        this.in = in;
        this.out = out;
        this.xmlProducerService = xmlProducerService;
        this.xmlProductService = xmlProductService;
        this.dbProducerService = dbProducerService;
        this.dbProductService = dbProductService;
    }
    @Override
    public IRequestHandler build(SourceCode source) {
        return switch (source) {
            case XML -> new RequestHandler(xmlProducerService, xmlProductService, in, out);
            case DB ->  new RequestHandler(dbProducerService, dbProductService, in, out);
        };
    }
}
