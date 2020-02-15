package view;

import controller.Controller;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;
import model.*;
import exception.*;


public class Frame {

    private static final Logger LOGGER = LogManager.getLogger(Frame.class);
    private JFrame frame;
    private JTextField senderField = new JTextField();
    private JTextField subjectField = new JTextField();
    private JTextArea messageArea = new JTextArea();
    private JTextArea memoArea = new JTextArea();

    public Frame() {
        frame = new JFrame();
        setFrame();
        setMainPart();
        setMemoPanel();
    }

    private void setFrame() {
        frame.setLayout(new GridLayout(2, 1));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(550,800));
        frame.setSize(new Dimension(550, 800));
        frame.setTitle("SMTP");
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }
    private void setMainPart() {
        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout());
        panel1.add(new Label("to"));
        senderField.setColumns(46);
        senderField.setToolTipText("to");
        panel1.add(senderField);
        panel1.add(new Label("subject"));
        subjectField.setColumns(43);
        subjectField.setToolTipText("subject");
        panel1.add(subjectField);
        JScrollPane panel2 = new JScrollPane((messageArea));
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3,1));
        panel.add(panel1);
        panel.add(panel2);
        setButtons(panel);
        frame.add(panel);
    }

    private void setButtons(JPanel panel) {
        JButton sendButton = new JButton();
        sendButton.setText("Send message");

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map<MailFormUnits, String> parameters = new HashMap<MailFormUnits, String>();
                parameters.put(MailFormUnits.SENDER, senderField.getText());
                parameters.put(MailFormUnits.SUBJECT, subjectField.getText());
                parameters.put(MailFormUnits.MAIL_TEXT, messageArea.getText());

                try {
                    Controller.getInstance().processRequest("send_message", parameters);
                    updateMemo();
                } catch (InvalidParameterException | SmtpException exc) {
                    LOGGER.log(Level.ERROR, exc);
                    JOptionPane.showMessageDialog(frame, exc.getMessage());
                }
            }
        });

        JButton allCommandsButton = new JButton();
        allCommandsButton.setText("all commands");

        allCommandsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CommandsDialog commandsDialog = new CommandsDialog(memoArea);
                commandsDialog.show();
            }
        });


        JPanel panel1 = new JPanel();
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 50, 0, 50);
        panel1.setLayout(new GridBagLayout());
        panel1.add(sendButton, c);
        panel1.add(allCommandsButton, c);
        panel.add(panel1);
    }

    private void setMemoPanel() {
        memoArea.setBackground(Color.BLACK);
        memoArea.setEnabled(false);
        frame.add(new JScrollPane((new JPanel()).add(memoArea)));
    }

    private void updateMemo() {
        memoArea.setText("");
        memoArea.setText(MemoBuffer.getInstance().toString());
    }

    public void show() {
        frame.setVisible(true);
    }
}
