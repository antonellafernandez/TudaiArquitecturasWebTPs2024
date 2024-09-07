package com.dao.example.tudai;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UserDao implements Dao<User> {
    
    private List<User> users = new ArrayList<User>();
    
    public UserDao() {
        users.add(new User("John", "john@domain.com"));
        users.add(new User("Susan", "susan@domain.com"));
    }
    

    public Optional<User> get(long id) {
        return Optional.ofNullable(users.get((int) id));
    }
    
  
    public List<User> getAll() {
        return users;
    }
    

    public void save(User user) {
        users.add(user);
    }
    
   
    public void update(User user, String[] params) {
        user.setName(Objects.requireNonNull(
          params[0], "Name cannot be null"));
        user.setEmail(Objects.requireNonNull(
          params[1], "Email cannot be null"));
        
        users.add(user);
    }
    

    public void delete(User user) {
        users.remove(user);
    }
}
