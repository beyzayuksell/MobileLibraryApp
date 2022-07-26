package com.beyzayuksel.libraryapp;

// Model
// Data for Database

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

// Define Table
@Entity(tableName = "table_name")
public class MainData implements Serializable {

    // Create id column in table
    @PrimaryKey(autoGenerate = true)
    private int ID;

    @ColumnInfo(name = "text")
    private String text;

    // Getter - Setter
    // For Variable Update

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
