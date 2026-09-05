package api;

import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import framework.config.ConfigReader;

import java.util.Map;

public class APIClient {

    private final Playwright playwright;
    private final APIRequestContext apiRequestContext;

    public APIClient() {

        playwright = Playwright.create();

        apiRequestContext =
                playwright.request().newContext(
                        new com.microsoft.playwright.APIRequest
                                .NewContextOptions()
                                .setBaseURL(
                                        ConfigReader.get("api.base.url")
                                )
                                .setExtraHTTPHeaders(
                                        Map.of(
                                                "Content-Type",
                                                "application/json"
                                        )
                                )
                );
    }

    // GET
    public APIResponse get(String endpoint) {

        return apiRequestContext.get(endpoint);
    }

    // GET with query parameters
    public APIResponse getWithQueryParams(
            String endpoint,
            Map<String, String> queryParams) {

        StringBuilder queryString =
                new StringBuilder(endpoint);

        if (!queryParams.isEmpty()) {

            queryString.append("?");

            for (Map.Entry<String, String> entry :
                    queryParams.entrySet()) {

                queryString
                        .append(entry.getKey())
                        .append("=")
                        .append(entry.getValue())
                        .append("&");
            }

            queryString.setLength(
                    queryString.length() - 1
            );
        }

        return apiRequestContext.get(
                queryString.toString()
        );
    }

    // GET with custom header
    public APIResponse getWithHeader(
            String endpoint,
            String headerName,
            String headerValue) {

        return apiRequestContext.get(
                endpoint,
                RequestOptions.create()
                        .setHeader(
                                headerName,
                                headerValue
                        )
        );
    }

    // GET with Bearer token
    public APIResponse getWithBearerToken(
            String endpoint) {

        String token =
                ConfigReader.get("api.token");

        return apiRequestContext.get(
                endpoint,
                RequestOptions.create()
                        .setHeader(
                                "Authorization",
                                "Bearer " + token
                        )
        );
    }

    // POST
    public APIResponse post(
            String endpoint,
            Object requestBody) {

        return apiRequestContext.post(
                endpoint,
                RequestOptions.create()
                        .setData(requestBody)
        );
    }

    // POST with custom header
    public APIResponse postWithHeader(
            String endpoint,
            Object requestBody,
            String headerName,
            String headerValue) {

        return apiRequestContext.post(
                endpoint,
                RequestOptions.create()
                        .setHeader(
                                headerName,
                                headerValue
                        )
                        .setData(requestBody)
        );
    }

    // POST with Bearer token
    public APIResponse postWithBearerToken(
            String endpoint,
            Object requestBody) {

        String token =
                ConfigReader.get("api.token");

        return apiRequestContext.post(
                endpoint,
                RequestOptions.create()
                        .setHeader(
                                "Authorization",
                                "Bearer " + token
                        )
                        .setData(requestBody)
        );
    }

    // PUT
    public APIResponse put(
            String endpoint,
            Object requestBody) {

        return apiRequestContext.put(
                endpoint,
                RequestOptions.create()
                        .setData(requestBody)
        );
    }

    // PATCH
    public APIResponse patch(
            String endpoint,
            Object requestBody) {

        return apiRequestContext.patch(
                endpoint,
                RequestOptions.create()
                        .setData(requestBody)
        );
    }

    // DELETE
    public APIResponse delete(String endpoint) {

        return apiRequestContext.delete(endpoint);
    }

    // CLOSE
    public void close() {

        apiRequestContext.dispose();
        playwright.close();
    }
}