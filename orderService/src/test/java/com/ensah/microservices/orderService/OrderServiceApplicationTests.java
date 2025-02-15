package com.ensah.microservices.orderService;

import com.ensah.microservices.orderService.stubs.InventoryClientStub;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import static org.hamcrest.MatcherAssert.assertThat;

import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
class OrderServiceApplicationTests {

	@ServiceConnection
	static MySQLContainer mySQLContainer = new MySQLContainer<>(DockerImageName.parse("mysql:latest"));
	@LocalServerPort
	private Integer port;
	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}
	static {
		mySQLContainer.start();
	}
	@Test
	void shouldSubmitOrder() {
		String submittedOrderJSON = """
				{
				    "orderNumber":"55555",
				    "skuCode":"iphone15",
				    "price": 1000,
				    "quantity": 1
				}
				""";
		InventoryClientStub.stubInventoryCall("iphone15", 1);
		String string = RestAssured.given()
				.contentType(ContentType.JSON)
				.body(submittedOrderJSON)
				.when()
				.post("api/order")
				.then()
				.log().all()
				.statusCode(200)
				.extract()
				.body()
				.asString();
		assertThat(string, Matchers.is("Order Placed"));
	}

}
