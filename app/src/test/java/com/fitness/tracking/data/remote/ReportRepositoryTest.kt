package com.fitness.tracking.data.remote

import com.fitness.tracking.domain.entities.ReportEntity
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit

class ReportRepositoryTest {
    @Rule
    @JvmField
    var rule = MockitoJUnit.rule()

    @Mock
    var remoteAPI: RemoteAPI? = null

    @InjectMocks
    var repository: AppRepository? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {

    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
    }

    @Test
    fun testGetSuccessful() {
        runBlocking {
            val observer = repository!!.getReports()!!.test()
            observer.awaitTerminalEvent()
            observer.assertNoErrors().assertValue { r: List<ReportEntity> -> r.isNotEmpty() }
        }
    }

}