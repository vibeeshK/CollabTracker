package colbTrk;

import java.util.ArrayList;

/**
 * Handler that retrieves all users details and simplifies reading
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class UsersHandler {

	private ArrayList<UserPojo> usersList = null;
	private String[] usersNamesStrings = null;
	private String[] usersShortNames = null;

	CatelogPersistenceManager catelogPersistenceManager = null;
	Commons commons = null;
	//private static UsersHandler usersHandler = null; // removed singleton to avoid building up of unknown users

	private UsersHandler(CatelogPersistenceManager inCatelogPersistenceManager,
			Commons inCommons) {
		System.out.println("UsersHandler inCommons passed " + inCommons);
		
		catelogPersistenceManager = inCatelogPersistenceManager;

		commons = inCommons;
		usersList = catelogPersistenceManager.readUsersList();
		System.out.println("usersList.size() " + usersList.size());
		System.out.println("usersList.get(0).shortId " + usersList.get(0).rootSysLoginID);
		System.out.println("usersList.get(readUsersList.size-1).shortId " + usersList.get(usersList.size()-1).rootSysLoginID);
		creategetUsersNamesStrings();	// set up username strings

	}
	
	public int appendUserPojo(UserPojo inUserPojo){
		int insertedUserLocation = usersList.size();	// insert at the end of the list
		usersList.add(insertedUserLocation,inUserPojo);
		creategetUsersNamesStrings(); //  refresh the names lists with new name
		return insertedUserLocation;
	}
	
	public static synchronized UsersHandler createInstance(CatelogPersistenceManager inCatelogPersistenceManager,
			Commons inCommons){
		UsersHandler usersHandler = new UsersHandler(inCatelogPersistenceManager, inCommons);
		return usersHandler;
	}

	public String[] getUsersNamesStrings(){
		if (usersNamesStrings == null) {
			creategetUsersNamesStrings();
		}
		return usersNamesStrings;
	}
	
	public ArrayList<UserPojo> getUserDetails() {
		return usersList;
	}

	public void creategetUsersNamesStrings(){
		System.out.println("at creategetUsersNamesStrings ");
		
		usersNamesStrings = new String[usersList.size()];
		usersShortNames = new String[usersList.size()];
		for (int userCount = 0; userCount<usersList.size(); userCount++) {
			usersNamesStrings[userCount] = usersList.get(userCount).getDisplayString();
			usersShortNames[userCount] = usersList.get(userCount).rootSysLoginID.toUpperCase();
			System.out.println("at creategetUsersNamesStrings usersShortNames[" + userCount + "]: " + usersShortNames[userCount]);
		}
	}

	public String getUserShortnameByIndex(int userIndex){
		return usersList.get(userIndex).rootSysLoginID;
	}
	
	public int getIndexOfUserShortId(String userShortId){
		int userIndex = -1;
		System.out.println("at start of getIndexOfUserShortId : " + userShortId);
		System.out.println("in getIndexOfUserShortId usersShortNames length : " + usersShortNames);
		System.out.println("in getIndexOfUserShortId usersShortNames[0] : " + usersShortNames[0]);
		System.out.println("in getIndexOfUserShortId usersShortNames[1] : " + usersShortNames[1]);
		userIndex = commons.getIndexOfStringInArray(userShortId.toUpperCase(), usersShortNames);
		return userIndex;
	}
	
	public UserPojo getUserDetailsFromRootSysLoginID(String userShortId){
		int userIndex = -1;
		UserPojo userDetails = null;
		System.out.println("at start of getUserDetailsFromRootSysLoginID : " + userShortId);
		System.out.println("in getUserDetailsFromRootSysLoginID usersShortNames: " + usersShortNames);
		System.out.println("in getUserDetailsFromRootSysLoginID usersShortNames length : " + usersShortNames.length);
		System.out.println("in getUserDetailsFromRootSysLoginID usersShortNames[0] : " + usersShortNames[0]);

		userIndex = commons.getIndexOfStringInArray(userShortId.toUpperCase(), usersShortNames);
		if (userIndex != -1) {
			userDetails = usersList.get(userIndex);
		}
		return userDetails;
	}
	
	public int getUsersCount(){
		return usersList.size();
	}

	public boolean doesUserHaveUpdateRightsOverMember(String inSuperuser, String inMember){		
	// check if the mentioned super user has update rights over the member
		boolean hasRightsOverMember = false;

		if (inSuperuser != null && inMember != null
			&& (doesUserHaveSuperuserRightsOverMember(inSuperuser, inMember)
				|| inMember.equals(inSuperuser))){
			hasRightsOverMember = true;
		}
		return hasRightsOverMember;
	}

	public boolean doesUserHaveSuperuserRightsOverMember(String inSuperuser, String inMember){		
	// check if the mentioned super user user rights over member.
	// This doesn't consider the same member as superuser for oneself
		boolean superuser = false;
		UserPojo superUsersUserPojo = getUserDetailsFromRootSysLoginID(inSuperuser);
		UserPojo memberUserPojo = getUserDetailsFromRootSysLoginID(inMember);

		if (superUsersUserPojo != null && memberUserPojo != null
			&& (superUsersUserPojo.hasAdminPrivilege() || 
				superUsersUserPojo.hasTeamLeaderPrivilege() ||
				memberUserPojo.leadID.equals(inSuperuser))){
			superuser = true;
		}		
		return superuser;
	}
}