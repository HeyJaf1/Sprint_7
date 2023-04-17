package courier;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class CourierSteps {
    protected final String BASE_URI = "http://qa-scooter.praktikum-services.ru";
    protected final String POST_CREATE_COURIER = "/api/v1/courier";
    protected final String POST_LOGIN_COURIER = "/api/v1/courier/login";
    protected final String DELETE_COURIER = "/api/v1/courier/";
    @Step("create new courier")
    public ValidatableResponse create(Courier courier) {
        return getSpec()
                .body(courier)
                .when()
                .post(POST_CREATE_COURIER)
                .then().log().all();
    }

    private RequestSpecification getSpec() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI);
    }
    @Step("login courier with login and password")
    public ValidatableResponse login(Credentials credentials) {
        return getSpec()
                .body(credentials)
                .when()
                .post(POST_LOGIN_COURIER)
                .then().log().all();
    }

    @Step
    public ValidatableResponse deleteCourier(int id) {
        return getSpec()
                .when()
                .delete(DELETE_COURIER + id)
                .then();
    }
}