package courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

public class CourierAssertions {
    @Step("Create courier in system")
    public void createdSuccess(ValidatableResponse response) {
        response.assertThat()
                .statusCode(201)
                .body("ok", is(true));
    }

    @Step("Login courier in system")
    public void loginSuccess(ValidatableResponse response) {
        response.assertThat()
                .statusCode(200)
                .body("id", greaterThan(0));
    }

    @Step("There aren't all values in body for create")
    public void createCourierFailed(ValidatableResponse response) {
        response.assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Step("Create courier with")
    public void createCourierAvailable(ValidatableResponse response) {
        response.assertThat()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется"));
    }

    @Step("Check message after login")
    public void loginCourierWithoutLogin(ValidatableResponse response) {
        response.assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Step("Check message after")
    public void loginCourierWithFalseValues(ValidatableResponse response) {
        response.assertThat()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }
}