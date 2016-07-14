package de.hanbei.dwserver.auth;

import com.google.common.base.Optional;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.Authorizer;
import io.dropwizard.auth.basic.BasicCredentials;

import java.util.HashMap;
import java.util.Map;

public class BasicAuthAutenticator implements Authenticator<BasicCredentials, User>, Authorizer<User> {

    private final Map<String, String> credentials;

    public BasicAuthAutenticator() {
        this.credentials = new HashMap<>();
        this.credentials.put("amazon", "amazon");
        this.credentials.put("become", "become");
        this.credentials.put("billiger", "billiger");
        this.credentials.put("buscape", "buscape");
        this.credentials.put("ebay", "ebay");
        this.credentials.put("fred", "fred");
        this.credentials.put("kelkoo", "kelkoo");
        this.credentials.put("leguide", "leguide");
        this.credentials.put("mercadolibre", "mercadolibre");
        this.credentials.put("pricegrabber", "pricegrabber");
        this.credentials.put("shopmania", "shopmania");
        this.credentials.put("shoppingcom", "shoppingcom");
        this.credentials.put("shopzilla", "shopzilla");
        this.credentials.put("skapiec", "skapiec");
        this.credentials.put("twenga", "twenga");
        this.credentials.put("zoom", "zomm");
        this.credentials.put("dummy", "dummy");
    }

    @Override
    public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
        String username = credentials.getUsername();
        if (isAuthenticable(username, credentials.getPassword())) {
            return Optional.of(new User(username));
        }
        return Optional.absent();
    }

    private boolean isAuthenticable(String username, String password) {
        return Optional.fromNullable(credentials.get(username)).or("").equals(password);
    }

    @Override
    public boolean authorize(User principal, String role) {
        return credentials.get(principal.getName()) != null;
    }

}
