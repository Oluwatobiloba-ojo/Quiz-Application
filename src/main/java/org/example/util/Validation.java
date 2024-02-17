package org.example.util;

public class Validation {
    public static boolean validateEmail(String email) {
        return email.matches("[A-Za-z0-9]+@[a-z]+.[a-z]{3}");
    }

    public static boolean validatePassword(String password) {
        return password.matches("[A-Z][a-z]{5,}[0-9]{2,}[!@$%&*+()_-{}:|]*");
    }

    public static boolean validateDate(String dateOfBirth) {
        if (dateOfBirth.matches("[1-9][0-9][0-9][0-9]/[0-9][0-9]/[0-3][0-9]")) {
            String [] date = dateOfBirth.split("/");
            if (Integer.parseInt(date[2]) < 0 || Integer.parseInt(date[2]) >31) return false;
            if (Integer.parseInt(date[1]) < 0 || Integer.parseInt(date[1]) > 12) return false;
            return true;
        }
        return false;
    }

}
