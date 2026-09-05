package api;

import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;

public class ApiClient {

    private final APIRequestContext requestContext;

    public ApiClient(APIRequestContext requestContext) {
        this.requestContext = requestContext;
    }

    // GET
    public APIResponse get(String endpoint) {
        return requestContext.get(endpoint);
    }

    // GET with query parameters / headers
    public APIResponse get(
            String endpoint,
            RequestOptions options) {

        return requestContext.get(endpoint, options);
    }

    // POST
    public APIResponse post(
            String endpoint,
            String requestBody) {

        return requestContext.post(
                endpoint,
                RequestOptions.create()
                        .setData(requestBody)
        );
    }

    // POST with custom options
    public APIResponse post(
            String endpoint,
            String requestBody,
            RequestOptions options) {

        options.setData(requestBody);

        return requestContext.post(endpoint, options);
    }

    // PUT
    public APIResponse put(
            String endpoint,
            String requestBody) {

        return requestContext.put(
                endpoint,
                RequestOptions.create()
                        .setData(requestBody)
        );
    }

    // DELETE
    public APIResponse delete(String endpoint) {
        return requestContext.delete(endpoint);
    }
}