package com.supprojectstarter.forms;

import com.supprojectstarter.bean.User;
import com.supprojectstarter.dao.DAOException;
import com.supprojectstarter.dao.DAOUser;
import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public final class LoginForm {
    private static final String FIELD_EMAIL = "email";
    private static final String FIELD_PASSWORD = "password";
    private static final String FIELD_EMAIL_ERROR = "email_error_log";
    private static final String FIELD_PASSWORD_ERROR = "password_error_log";
    private static final String ENCRYPTION_ALGORITHM = "SHA-256";
    private String result;
    private Map<String, String> errors = new HashMap<String, String>();

    private static String getFieldValue(HttpServletRequest request, String fieldName) {
        String value = request.getParameter(fieldName);
        if (value == null || value.trim().length() == 0) {
            return null;
        } else {
            return value;
        }
    }

    public String getResult() {
        return result;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public User connectUser(HttpServletRequest request) {
        String email = getFieldValue(request, FIELD_EMAIL);
        String password = getFieldValue(request, FIELD_PASSWORD);
        User user ;
        try {
            if (processEmail(email)) {
                user = DAOUser.getInstance().findByEmail(email);
                processPassword(password, user);
            }
            if (errors.isEmpty()) {
                result = "Vous êtes connecté en tant que " + email;
            } else {
                result = "Echec de la connection.";
            }
        } catch (DAOException e) {
            result = "Echec de la connection : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
            e.printStackTrace();
        }
        return user;
    }

    private Boolean processEmail(String email) {
        try {
            validationEmail(email);
        } catch (FormValidationException e) {
            setError(FIELD_EMAIL_ERROR, e.getMessage());
        }
        if (errors.isEmpty()) {
            return true;
        }
        return false;
    }

    private void processPassword(String password, User user) {
        try {
            validationPassword(password);
        } catch (FormValidationException e) {
            setError(FIELD_PASSWORD_ERROR, e.getMessage());
        }
        ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
        passwordEncryptor.setAlgorithm(ENCRYPTION_ALGORITHM);
        passwordEncryptor.setPlainDigest(false);
        if (!passwordEncryptor.checkPassword(password, user.getPassword()) && errors.isEmpty()) {
            setError(FIELD_PASSWORD_ERROR, "Mot de passe incorrect.");
        }
    }

    private void validationEmail(String email) throws FormValidationException {
        if (email != null) {
            if (!email.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)")) {
                throw new FormValidationException("Merci de saisir une adresse mail valide.");
            } else if (DAOUser.getInstance().findByEmail(email) == null) {
                throw new FormValidationException("Aucun compte n'existe avec cette adresse email.");
            }
        } else {
            throw new FormValidationException("Merci de saisir une adresse mail.");
        }
    }

    private void validationPassword(String password) throws FormValidationException {
        if (password != null) {
            if (password.length() < 3) {
                throw new FormValidationException("Le mot de passe doit contenir au moins 3 caractères.");
            }
        } else {
            throw new FormValidationException("Merci de saisir votre mot de passe.");
        }
    }

    private void setError(String field, String message) {
        errors.put(field, message);
    }
}
