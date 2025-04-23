package zadatak02;

import java.util.HashMap;
import java.util.Map;

public class repository {


    public static class UserRepository {
        private Map<Integer, User> userMap = new HashMap<>();

        public void createUser(User user) {
            userMap.put(user.getId(), user);
        }

        public User getUserById(int id) {
            return userMap.get(id);
        }

        public void updateUser(User user) {
            userMap.put(user.getId(), user);
        }

        public void deleteUser(int id) {
            userMap.remove(id);
        }

        public boolean existsById(int id) {
            return userMap.containsKey(id);
        }
    }

}
