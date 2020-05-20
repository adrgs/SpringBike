package site.springbike.repository;

import site.springbike.crypto.SBCrypt;
import site.springbike.model.User;

public class UserRepository {
    public static User findByNameAndPassword(String name, String password) {

        User user = (User) ModelRepository.useModel(new User()).findByColumnLowerCase("username", name.toLowerCase());
        if (user == null) {
            user = (User) ModelRepository.useModel(new User()).findByColumnLowerCase("email", name.toLowerCase());
            if (user == null) return null;

            if (SBCrypt.checkPassword(password, user.getPassword())) {
                return user;
            }
        } else {
            if (SBCrypt.checkPassword(password, user.getPassword())) {
                return user;
            }
        }

        return null;
    }
}
