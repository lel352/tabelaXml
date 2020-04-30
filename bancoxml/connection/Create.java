/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bancoxml.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Class para Criar Tabela produtos
 *
 * @author Leandro Sartor / Cássio Benincá
 */
public class Create {
    /** 
     * Método para criar a conexão com o banco
     * 
     * @param path - caminho do banco de dados para efetuar a conexão
     */
    public void Createbank(String path){
        Connect connect = new Connect();
        Connection conn = null;
        try {
            conn = connect.connect(path);
            DropTable(conn);
            CreateTable(conn);
            Valeus(conn);
        } catch (SQLException ex) {
            Logger.getLogger(Create.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
           if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "ERRO: " + ex.getMessage());
                }
            }
        }
    }
    
    /** Método para deletar tabela produtos
     * 
     * @param conn - conexão do banco
     */
    
    public void DropTable(Connection conn) {
        PreparedStatement ps = null;
        String sql = "begin "
                + " execute immediate ('drop table Produtos'); "
                + " exception when others then null; "
                + " end; ";
        try {
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            
        } catch (SQLException e) {
        } finally {
        }
    }
    
    /** Método para criar a tabela produtos
     * 
     * @param conn - conexão com o banco de dados
     */

    public void CreateTable(Connection conn){
        PreparedStatement ps = null;
        String sql = "CREATE TABLE Produtos(CADPRO NUMBER NOT NULL PRIMARY KEY, NOME VARCHAR2(40) NOT NULL, DATACADASTRO DATE, PESOLIQUIDO NUMBER(9,2), "
            +"	PRECOUNITARIO NUMBER(9,2), CARACTERISTICAS VARCHAR2(80), ALTURA NUMBER(9,2), LARGURA NUMBER(9,2), PROFUNDIDADE NUMBER(9,2) )";
        try {
        
            ps = conn.prepareStatement(sql);
            ps.execute();  
        
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERRO: " + e.getMessage());

            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "ERRO: " + ex.getMessage());
                }
            }

        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "ERRO: " + ex.getMessage());
                }
            }
            /*if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "ERRO: " + ex.getMessage());
                }
            }*/
        }
    }
    /** Método para inserir valores no banco de dados na tabela produtos
     * 
     * @param conn - Conexão com o banco de dados
     * @param sql - comando de inserção de valores no banco de dados 
     */
    
    public void InsertValues(Connection conn, String sql){
        try {
            Statement sta = conn.createStatement();
            sta.executeUpdate(sql);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERRO: " + e.getMessage());

            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "ERRO: " + ex.getMessage());
                }
            }

        } finally {
          
        }
    }
    
    /** Método com o valoresa a serem inseridos no banco de dados
     *  
     * @param conn - conexão com o banco de dados
     */
    
    public void Valeus(Connection conn){
        for (int i=0;i<=2;i++){
            switch(i){
               case 0: InsertValues(conn,"insert into produtos values(1,'Produto 1','01/01/2001', 0.00,10.00,'0',10.00,20.00, 1.00)");
                 break;   
               case 1: InsertValues(conn,"insert into produtos values(2,'Produto 2','01/01/2001', 0.00, 0.00,'0', 0.00, 0.00, 0.00)");
                 break;                      
               case 2: InsertValues(conn,"insert into produtos values(3,'Produto 3','05/05/2005',10.00,12.00,'1',10.00,15.00,20.00)");
                 break;                                         
            }
        }
    }
    
    
   /* "insert into produtos values(1,'Produto 1','01/01/2001', 0.00,10.00,0,10.00,20.00, 1.00)"    
      "insert into produtos values(2,'Produto 2','01/01/2001', 0.00, 0.00,0, 0.00, 0.00, 0.00)" 
      "insert into produtos values(3,'Produto 3','05/05/2005',10.00,12.00,1,10.00,15.00,20.00)" */
}
