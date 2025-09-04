package edu.ccrm.domain;

import java.time.LocalDate;

// Abstract class
public abstract class Person {
    protected String id;
    protected String fullName;
    protected String email;
    protected boolean active;
    protected LocalDate createdAt;

    public Person(String id, String fullName, String email) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.createdAt = LocalDate.now();
        this.active = true;
    }

    public abstract String getProfile();

    // Getters/Setters
    public String getId() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public boolean isActive() { return active; }
    public LocalDate getCreatedAt() { return createdAt; }

    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setEmail(String email) { this.email = email; }
    public void setActive(boolean active) { this.active = active; }

    // toString override (demonstrates polymorphism)
    @Override
    public String toString() {
        return String.format("ID: %s, Name: %s, Email: %s, Active: %b, Since: %s",
            id, fullName, email, active, createdAt);
    }
}
