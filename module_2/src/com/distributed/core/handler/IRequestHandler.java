package com.distributed.core.handler;

import java.io.IOException;

public interface IRequestHandler {
    void handle() throws IOException;
}
