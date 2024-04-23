package appsec.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constants {

	// API IDs

	public static final String API_NR_SEARCH = "api.nr.search";
	public static final String API_USER_CREATE = "api.user.create";
	public static final String API_SUPERADMIN_CREATE = "api.superadmin.create";
	public static final String API_STAKEHOLDER_CREATE = "api.stakeholder.create";
	public static final String API_STAKEHOLDER_LIST = "api.stakeholder.list";
	public static final String API_STAKEHOLDER_UPDATE = "api.stakeholder.update";
	public static final String API_ADMIN_TYPES_LIST = "api.key";
    public static final String API_USER_LIST = "api.user.list";
	public static final String API_USER_LOGIN = "api.user.login";
    public static final String API_PASSWORD_RESET = "api.password.reset";
    public static final String API_PASSWORD_CHANGE = "api.password.change";
    public static final String API_USER_ACTIVATE = "api.user.activate";
    public static final String API_USER_DISABLE = "api.user.disable";
    public static final String API_USER_ENABLE = "api.user.enable";
    public static final String API_USER_READ = "api.user.read";
	public static final String API_SUBUSER_READ = "api.subuser.list";
    public static final String API_PROFILE_UPDATE = "api.profile.update";
    public static final String API_USER_EDIT = "api.user.edit";
    public static final String API_USER_LOGOUT = "api.user.logout";
    public static final String API_REFRESH_TOKEN = "api.token.refresh";
    
	// DB TABLE NAMES

	public static final String TABLE_LOG = "vh_audit_trail";
	public static final String TABLE_USER = "vm_rto_user";
	public static final String TABLE_STAKEHOLDER = "vm_stakeholder";
	public static final String TABLE_ADMIN_TYPES = "vm_user_list";

	public static final String TABLE_RC = "rc";
	public static final String TABLE_VERIFICATION_TOKEN = "vt_verification_token";
	public static final String TABLE_REFRESH_TOKEN = "vt_refresh_token";
	public static final String TABLE_ROLE = "vm_role";
	
	// DB SEQUENCE NAMES

	public static final String SEQ_VERIFICATION_TOKEN = "verification_token_seq";

	public static final String DB_SYSTEM_DB2 = "DB2";
	/**
	 * DB System - ORACLE
	 */
	public static final String DB_SYSTEM_ORACLE = "ORACLE";
	/**
	 * DB System - MS-SQL
	 */
	public static final String DB_SYSTEM_MSSQL = "MSSQL";
	public static final String DB_SYSTEM_POSTGRES = "POSTGRES";
	public static final int PREPAID_PURCD = 902;
	public static final String NOTSUFFICIENTAMT = "Not Sufficient Amount";
	public static final String TXN_NOT_VALID = "Transaction not valid";
	public static final int NR_FEES = 50;
	public static final String WAITFORTIME = "Please try After 30 Minutes";
	public static final String PENDING_TEMP_APPROVE = "PENDING_TEMP_APPROVE";

	public static final String TECHNICAL_ERROR = "Some Technical error occurred. Please try after some time.";
	public static String NOC_ISSUED = "NOC ISSUED";

	public static final String DL_USER = "paidNR";
    public static final String DL_PASSW = "34fed48ab5b141f8a7b3f8af04c8792b";

	// ENDPOINTS EXPOSED

	public static final String EMAIL_PATH = "/emailapi/emailService";
	public static final String AUTH_PATH = "/apis/auth/login";
	public static final String CREATE_USER_PATH = "/apis/user/create";
	public static final String SEARCH_VEHICLE_DEATILS_BY_REGN_NO = "/apis/vehiclesearch/getVehicleDetailsByRegNo";
	public static final String SEARCH_VEHICLE_DEATILS_BY_CHASI_NO = "/apis/vehiclesearch/getVehicleDetailsByChasiNo";
	public static final String SEARCH_DL_DETAILS = "/apis/vehiclesearch/getLicenseDetails";
	public static final String CREATE_ADMIN_PATH = "/apis/user/superadmin/create";
	public static final String SEARCH_PATH = "/apis/search";
	public static final String CREATE_STAKEHOLDER_PATH = "/apis/stakeholder/create";
	public static final String UPDATE_STAKEHOLDER_PATH = "/apis/stakeholder/update/";
	public static final String LIST_SUBUSER_TYPES_PATH = "/apis/user/subuser/list/";
	public static final String LIST_ADMIN_TYPES_PATH = "/apis/admintypes/list";
    public static final String LIST_STAKEHOLDER_BY_TYPE_PATH = "/apis/stakeholder";
	public static final String LIST_STAKEHOLDER_PATH = "/apis/stakeholder/list";
	public static final String LIST_USER_PATH = "/apis/user/list";
	public static final String PASSWORD_RESET_PATH = "/apis/user/password/reset";
	public static final String ACTIVATE_USER_PATH = "/apis/user/activate";
	public static final String UPDATE_PASSWORD_PATH = "/apis/user/password/change";
	public static final String SET_PASSWORD_PATH = "/apis/user/password/set";
	public static final String ADMIN_PASSWORD_RESET_PATH = "/apis/user/admin/password/reset";
	public static final String USER_DISABLE_PATH = "/apis/user/block";
	public static final String USER_ENABLE_PATH = "/apis/user/unblock";
	public static final String USER_READ_PATH = "/apis/user/read";
	public static final String USER_ONBOARD_PATH = "/apis/user/onboard";
	public static final String PROFILE_UPDATE_PATH = "/apis/user/profile/update";
	public static final String USER_EDIT_PATH = "/apis/user/edit";
	public static final String USER_FETCH_PATH = "/apis/user/status";
	public static final String LOGOUT_PATH = "/apis/auth/logout";
	public static final String REFRESH_TOKEN_PATH = "/apis/auth/refresh";
	public static final String API_DOC_PATH = "/apidoc";
	public static final String SWAGGER_PATH = "/swagger";

    public static final String LOGIN_PAGE = "/#/Login";
	

	public static final String[] DEFINED_ENDPOINTS = { CREATE_USER_PATH, AUTH_PATH, SEARCH_VEHICLE_DEATILS_BY_REGN_NO,
			SEARCH_VEHICLE_DEATILS_BY_CHASI_NO,SEARCH_DL_DETAILS,
			CREATE_STAKEHOLDER_PATH, UPDATE_STAKEHOLDER_PATH, LIST_STAKEHOLDER_BY_TYPE_PATH, CREATE_ADMIN_PATH, LIST_USER_PATH,
			PASSWORD_RESET_PATH, ACTIVATE_USER_PATH, UPDATE_PASSWORD_PATH, SET_PASSWORD_PATH, ADMIN_PASSWORD_RESET_PATH,
			USER_DISABLE_PATH, USER_ENABLE_PATH, USER_READ_PATH, PROFILE_UPDATE_PATH, USER_EDIT_PATH, USER_FETCH_PATH,
			LOGOUT_PATH, API_DOC_PATH, SWAGGER_PATH };

	// FIELD NAMES


	public static final String APPLICANT_NAME = "name";
	public static final String STAKE_CODE = "stakeCode";
	public static final String ADDRESS_2 = "address2";
	public static final String ADDRESS = "address";
	public static final String PINCODE = "pincode";
	public static final String PHONE_NO_O = "phone";
	public static final String PHONE_NO_M = "mobile";
	public static final String EMAIL_ID = "emailId";
	public static final String STATE_CODE = "stateCode";
	public static final String IFSC_CODE = "ifscCode";
	public static final String CREATED_BY = "createdBy";
	public static final String LAST_UPDATED_BY = "lastUpdatedBy";
	public static final String USER_STATUS = "userStatus";
	public static final String DESIGNATION = "designation";
	public static final String RTO_CODE = "rtoCode";
	public static final String DISTRICT_CODE = "distCode";
	public static final String NR_SARATHI = "NR-Sarathi";
	public static final String USER_TYPE = "userType";
	public static final String USER_ID = "userId";
	public static final String CREATED_DATE = "createdDate";
	public static final String LAST_UPDATED_DATE = "lastUpdatedDate";
	public static final String VERIFIED_DATE_USER_3 = "verifiedDateUser3";
	public static final Object OLD_PASSWORD = "oldPassword";
	public static final Object NEW_PASSWORD = "newPassword";
	public static final Object CONFIRM_PASSWORD = "confirmPassword";
	public static final String TOKEN_INVALID = "invalidToken";
	public static final String TOKEN_EXPIRED = "expired";
	public static final String TOKEN_VALID = "valid";
	public static final String MESSAGE = "message";
	public static final String ACCESS_TOKEN = "accessToken";
	public static final String REFRESH_TOKEN = "refreshToken";
	public static final String API_KEY = "apiKey";
	public static final String PROJECT = "project";
	public static final String PASSWORD = "password";
	public static final String PHONE_NO = "phone_O";
	public static final String MOBILE_NO = "phone_M";
	public static final String CODE = "code";
	public static final String DESCRIPTION = "description";
	public static final String ADMIN_USER_TYPE = "adminUserType";

	public static final String RC_NO = "rc_no";
	public static final String CHASSIS_NO = "chassis_no";
	public static final String ENGINE_NO = "engine_no";
	public static final String BODY_TYPE = "body_type";
	public static final String FUEL = "fuel";
	public static final String COLOR = "color";
	public static final String MANUFACTURER_NAME = "manufacturer";
	public static final String MODEL = "model";
	public static final String RC_CREATED_ON = "rc_created_on";
	public static final String REQUEST = "request";
	public static final String FAILED = "Failed";
	public static final String SUCCESS = "Success";
	public static final String ACCEPT = "accept";

	public static final String CONTENT_TYPE = "Content-Type";
	public static final String APPLICATION_JSON = "application/json";
	public static final String AUTHORIZATION = "authorization";
	public static final String COUNT = "count";
	public static final String X_AUTH_TOKEN = "x-authenticated-user-token";
    public static final String INTEGRITY_TOKEN = "client-token";
	public static final String CLIENT = "client";
    public static final String X_API_KEY = "x-api-key";
    
	// ERROR MESSAGES

	public static final String MSG_SLOT_NOT_AVAILABLE = "Slot not available";
	public static final String MSG_UNAUTHORIZED_USER = "Unauthorized";
	public static final String MSG_USER_CREATION_FAILED = "User creation failed";
	public static final String MSG_STAKEHOLDER_CREATED = "Stakeholder created successfully";
	public static final String MSG_STAKEHOLDER_UPDATED = "Stakeholder updated successfully";
	public static final String MSG_STAKEHOLDER_CREATION_FAILED = "Stakeholder creation failed";
	public static final String MSG_CODE_EXISTS = "Code already exists";
	public static final String MSG_UNAUTHORIZED_DOC = "User not authorized to view requested document";
	public static final String MSG_NO_STAKEHOLDERS = "No stakeholders exist of selected user type";
	public static final String MSG_STAKECODE_NOT_EXIST = "Stakeholder Code does not exist";
	public static final String MSG_ADMIN_EXIST_FOR_STAKE = "Admin already exists for stakeholder";
	public static final String MSG_USER_ID_EXISTS = "User ID already exists";
	public static final String MSG_IFSC_EXISTS = "User already exists for selected bank branch";
	public static final String MSG_EMAIL_ID_EXISTS = "Email ID already exists";
	public static final String MSG_USER_ID_MANDATORY = "User ID is mandatory";
	public static final String MSG_USER_ID_LENGTH = "User ID should contain atleast 3 characters";
	public static final String MSG_NAME_MANDATORY = "Name is mandatory";
	public static final String MSG_EMAIL_MANDATORY = "Email ID is mandatory";
	public static final String MSG_EMAIL_INVALID = "Email ID is invalid";
	public static final String MSG_MOBILE_INVALID = "Mobile No is invalid";
	public static final String MSG_MOBILE_MANDATORY = "Mobile No is mandatory";
	public static final String MSG_STAKEHOLDER_NOT_EXIST = "No Stakeholder exist for selected user type";
	public static final String MSG_USERS_NOT_EXIST = "No users found";
	public static final String MSG_LOGIN_FAILED = "Login failed";
	public static final String MSG_INVALID_USERID = "Invalid userId";
	public static final Object MSG_USER_CREATED = "User created and an activation mail has been sent to the user";
	public static final String MSG_PASSWORD_INCORRECT = "Incorrect Password";
	public static final String MSG_PASSWORD_NOT_MATCH = "Password and Confirm Password should match";
	public static final String MSG_PASSWORD_LENGTH = "Password should contain atleast 8 to 20 characters";
	public static final String MSG_INVALID_TOKEN = "Invalid Token";
	public static final String MSG_EXPIRED_TOKEN = "Refresh Token expired";
	public static final String MSG_USER_DISABLED = "User already blocked";
	public static final String MSG_USER_ENABLED = "User already active";
	public static final String MSG_USER_NOT_FOUND = "User not found";
	public static final String MSG_MAX_TRIES = "Your User ID is locked due to maximum unsuccessful login attempts. Please contact your admin to unlock it or try after 1 hour.";
	public static final String MSG_USER_INACTIVE = "User is inactive";
	public static final String MSG_AUTH_EXCEPTION = "Error";
	public static final String MSG_PASSWORD_CONSTRAINTS = "Password must contain 8-20 characters and should be a combination of upper case and lower case letters, number and special characters (-+_!@#$%^&*., ?)";
    public static final String MSG_ADMINTYPES_NOT_EXIST = "No Stakeholder exist for selected user type";
	public static final String MSG_INVALID_ADMINTYPE = "Invalid Admin type";
	public static final String MSG_PASSWORD_RESET = "Please reset your password";
	

    // EMAIL SUBJECTS

	public static final String SUBJECT_PASSWORD_RESET = "Password Reset for Paid NR Services Portal";
	public static final String SUBJECT_USER_ONBOARD = "Pain NR Services Onboarding Confirmation";


	// CONSTANT VALUES

	public static final int ACTIVATION_TOKEN_EXPIRATION =  60 * 24 * 7;
	public static final int PASSWORD_RESET_TOKEN_EXPIRATION = 10;
	public static final long JWT_TOKEN_VALIDITY =  60 * 10 ;
    public static final long REFRESH_TOKEN_VALIDITY =  60 * 60 * 24;
    
	public static final String ADMIN_SUPERADMIN_ROLES = "'" + Constants.SUPERADMIN + "','" + Constants.MORTH + "','"
			+ Constants.ADMIN_BANK + "','" + Constants.ADMIN_INSURANCE + "','" + Constants.ADMIN_MANUFACTURER + "','"
			+ Constants.ADMIN_TRANSPORTER + "','" + Constants.ADMIN_NBFC + "','" + Constants.ADMIN_VAS + "'";
	
	public static final String ALL_ROLES = "'" + Constants.SUPERADMIN + "','" + Constants.MORTH + "','"
			+ Constants.ADMIN_BANK + "','" + Constants.ADMIN_INSURANCE + "','" + Constants.ADMIN_MANUFACTURER + "','"
			+ Constants.ADMIN_TRANSPORTER + "','" + Constants.ADMIN_NBFC + "','" + Constants.ADMIN_VAS + "','"
			+ Constants.USER_BANK + "','" + Constants.USER_INSURANCE + "','" + Constants.USER_MANUFACTURER + "','"
			+ Constants.USER_TRANSPORTER + "','" + Constants.USER_NBFC + "','" + Constants.USER_VAS + "'";

	public static final String NEW_LINE = System.getProperty("line.separator");
	public static final String EMPTY_STRING = "";
	public static final String EMAIL_REGEX = "[a-z0-9._%+-]+@[a-z0-9.-]+.\\.[a-z]{2,}";
	public static final String MOBILE_REGEX = "^\\d{10}$";
    public static final String PASSWORD_REGEX= "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[-+_!@#$%^&*., ?]).+$";
 
	public static final String USER_INACTIVE = "I";
	public static final String USER_ACTIVE = "A";
	public static final String USER_BLOCKED = "B";
	public static final String USER_LOCKED = "L";
	public static final String USER_ACTIVE_NOT_LOGGED_IN = "F";

	public static final long MAX_LOGIN_TRIES = 3;
	public static final long MAX_LOGIN_ATTEMPTS_HOUR = 1;

	
	public static final String USER_NBFC = "N";
	public static final String USER_TRANSPORTER = "T";
	public static final String USER_BANK = "B";
	public static final String USER_INSURANCE = "I";
	public static final String USER_MANUFACTURER = "M";
	public static final String USER_VAS = "V";

	public static final String USER_NBFC_TEXT = "NBFC User";
	public static final String USER_TRANSPORTER_TEXT = "Transporter User";
	public static final String USER_BANK_TEXT = "Bank User";
	public static final String USER_INSURANCE_TEXT = "Insurance User";
	public static final String USER_MANUFACTURER_TEXT = "Manufacturer User";
	public static final String USER_VAS_TEXT = "V";

	public static final String SUPERADMIN = "S";
	public static final String MORTH = "MORTH";
	public static final String ADMIN_NBFC = "AN";
	public static final String ADMIN_TRANSPORTER = "AT";
	public static final String ADMIN_BANK = "AB";
	public static final String ADMIN_INSURANCE = "AI";
	public static final String ADMIN_MANUFACTURER = "AM";
	public static final String ADMIN_VAS = "VAS";

	public static final String SUPERADMIN_TEXT = "Superadmin";
	public static final String MORTH_TEXT = "MORTH";
	public static final String ADMIN_NBFC_TEXT = "NBFC Admin";
	public static final String ADMIN_TRANSPORTER_TEXT = "Transporter Admin";
	public static final String ADMIN_BANK_TEXT = "Bank Admin";
	public static final String ADMIN_INSURANCE_TEXT = "Insurance Admin";
	public static final String ADMIN_MANUFACTURER_TEXT = "Manufacturer Admin";
	public static final String ADMIN_VAS_TEXT = "VAS Admin";

    public static final String STAKE_NBFC_TEXT = "NBFCs";
	public static final String STAKE_TRANSPORTER_TEXT = "Transporters";
	public static final String STAKE_BANK_TEXT = "Banks";
	public static final String STAKE_INSURANCE_TEXT = "Insurance Companies";
	public static final String STAKE_MANUFACTURER_TEXT = "Manufacturers";
	public static final String STAKE_VAS_TEXT = "VAS";

	public static final Map<String, String> USERTYPE_CODE_DESCRIPTION = new HashMap<>();
	static {
		try {
			USERTYPE_CODE_DESCRIPTION.put(SUPERADMIN, SUPERADMIN_TEXT);
			USERTYPE_CODE_DESCRIPTION.put(MORTH, MORTH_TEXT);
			USERTYPE_CODE_DESCRIPTION.put(ADMIN_BANK, ADMIN_BANK_TEXT);
			USERTYPE_CODE_DESCRIPTION.put(ADMIN_INSURANCE, ADMIN_INSURANCE_TEXT);
			USERTYPE_CODE_DESCRIPTION.put(ADMIN_MANUFACTURER, ADMIN_MANUFACTURER_TEXT);
			USERTYPE_CODE_DESCRIPTION.put(ADMIN_NBFC, ADMIN_NBFC_TEXT);
			USERTYPE_CODE_DESCRIPTION.put(ADMIN_TRANSPORTER, ADMIN_TRANSPORTER_TEXT);
			USERTYPE_CODE_DESCRIPTION.put(ADMIN_VAS, ADMIN_VAS_TEXT);
			USERTYPE_CODE_DESCRIPTION.put(USER_BANK, USER_BANK_TEXT);
			USERTYPE_CODE_DESCRIPTION.put(USER_INSURANCE, USER_INSURANCE_TEXT);
			USERTYPE_CODE_DESCRIPTION.put(USER_MANUFACTURER, USER_MANUFACTURER_TEXT);
			USERTYPE_CODE_DESCRIPTION.put(USER_NBFC, USER_NBFC_TEXT);
			USERTYPE_CODE_DESCRIPTION.put(USER_TRANSPORTER, USER_TRANSPORTER_TEXT);
			USERTYPE_CODE_DESCRIPTION.put(USER_VAS, USER_VAS_TEXT);
			
		}

		catch (Exception e) {
			System.out.println(e);
		}
	}

    public static final Map<String, String> STAKETYPE_CODE_DESCRIPTION = new HashMap<>();
	static {
		try {
			STAKETYPE_CODE_DESCRIPTION.put(ADMIN_BANK, STAKE_BANK_TEXT);
			STAKETYPE_CODE_DESCRIPTION.put(ADMIN_INSURANCE, STAKE_INSURANCE_TEXT);
			STAKETYPE_CODE_DESCRIPTION.put(ADMIN_MANUFACTURER, STAKE_MANUFACTURER_TEXT);
			STAKETYPE_CODE_DESCRIPTION.put(ADMIN_NBFC, STAKE_NBFC_TEXT);
			STAKETYPE_CODE_DESCRIPTION.put(ADMIN_TRANSPORTER, STAKE_TRANSPORTER_TEXT);
			STAKETYPE_CODE_DESCRIPTION.put(ADMIN_VAS, STAKE_VAS_TEXT);
			
		}

		catch (Exception e) {
			System.out.println(e);
		}
	}

	public static final List<String> ADMIN_TYPES = new ArrayList<>();
	static {
		try {
			ADMIN_TYPES.add(ADMIN_BANK);
			ADMIN_TYPES.add(ADMIN_INSURANCE);
			ADMIN_TYPES.add(ADMIN_MANUFACTURER);
			ADMIN_TYPES.add(ADMIN_NBFC);
			ADMIN_TYPES.add(ADMIN_TRANSPORTER);
			ADMIN_TYPES.add(ADMIN_VAS);
		}

		catch (Exception e) {
			System.out.println(e);
		}
	}

	public static final List<String> USER_TYPES = new ArrayList<>();
	static {
		try {
			USER_TYPES.add(USER_BANK);
			USER_TYPES.add(USER_INSURANCE);
			USER_TYPES.add(USER_MANUFACTURER);
			USER_TYPES.add(USER_NBFC);
			USER_TYPES.add(USER_TRANSPORTER);
			USER_TYPES.add(USER_VAS);
		}

		catch (Exception e) {
			System.out.println(e);
		}
	}

	public static final Map<String, List<String>> ADMIN_SUBUSER = new HashMap<>();
	static {
		try {
			ADMIN_SUBUSER.put(SUPERADMIN, new ArrayList<>());
			ADMIN_SUBUSER.get(SUPERADMIN).add(MORTH);
			ADMIN_SUBUSER.get(SUPERADMIN).add(ADMIN_BANK);
			ADMIN_SUBUSER.get(SUPERADMIN).add(ADMIN_INSURANCE);
			ADMIN_SUBUSER.get(SUPERADMIN).add(ADMIN_MANUFACTURER);
			ADMIN_SUBUSER.get(SUPERADMIN).add(ADMIN_NBFC);
			ADMIN_SUBUSER.get(SUPERADMIN).add(ADMIN_TRANSPORTER);
			ADMIN_SUBUSER.get(SUPERADMIN).add(ADMIN_VAS);

			ADMIN_SUBUSER.put(MORTH, new ArrayList<>());
			ADMIN_SUBUSER.get(MORTH).add(ADMIN_BANK);
			ADMIN_SUBUSER.get(MORTH).add(ADMIN_INSURANCE);
			ADMIN_SUBUSER.get(MORTH).add(ADMIN_MANUFACTURER);
			ADMIN_SUBUSER.get(MORTH).add(ADMIN_NBFC);
			ADMIN_SUBUSER.get(MORTH).add(ADMIN_TRANSPORTER);

			ADMIN_SUBUSER.put(ADMIN_BANK, new ArrayList<>());
			ADMIN_SUBUSER.get(ADMIN_BANK).add(USER_BANK);

			ADMIN_SUBUSER.put(ADMIN_INSURANCE, new ArrayList<>());
			ADMIN_SUBUSER.get(ADMIN_INSURANCE).add(USER_INSURANCE);

			ADMIN_SUBUSER.put(ADMIN_NBFC, new ArrayList<>());
			ADMIN_SUBUSER.get(ADMIN_NBFC).add(USER_NBFC);

			ADMIN_SUBUSER.put(ADMIN_MANUFACTURER, new ArrayList<>());
			ADMIN_SUBUSER.get(ADMIN_MANUFACTURER).add(USER_MANUFACTURER);

			ADMIN_SUBUSER.put(ADMIN_TRANSPORTER, new ArrayList<>());
			ADMIN_SUBUSER.get(ADMIN_TRANSPORTER).add(USER_TRANSPORTER);

			ADMIN_SUBUSER.put(ADMIN_VAS, new ArrayList<>());
			ADMIN_SUBUSER.get(ADMIN_VAS).add(USER_VAS);

		}

		catch (Exception e) {
			System.out.println(e);
		}
	}

	public static final String DL = "DL";
	public static final String RC = "RC";

	public static final String ACCESS_TO = "accessTo";
	public static final String SERVICE_CHARGE = "serviceCharge";

	public static final Map<String, ArrayList<Map<String, ArrayList<String>>>> ACCESS_CHARGE;
    static {

		Map<String, ArrayList<String>> accessToMap = new HashMap<>();
		Map<String, ArrayList<Map<String, ArrayList<String>>>> map = new HashMap<>();
		Map<String, ArrayList<String>> serviceChargeMap = new HashMap<>();

		accessToMap = new HashMap<>();
		serviceChargeMap = new HashMap<>();
		accessToMap.put(ACCESS_TO, new ArrayList<>());
		serviceChargeMap.put(SERVICE_CHARGE, new ArrayList<>());
		accessToMap.get(ACCESS_TO).add(DL);
		accessToMap.get(ACCESS_TO).add(RC);
		serviceChargeMap.get(SERVICE_CHARGE).add("50");
		map.put(USER_NBFC, new ArrayList<>());
		map.get(USER_NBFC).add(serviceChargeMap);
		map.get(USER_NBFC).add(accessToMap);

		accessToMap = new HashMap<>();
		serviceChargeMap = new HashMap<>();
		accessToMap.put(ACCESS_TO, new ArrayList<>());
		serviceChargeMap.put(SERVICE_CHARGE, new ArrayList<>());
		accessToMap.get(ACCESS_TO).add(DL);
		accessToMap.get(ACCESS_TO).add(RC);
		serviceChargeMap.get(SERVICE_CHARGE).add("50");
		map.put(USER_BANK, new ArrayList<>());
		map.get(USER_BANK).add(serviceChargeMap);
		map.get(USER_BANK).add(accessToMap);

		accessToMap = new HashMap<>();
		serviceChargeMap = new HashMap<>();
		accessToMap.put(ACCESS_TO, new ArrayList<>());
		serviceChargeMap.put(SERVICE_CHARGE, new ArrayList<>());
		accessToMap.get(ACCESS_TO).add(DL);
		accessToMap.get(ACCESS_TO).add(RC);
		serviceChargeMap.get(SERVICE_CHARGE).add("50");
		map.put(USER_INSURANCE, new ArrayList<>());
		map.get(USER_INSURANCE).add(serviceChargeMap);
		map.get(USER_INSURANCE).add(accessToMap);

		accessToMap = new HashMap<>();
		serviceChargeMap = new HashMap<>();
		serviceChargeMap.put(SERVICE_CHARGE, new ArrayList<>());
		accessToMap.put(ACCESS_TO, new ArrayList<>());
		accessToMap.get(ACCESS_TO).add(DL);
		serviceChargeMap.get(SERVICE_CHARGE).add("100");
		map.put(USER_TRANSPORTER, new ArrayList<>());
		map.get(USER_TRANSPORTER).add(serviceChargeMap);
		map.get(USER_TRANSPORTER).add(accessToMap);

		accessToMap = new HashMap<>();
		serviceChargeMap = new HashMap<>();
		serviceChargeMap.put(SERVICE_CHARGE, new ArrayList<>());
		accessToMap.put(ACCESS_TO, new ArrayList<>());
		accessToMap.get(ACCESS_TO).add(RC);
		serviceChargeMap.get(SERVICE_CHARGE).add("100");
		map.put(USER_MANUFACTURER, new ArrayList<>());
		map.get(USER_MANUFACTURER).add(serviceChargeMap);
		map.get(USER_MANUFACTURER).add(accessToMap);

		ACCESS_CHARGE = Collections.unmodifiableMap(map);

	}

	private Constants() {
		throw new IllegalStateException("Utility class");
	}
}
