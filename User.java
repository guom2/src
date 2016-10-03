
public class User {

	String username;
	String ip_address;
	
	User(String name, String address) {
		this.username = name;
		this.ip_address = address;
	}
	
	@Override
	public String toString() {
		return(username);
	}
}
