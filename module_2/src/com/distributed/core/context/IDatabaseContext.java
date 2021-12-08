package com.distributed.core.context;

import java.io.Closeable;
import java.sql.ResultSet;

public interface IDatabaseContext extends Closeable {
    ResultSet executeQuery(String sql);
}
