package com.vvvital.psychologistsmp.model;

public enum Categories {
    CLINICAL_PSYCHOLOGIST("Клінічний психолог"),

    CHILD_PSYCHOLOGIST("Дитячий психолог"),

    FAMILY_PSYCHOLOGIST("Сімейний психолог"),

    ORGANIZATIONAL_PSYCHOLOGIST("Організаційний психолог"),

    PSYCHOTHERAPIST("Психотерапевт"),
    PSYCHOLOGIST_ON_ADDICTION_PROBLEMS("Психолог з проблем залежностей"),

    SCHOOL_PSYCHOLOGIST("Шкільний психолог"),

    PSYCHOLOGIST_FOR_TEENAGERS("Психолог для підлітків"),

    PSYCHOLOGIST_FOR_AGER("Психолог для старшої вікової категорії"),

    SPORTS_PSYCHOLOGIST("Спортивний психолог"),

    PSYCHOLOGIST_SEXOLOGIST("Психолог-сексолог"),

    GROUP_THERAPY("Групова терапія"),

    ONLINE_CONSULTATION("Онлайн консультації"),

    RECOMMENDED_PSYCHOLOGIST("Рекомендовані психологи"),

    HAVE_ARTICLES("Мають статті"),

    PSYCHOLOGIST_CLOSE("Психологи поруч зі мною");

    private final String displayCategories;

    Categories(String displayCategories) {
        this.displayCategories = displayCategories;
    }

    @Override
    public String toString() {
        return displayCategories;
    }
}
