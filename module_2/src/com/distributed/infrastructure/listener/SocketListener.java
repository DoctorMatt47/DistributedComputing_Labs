package com.distributed.infrastructure.listener;

import com.distributed.core.handler.IRequestHandlerBuilder;
import com.distributed.domain.valuetype.SourceCode;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SocketListener implements ISocketListener {
    private final IRequestHandlerBuilder builder;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;

    public SocketListener(IRequestHandlerBuilder builder,
                          ObjectInputStream in,
                          ObjectOutputStream out) {
        this.builder = builder;
        this.in = in;
        this.out = out;
    }

    @Override
    public void listen() throws IOException {
        System.out.println("Connected...");
        while (!Thread.interrupted()) {
            var sourceCode = SourceCode.values()[in.readInt()];
            builder.build(sourceCode).handle();
        }
    }
}
