package by.maiseichyk.finalproject.entity;

import java.util.Objects;

public class User extends AbstractEntity {
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private UserType role;
    private String email;
    private double balance;

    public User() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserType getRole() {
        return role;
    }

    public void setRole(UserType role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public static class UserBuilder {
        private final User user;

        public UserBuilder() {
            user = new User();
        }

        public UserBuilder setLogin(String login) {
            user.login = login;
            return this;
        }
        public UserBuilder setPassword(String password) {
            user.password = password;
            return this;
        }

        public UserBuilder setLastName(String lastName) {
            user.lastName = lastName;
            return this;
        }

        public UserBuilder setName(String name) {
            user.firstName = name;
            return this;
        }

        public UserBuilder setEmail(String email) {
            user.email = email;
            return this;
        }

        public UserBuilder setUserRole(UserType userRole) {
            user.role = userRole;
            return this;
        }

        public  UserBuilder setBalance(double balance){
            user.balance = balance;
            return this;
        }

        public User build() {
            return user;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Double.compare(user.balance, balance) == 0 && Objects.equals(login, user.login) && Objects.equals(password, user.password) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && role == user.role && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, firstName, lastName, role, email, balance);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName()).append("{");
        sb.append("login='").append(login);
        sb.append("', password='").append(password);
        sb.append("', name='").append(firstName);
        sb.append("', surname='").append(lastName);
        sb.append(", role=").append(role);
        sb.append(", email=").append(email).append("}");
        return sb.toString();
    }
}
