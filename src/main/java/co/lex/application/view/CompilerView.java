package co.lex.application.view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by jotauribe on 18/11/16.
 */
public class CompilerView {
    private JButton compilarEnCButton;
    private JButton Compilar;
    private JTextArea codeTextArea;
    private JTextArea textArea2;

    public CompilerView() {
        Compilar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codeString = codeTextArea.getText();
                JOptionPane.showMessageDialog(null, "hola");
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
