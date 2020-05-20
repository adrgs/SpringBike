package site.springbike.session;

import com.google.gson.Gson;

public class UserSession {
    public Integer id;
    public String hmac;

    public UserSession(Integer id, String hmac) {
        this.id = id;
        this.hmac = hmac;
    }

    public UserSession(Integer id) {
        this.id = id;
    }

    public UserSession() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHmac() {
        return hmac;
    }

    public void setHmac(String hmac) {
        this.hmac = hmac;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
