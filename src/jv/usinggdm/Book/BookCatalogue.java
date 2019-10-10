package jv.usinggdm.Book;

import jv.usinggdm.Users.User;

import java.util.Arrays;

public class BookCatalogue {
    // variables
    private Book[] books;
    private User actingUser;
    // variables

        public BookCatalogue(User actingUser){
            this.actingUser = actingUser;
        }

    // functions
        public Book[] getBooks(){
            return this.books;
        }
        public void append(Book book){
            if(book==null)return;
                if(this.books==null) {
                    this.books = new Book[1];
                    this.books[0] = book;
                } else {
                    this.books = Arrays.copyOf(this.books, this.books.length + 1);
                    this.books[this.books.length - 1] = book;
                }

        }
            public boolean existInCatalogue(Book book){
                    if(this.books==null) return false;
                for(Book b : this.books)
                    if(b.equals(book))return true;
                return false;
            }
                public void displayBook(String name, int type){
                    for(int i=0; i<books.length; i++)
                        if (this.books[i].getName().equals(name) && this.books[i].getType() == type) {
                            BookDisplay bookDisplay = new BookDisplay(books[i], actingUser);
                            bookDisplay.setVisible(true);
                        }

                }
                    public void remove(String name, byte type){
                            for(int i=0; i<books.length; i++) {
                                if (this.books[i].getName().equals(name) && this.books[i].getType() == type) {
                                    if (i == books.length - 1) {
                                        this.books[books.length-1] = null;
                                        this.books = Arrays.copyOf(this.books, this.books.length-1);
                                        System.gc();
                                    }
                                    else if(books.length == 1){
                                        this.books[0] = null;
                                        System.gc();
                                        return;
                                    }
                                    else {
                                        this.books[i] = null;
                                        System.arraycopy(this.books, i + 1, this.books, i, this.books.length - (i + 1));
                                        this.books = Arrays.copyOf(this.books, books.length - 1);
                                        System.gc();
                                    }
                                }
                            }

                    }
                        public Book searchForBook(String name, byte type){
                                for(Book i : books)
                                    if(i.getName().equals(name)&&i.getType()==type)
                                        return i;
                                return null;
                        }
                            public void sortByName(){
                                    if(this.books==null||this.books.length==1) return;
                                for(int i=0; i<books.length;i++)
                                    for(int j=i; j<books.length; j++){
                                        if(books[j].getName().compareTo(books[i].getName())<0){
                                            Book p = this.books[i];
                                            this.books[i] = this.books[j];
                                            this.books[j] = p;
                                        }
                                    }
                            }
    // functions

                public User getActingUser() {
        return actingUser;
    }
}
