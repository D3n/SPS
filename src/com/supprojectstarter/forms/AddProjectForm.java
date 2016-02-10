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
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public final class AddProjectForm {
    private static final String ATT_SESSION = "sessionUser";
    private static final String FIELD_USER = "user";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_AMOUNT = "amount";
    private static final String FIELD_DESCRIPTION = "description";
    private static final String FIELD_STARTDATE = "dateStart";
    private static final String FIELD_ENDDATE = "dateEnd";
    private static final String FIELD_CATEGORY = "category";
    private String result;
    private Map<String, String> errors = new HashMap<String, String>();
    private DateTime sDGlobal;
    private DateTime eDGlobal;

    private static String getFieldValue(HttpServletRequest request, String fieldName) {
        String value ;
        value = request.getParameter(fieldName);
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

    public Project createProject(HttpServletRequest request, String originCreation) {
        String name = getFieldValue(request, FIELD_NAME);
        String amount = getFieldValue(request, FIELD_AMOUNT);
        String description = getFieldValue(request, FIELD_DESCRIPTION);
        String startDate = getFieldValue(request, FIELD_STARTDATE);
        String endDate = getFieldValue(request, FIELD_ENDDATE);
        String category = getFieldValue(request, FIELD_CATEGORY);
        Project project = new Project();
        User creator ;
        String user ;
        if (originCreation.equals("admin")) {
            user = getFieldValue(request, FIELD_USER);
        } else {
            HttpSession session = request.getSession();
            creator = (User) session.getAttribute(ATT_SESSION);
        }
        int id = -1;
        DateTime creationDate = new DateTime();
        try {
            processName(name, project);
            processAmount(amount, project);
            processDescription(description, project);
            processStartDate(startDate, project);
            processEndDate(endDate, project);
            processCategory(category, project);
            if (originCreation.equals("admin")) {
                processUser(user, project);
            } else {
                project.setCreatorUser(creator);
            }
            project.CreationDateWithDateTime(creationDate);
            if (errors.isEmpty()) {
                System.out.println("ERRORS : " + errors.toString());
                id = DAOProject.getInstance().insert(project);
                project.setId(id);
                result = "Projet ajouté.";
            } else {
                result = "Echec, projet non ajouté.";
            }
        } catch (DAOException e) {
            result = "Echec de l'inscription : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
            e.printStackTrace();
        }
        return project;
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
        sDGlobal = sD;
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

    private void processUser(String user, Project project) {
        int idUser = -1;
        try {
            idUser = validationUser(user);
        } catch (FormValidationException e) {
            setErreur(FIELD_USER, e.getMessage());
        }
        User usr = DAOUser.getInstance().find(idUser);
        project.setCreatorUser(usr);
    }

    private int validationCategory(String categoryParam) throws FormValidationException {
        int idCateg = -1;
        String category ;
        if (categoryParam == null || categoryParam.trim().length() == 0) {
            throw new FormValidationException("Vous n'avez pas sélectionner de catégorie.");
        } else {
            category = categoryParam.trim();
            try {
                idCateg = Integer.parseInt(category);
            } catch (Exception e) {
                throw new FormValidationException("La catégorie n'est pas valide.");
            }
            if (DAOCategory.getInstance().find(idCateg) == null) {
                throw new FormValidationException("La catégorie n'existe pas.");
            }
        }
        return idCateg;
    }

    private int validationUser(String userParam) throws FormValidationException {
        int idUser = -1;
        if (userParam == null || userParam.trim().length() == 0) {
            throw new FormValidationException("Vous n'avez pas sélectionner d'utilisateur");
        } else {
            try {
                idUser = Integer.parseInt(userParam.trim());
            } catch (Exception e) {
                throw new FormValidationException("L'utilisateur n'est pas valide.");
            }
            if (DAOUser.getInstance().find(idUser) == null) {
                throw new FormValidationException("L'utilisateur n'existe pas.");
            }
        }
        return idUser;
    }

    private void validationName(String nameParam) throws FormValidationException {
        String name = nameParam.trim();
        if (!name.isEmpty() && name.length() < 3) {
            throw new FormValidationException("Le nom du projet doit faire plus de 3 caractères.");
        } else if (DAOProject.getInstance().findByName(name) != null) {
            throw new FormValidationException("Ce projet existe déjà, nommez le différemment .");
        }
    }

    private int validationAmount(String amountParam) throws FormValidationException {
        int a = -1;
        String amount = amountParam.trim();
        if (amount == null || amount.equals("")) {
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
            eDGlobal = dt;
        }
        if (eDGlobal.isBefore(sDGlobal)) {
            throw new FormValidationException("La date de fin ne peut pas être inférieure à la date de début.");
        }
        return dt;
    }

    private void setErreur(String field, String message) {
        errors.put(field, message);
    }
}
