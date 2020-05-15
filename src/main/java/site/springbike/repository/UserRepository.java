package site.springbike.repository;

import site.springbike.model.User;

public class UserRepository {

    public static User findByNameAndPassword(String name, String password) {

        User user = (User) ModelRepository.useModel(new User()).findByColumn("username", name.toLowerCase());
        if (user == null) {
            user = (User) ModelRepository.useModel(new User()).findByColumn("email", name.toLowerCase());

        }


        return null;
    }

}
