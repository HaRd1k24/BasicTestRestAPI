package REST;

import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static com.sun.xml.ws.spi.db.BindingContextFactory.LOGGER;
import static io.restassured.RestAssured.*;
import static org.hamcrest.core.IsEqual.equalTo;



public class APITest {

    @Test
    @Order(1)
    public void getSecondPost(){
        when().
                get("http://localhost:3000/posts/2").
        then().statusCode(200).assertThat().body("author",equalTo("bug"));
        Response response = get("http://localhost:3000/posts/2");
        LOGGER.info(""+ response.getBody().asString());

    }

    @Test
    @Order(2)
    public void postNewPost(){
        given().
                contentType("application/json").body("{\n"+
                "   \"id\":3,\n" +
                 "   \"title\":\"second\",\n" +
                "    \"author\":\"noname\"\n"+
                "  }\n").
                when().
                post("http://localhost:3000/posts").
                then().
                statusCode(201);
       LOGGER.info("New user!");

        when().
                get("http://localhost:3000/posts/3").
                then().
                statusCode(200).
                assertThat().body("author",equalTo("noname"));
        Response response = get("http://localhost:3000/posts/3");
        LOGGER.info("New post"+response.getBody().asString());

    }

    @Test
    @Order(3)
    public void changePost(){
        given().
                contentType("application/json").body("{\n"+
                "   \"id\":3,\n" +
                "   \"title\":\"THIRD\",\n" +
                "    \"author\":\"BUG\"\n"+
                "  }\n").
                when().
                put("http://localhost:3000/posts/3").
                then().
                statusCode(200);
        LOGGER.info("Post has been edited");

        when().
                get("http://localhost:3000/posts/3").
                then().
                statusCode(200).
                assertThat().body("title",equalTo("THIRD"))
                .body("author",equalTo("BUG"));
                Response response = get("http://localhost:3000/posts/3");
                LOGGER.info("New data is + " + response.getBody().asString());

    }

    @Test
    @Order(4)
    public void postDelete(){
        when().
                delete("http://localhost:3000/posts/3").
                then().
                statusCode(200).
                assertThat().body("{}", Matchers.nullValue());
        LOGGER.info("Post has been deleted");

    }
}
