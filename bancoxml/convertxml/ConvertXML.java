/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bancoxml.convertxml;

import bancoxml.connection.Connect;
import bancoxml.connection.Table;
import bancoxml.frame.Main;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;

/**
 * 
 * Class converte a tabela em um aquivo XML
 *
 * @author Leandro Sartor / Cássio Benincá
 */
public class ConvertXML {

    /**
     * converte o dados da tabela em uma estrutura XML
     * 
     * @param path - caminho do banco de dados
     * @param table - tabela que deseja converter em xml
     *
     */
    public void ConverterXMl(String path, String table) {
        Table tabela = new Table();
        if (tabela.TabelaExiste(path, table)) {

            Connection conn = null;
            PreparedStatement sta = null;
            ResultSetMetaData resmd = null;
            ResultSet rs = null;
            try {
                conn = Connect.connect(path);

                String sql = "Select * from " + table;
                sta = conn.prepareStatement(sql);
                resmd = sta.executeQuery().getMetaData();
                rs = sta.executeQuery();

                String xml = "";
                xml = "<?xml version=\"1.0\" standalone=\"yes\" ?>\n";
                xml += " <DATAPACKET Version=\"2.0\">\n";
                xml += "    <METADATA>\n";
                xml += "         <FIELDS>\n";
                for (int i = 1; i <= resmd.getColumnCount(); i++) {
                    xml += "            <FIELD attrname=\"" + resmd.getColumnName(i) + "\" fieldtype=\"";
                    if ("INTEGER".equals(resmd.getColumnTypeName(i).toUpperCase())) {
                        xml += "i4\"";
                        if (resmd.isNullable(i) == 0) {
                            xml += " required=\"true\"";
                        }
                        xml += " />\n";
                    } else if ("VARCHAR2".equals(resmd.getColumnTypeName(i).toUpperCase())) {
                        xml += "string\"";
                        if (resmd.isNullable(i) == 0) {
                            xml += " required=\"true\"";
                        }
                        xml += " WIDTH=\"" + resmd.getColumnDisplaySize(i) + "\" />\n";
                    } else if ("DATE".equals(resmd.getColumnTypeName(i).toUpperCase())) {
                        xml += "SQLdataTime\"";
                        if (resmd.isNullable(i) == 0) {
                            xml += " required=\"true\"";
                        }
                        xml += " />\n";
                    } else if ("NUMBER".equals(resmd.getColumnTypeName(i).toUpperCase())) {
                        if (resmd.getPrecision(i)== 0){
                            xml += "i4\"";
                            if (resmd.isNullable(i) == 0) {
                                xml += " required=\"true\"";
                            }
                            xml += " />\n";
                        } else {
                            xml += "fixed\"";
                            if (resmd.isNullable(i) == 0) {
                                xml += " required=\"true\"";
                            }
                            xml += " DECIMALS=\""+resmd.getScale(i)+"\" WIDTH=\"" + (resmd.getPrecision(i)) + "\" />\n";
                        }
                        //xml += " WIDTH=\"" + (resmd.getColumnDisplaySize(i) - 2) + "\" DECIMALS=\"2\" />\n";
                    }    
                }
                xml += "         </FIELDS>\n";
                xml += "         <PARAMS LCID=\"0\" />\n";
                xml += "    </METADATA>\n";
                xml += "    <ROWDATA>\n";
                while (rs.next()) {
                    xml += "    <ROW ";
                    for (int i = 1; i <= resmd.getColumnCount(); i++) {
                        xml += resmd.getColumnName(i) + "=\"";
                        if (("VARCHAR2".equals(resmd.getColumnTypeName(i).toUpperCase())) ||("VARCHAR2".equals(resmd.getColumnTypeName(i).toUpperCase())))  {
                            xml += rs.getString(i) + "\" ";
                        } else if ("DATE".equals(resmd.getColumnTypeName(i).toUpperCase())) {
                            String campo = rs.getString(i);
                            String subCampo = campo.substring(0, 9);
                            subCampo = subCampo.replace("-", "");
                            xml += subCampo + "\" ";
                        } else if (("NUMBER".equals(resmd.getColumnTypeName(i))) || ("INTEGER".equals(resmd.getColumnTypeName(i).toUpperCase()))) {
                            if (resmd.isNullable(i) == 0) {
                                xml += rs.getString(i) + "\" ";
                            } else {
                                String formato = "R$ #,##0.00";
                                DecimalFormat d = new DecimalFormat(formato);
                                xml += d.format(rs.getFloat(i)) + "\" ";
                            }
                        }
                    }
                    xml += "/>\n";
                }

                xml += "    </ROWDATA>\n";
                xml += " </DATAPACKET>";

                Main.setTextArea(xml);
            } catch (SQLException ex) {
                System.out.println("ERRO: " + ex.getMessage());
            } finally {
                if (sta != null) {
                    try {
                        sta.close();
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
        } else {
            JOptionPane.showMessageDialog(null, "Tabela não existe !!!");
        }
    }
}
