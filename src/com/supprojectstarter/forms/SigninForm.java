package com.supprojectstarter.forms;

import com.supprojectstarter.bean.Group;
import com.supprojectstarter.bean.User;
import com.supprojectstarter.dao.DAOException;
import com.supprojectstarter.dao.DAOGroup;
import com.supprojectstarter.dao.DAOUser;
import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public final class SigninForm {
    private static final String FIELD_EMAIL = "email";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_FIRSTNAME = "firstname";
    private static final String FIELD_PASSWORD = "password";
    private static final String FIELD_CONFIRMATION = "confirmation";
    private static final String ENCRYPTION_ALGORITHM = "SHA-256";
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

    public User registerUser(HttpServletRequest request) {
        String email = getFieldValue(request, FIELD_EMAIL);
        String password = getFieldValue(request, FIELD_PASSWORD);
        String confirmation = getFieldValue(request, FIELD_CONFIRMATION);
        String name = getFieldValue(request, FIELD_NAME);
        String firstname = getFieldValue(request, FIELD_FIRSTNAME);
        User user = new User();
        Group groupUser = new Group();
        Group groupAdmin = new Group();
        groupUser.setLabel("User");
        groupAdmin.setLabel("Admin");
        // Test if the groups are already created in database, if not then create them
        if (DAOGroup.getInstance().findByName("Admin") == null) {
            DAOGroup.getInstance().insert(groupAdmin);
        }
        if (DAOGroup.getInstance().findByName("User") == null) {
            DAOGroup.getInstance().insert(groupUser);
        }
        // The first user to signin is set to Admin, after that others are normal users by default
        if (DAOUser.getInstance().findAll().isEmpty()) {
            user.setGroup(DAOGroup.getInstance().findByName("Admin"));
        } else {
            user.setGroup(DAOGroup.getInstance().findByName("User"));
        }
        try {
            processEmail(email, user);
            processPassword(password, confirmation, user);
            processName(name, user);
            processFirstname(firstname, user);
            if (errors.isEmpty()) {
                DAOUser.getInstance().insert(user);
                result = "Succès de l'inscription.";
            } else {
                result = "Echec de l'inscription.";
            }
        } catch (DAOException e) {
            result = "Echec de l'inscription : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
            e.printStackTrace();
        }
        return user;
    }

    private void processEmail(String email, User user) {
        try {
            validationEmail(email);
        } catch (FormValidationException e) {
            setErreur(FIELD_EMAIL, e.getMessage());
        }
        user.setMailAddress(email);
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

    private void validationEmail(String emailParam) throws FormValidationException {
        String email = emailParam.trim();
        if (email != null) {
            if (!email.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)")) {
                throw new FormValidationException("Merci de saisir une adresse mail valide.");
            } else if (DAOUser.getInstance().findByEmail(email) != null) {
                throw new FormValidationException("Cette adresse email est déjà utilisée, merci d'en choisir une autre.");
            }
        } else {
            throw new FormValidationException("Merci de saisir une adresse mail.");
        }
    }

    private void validationPassword(String passwordParam, String confirmationParam) throws FormValidationException {
        String password = passwordParam.trim();
        String confirmation = confirmationParam.trim();
        if (password != null && confirmation != null) {
            if (!password.equals(confirmation)) {
                throw new FormValidationException("Les mots de passe entrés sont différents, merci de les saisir à nouveau.");
            } else if (password.length() < 3) {
                throw new FormValidationException("Les mots de passe doivent contenir au moins 3 caractères.");
            }
        } else {
            throw new FormValidationException("Merci de saisir et confirmer votre mot de passe.");
        }
    }

    private void validationName(String nameParam) throws FormValidationException {
        String name = nameParam.trim();
        if (name != null && name.length() < 3) {
            throw new FormValidationException("Le name d'user doit contenir au moins 3 caractères.");
        }
    }

    private void validationFirstname(String firstnameParam) throws FormValidationException {
        String firstname = firstnameParam.trim();
        if (firstname != null && firstname.length() < 3) {
            throw new FormValidationException("Le firstname d'user doit contenir au moins 3 caractères.");
        }
    }

    private void setErreur(String field, String message) {
        errors.put(field, message);
    }
}
