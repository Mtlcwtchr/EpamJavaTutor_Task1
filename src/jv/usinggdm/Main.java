package jv.usinggdm;

import jv.usinggdm.Book.*;
import jv.usinggdm.Users.User;


public class Main {

    public static void main(String[] args) {
            Book book = new Book();
            book.setName("Harry Potter and The Philosopher's stone");
            book.setAuthor("J.K. Rowling");
            book.setPublisher("New York publishers group");
            book.setRelease(1997);
            User user = new User(0);
                user.setAccessModifier((byte)1);
                BookCatalogue someCatalogue = new BookCatalogue(user);
                    someCatalogue.append(book);
                BookCatalogueDisplay bookCatalogueDisplay = new BookCatalogueDisplay(someCatalogue);
                 bookCatalogueDisplay.setVisible(true);
    }
}
