package jv.usinggdm.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class BookCatalogueDisplay extends JFrame {

        private BookCatalogue bookCatalogue;
          private JPanel contentPanel;
           private JList<String> booksList;
           private DefaultListModel<String> defaultListModel = new DefaultListModel<>();
            private JButton addBook;
             private JButton getBook;
              private JButton sortByName;
               private JButton removeBook;
                private JButton exit;
                private final Font font = new Font("Times New Roman", Font.PLAIN, 20);
                 private JLabel usernameDisplay;


        public BookCatalogueDisplay(BookCatalogue bookCatalogue) {
            super("Book Catalogue display");
            this.bookCatalogue = bookCatalogue;
            this.setResizable(false);
            this.setBounds(0, 0, 920, 1080);
            this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

                // Catalogue display
                        this.bookCatalogue.sortByName();
                        if(this.bookCatalogue.getBooks()!=null)
                        for(Book i : this.bookCatalogue.getBooks())
                            defaultListModel.addElement(i.getName() + " : " + i.getType());

                        this.booksList = new JList<>(defaultListModel);
                        this.booksList.setBounds(40,40,512, 756);
                        this.booksList.setFont(font);
                        this.booksList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                        this.booksList.setLayoutOrientation(JList.VERTICAL);
                        this.booksList.setVisibleRowCount(-1);

                    addBook = new JButton("Add book");
                    addBook.setFont(font);
                    addBook.setBounds(612, 120, 200, 40);
                        addBook.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                    BookCreator bookCreator = new BookCreator(bookCatalogue, defaultListModel);
                                        bookCreator.setVisible(true);
                            }
                        });

                    getBook = new JButton("Get information");
                    getBook.setBounds(612, 180, 200, 40);
                    getBook.setFont(font);
                        getBook.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(!booksList.isSelectionEmpty())
                                    bookCatalogue.displayBook(booksList.getSelectedValue().substring(0,booksList.getSelectedValue().length()-4), Byte.valueOf(booksList.getSelectedValue().substring((booksList.getSelectedValue().length()-1))));
                            }
                        });


                    removeBook = new JButton("Remove chosen");
                    removeBook.setFont(font);
                    removeBook.setBounds(612, 240, 200, 40);
                    removeBook.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(!booksList.isSelectionEmpty())
                                 if(bookCatalogue.getActingUser().getAccessModifier()==1){
                                    bookCatalogue.remove(booksList.getSelectedValue().substring(0,booksList.getSelectedValue().length()-4), Byte.valueOf(booksList.getSelectedValue().substring((booksList.getSelectedValue().length()-1))));
                                       defaultListModel.remove(booksList.getSelectedIndex());
                                        validate();
                                 }
                            }
                    });

                    sortByName = new JButton("Sort list by book name");
                    sortByName.setFont(font);
                    sortByName.setBounds(156, 826, 300, 40);
                        sortByName.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                bookCatalogue.sortByName();
                                    for(int i=0; i<bookCatalogue.getBooks().length; i++)
                                        for(int j=i; j<bookCatalogue.getBooks().length; j++)
                                            if(defaultListModel.elementAt(j).compareTo(defaultListModel.elementAt(i))<0) {
                                                String p = defaultListModel.elementAt(i);
                                                defaultListModel.setElementAt(defaultListModel.elementAt(j), i);
                                                defaultListModel.setElementAt(p,j);
                                            }
                                validate();
                            }
                        });

                        exit = new JButton("Exit");
                        exit.setFont(font);
                        exit.setBounds(612, 826, 200, 40);
                            exit.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    WriteCatalogue();
                                        dispose();
                                }
                            });

                        JScrollPane scrollPane = new JScrollPane(booksList);
                            scrollPane.setBounds(40,40,512, 756);
                                scrollPane.setFont(font);

                    usernameDisplay = new JLabel(((bookCatalogue.getActingUser().getAccessModifier()==1) ? "Administrator: " : "User: ") + bookCatalogue.getActingUser().getUsername());
                        usernameDisplay.setBounds(612, 60, 260, 40);
                            usernameDisplay.setFont(font);


                this.contentPanel = new JPanel();
                    this.contentPanel.setLayout(null);
                        this.contentPanel.add(scrollPane);
                            this.contentPanel.add(addBook);
                                this.contentPanel.add(getBook);
                                    this.contentPanel.add(sortByName);
                                        this.contentPanel.add(removeBook);
                                            this.contentPanel.add(usernameDisplay);
                                                this.contentPanel.add(exit);
                            setContentPane(this.contentPanel);


        }

    public BookCatalogue getBookCatalogue() {
        return bookCatalogue;
    }
    public DefaultListModel<String> getDefaultListModel() {
        return defaultListModel;
    }
        private void WriteCatalogue(){
            try(BufferedWriter writeCatalogue = new BufferedWriter(new FileWriter("D:\\Java\\Projs\\BookAccounting\\Database\\BookCatalogue.txt"))){
                    for(int i=0; i<bookCatalogue.getBooks().length; i++)
                writeCatalogue.write(i
                        + "[n:" + bookCatalogue.getBooks()[i].getName() + "]"
                        + "[a:" + bookCatalogue.getBooks()[i].getAuthor() + "]"
                        + "[p:" + bookCatalogue.getBooks()[i].getPublisher() + "]"
                        + "[r:" + bookCatalogue.getBooks()[i].getRelease() + "]"
                        + "[t:" + bookCatalogue.getBooks()[i].getType() + "]"
                        + "[d:" + bookCatalogue.getBooks()[i].getDescription() + "]\r\n");
                writeCatalogue.flush();
            } catch (IOException ex){
                ex.printStackTrace();
            }
        }

}
