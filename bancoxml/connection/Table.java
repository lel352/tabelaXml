/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bancoxml.connection;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class verifica existencia da tabela
 *
 * @author Leandro Sartor / Cássio Benincá
 */
public class Table {
  /**
   * Verifica a existencia de uma tabela no banco
   * 
   * @param path - caminho do banco de dados
   * @param table - nome da tabela que será verificado se existe no banco
   * @return boolean - true existe / false não existe
   */
    
    public boolean TabelaExiste(String path, String table) {
        Connection conn = null;
        PreparedStatement ps = null;
        Integer codigo = 0;
        boolean result = true;
        try {
            conn = Connect.connect(path);
            String sql = " Select * From "+table;
            ps = conn.prepareStatement(sql);
            ps.executeQuery();
            /*DatabaseMetaData tabela = conn.getMetaData();
// check if "employee" table is there  
            ResultSet rs = tabela.getTables(null, null, table, null);
            if (rs.next()) {
                // Table exists
                result = true;
            } else {
                result = false;
            }*/
        } catch (SQLException e) {
           // System.out.println("ERRO: " + e.getMessage());
            result = false;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println("ERRO: " + ex.getMessage());
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    System.out.println("ERRO: " + ex.getMessage());
                }
            }
        }
        return result;
    }
}
