package com.mycompany.projeto_poo2.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoMySQL {
    private static final String URL = "jdbc:mysql://localhost:3306/veiculos_bd";
    private static final String USUARIO = "root";
    private static final String SENHA = "rhosutfprbd";
    
    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}