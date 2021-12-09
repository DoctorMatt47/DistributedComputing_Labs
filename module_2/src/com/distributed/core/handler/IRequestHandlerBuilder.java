package com.distributed.core.handler;

import com.distributed.domain.valuetype.SourceCode;

public interface IRequestHandlerBuilder {
    IRequestHandler build(SourceCode source);
}
