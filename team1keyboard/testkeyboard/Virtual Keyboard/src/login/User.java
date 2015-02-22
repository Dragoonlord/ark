package login;

public class User {
	private String userName;
	public User(String user){
		setUser(user);
	}
	public void setUser(String user){
		if(user != null || !user.isEmpty()){
			userName = user;
		}else{
			throw new IllegalArgumentException("The User Name was null or Empty");
			
		}
		
	}
	
	public String getUser(){
		String str = userName;
		return str;
	}
}
