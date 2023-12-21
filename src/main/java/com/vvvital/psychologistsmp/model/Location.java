package com.vvvital.psychologistsmp.model;

public enum Location {
    KYIV("Київ"),
    ODESSA("Одесса"),
    DNIPRO("Дніпро"),
    LVIV("Львів"),
    KHARKIV("Харків"),
    POLTAVA("Полтава"),
    DONETCK("Донецьк"),
    LUGANSK("Луганськ"),
    ZAPORIZHYA("Запоріжжя"),
    KROPYVNYTCKIY("Кропивницький"),
    CHERKASSY("Черкаси"),
    KHERSON("Херсон"),
    MYKOLAYIV("Миколаїв"),
    VINNYTCA("Вінниця"),
    RIVNE ("Рівне"),
    ZHYTOMYR("Житомир"),
    IVANO_FRANKIVSK("Івано-Франківськ"),
    TERNOPIL("Тернопіль"),
    UZHGOROD("Ужгород"),
    CHERNIVCI("Чернівці"),
    SIMFEROPIL("Сімферополь"),
    ALL("All");

    private final String displayLocation;

    Location(String displayLocation) {
        this.displayLocation = displayLocation;
    }

    @Override
    public String toString() {
        return displayLocation;
    }
    }
