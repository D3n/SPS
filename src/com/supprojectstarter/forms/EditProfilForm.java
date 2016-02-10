package com.supprojectstarter.forms;

import com.supprojectstarter.bean.User;
import com.supprojectstarter.dao.DAOException;
import com.supprojectstarter.dao.DAOUser;
import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public final class EditProfilForm {
    private static final String FIELD_NAME = "name";
    private static final String FIELD_FIRSTNAME = "firstname";
    private static final String FIELD_PASSWORD = "password";
    private static final String FIELD_CONFIRMATION = "confirmation";
    private static final String ENCRYPTION_ALGORITHM = "SHA-256";
    private static final String ATT_SESSION = "sessionUser";
    private String result;
    private Map<String, String> errors = new HashMap<String, String>();

    private static String getFieldValue(HttpServletRequest request, String fieldName) {
        String value = request.getParameter(fieldName);
        if (value == null || value.trim().length() == 0) {
            return null;
        } else {
            return value.trim();
        }
    }

    public String getResult() {
        return result;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public User editProfile(HttpServletRequest request) {
        User userAuth = (User) request.getSession().getAttribute(ATT_SESSION);
        String password = getFieldValue(request, FIELD_PASSWORD);
        String confirmation = getFieldValue(request, FIELD_CONFIRMATION);
        String name = getFieldValue(request, FIELD_NAME);
        String firstname = getFieldValue(request, FIELD_FIRSTNAME);
        try {
            if (password != null && confirmation != null) {
                processPassword(password, confirmation, userAuth);
            }
            processName(name, userAuth);
            processFirstname(firstname, userAuth);
            if (errors.isEmpty()) {
                DAOUser.getInstance().update(userAuth);
                result = "Profil modifié.";
            } else {
                result = "Echec de la modification de votre profil.";
            }
        } catch (DAOException e) {
            result = "Echec de la modification : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
            e.printStackTrace();
        }
        return userAuth;
    }

    private void processName(String name, User user) {
        try {
            validationName(name);
        } catch (FormValidationException e) {
            setErreur(FIELD_NAME, e.getMessage());
        }
        user.setName(name);
    }

    private void processFirstname(String Firstname, User user) {
        try {
            validationFirstname(Firstname);
        } catch (FormValidationException e) {
            setErreur(FIELD_FIRSTNAME, e.getMessage());
        }
        user.setFirstname(Firstname);
    }

    private void processPassword(String password, String confirmation, User user) {
        try {
            validationPassword(password, confirmation);
        } catch (FormValidationException e) {
            setErreur(FIELD_PASSWORD, e.getMessage());
            setErreur(FIELD_CONFIRMATION, null);
        }
        ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
        passwordEncryptor.setAlgorithm(ENCRYPTION_ALGORITHM);
        passwordEncryptor.setPlainDigest(false);
        String EncryptedPassword = passwordEncryptor.encryptPassword(password);
        user.setPassword(EncryptedPassword);
    }

    private void validationPassword(String passwordParam, String confirmationParam) throws FormValidationException {
        String password = passwordParam.trim();
        String confirmation = confirmationParam.trim();
        if (!password.equals(confirmation)) {
            throw new FormValidationException("Les mots de passe entrés sont différents, merci de les saisir à nouveau.");
        } else if (password.length() < 3) {
            throw new FormValidationException("Les mots de passe doivent contenir au moins 3 caractères.");
        }
    }

    private void validationName(String name) throws FormValidationException {
        if (name != null && name.trim().length() < 3) {
            throw new FormValidationException("Le nom doit contenir au moins 3 caractères.");
        }
    }

    private void validationFirstname(String firstname) throws FormValidationException {
        if (firstname != null && firstname.trim().length() < 3) {
            throw new FormValidationException("Le prénom doit contenir au moins 3 caractères.");
        }
    }

    private void setErreur(String field, String message) {
        errors.put(field, message);
    }
}
