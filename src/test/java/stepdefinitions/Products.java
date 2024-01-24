package stepdefinitions;
import io.cucumber.cienvironment.internal.com.eclipsesource.json.JsonObject;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import static org.junit.Assert.*;
import org.json.simple.JSONObject;

public class Products {

    public int StatusCode;
    public Response response;
    public RequestSpecification httpRequest;
    public int ResponseCode;
    public ResponseBody body;
    public  JSONObject requestParams;
    public JsonPath jsnpath;

    @Given("I hit the url of GET products API endpoint")
    public void i_hit_the_url_of_get_products_API_endpoint(){
        RestAssured.baseURI="https://fakestoreapi.com/";
    }

    @When("I pass the url of products in the request")
    public void i_pass_the_url_of_products_in_the_request() {
        httpRequest=RestAssured.given();
        response = httpRequest.get("products");
    }

    @Then("I receive the response code as {int}")
    public void i_receive_the_response_code_as(Integer int1) {
        ResponseCode = response.getStatusCode();
        assertEquals(ResponseCode,200);
    }

    @Then("I verify the rate of the first product is {}")
    public void i_verify_the_rate_of_the_first_product_is(String rate)
    {
        body=response.getBody();
        //convert body to string
        String responseBody=body.asString();
        //JSON representation from the Respnse body
        jsnpath=response.jsonPath();
        String s=jsnpath.getJsonObject("rating[0].rate").toString();
        assertEquals(rate,s);

    }

    @Given("I hit the url of post products api endpoint")
    public void i_hit_the_url_of_post_products_api_endpoint()
    {
        RestAssured.baseURI="https://fakestoreapi.com/";
        httpRequest = RestAssured.given();
        requestParams = new JSONObject();
    }

    @And("I pass the request body of product title {}")
    public void i_pass_the_request_body_of_product_title(String title)
    {
        requestParams.put("title",title);
        requestParams.put("price","13.5");
        requestParams.put("description","lorem ipsum set");
        requestParams.put("image","https://i.pravatar.cc");
        requestParams.put("category","electronic");
        httpRequest.body(requestParams.toJSONString());
        response=httpRequest.post("products");
        body=response.getBody();
        jsnpath=response.jsonPath();

        System.out.println(response.getStatusLine());
        System.out.println(body.asString());
    }

    @Then("I receive the response body with id as {}")
    public void iReceiveTheResponseBodyWithIdAs(String id) {
        String s=jsnpath.getJsonObject("id").toString();
        assertEquals("21",s);
    }

    @Given("I hit the url of put products api endpoint")
    public void iHitTheUrlOfPutProductsApiEndpoint() {
        RestAssured.baseURI="https://fakestoreapi.com/";
        requestParams = new JSONObject();
    }

    @When("I pass the url of products in the request with {}")
    public void iPassTheUrlOfProductsInTheRequestWith(String productnumber) {
        httpRequest=RestAssured.given();

        requestParams.put("title","test product");
        requestParams.put("price","13.5");
        requestParams.put("description","lorem ipsum set");
        requestParams.put("image","https://i.pravatar.cc");
        requestParams.put("category","electronic");

        httpRequest.body(requestParams.toJSONString());
        response = httpRequest.put("products/"+productnumber);
        body=response.getBody();
        jsnpath=response.jsonPath();
        System.out.println(response.getStatusLine());
        System.out.println(body.asString());
    }


    @Given("I hit the url of delete products api endpoint")
    public void iHitTheUrlOfDeleteProductsApiEndpoint() {
        RestAssured.baseURI="https://fakestoreapi.com/";
        requestParams = new JSONObject();
    }

    @When("I pass the url of delete product in the request with {}")
    public void iPassTheUrlOfDeleteProductInTheRequestWith(String productnumber) {
        httpRequest=RestAssured.given();

        requestParams.put("title","test product");
        requestParams.put("price","13.5");
        requestParams.put("description","lorem ipsum set");
        requestParams.put("image","https://i.pravatar.cc");
        requestParams.put("category","electronic");

        httpRequest.body(requestParams.toJSONString());
        response = httpRequest.delete("products/"+productnumber);
        body=response.getBody();
        jsnpath=response.jsonPath();
        System.out.println(response.getStatusLine());
        System.out.println(body.asString());
    }
}
