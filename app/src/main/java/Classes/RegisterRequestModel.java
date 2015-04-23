package Classes;

/**
 * Created by mertocan on 23/04/2015.
 */
public class RegisterRequestModel {
    public String MobileId;
    public String Email;

    public String getProvider() {
        return Provider;
    }

    public String getMobileId() {
        return MobileId;
    }

    public String getEmail() {
        return Email;
    }

    public String getToken() {
        return Token;
    }

    public String Token;

    public void setProvider(String provider) {
        Provider = provider;
    }

    public void setToken(String token) {
        Token = token;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setMobileId(String mobileId) {
        MobileId = mobileId;
    }

    public String Provider;

}
