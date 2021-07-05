package com.example.db.interfaces;

import com.example.db.Database;

import java.sql.Connection;

public abstract class AbstractInterface {
    protected static final Database database = Database.getInstance();
    protected static final Connection connection = database.getConnection();
}
