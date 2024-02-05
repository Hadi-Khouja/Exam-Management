package com.management.app.managemenetapp;

import com.management.app.types.enums.AcademicGrade;
import javafx.scene.control.*;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

/**
 * this class is responsible for adding Validation for the user inputs
 */
public class ValidationManager {
    private final String validationRequiredText;
    private final String emailValidationText;
    private final String passwordsDontMatchText;
    private final String alphabeticValuesText;
    private final String enterthecodeText;
    private final String passwordFieldText;
    private final String telephoneNumberText;
    private final String matriculationNumberText;
    private final String numbersOnlyText;
    private final ValidationSupport validationSupport = new ValidationSupport();

    /**
     * constructor that translates the standard feedback text from Validation
     */
    public ValidationManager() {
        validationRequiredText = Translator.getInstance().translate("ValueRequired");
        emailValidationText = Translator.getInstance().translate("ValidationEmail");
        passwordsDontMatchText = Translator.getInstance().translate("ValidationPasswordsDontMatch");
        alphabeticValuesText = Translator.getInstance().translate("ValidationAlphabeticValues");
        enterthecodeText = Translator.getInstance().translate("ValidationEnterthecode");
        passwordFieldText = Translator.getInstance().translate("Validation8Character");
        telephoneNumberText = Translator.getInstance().translate("ValidationTelephonenumber");
        matriculationNumberText = Translator.getInstance().translate("ValidationMatriculationNumber");
        numbersOnlyText = Translator.getInstance().translate("ValidationNumbersOnly");
    }

    public Boolean isInvalid() {
        return validationSupport.isInvalid();
    }

    /**
     * sets up the required Validation for a username Field
     * it must be alphabetical symbols only
     *
     * @param usernameTextField the TextField that should a validation
     */
    public void setupUsernameValidation(TextField usernameTextField) {
        validationSupport.registerValidator(usernameTextField, Validator.createEmptyValidator(validationRequiredText));
        validationSupport.registerValidator(usernameTextField, Validator.createRegexValidator(alphabeticValuesText, "^[a-zA-Z]+$", Severity.ERROR));
    }

    /**
     * sets up the Validation for a PasswordField and an optional repeatPasswordField
     * <p>
     * must at least have 1 Uppercase, 1 lowercase letter, 1 number and 1 special character
     * and be at least 8 characters long
     * <p>
     * PasswordField and repeatPasswordField must have the same input
     *
     * @param passwordField       the passwordField that should get Validation
     * @param repeatPasswordField the optional repeatPassword field (null if not required)
     */
    public void setupPasswordValidation(PasswordField passwordField, PasswordField repeatPasswordField) {
        validationSupport.registerValidator(passwordField, Validator.createEmptyValidator(validationRequiredText));
        validationSupport.registerValidator(passwordField, Validator.createRegexValidator(passwordFieldText, "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", Severity.ERROR));

        validationSupport.registerValidator(repeatPasswordField, Validator.createEmptyValidator(validationRequiredText));
        validationSupport.registerValidator(repeatPasswordField, (Control, value) -> {
            boolean condition = value == null || value.equals(passwordField.getText());
            return ValidationResult.fromMessageIf(Control, passwordsDontMatchText, Severity.ERROR, !condition);
        });
    }

    /**
     * sets up the Validation of an email field
     *
     * @param emailTextField the email TextField that should get the Validation
     */
    public void setupEmailValidation(TextField emailTextField) {
        validationSupport.registerValidator(emailTextField, Validator.createEmptyValidator(validationRequiredText));
        validationSupport.registerValidator(emailTextField, Validator.createRegexValidator(emailValidationText, "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", Severity.ERROR));
    }

    /**
     * sets up Validation for name of things (only Alphabetic characters are allowed)
     *
     * @param nameTextField the textField that should get the Validation
     */
    public void setupNameValidation(TextField nameTextField) {
        validationSupport.registerValidator(nameTextField, Validator.createEmptyValidator(validationRequiredText));
        validationSupport.registerValidator(nameTextField, Validator.createRegexValidator(alphabeticValuesText, "^[a-zA-Z]+$", Severity.ERROR));
    }

    /**
     * sets up a required Validation for the textField
     *
     * @param textField the textField that should be required
     */
    public void setupRequiredValidation(TextField textField) {
        validationSupport.registerValidator(textField, Validator.createEmptyValidator(validationRequiredText));
    }

    /**
     * sets up validation for a 6-digit code
     *
     * @param codeTextField the code textField
     */
    public void setupCode(TextField codeTextField) {
        validationSupport.registerValidator(codeTextField, Validator.createEmptyValidator(validationRequiredText));
        validationSupport.registerValidator(codeTextField, Validator.createRegexValidator(enterthecodeText, "^\\d{6}$", Severity.ERROR));
    }

    /**
     * sets up the validation for a phone number
     * either 10 digits starting with a 0 or + and country code
     *
     * @param phoneNumberTextField the textField that should get phoneNumber validation
     */
    public void setupPhoneNumberValidation(TextField phoneNumberTextField) {
        validationSupport.registerValidator(phoneNumberTextField, Validator.createEmptyValidator(validationRequiredText));
        validationSupport.registerValidator(phoneNumberTextField, Validator.createRegexValidator(telephoneNumberText, "^(\\+\\d{1,4})?\\s*\\d{6,}$", Severity.ERROR));
    }

    public void setupNumbersTextField(TextField numberTextField) {
        setupRequiredValidation(numberTextField);
        validationSupport.registerValidator(numberTextField, Validator.createRegexValidator(numbersOnlyText, "^[0-9]*$", Severity.ERROR));
    }

    public void setupDatePicker(DatePicker datePicker) {
        validationSupport.registerValidator(datePicker, Validator.createEmptyValidator(validationRequiredText));
    }

    public void setupPhoneTextField(TextField phoneTextField) {
        validationSupport.registerValidator(phoneTextField, Validator.createEmptyValidator(validationRequiredText));
        validationSupport.registerValidator(phoneTextField, Validator.createRegexValidator(telephoneNumberText, "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$", Severity.ERROR));

    }

    public void setupMatriculationTextField(TextField matriculationTextField) {
        validationSupport.registerValidator(matriculationTextField, Validator.createEmptyValidator(validationRequiredText));
        validationSupport.registerValidator(matriculationTextField, Validator.createRegexValidator(matriculationNumberText, "^\\d{7}$", Severity.ERROR));
    }

    public <T> void setupChoiceBoxValidation(ChoiceBox<T> choiceBox) {
        validationSupport.registerValidator(choiceBox, Validator.createEmptyValidator(validationRequiredText));
    }
}
