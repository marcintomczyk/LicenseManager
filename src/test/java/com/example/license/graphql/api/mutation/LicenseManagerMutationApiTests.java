package com.example.license.graphql.api.mutation;


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

import javax.persistence.EntityManager;
import java.io.IOException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class LicenseManagerMutationApiTests {

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private EntityManager em;

    @Before
    public void setUp() {
        License license1 = new License("test-license-1", "aaaa-xxxxx-11111");
        licenseRepository.save(license1);
    }

    @Test
    public void isLicenseCreated() throws IOException {
        GraphQLResponse response = graphQLTestTemplate.perform("graphql/newLicense.graphql", null);
        assertNotNull(response);
        assertTrue(response.isOk());
        assertEquals("test-license-1", response.get("$.data.newLicense.name"));
        assertEquals("aaa-1259589331-xxxx", response.get("$.data.newLicense.secretKey"));
        assertEquals("NEW", response.get("$.data.newLicense.status"));
	}

    @Test()
    public void IsErrorRaisedWhenUpdatingLicenseStatusWithWrongSecretKey() throws IOException, JSONException {
        GraphQLResponse response = graphQLTestTemplate.perform("graphql/updateLicenseStatusWithWrongSecretKey.graphql", null);
        JSONObject rootJson = new JSONObject(response.getRawResponse().getBody());
        JSONArray errorsJson = rootJson.getJSONArray("errors");
        JSONObject errorDetails = errorsJson.getJSONObject(0);
        assertNotNull(response);
        assertTrue(response.isOk());
        assertEquals("DataFetchingException", errorDetails.getString("errorType"));
        String errorMessage = errorDetails.getString("message");
        assertEquals("SecretKeys don't match", errorMessage);
    }

    @Test
    public void isLicensedUpdatedSuccessfullyToUnhealthyStatus() throws IOException {
        GraphQLResponse response = graphQLTestTemplate
                .perform("graphql/updateLicenseToUnhealthyStatus.graphql", null);
        assertNotNull(response);
        assertTrue(response.isOk());
        assertEquals("test-license-1", response.get("$.data.updateLicenseStatus.name"));
        assertEquals("aaaa-xxxxx-11111", response.get("$.data.updateLicenseStatus.secretKey"));
        assertEquals("UNHEALTHY", response.get("$.data.updateLicenseStatus.status"));
    }

    @Test()
    public void isErrorRaisedWhenUpdatingLicenseWithUnallowedValue() throws IOException, JSONException {
        GraphQLResponse response = graphQLTestTemplate
                .perform("graphql/updateLicenseToUnallowedStatus.graphql", null);
        System.out.println(response.getRawResponse());
        JSONObject rootJson = new JSONObject(response.getRawResponse().getBody());
        JSONArray errorsJson = rootJson.getJSONArray("errors");
        JSONObject errorDetails = errorsJson.getJSONObject(0);
        assertNotNull(response);
        assertTrue(response.isOk());
        assertEquals("ValidationError", errorDetails.getString("errorType"));
        String errorMessage = errorDetails.getString("message");
        assertTrue(errorMessage.contains("argument 'input.status' with value 'EnumValue{name='STALE'}' is not a valid 'UpdateLicenseStatus'"));
    }


}