package view;

import controller.Controller;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import model.*;
import exception.*;


public class Frame {

    private static final Logger LOGGER = LogManager.getLogger(Frame.class);
    private JFrame frame;
    private JTextField smtpServerField = new JTextField();
    private JTextField fromField = new JTextField();
    private JTextField toField = new JTextField();
    private JTextField subjectField = new JTextField();
    private JTextArea messageArea = new JTextArea();
    private JTextArea memoArea = new JTextArea();

    public Frame() {
        frame = new JFrame();
        setFrame();
        setFieldsPanel();
        setMessagePanel();
        setButtons();
        setMemoPanel();
    }

    private void setFrame() {
        frame.setLayout(new GridLayout(4, 1));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(700,550));
        frame.setSize(new Dimension(700, 550));
        frame.setTitle("SMTP");
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }
    private void setFieldsPanel() {
        JPanel panel = new JPanel();

        panel.add(new Label("SMTP server"));
        smtpServerField.setColumns(25);
        smtpServerField.setToolTipText("SMTP server");
        panel.add(smtpServerField);

        panel.add(new Label("from"));
        fromField.setColumns(25);
        fromField.setToolTipText("from");
        panel.add(fromField);

        panel.add(new Label("to"));
        toField.setColumns(25);
        toField.setToolTipText("to");
        panel.add(toField);

        panel.add(new Label("subject"));
        subjectField.setColumns(25);
        subjectField.setToolTipText("subject");
        panel.add(subjectField);

        frame.add(panel);
    }
    private void setMessagePanel() {
        frame.add(new JScrollPane((new JPanel()).add(messageArea)));
    }
    private void setButtons() {
        JButton sendButton = new JButton();
        sendButton.setText("send_message");

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map<MailFormUnits, String> parameters = new HashMap<MailFormUnits, String>();
                parameters.put(MailFormUnits.SMTP_SERVER, smtpServerField.getText());
                parameters.put(MailFormUnits.FROM, fromField.getText());
                parameters.put(MailFormUnits.TO, toField.getText());
                parameters.put(MailFormUnits.SUBJECT, subjectField.getText());
                parameters.put(MailFormUnits.MAIL_TEXT, messageArea.getText());

                try {
                    Controller.getInstance().processRequest(sendButton.getText(), parameters);
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


        JPanel panel = new JPanel();
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 50, 0, 50);
        panel.setLayout(new GridBagLayout());
        panel.add(sendButton, c);
        panel.add(allCommandsButton, c);
        frame.add(panel);
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
