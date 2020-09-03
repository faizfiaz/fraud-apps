package com.fraud.apps.data.remote;

import com.fraud.apps.domain.entities.requests.LoginRequest;
import com.fraud.apps.domain.entities.response.UserResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import io.reactivex.observers.TestObserver;

public class ReportRepositoryTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock RemoteAPI remoteAPI;

    @InjectMocks
    AppRepository repository;

    private LoginRequest request;
    private UserResponse response;

    @Before
    public void setUp() throws Exception {
        request = new LoginRequest();
        request.setIdentifier("identifier");
        request.setPassword("123");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testLoginSuccessful() {
        LoginRequest request = new LoginRequest("+620727", "456");
        TestObserver<UserResponse> observer = repository.login(request).test();
        observer.awaitTerminalEvent();
        observer.assertNoErrors().assertValue(r -> !r.getData().getToken().isEmpty());
    }

    @Test
    public void testLoginUnauthorized() {
        LoginRequest request = new LoginRequest("+620727", "4567");
        TestObserver<UserResponse> observer = repository.login(request).test();
        observer.awaitTerminalEvent();
        observer.assertNoErrors().assertValue(r -> r.getModelStatus().getStatus() == 401);
    }
    @Test
    public void testGetUser(){

    }
}