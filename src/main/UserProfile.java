package main;

import java.io.*;

public class UserProfile implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private int maxLevelUnlocked;

    public UserProfile(String username) {
        this.username = username;
        this.maxLevelUnlocked = 1; // Start with level 1 unlocked
    }

    public String getUsername() {
        return username;
    }

    public int getMaxLevelUnlocked() {
        return maxLevelUnlocked;
    }

    public void unlockNextLevel() {
        if (maxLevelUnlocked < 3) {
            maxLevelUnlocked++;
        }
    }

    public static UserProfile loadProfile(String username) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(username + ".profile"))) {
            return (UserProfile) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new UserProfile(username);
        }
    }

    public void saveProfile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(username + ".profile"))) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}