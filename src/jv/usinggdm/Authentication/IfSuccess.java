package jv.usinggdm.Authentication;


import jv.usinggdm.Book.Book;
import jv.usinggdm.Book.BookCatalogue;
import jv.usinggdm.Book.BookCatalogueDisplay;
import jv.usinggdm.Users.User;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IfSuccess extends Thread {

    private User user;
        private BufferedReader readCatalogue;
                private BookCatalogue bookCatalogue;

        IfSuccess(User user){
            this.user = user;
                try{
                    readCatalogue = new BufferedReader(new FileReader("D:\\Java\\Projs\\BookAccounting\\Database\\BookCatalogue.txt"));
                        start();
                } catch (IOException ex){
                    ex.printStackTrace();
                }
        }

        private void ReadCatalogueFromFile() throws IOException{
            bookCatalogue = new BookCatalogue(user);
                String line = readCatalogue.readLine();
                while (line!=null){
                    System.out.println(line);
                    Book book = new Book();
                    Pattern bookNamePattern = Pattern.compile("\\[n:.+?]");
                    Pattern authorNamePattern = Pattern.compile("\\[a:.+?]");
                    Pattern publisherNamePattern = Pattern.compile("\\[p:.+?]");
                    Pattern releaseYearPattern = Pattern.compile("\\[r:[0-9]{4}]");
                    Pattern typePattern = Pattern.compile("\\[t:[0-1]]");
                    Pattern descriptionPattern = Pattern.compile("\\[d:.+?]");
                    Pattern descriptionBigStart = Pattern.compile("\\[d:[^\\[\\]]+?$");
                    Pattern descriptionBigEnd = Pattern.compile(".+?]");
                    Pattern descriptionBigContinue = Pattern.compile("[^\\[\\]]+?$");
                    Matcher foundBookName = bookNamePattern.matcher(line);
                    Matcher foundAuthorName = authorNamePattern.matcher(line);
                    Matcher foundPublisherName = publisherNamePattern.matcher(line);
                    Matcher foundReleaseYear = releaseYearPattern.matcher(line);
                    Matcher foundType = typePattern.matcher(line);
                    Matcher foundDescription = descriptionPattern.matcher(line);
                    Matcher foundDescriptionStart = descriptionBigStart.matcher(line);
                        StringBuilder description = new StringBuilder();
                    while(foundBookName.find())
                        book.setName(line.substring(foundBookName.start()+3, foundBookName.end()-1));
                    while(foundAuthorName.find())
                        book.setAuthor(line.substring(foundAuthorName.start()+3, foundAuthorName.end()-1));
                    while(foundPublisherName.find())
                        book.setPublisher(line.substring(foundPublisherName.start()+3,foundPublisherName.end()-1));
                    while(foundReleaseYear.find())
                        book.setRelease(Integer.valueOf(line.substring(foundReleaseYear.start()+3, foundReleaseYear.end()-1)));
                    while(foundType.find())
                        book.setType(Integer.valueOf(line.substring(foundType.start()+3, foundType.end()-1)));
                    while(foundDescription.find())
                        description.append(line.substring(foundDescription.start()+3,foundDescription.end()-1));

                    while(foundDescriptionStart.find()){
                            description.append(line.substring(foundDescriptionStart.start()+3,foundDescriptionStart.end()));
                                line = readCatalogue.readLine();
                                    description.append("\n");
                                    while(true){
                                            Matcher foundDescriptionEnd = descriptionBigEnd.matcher(line);
                                            Matcher foundDescriptionContinue = descriptionBigContinue.matcher(line);
                                                if (foundDescriptionContinue.find())
                                                    description.append(line);
                                                if(foundDescriptionEnd.find()){
                                                        description.append(line.substring(foundDescriptionEnd.start(), foundDescriptionEnd.end()-1));
                                                        break;
                                                }
                                        description.append("\n");
                                        line = readCatalogue.readLine();
                                    }
                        }

                    book.setDescription(description.toString());
                    bookCatalogue.append(book);
                    System.out.println(book.getName()+" "+book.getAuthor()+" "+book.getPublisher()+" "+book.getRelease()+" "+book.getType()+" "+book.getDescription());
                    line = readCatalogue.readLine();
                }
        }

    @Override
    public void run() {
            try {
                ReadCatalogueFromFile();
                BookCatalogueDisplay bookCatalogueDisplay = new BookCatalogueDisplay(bookCatalogue);
                bookCatalogueDisplay.setVisible(true);
            } catch (IOException ex){
                ex.printStackTrace();
            }
    }
}
