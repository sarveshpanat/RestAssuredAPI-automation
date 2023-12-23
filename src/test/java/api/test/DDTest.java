package api.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class DDTest {

	@Test(priority = 1, dataProvider = "Data", dataProviderClass = DataProviders.class)
	public void testPostUser(String userID, String username, String fname, String lname, String email, String pwd,
			String ph) {

		User userpayload = new User();
		userpayload.setId(Integer.parseInt(userID));
		userpayload.setUsername(username);
		userpayload.setFirstName(fname);
		userpayload.setLastName(lname);
		userpayload.setEmail(email);
		userpayload.setPassword(pwd);
		userpayload.setPhone(ph);

		Response response = UserEndPoints.createUser(userpayload);
		response.then().log().body();

		Assert.assertEquals(response.getStatusCode(), 200);
	}

	@Test(priority = 2, dataProvider = "UserNames", dataProviderClass = DataProviders.class)
	public void testGetUser(String username) {

		Response response = UserEndPoints.readUser(username);
		response.then().log().body();
		Assert.assertEquals(response.getStatusCode(), 200);
	}

	@Test(priority = 3, dataProvider = "UserNames", dataProviderClass = DataProviders.class)
	public void testDeleteUserByName(String username) {

		Response response = UserEndPoints.deleteUser(username);
		response.then().statusCode(200);
		response.then().log().body();
	}
}
