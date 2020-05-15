package site.springbike.cache;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import site.springbike.model.Client;
import site.springbike.model.Company;
import site.springbike.model.User;
import site.springbike.repository.ModelRepository;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserCacheManager {

    private static UserCacheManager instance;
    private ConcurrentHashMap<Integer, User> cacheMap;

    private UserCacheManager() {
        cacheMap = new ConcurrentHashMap<>();
    }

    public static UserCacheManager getInstance() {
        return Loader.INSTANCE;
    }

    private static class Loader {
        static final UserCacheManager INSTANCE = new UserCacheManager();
    }

    public User getUser(Integer id) {
        if (cacheMap.containsKey(id)) {
            return cacheMap.get(id);
        } else {
            User user = (User) ModelRepository.useModel(new User()).selectByPrimaryKey(id);
            if (user.getType().equals("Company")) {
                user = (Company) ModelRepository.useModel(new Company()).selectByPrimaryKey(id);
            } else if (user.getType().equals("Client")) {
                user = (Client) ModelRepository.useModel(new Client()).selectByPrimaryKey(id);
            }
            cacheMap.put(id, user);
            return user;
        }
    }

    public void putUser(User user) {
        cacheMap.put(user.getId(), user);
    }
}
