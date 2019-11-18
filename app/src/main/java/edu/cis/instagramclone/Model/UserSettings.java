package edu.cis.instagramclone.Model;

/**
 * Created by User on 6/30/2017.
 */

public class UserSettings { /*** TODO 3c: Create instance variables, constructor, and a setSettings method ***/



    public UserSettings() {

    }



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserAccountSettings getSettings() {
        return settings;
    }


    @Override
    public String toString() {
        return "UserSettings{" +
                "user=" + user +
                ", settings=" + settings +
                '}';
    }
}
