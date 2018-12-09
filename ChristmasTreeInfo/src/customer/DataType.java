package customer;

import christmastreeinfo.Lang;

public enum DataType {
NAME(Lang.NAME),

ADDRESS(Lang.ADDRESS),
CITY(Lang.CITY),

STREET(Lang.STREET),
ZONE(Lang.ZONE),

PHONE_NUMBER(Lang.PHONE_NUMBER),
EMAIL_ADDRESS(Lang.EMAIL_ADDRESS),

PICKUP_DATE(Lang.PICKUP_DATE),
LAST_PICKUP_YEAR(Lang.LAST_PICKUP_DATE),
	
DATA_LOCKED(Lang.DATA_LOCKED),
DATA_EDIT_LOCKED(Lang.DATA_EDIT_LOCK),

UUID(Lang.UUID);

String text;
DataType(String txt) {
	text = txt;
}

public String getText() {
	return text;
}
}
