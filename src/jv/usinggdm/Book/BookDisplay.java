package jv.usinggdm.Book;

import jv.usinggdm.SendingEmail.EmailAuthorizationDisplay;
import jv.usinggdm.SendingEmail.EmailSender;
import jv.usinggdm.Users.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class BookDisplay extends JFrame {

        private Book book;
         private User actingUser;
          private JLabel bookName;
           private JLabel bookAuthor;
            private JLabel bookPublisher;
             private JLabel bookRelease;
              private JLabel bookType;
               private JTextArea bookDescription;
                private JButton saveDescriptionChanges;
                 private JButton restoreDefaultDescription;
        private final Font font = new Font("Times New Roman", Font.PLAIN, 20);


    public BookDisplay(Book book, User actingUser){
            super("Book display");
            this.book = book;
                this.actingUser = actingUser;
                this.setResizable(false);
                this.setBounds(0,0,1024,1024);
                this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            // determine book parameters display
            bookName = new JLabel("Name: "+this.book.getName());
            bookName.setFont(font);
                bookName.setSize(bookName.getPreferredSize());
                    bookName.setLocation(10,40);
                bookAuthor = new JLabel("Author: "+this.book.getAuthor());
                bookAuthor.setFont(font);
                    bookAuthor.setSize(bookAuthor.getPreferredSize());
                        bookAuthor.setLocation(10,80);
                    bookPublisher = new JLabel("Publisher: "+this.book.getPublisher());
                    bookPublisher.setFont(font);
                        bookPublisher.setSize(bookPublisher.getPreferredSize());
                            bookPublisher.setLocation(10,120);
                        bookRelease = new JLabel("Release year: "+this.book.getRelease());
                        bookRelease.setFont(font);
                            bookRelease.setSize(bookRelease.getPreferredSize());
                                bookRelease.setLocation(10, 160);
                            bookType = (this.book.getType()==1) ? new JLabel("eBook") : new JLabel("Paper book");
                            bookType.setFont(font);
                                bookType.setSize(bookType.getPreferredSize());
                                    bookType.setLocation(10, 200);
                                bookDescription = new JTextArea();
                                bookDescription.setFont(font);
                                    bookDescription.setText(this.book.getDescription());
                                        bookDescription.setSize(994, 512);
                                        bookDescription.setLocation(10, 240);
            // determine book parameters display
            saveDescriptionChanges = new JButton("Save changes");
            restoreDefaultDescription = new JButton("Defaults");
            saveDescriptionChanges.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(actingUser.getAccessModifier()==1) {
                        book.setDescription(bookDescription.getText());
                    }
                    else bookDescription.setText("Access denied");
                }
            });
            restoreDefaultDescription.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    bookDescription.setText(book.getDescription());
                }
            });
            saveDescriptionChanges.setSize(300,40);
            saveDescriptionChanges.setFont(font);
            restoreDefaultDescription.setSize(300,40);
            restoreDefaultDescription.setFont(font);
            saveDescriptionChanges.setLocation(210, 800);
            restoreDefaultDescription.setLocation(512, 800);

            JPanel parametersPanel = new JPanel();
                parametersPanel.setLayout(null);
                parametersPanel.add(bookName);
                        parametersPanel.add(bookAuthor);
                            parametersPanel.add(bookPublisher);
                                parametersPanel.add(bookRelease);
                                    parametersPanel.add(bookType);
                                        parametersPanel.add(bookDescription);
                                            parametersPanel.add(saveDescriptionChanges);
                                                parametersPanel.add(restoreDefaultDescription);
            setContentPane(parametersPanel);
        }
}
