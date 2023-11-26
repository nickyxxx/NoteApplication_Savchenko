// Note.java
package com.example.noteapplication_savchenko;

public class Note {
    private String name;
    private String description;

    public Note() {
        // Empty constructor needed for Firebase
    }

    public Note(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return name + "\n" + description;
    }
}
