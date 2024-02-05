package com.management.app.managemenetapp;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The Translator class is responsible for managing internationalization (i18n) and localization (l10n) in the application.
 * It follows the Singleton pattern to ensure only one instance is created.
 */
public class Translator {
    private static Translator instance;

    private Translator() {
    }

    /**
     * Returns the singleton instance of the Translator.
     * If the instance does not exist, a new one is created.
     *
     * @return The Translator instance
     */
    public static Translator getInstance() {
        if (instance == null) {
            instance = new Translator();
        }
        return instance;
    }

    /**
     * Retrieves the ResourceBundle containing translations for the current locale.
     *
     * @return The ResourceBundle for translations
     */
    public ResourceBundle getBundle() {
        return ResourceBundle.getBundle("translations");
    }

    /**
     * Translates the provided text using the ResourceBundle for the current locale.
     *
     * @param text The text to be translated
     * @return The translated text
     */
    public String translate(String text) {
        return getBundle().getString(text);
    }

    /**
     * Changes the default language/locale for the application.
     *
     * @param locale The new locale to set as default
     */
    public void changeLanguage(Locale locale) {
        Locale.setDefault(locale);
    }
}
