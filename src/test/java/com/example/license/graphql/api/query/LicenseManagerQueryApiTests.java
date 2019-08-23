package com.example.license.graphql.api.query;


import com.example.license.model.License;
import com.example.license.repository.LicenseRepository;
import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class LicenseManagerQueryApiTests {

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    @Autowired
    private LicenseRepository licenseRepository;

    @Before
    public void setUp() {
        License license1 = new License("test-license-1", "aaaa-xxxxx-11111");
        License license2 = new License("test-license-2", "aaaa-xxxxx-22222");
        licenseRepository.save(license1);
        licenseRepository.save(license2);
    }

    @Test
    public void getAllLicenses() throws IOException {
        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/licenses.graphql");
        assertNotNull(response);
        assertTrue(response.isOk());
        assertEquals("test-license-1", response.get("$.data.licenses[0].name"));

    }

    @Test
    public void getLicense() throws IOException {
        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/license.graphql");
        assertNotNull(response);
        assertTrue(response.isOk());
        assertEquals("test-license-1", response.get("$.data.license.name"));
        assertEquals("aaaa-xxxxx-11111", response.get("$.data.license.secretKey"));
    }

    @Test
    public void isValidationErrorRaisedWhenFetchingAllLicenseWithSecretKey() throws IOException, JSONException {
        GraphQLResponse response = graphQLTestTemplate.
                postForResource("graphql/licensesWrongRequestWithSecretKey.graphql");
        JSONObject rootJson = new JSONObject(response.getRawResponse().getBody());
        JSONArray errorsJson = rootJson.getJSONArray("errors");
        JSONObject errorDetails = errorsJson.getJSONObject(0);
        assertNotNull(response);
        assertTrue(response.isOk());
        assertEquals("ValidationError", errorDetails.getString("errorType"));
        String errorDescription = errorDetails.getString("description");
        assertTrue(errorDescription.contains("secretKey"));
    }


    @Test
    public void isSecretKeyAvailableWhenFetchingSingleLicense() throws IOException, JSONException {
        GraphQLResponse response = graphQLTestTemplate.
                postForResource("graphql/license.graphql");

        JSONObject dataJson = new JSONObject(response.getRawResponse().getBody()).getJSONObject("data");
        JSONObject licenseJson = dataJson.getJSONObject("license");

        assertNotNull(response);
        assertTrue(response.isOk());
        assertTrue(licenseJson.has("secretKey"));
    }

    @Test
    public void getAllLicensesByStatus() throws IOException, JSONException {
        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/licensesByStatusNEW.graphql");
        JSONObject dataJson = new JSONObject(response.getRawResponse().getBody()).getJSONObject("data");
        JSONArray licensesByStatusJson = dataJson.getJSONArray("licensesByStatus");
        assertNotNull(response);
        assertTrue(response.isOk());
        assertEquals(2, licensesByStatusJson.length());
        assertEquals("NEW", response.get("$.data.licensesByStatus[0].status"));
        assertEquals("NEW", response.get("$.data.licensesByStatus[1].status"));
    }


}