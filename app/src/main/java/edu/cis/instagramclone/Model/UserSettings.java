package edu.cis.instagramclone.Model;

/**
 * Created by User on 6/30/2017.
 */

public class UserSettings { //3c: Create instance variables, constructor, and a setSettings method

    private User user;
    private UserAccountSettings settings;

    public UserSettings() {

    }

    public UserSettings(User inUser, UserAccountSettings inSettings)
    {
        user = inUser;
        settings = inSettings;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setSettings(UserAccountSettings settings)
    {
        this.settings = settings;
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
