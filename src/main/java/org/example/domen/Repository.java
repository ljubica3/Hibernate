package org.example.domen;

import java.util.HashMap;
import java.util.Map;

public class Repository {

    public static class UserRepository {
        private Map<Long, User> userMap = new HashMap<>();

        public void createUser(User user) {
            userMap.put(user.getId(), user);
        }

        public User getUserById(Long id) {
            return userMap.get(id);
        }

        public void updateUser(User user) {
            userMap.put(user.getId(), user);
        }

        public void deleteUser(Long id) {
            userMap.remove(id);
        }

        public boolean existsById(Long id) {
            return userMap.containsKey(id);
        }
    }

}
