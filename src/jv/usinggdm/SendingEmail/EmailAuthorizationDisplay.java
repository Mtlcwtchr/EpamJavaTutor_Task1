package jv.usinggdm.SendingEmail;

import jv.usinggdm.Users.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailAuthorizationDisplay extends JFrame {

        private String password;
            private JButton okButton;
                private JTextField passwordField;
                    private User actingUser;
    private final Font font = new Font("Times New Roman", Font.PLAIN, 20);
                        private ArrayList<String> recipients = new ArrayList<>();


    public EmailAuthorizationDisplay(User actingUser) throws IOException {
                    super("Book display");
                        this.actingUser = actingUser;
                    this.setResizable(false);
                    this.setBounds(0,0,512,256);
                    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                        BufferedReader bufferedReader = new BufferedReader(new FileReader("D:\\Java\\Projs\\BookAccounting\\Database\\Users.txt"));
                            String line = bufferedReader.readLine();
                            Pattern emailPattern = Pattern.compile("\\[m:[a-zA-Z0-9[-. _]]+?@gmail\\.com]");
                                while (line!=null) {
                                    System.out.println(line);
                                    Matcher foundEmail = emailPattern.matcher(line);
                                    while (foundEmail.find()) {
                                        if (!line.substring(foundEmail.start() + 3, foundEmail.end() - 1).equals(actingUser.getEmail())) {
                                                recipients.add(line.substring(foundEmail.start() + 3, foundEmail.end() - 11));
                                            System.out.println(line.substring(foundEmail.start() + 3, foundEmail.end() - 11));
                                        }
                                    }
                                    line = bufferedReader.readLine();
                                }

                        okButton = new JButton("OK");
                        okButton.setBounds(231,145,75,40);
                        okButton.setFont(font);
                            okButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    password = passwordField.getText();
                                        EmailSender sender = new EmailSender(actingUser.getEmail().substring(0, actingUser.getEmail().length()-10), password);
                                            sender.setSubject("Virtual library");
                                                sender.setBody("Some books description has been changed.\nVisit virtual library to see changes.");
                                                    sender.setRecipient("Inkvizitorell@gmail.com");
                                                        sender.start();
                                }
                            });

                        passwordField = new JTextField("Your email password");
                        passwordField.setBounds(10,50,492,75);
                        passwordField.setFont(font);

                    JPanel panel = new JPanel();
                        panel.setLayout(null);
                            panel.add(okButton);
                                panel.add(passwordField);
                                    setContentPane(panel);
    }

}
