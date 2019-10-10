package jv.usinggdm.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class BookCreator extends JFrame {

        private Book book;
          private JLabel bookName; private JTextField name;
           private JLabel bookAuthor; private JTextField author;
            private JLabel bookPublisher; private JTextField publisher;
             private JLabel bookRelease; private JTextField release;
              private JLabel bookType; private JTextField type;
               private JTextArea bookDescription;
                private JButton saveChanges;
        private final Font font = new Font("Times New Roman", Font.PLAIN, 20);
            private BookCatalogue bookCatalogue;
            private DefaultListModel<String> defaultListModel;


    public BookCreator(BookCatalogue bookCatalogue, DefaultListModel<String> defaultListModel){
            super("New book");
             this.book = new Book();
             this.bookCatalogue = bookCatalogue;
             this.defaultListModel = defaultListModel;
                this.setResizable(false);
                this.setBounds(0,0,1024,1024);
                this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            // determine book parameters display
            bookName = new JLabel("Name: ");
            bookName.setFont(font);
                bookName.setSize(bookName.getPreferredSize());
                    bookName.setLocation(10,40);
                    name = new JTextField("Type book name here");
                    name.setBounds(200, 40, 256, 40);
                    name.setFont(font);
                bookAuthor = new JLabel("Author: ");
                bookAuthor.setFont(font);
                    bookAuthor.setSize(bookAuthor.getPreferredSize());
                        bookAuthor.setLocation(10,80);
                        author = new JTextField("Type author name here");
                        author.setBounds(200,80,256,40);
                        author.setFont(font);
                    bookPublisher = new JLabel("Publisher: ");
                    bookPublisher.setFont(font);
                        bookPublisher.setSize(bookPublisher.getPreferredSize());
                            bookPublisher.setLocation(10,120);
                            publisher = new JTextField("Type publisher name here");
                            publisher.setBounds(200,120,256,40);
                            publisher.setFont(font);
                        bookRelease = new JLabel("Release year: ");
                        bookRelease.setFont(font);
                            bookRelease.setSize(bookRelease.getPreferredSize());
                                bookRelease.setLocation(10, 160);
                                release = new JTextField("Type release year here");
                                release.setBounds(200,160,256,40);
                                release.setFont(font);
                            bookType = new JLabel("Book type: ");
                            bookType.setFont(font);
                                bookType.setSize(bookType.getPreferredSize());
                                    bookType.setLocation(10, 200);
                                    type = new JTextField("Type eBook (for eBooks) or paper book (for paper book) here");
                                    type.setBounds(200,200,256,40);
                                    type.setFont(font);
                                bookDescription = new JTextArea();
                                bookDescription.setFont(font);
                                    bookDescription.setText(this.book.getDescription());
                                        bookDescription.setSize(994, 512);
                                        bookDescription.setLocation(10, 240);
            // determine book parameters display
            saveChanges = new JButton("Save changes");
            saveChanges.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                            book.setName(name.getText());
                            book.setAuthor(author.getText());
                            book.setPublisher(publisher.getText());
                                if(release.getText().matches("[1-9]++"))
                            book.setRelease(Integer.valueOf(release.getText()));
                                else book.setRelease(0);
                            book.setType((byte)(type.getText().toLowerCase().equals("ebook")? 1 : 0));
                            book.setDescription(bookDescription.getText());
                                if(bookCatalogue.getActingUser().getAccessModifier()==1)
                                    addBook(book);
                                else
                                    suggestBook(book);
                            setVisible(false);
                            dispose();
                }
            });

            saveChanges.setSize(300,40);
            saveChanges.setFont(font);
            saveChanges.setLocation(210, 800);

            JPanel parametersPanel = new JPanel();
                parametersPanel.setLayout(null);
                parametersPanel.add(bookName); parametersPanel.add(name);
                        parametersPanel.add(bookAuthor); parametersPanel.add(author);
                            parametersPanel.add(bookPublisher); parametersPanel.add(publisher);
                                parametersPanel.add(bookRelease); parametersPanel.add(release);
                                    parametersPanel.add(bookType); parametersPanel.add(type);
                                        parametersPanel.add(bookDescription);
                                            parametersPanel.add(saveChanges);
            setContentPane(parametersPanel);
        }

        private void addBook(Book book){
            if(!bookCatalogue.existInCatalogue(book)) {
                bookCatalogue.append(book);
                defaultListModel.addElement(book.getName() + " : " + book.getType());
            }
        }
            private void suggestBook(Book book){

            }
}
