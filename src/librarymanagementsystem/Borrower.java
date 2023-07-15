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
public class Borrower {

    public int id;
    public String name;
    public String book;
    public LocalDate borrowAt;
    public LocalDate returnAt;

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getBook() {
        return this.book;
    }

    public LocalDate getBorrowAt() {
        return this.borrowAt;
    }

    public LocalDate getReturnAt() {
        return this.returnAt;
    }

}
