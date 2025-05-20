package com.TillDawn.Controllers;

import com.TillDawn.Models.App;
import com.TillDawn.Models.Result;
import com.TillDawn.Models.User;

public class SignUpMenuController {
    private boolean isPasswordStrong(String password){
        if (password.length() < 8)
            return false;
        boolean hasUpper = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        String specialChars = "@#$%&*)(_";

        for (char c : password.toCharArray()){
            if (Character.isUpperCase(c))
                hasUpper = true;
            else if (Character.isDigit(c))
                hasDigit = true;
            else if (specialChars.indexOf(c) >= 0)
                hasSpecial = true;
        }

        return hasSpecial && hasDigit && hasUpper;
    }

    public Result signUp(String username, String password){
        if (username == null || username.isEmpty())
            return new Result(false, "Username can't be empty!");
        if (User.getUserByUserName(username) != null)
            return new Result(false, "Username exists! choose another one!");
        if (password == null || password.isEmpty() || !isPasswordStrong(password))
            return new Result(false, "Password is weak!");
        User user = new User(username, password);
        App.getApp().getUsers().add(user);
        return new Result(true, "User created successfully!");
    }
}
