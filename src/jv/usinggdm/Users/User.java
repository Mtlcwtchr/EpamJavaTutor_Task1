package jv.usinggdm.Users;

import java.io.Serializable;

public class User implements Serializable {
    private int ID;
        private String username = "";
            private int accessModifier = 0; // 0 if user, 1 if admin, 0 is default
                private String email;

        public User(int ID){
            this.ID = ID;
        }

    // Getters and Setters
    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public int getAccessModifier() {
        return accessModifier;
    }
    public void setAccessModifier(int accessModifier) {
        this.accessModifier = accessModifier;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    // Getters and Setters

}
