/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bancoxml.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 * Class conexão
 *
 * @author Leandro Sartor / Cássio Benincá
 */
public class Connect {
    
    /** 
     * Método para efetuar a conexão com o banco de dados
     * 
     * @param path - caminho do banco de dados
     * @return Connection - retorna a conexão com o banco
     * @throws SQLException  - erro de conexão
     */

    public static Connection connect(String path) throws SQLException {
        Connection conn = null;
        Statement stat = null;
        ResultSet rset = null;


        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@"+path+":1521:xe", "system", "system");
            stat = conn.createStatement();

        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
   
}
