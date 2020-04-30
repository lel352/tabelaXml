/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bancoxml.savefile;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 * Class para salvar aquivos
 *
 * @author Leandro Sartor / Cássio Benincá
 */
public class SaveFile {
    /**
     * Salvar aquivo em um destino especificado pelo usuario
     * 
     * @param file - caminho/arquivo onde será salvo o aquivo
     * @param textFile - texto que será salvo no arquivo  
     * @return boolean - true se foi salvo ou false se não foi salvo
     */
    public boolean SaveFile(String file, String textFile) {
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        boolean result = true;
        try {
            fileWriter = new FileWriter(file, false);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(textFile);
            bufferedWriter.flush();
            //Se chegou ate essa linha, conseguiu salvar o arquivo com sucesso.
            JOptionPane.showMessageDialog(null, "Salvo com sucesso");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar o arquivo: " + ex.getMessage());
             result = false;
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao salvar o arquivo: " + ex.getMessage());
                    result = false;
                }
            }
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao salvar o arquivo: " + ex.getMessage());
                    result = false;
                }
            }
        }
        return result;
    }
}
