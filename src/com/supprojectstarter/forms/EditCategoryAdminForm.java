package com.supprojectstarter.forms;

import com.supprojectstarter.bean.Category;
import com.supprojectstarter.dao.DAOCategory;
import com.supprojectstarter.dao.DAOException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public final class EditCategoryAdminForm {
    private static final String FIELD_NAME_CATEGORY = "name";
    private static final String FIELD_ID = "id";
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

    public Category editCategory(HttpServletRequest request) {
        String idString = getFieldValue(request, FIELD_ID);
        String categoryName = getFieldValue(request, FIELD_NAME_CATEGORY);
        Category category ;
        try {
            category = processId(idString);
            processCategory(categoryName, category);
            if (errors.isEmpty()) {
                DAOCategory.getInstance().update(category);
                result = "Catégorie modifiée.";
            } else {
                result = "Echec, catégorie non modifiée.";
            }
        } catch (DAOException e) {
            result = "Echec lors de la modification : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
            e.printStackTrace();
        }
        return category;
    }

    private Category processId(String id) {
        int i = -1;
        try {
            i = validationId(id);
        } catch (FormValidationException e) {
            setErreur(FIELD_ID, e.getMessage());
        }
        if (i != -1 && errors.isEmpty()) {
            return DAOCategory.getInstance().find(i);
        }
        return null;
    }

    private void processCategory(String categoryName, Category category) {
        try {
            validationCategory(categoryName);
        } catch (FormValidationException e) {
            setErreur(FIELD_NAME_CATEGORY, e.getMessage());
        }
        category.setName(categoryName);
    }

    private void validationCategory(String category) throws FormValidationException {
        if (category == null) {
            throw new FormValidationException("Entrez un nom de catégorie.");
        } else if (category != null && category.trim().length() < 3) {
            throw new FormValidationException("Le nom de la catégorie doit contenir au moins 3 caractères.");
        } else if (DAOCategory.getInstance().findByName(category.trim()) != null) {
            throw new FormValidationException("Cette catégorie existe déjà.");
        }
    }

    private int validationId(String idParam) throws FormValidationException {
        int a = -1;
        String id = idParam.trim();
        if (id.isEmpty()) {
            throw new FormValidationException("ID de Category vide.");
        } else {
            try {
                a = Integer.parseInt(id);
            } catch (Exception e) {
                throw new FormValidationException("ID de Category non valide.");
            }
        }
        if (DAOCategory.getInstance().find(a) == null) {
            throw new FormValidationException("Cet ID de category n'existe pas.");
        }
        return a;
    }

    private void setErreur(String field, String message) {
        errors.put(field, message);
    }
}
