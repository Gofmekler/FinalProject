package by.maiseichyk.finalproject.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class User extends AbstractEntity {
    private String login;
    private String password;
    private String firstname;
    private String lastname;
    private UserRole role;
    private String email;
    private BigDecimal balance;

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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public static class UserBuilder {
        private final User user;

        public UserBuilder() {
            user = new User();
        }

        public UserBuilder setId(int id){
            user.setId(id);
            return this;
        }

        public UserBuilder setLogin(String login) {
            user.login = login;
            return this;
        }
        public UserBuilder setPassword(String password) {
            user.password = password;
            return this;
        }

        public UserBuilder setLastname(String lastname) {
            user.lastname = lastname;
            return this;
        }

        public UserBuilder setName(String name) {
            user.firstname = name;
            return this;
        }

        public UserBuilder setEmail(String email) {
            user.email = email;
            return this;
        }

        public UserBuilder setUserRole(UserRole userRole) {
            user.role = userRole;
            return this;
        }

        public  UserBuilder setBalance(BigDecimal balance){
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
        return Objects.equals(login, user.login) && Objects.equals(password, user.password) && Objects.equals(firstname, user.firstname) && Objects.equals(lastname, user.lastname) && role == user.role && Objects.equals(email, user.email) && Objects.equals(balance, user.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, firstname, lastname, role, email, balance);
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", role=" + role +
                ", email='" + email + '\'' +
                ", balance=" + balance +
                '}';
    }
}
