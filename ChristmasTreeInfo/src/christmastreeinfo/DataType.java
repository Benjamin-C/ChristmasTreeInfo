package christmastreeinfo;

public enum DataType {
NAME(Lang.NAME),

ADDRESS(Lang.ADDRESS),
CITY(Lang.CITY),

STREET(Lang.STREET),
REIGON(Lang.REIGON),

PHONE_NUMBER(Lang.PHONE_NUMBER),
EMAIL_ADDRESS(Lang.EMAIL_ADDRESS),

PICKUP_DATE(Lang.PICKUP_DATE),
LAST_PICKUP_YEAR(Lang.LAST_PICKUP_DATE);

String text;
DataType(String txt) {
	text = txt;
}

public String getText() {
	return text;
}
}
