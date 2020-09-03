package com.fraud.apps.domain.mappers

import com.fraud.apps.domain.entities.ReportEntity
import com.fraud.apps.domain.exceptions.MapperException
import com.fraud.apps.domain.models.Report
import com.fraud.apps.utils.FormatterDate
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.junit.MockitoJUnit

class ReportMapperTest {
    @Rule
    @JvmField
    var rule = MockitoJUnit.rule()

    @InjectMocks
    var reportMapper: ReportMapper? = null
    private var report: Report? = null
    private var reportEntity: ReportEntity? = null

    @Before
    fun setUp() {
        report = Report()
        report!!.id = "1"
        reportEntity = ReportEntity()
        reportEntity!!.id = "1"
        reportEntity!!.createdAt = FormatterDate.stringToDate("2020-09-03T21:13:45+0700")
        reportEntity!!.updatedAt = FormatterDate.stringToDate("2020-09-03T21:13:45+0700")
    }

    @Test
    fun createObjectValid() {
        Assert.assertNotNull(reportMapper!!.createObject())
    }

    @Test
    fun createEntityValid() {
        Assert.assertNotNull(reportMapper!!.createEntity())
    }

    @Test
    @Throws(MapperException::class)
    fun defineObjectValid() {
        reportMapper!!.baseEntity = reportEntity
        Assert.assertSame(report, reportMapper!!.defineObject(report))
    }

    @Test
    @Throws(MapperException::class)
    fun defineEntityValid() {
        Assert.assertSame(reportEntity, reportMapper!!.defineEntity(reportEntity))
    }
}