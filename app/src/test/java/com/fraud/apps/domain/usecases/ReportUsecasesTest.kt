package com.fraud.apps.domain.usecases

import com.fraud.apps.data.remote.AppRepository
import com.fraud.apps.domain.mappers.FraudMapper
import com.fraud.apps.domain.mappers.ReportMapper
import com.fraud.apps.domain.usecases.user.AppUsecases
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.junit.MockitoJUnit

class ReportUsecasesTest {

    @Rule
    @JvmField
    var rule = MockitoJUnit.rule()
    var appUsecases: AppUsecases? = null

    @InjectMocks
    var reportMapper: ReportMapper? = null

    @InjectMocks
    var fraudMapper: FraudMapper? = null

    @InjectMocks
    var appRepository: AppRepository? = null

    @Before
    fun setUp() {
        appUsecases = AppUsecases(reportMapper!!, fraudMapper!!, appRepository)
    }

    @Test
    fun testGetReportApi() {
        runBlocking {
            var data = appUsecases!!.getReportListFromAPI().test()
            data.assertComplete()
            Assert.assertTrue(data.values() is List<*>)
        }
    }
}