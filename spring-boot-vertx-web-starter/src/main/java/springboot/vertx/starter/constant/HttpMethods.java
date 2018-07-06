package springboot.vertx.starter.constant;

/**
 * @author iheng
 * @date 3/8/18
 */
public enum HttpMethods {
    GET(HttpMethod.GET),
    POST(HttpMethod.POST),
    DELETE(HttpMethod.DELETE),
    PUT(HttpMethod.PUT),
    OPTIONS(HttpMethod.OPTIONS),
    HEAD(HttpMethod.HEAD);

    private HttpMethod value;

    HttpMethods(HttpMethod value){
        this.value=value;
    }

    public HttpMethod getValue() {
        return value;
    }
}
