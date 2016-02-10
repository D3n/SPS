package com.supprojectstarter.forms;

import com.supprojectstarter.bean.Category;
import com.supprojectstarter.dao.DAOCategory;
import com.supprojectstarter.dao.DAOException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public final class AddCategoryAdminForm {
    private static final String FIELD_NAME_CATEGORY = "name";
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

    public Category createCategory(HttpServletRequest request) {
        String categoryName = getFieldValue(request, FIELD_NAME_CATEGORY);
        Category category ;
        try {
            category = processCategory(categoryName);
            if (errors.isEmpty()) {
                DAOCategory.getInstance().insert(category);
                result = "Catégorie ajoutée.";
            } else {
                result = "Echec, catégorie non ajoutée.";
            }
        } catch (DAOException e) {
            result = "Echec lors de la création : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
            e.printStackTrace();
        }
        return category;
    }

    private Category processCategory(String categoryName) {
        try {
            validationCategory(categoryName);
        } catch (FormValidationException e) {
            setErreur(FIELD_NAME_CATEGORY, e.getMessage());
        }
        Category cat = new Category();
        cat.setName(categoryName);
        return cat;
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

    private void setErreur(String field, String message) {
        errors.put(field, message);
    }
}
