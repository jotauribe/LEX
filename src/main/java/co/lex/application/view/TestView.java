package co.lex.application.view;

import co.lex.domain.model.lexicon.analysis.Token;
import co.lex.domain.model.syntax.analysis.AnalysisTree;
import co.lex.domain.model.syntax.analysis.SyntaxAnalysisReport;
import co.lex.domain.model.syntax.analysis.SyntaxAnalyzer;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestView extends JDialog {
    private JPanel contentPane;
    private JButton evaluateBtn;
    private JTextArea runTextArea;
    private JTextArea codeTextArea;
    private JButton LimpiarBtn;
    private JButton buttonOK;
    private JButton buttonCancel;
    private SyntaxAnalyzer syntaxAnalyzer;

    public TestView() {
        setContentPane(contentPane);
        setModal(true);

        syntaxAnalyzer = new SyntaxAnalyzer();

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        evaluateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String codeString = codeTextArea.getText();
                runTextArea.setText("");
                SyntaxAnalysisReport syntaxAnalysisReport = syntaxAnalyzer.evaluate(codeString);
                if(syntaxAnalysisReport.isEverythingOK()){
                    runTextArea.append("BUILD SUCCESSFULL");
                }
                else {
                    List<Token> lexicalErrors = syntaxAnalysisReport.lexicalErrorList();
                    runTextArea.setText(null);
                    for (Token t : lexicalErrors) {
                        String subString = codeString.substring(0, t.endLocation());
                        //System.out.print("MAIN:   "+t.startLocation()+"   "+t.endLocation());
                        runTextArea.append("\nLexical Error in line "+countLines(subString));
                    }
                    AnalysisTree analysisTree = syntaxAnalysisReport.syntaxAnalysisTree();
                    Token t = analysisTree.rightmostValidToken();
                    System.out.print(t);
                    if(t != null){
                        String subString = codeString.substring(0, t.endLocation());
                        runTextArea.append("\nSyntax Error in line "+countLines(subString));
                    }
                    else
                        runTextArea.append("\nSyntax Error");
                }

            }
        });
        LimpiarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                codeTextArea.setText("");
            }
        });
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        TestView dialog = new TestView();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private int countLines(String aString){
        Pattern p = Pattern.compile("\n");
        Matcher m = p.matcher(aString);
        int count = 1;
        while (m.find()) {
            count++;
        }
        return count;
    }

}
