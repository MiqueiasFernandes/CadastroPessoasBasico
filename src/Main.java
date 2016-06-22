
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import pessoas.presenter.MainPresenter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mfernandes
 */
public class Main {

    public static void main(String[] args) {

        try {
            new MainPresenter();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Houve um erro ao inicializar o programa\n"
                    + "contate o administrador. detales:\n" + ex);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Houve um erro ao inicializar o programa\n"
                    + "contate o administrador. detales:\n" + ex);
        }
    }
}
