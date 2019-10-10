package jv.usinggdm.Book;

import java.util.Objects;

public class Book {
    // variables
    private String name;
        private String author;
            private String publisher;
                private String description;
                    private int releaseYear;
                        private int type = 0; // 0 - paper book, 1 - eBook, default - paper book.
    // variables

        Book(byte t){
            this.type = t;
        }
        public Book(){}

    // Getters and Setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getPublisher() {
        return publisher;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    public int getRelease() {
        return releaseYear;
    }
    public void setRelease(int releaseYear) {
        this.releaseYear = releaseYear;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    // Getters and Setters

    // Equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return getType() == book.getType() &&
                Objects.equals(getName(), book.getName()) &&
                Objects.equals(getAuthor(), book.getAuthor()) &&
                Objects.equals(getPublisher(), book.getPublisher()) &&
                Objects.equals(getRelease(), book.getRelease()) &&
                Objects.equals(getDescription(), book.getDescription());
    }
    @Override
    public int hashCode() {
        return Objects.hash(getName(), getAuthor(), getPublisher(), getRelease(), getType(), getDescription());
    }
    // Equals and hashCode

}
