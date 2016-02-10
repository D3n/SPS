package com.supprojectstarter.forms;

import com.supprojectstarter.bean.Category;
import com.supprojectstarter.bean.Project;
import com.supprojectstarter.bean.User;
import com.supprojectstarter.dao.DAOCategory;
import com.supprojectstarter.dao.DAOException;
import com.supprojectstarter.dao.DAOProject;
import com.supprojectstarter.dao.DAOUser;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public final class EditProjectForm {
    private static final String FIELD_ID = "id";
    private static final String FIELD_USER = "creator";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_AMOUNT = "amount";
    private static final String FIELD_DESCRIPTION = "description";
    private static final String FIELD_STARTDATE = "dateStart";
    private static final String FIELD_ENDDATE = "dateEnd";
    private static final String FIELD_CATEGORY = "category";
    private String result;
    private Map<String, String> errors = new HashMap<String, String>();

    private static String getFieldValue(HttpServletRequest request, String fieldName) {
        String value = request.getParameter(fieldName).trim();
        if (value == null || value.length() == 0) {
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

    public Project editProject(HttpServletRequest request, String originCreation) {
        String idString = getFieldValue(request, FIELD_ID);
        String name = getFieldValue(request, FIELD_NAME);
        String amount = getFieldValue(request, FIELD_AMOUNT);
        String description = getFieldValue(request, FIELD_DESCRIPTION);
        String startDate = getFieldValue(request, FIELD_STARTDATE);
        String endDate = getFieldValue(request, FIELD_ENDDATE);
        String category = getFieldValue(request, FIELD_CATEGORY);
        Project project ;
        DateTime today = new DateTime();
        String user ;
        if (originCreation.equals("admin")) {
            user = getFieldValue(request, FIELD_USER);
        }
        try {
            project = processId(idString);
            if (project.StartDateTime().isBefore(today) && originCreation.equals("user")) {
                setErreur(FIELD_STARTDATE, "Vous ne pouvez modifier un projet qui à déjà commencer.");
            }
            processName(name, project);
            processAmount(amount, project);
            processDescription(description, project);
            processStartDate(startDate, project);
            processEndDate(endDate, project);
            processCategory(category, project);
            if (originCreation == "admin") {
                processUser(user, project);
            }
            if (errors.isEmpty()) {
                DAOProject.getInstance().update(project);
                result = "Projet modifié.";
            } else {
                result = "Echec, projet non modifié.";
            }
        } catch (DAOException e) {
            result = "Echec de l'inscription : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
            e.printStackTrace();
        }
        return project;
    }

    private Project processId(String id) {
        int i = -1;
        try {
            i = validationId(id);
        } catch (FormValidationException e) {
            setErreur(FIELD_ID, e.getMessage());
        }
        if (i != -1 && errors.isEmpty()) {
            return DAOProject.getInstance().find(i);
        }
        return null;
    }

    private void processUser(String user, Project project) {
        String emailUser = "";
        try {
            emailUser = validationUser(user);
        } catch (FormValidationException e) {
            setErreur(FIELD_USER, e.getMessage());
        }
        User usr = DAOUser.getInstance().findByEmail(emailUser);
        project.setCreatorUser(usr);
    }

    private void processName(String name, Project project) {
        try {
            validationName(name);
        } catch (FormValidationException e) {
            setErreur(FIELD_NAME, e.getMessage());
        }
        project.setName(name);
    }

    private void processDescription(String description, Project project) {
        try {
            validationDescription(description);
        } catch (FormValidationException e) {
            setErreur(FIELD_DESCRIPTION, e.getMessage());
        }
        project.setDescription(description);
    }

    private void processAmount(String amount, Project project) {
        int a = -1;
        try {
            a = validationAmount(amount);
        } catch (FormValidationException e) {
            setErreur(FIELD_AMOUNT, e.getMessage());
        }
        project.setAmountP(a);
    }

    private void processStartDate(String startDate, Project project) {
        DateTime sD ;
        try {
            sD = validationStartDate(startDate);
        } catch (FormValidationException e) {
            setErreur(FIELD_STARTDATE, e.getMessage());
        }
        project.StartDateWithDateTime(sD);
    }

    private void processEndDate(String endDate, Project project) {
        DateTime eD ;
        try {
            eD = validationEndDate(endDate);
        } catch (FormValidationException e) {
            setErreur(FIELD_ENDDATE, e.getMessage());
        }
        project.EndDateWithDateTime(eD);
    }

    private void processCategory(String category, Project project) {
        int idCateg = -1;
        try {
            idCateg = validationCategory(category);
        } catch (FormValidationException e) {
            setErreur(FIELD_CATEGORY, e.getMessage());
        }
        Category cat = DAOCategory.getInstance().find(idCateg);
        project.setCategory(cat);
    }

    private int validationId(String idParam) throws FormValidationException {
        int a = -1;
        String id = idParam.trim();
        if (id.isEmpty()) {
            throw new FormValidationException("ID de projet vide.");
        } else {
            try {
                a = Integer.parseInt(id);
            } catch (Exception e) {
                throw new FormValidationException("ID de projet non valide.");
            }
        }
        if (DAOProject.getInstance().find(a) == null) {
            throw new FormValidationException("Cet ID de projet n'existe pas.");
        }
        return a;
    }

    private String validationUser(String userParam) throws FormValidationException {
        String emailUser = "";
        if (userParam == null || userParam.trim().length() == 0) {
            throw new FormValidationException("Vous n'avez pas sélectionner d'utilisateur");
        } else {
            emailUser = userParam.trim();
            if (DAOUser.getInstance().findByEmail(emailUser) == null) {
                throw new FormValidationException("L'utilisateur n'existe pas.");
            }
        }
        return emailUser;
    }

    private int validationCategory(String categoryParam) throws FormValidationException {
        int idCateg = -1;
        String category = categoryParam.trim();
        try {
            idCateg = Integer.parseInt(category);
        } catch (Exception e) {
            throw new FormValidationException("La catégorie n'est pas valide.");
        }
        return idCateg;
    }

    private void validationName(String nameParam) throws FormValidationException {
        String name = nameParam.trim();
        if (name != null && name.length() < 3) {
            throw new FormValidationException("Le nom du projet doit faire plus de 3 caractères.");
        }
    }

    private int validationAmount(String amountParam) throws FormValidationException {
        int a = -1;
        String amount = amountParam.trim();
        if (amount.isEmpty()) {
            throw new FormValidationException("Veuillez saisir un montant à atteindre pour ce projet.");
        } else {
            try {
                a = Integer.parseInt(amount);
                if (a <= 0) {
                    throw new FormValidationException("Le montant à atteindre n'est pas correct.");
                }
            } catch (Exception e) {
                throw new FormValidationException("Le montant à atteindre n'est pas correct.");
            }
        }
        return a;
    }

    private void validationDescription(String descriptionParam) throws FormValidationException {
        String description = descriptionParam.trim();
        if (description != null && description.length() < 10) {
            throw new FormValidationException("Le description du projet doit faire plus de 10 caractères.");
        }
    }

    private DateTime validationStartDate(String startDateParam) throws FormValidationException {
        DateTimeFormatter formatter;
        DateTime dt;
        String startDate = startDateParam.trim();
        if (startDate.isEmpty()) {
            throw new FormValidationException("Merci de saisir une date de début.");
        } else {
            formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");
            dt = formatter.parseDateTime(startDate);
        }
        return dt;
    }

    private DateTime validationEndDate(String endDateParam) throws FormValidationException {
        DateTimeFormatter formatter;
        DateTime dt;
        String endDate = endDateParam.trim();
        if (endDate.isEmpty()) {
            throw new FormValidationException("Merci de saisir une date de fin.");
        } else {
            formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");
            dt = formatter.parseDateTime(endDate);
        }
        return dt;
    }

    private void setErreur(String field, String message) {
        errors.put(field, message);
    }
}
