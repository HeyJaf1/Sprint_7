package order;

import io.restassured.response.ValidatableResponse;
import org.junit.Test;

public class GetOrderTest {
    private OrderSteps step = new OrderSteps();
    private OrderAssertions check = new OrderAssertions();
    @Test
    public void checkBody() {
        ValidatableResponse response = step.getOrderList();
        check.checkBodyList(response);
    }
}