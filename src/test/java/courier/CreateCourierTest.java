package courier;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CreateCourierTest {
    private CourierGenerator generator = new CourierGenerator();
    private CourierAssertions check;
    private CourierSteps step;
    private Courier courier;
    int id;

    @Before
    @Step("create objects")
    public void setUp() {
        step = new CourierSteps();
        courier = generator.random();
        check = new CourierAssertions();
    }

    @Test
    @Description("Create courier, login, get path and get id for delete")
    public void createCourierSuccessTest() {
        check.createdSuccess(step.create(courier));
        id = step.login(Credentials.from(courier)).extract().path("id");
    }

    @Test
    @Description("Create courier without login")
    public void createCourierFailedTest() {
        courier.setLogin(null);
        check.createCourierFailed(step.create(courier));
    }

    @Test
    @Description("Create courier without password")
    public void createCourierFailed2Test() {
        courier.setPassword(null);
        check.createCourierFailed(step.create(courier));
    }

    @Test
    @Description("Create courier with same login") //тут баг в теле ответа.
    public void createCourierDouble() {
        step.create(generator.courierDefault());
        check.createCourierAvailable(step.create(generator.courierDefault()));
    }

    @After
    @Step("delete test courier")
    public void deleteCourier() {
        if (id != 0) {
            step.deleteCourier(id);
        }
    }
}