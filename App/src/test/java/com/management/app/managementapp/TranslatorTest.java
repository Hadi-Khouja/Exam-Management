package com.management.app.managementapp;

import com.management.app.managemenetapp.Translator;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class TranslatorTest {
    @Test
    public void testGetInstance() {
        Translator instance1 = Translator.getInstance();
        Translator instance2 = Translator.getInstance();
        assertEquals(instance1, instance2);
    }

    @Test
    public void testTranslateEnglish() {
        Translator.getInstance().changeLanguage(Locale.ENGLISH);
        String translatedText = Translator.getInstance().translate("AppName");
        assertEquals("Exam Management App", translatedText, "the Translation should work");
    }

    @Test
    public void testTranslateGerman() {
        Translator.getInstance().changeLanguage(Locale.GERMAN);
        String translatedText = Translator.getInstance().translate("AppName");
        assertEquals("Prüfungsverwaltungsapp", translatedText, "the Translation should work");
    }

    @Test
    public void testGetBundle() {
        assertEquals(ResourceBundle.getBundle("translations"), Translator.getInstance().getBundle());
    }
}