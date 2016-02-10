package com.supprojectstarter.forms;

import com.supprojectstarter.bean.Contribution;
import com.supprojectstarter.bean.Project;
import com.supprojectstarter.bean.User;
import com.supprojectstarter.dao.DAOContribution;
import com.supprojectstarter.dao.DAOException;
import com.supprojectstarter.dao.DAOProject;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public final class ContributionForm {
    private static final String FIELD_ID_PROJECT = "idP";
    private static final String FIELD_AMOUNT = "amountC";
    private static final String ATT_SESSION = "sessionUser";
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

    public Contribution createContribution(HttpServletRequest request) {
        String idString = getFieldValue(request, FIELD_ID_PROJECT);
        String amountC = getFieldValue(request, FIELD_AMOUNT);
        Contribution contribution = new Contribution();
        Project project ;
        User userAuth = (User) request.getSession().getAttribute(ATT_SESSION);
        try {
            contribution.setProject(processId(idString, project));
            processAmount(amountC, contribution);
            contribution.setContributorUser(userAuth);
            if (errors.isEmpty()) {
                DAOContribution.getInstance().insert(contribution);
                result = "Contribution prise en compte.";
            } else {
                result = "Echec, contribution non prise en compte.";
            }
        } catch (DAOException e) {
            result = "Echec lors de la contribution : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
            e.printStackTrace();
        }
        return contribution;
    }

    private Project processId(String id, Project project) {
        int i = -1;
        try {
            i = validationId(id);
        } catch (FormValidationException e) {
            setErreur(FIELD_ID_PROJECT, e.getMessage());
        }
        if (i != -1 && errors.isEmpty()) {
            return DAOProject.getInstance().find(i);
        }
        return project;
    }

    private void processAmount(String amount, Contribution contribution) {
        int a = -1;
        try {
            a = validationAmount(amount);
        } catch (FormValidationException e) {
            setErreur(FIELD_AMOUNT, e.getMessage());
        }
        contribution.setAmountC(a);
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

    private int validationAmount(String amountParam) throws FormValidationException {
        int a = -1;
        if (amountParam == null || amountParam.trim().isEmpty()) {
            throw new FormValidationException("Veuillez saisir un montant pour contribuer à ce projet.");
        } else {
            try {
                a = Integer.parseInt(amountParam.trim());
                if (a <= 0) {
                    throw new FormValidationException("Le montant de contribution n'est pas correct.");
                }
            } catch (Exception e) {
                throw new FormValidationException("Le montant de contribution n'est pas correct.");
            }
        }
        return a;
    }

    private void setErreur(String field, String message) {
        errors.put(field, message);
    }
}
