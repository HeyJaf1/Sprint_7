package courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LoginCourierTest {
    private CourierGenerator generator = new CourierGenerator();
    private CourierAssertions check;
    private CourierSteps step;
    private Courier courier;
    private Credentials credentials;
    int id;

    @Before
    @Step("create objects for tests")
    public void setUp() {
        step = new CourierSteps();
        courier = generator.random();
        step.create(courier);
        credentials = Credentials.from(courier);
        check = new CourierAssertions();

    }


    @Test
    @Step("login with correct values")
    public void loginSuccessFullyTest() {
        ValidatableResponse responseLogin = step.login(credentials);
        id = responseLogin.extract().path("id");
        check.loginSuccess(responseLogin);
    }

    @Test
    @Step("login without login and check message error")
    public void loginWithotLoginTest() {
        check.loginCourierWithoutLogin(step.login(new Credentials("", courier.getPassword())));

    }

    @Test
    @Step("login without password and check message error")
    public void loginWithoutPasswordTest() {
        Credentials credentialsWithoutPassword = new Credentials(courier.getLogin(), "");
        ValidatableResponse responseMessageError = step.login(credentialsWithoutPassword);
        check.loginCourierWithoutLogin(responseMessageError);
    }

    @Test
    @Step("Check massage after input incorrect login")
    public void loginWithNotCorrectLogin() {
        Credentials credentialsWithNotCorrectLogin = new Credentials("qwerty", credentials.getPassword());
        ValidatableResponse responseMessageError = step.login(credentialsWithNotCorrectLogin);
        check.loginCourierWithFalseValues(responseMessageError); //тут баг
        id = responseMessageError.extract().path("id");
    }

    @Test
    @Step("Check message after login courier with incorrect password")
    public void loginWithNotCorrectPassword() {
        Credentials credentialsHere = new Credentials(credentials.getLogin(), "0000");
        check.loginCourierWithFalseValues(step.login(credentialsHere));
    }

    @After
    @Step("delete")
    public void deleteCourier() {
        if (id != 0) {
            step.deleteCourier(id);
        }
    }

    @After
    @Step("check")
    public void checkCourier() {
        if (id != 0) {
            step.deleteCourier(id);
        }
    }
}