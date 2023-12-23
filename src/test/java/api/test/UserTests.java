package api.test;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests {

	Faker faker;
	User payload;
	
	public Logger logger;

	@BeforeClass
	public void setupData() {

		faker = new Faker();
		payload = new User();

		payload.setId(faker.idNumber().hashCode());
		payload.setFirstName(faker.name().firstName());
		payload.setLastName(faker.name().lastName());
		payload.setEmail(faker.internet().safeEmailAddress());
		payload.setPassword(faker.internet().password(5, 10));
		payload.setPhone(faker.phoneNumber().cellPhone());
		payload.setUsername(faker.name().username());

		logger = LogManager.getLogger(this.getClass());
	}

	@Test(priority = 1)
	public void testPostUser() {

		logger.info("******************** Creating User *****************");
		Response response = UserEndPoints.createUser(payload);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("******************** User is created *****************");
	}

	@Test(priority = 2)
	public void testgetUser() {

		logger.info("******************** Reading User info *****************");
		Response response = UserEndPoints.readUser(payload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
	}

	@Test(priority = 3)
	public void testUpdateUser() {

		logger.info("******************** Updating User data *****************");
		payload.setFirstName(faker.name().firstName());
		payload.setLastName(faker.name().lastName());
		payload.setEmail(faker.internet().safeEmailAddress());
		payload.setPassword(faker.internet().password(5, 10));

		Response response = UserEndPoints.updateUser(payload.getUsername(), payload);
		response.then().statusCode(200);
		response.then().log().all();

		Response responseAfterUpdate = UserEndPoints.readUser(payload.getUsername());
		responseAfterUpdate.then().statusCode(200);
		responseAfterUpdate.then().log().body();

	}

	@Test(priority = 4)
	public void testDeleteUser() {

		logger.info("******************** Deleting User *****************");
		Response response = UserEndPoints.deleteUser(payload.getUsername());
		response.then().statusCode(200);
		response.then().log().body();

	}
}
