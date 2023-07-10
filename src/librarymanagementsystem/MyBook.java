/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;

import java.time.LocalDate;

/**
 *
 * @author AAR8
 */
public class MyBook {

    public int id;
    public String name;
    public String author;
    public String publisher;
    public LocalDate releaseDate;
    public LocalDate createdOn;

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public LocalDate getReleaseColumn() {
        return this.releaseDate;
    }

    public LocalDate getCreatedOn() {
        return this.createdOn;
    }
}
