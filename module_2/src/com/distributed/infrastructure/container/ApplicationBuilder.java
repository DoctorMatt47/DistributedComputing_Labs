package com.distributed.infrastructure.container;

import com.distributed.core.context.DatabaseContext;
import com.distributed.core.context.XmlContext;
import com.distributed.core.handler.RequestHandlerBuilder;
import com.distributed.core.parser.SoftwareXmlParser;
import com.distributed.core.service.producer.DbProducerService;
import com.distributed.core.service.producer.XmlProducerService;
import com.distributed.core.service.product.DbProductService;
import com.distributed.core.service.product.XmlProductService;
import com.distributed.core.validator.XmlValidator;
import com.distributed.domain.valuetype.ProductType;
import com.distributed.infrastructure.dto.producer.AddProducerRequest;
import com.distributed.infrastructure.dto.producer.UpdateProducerRequest;
import com.distributed.infrastructure.dto.product.AddProductRequest;
import com.distributed.infrastructure.dto.product.UpdateProductRequest;
import com.distributed.infrastructure.listener.SocketListener;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ApplicationBuilder {
    public void build() throws IOException {
        var xmlContext = new XmlContext(
                new XmlValidator("resources/software.xsd"),
                new SoftwareXmlParser("resources/software.xml"),
                "resources/software.xml");

        var xmlProducerService = new XmlProducerService(xmlContext);
        var xmlProductService = new XmlProductService(xmlContext);

        var dbContext = new DatabaseContext();

        var dbProducerService = new DbProducerService(dbContext);
        var dbProductService = new DbProductService(dbContext);

        var serverSocket = new ServerSocket(5555);

        new Thread(() -> {
            while (!Thread.interrupted()) {
                System.out.println("Listening...");
                try (Socket socket = serverSocket.accept()) {
                    var in = new ObjectInputStream(socket.getInputStream());
                    var out = new ObjectOutputStream(socket.getOutputStream());
                    var requestHandlerBuilder = new RequestHandlerBuilder(
                            in, out,
                            xmlProducerService,
                            xmlProductService,
                            dbProducerService,
                            dbProductService
                            );
                    var socketListener = new SocketListener(requestHandlerBuilder, in, out);
                    socketListener.listen();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            dbContext.close();
        }).start();
    }
}
