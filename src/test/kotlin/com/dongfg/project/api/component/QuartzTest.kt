package com.dongfg.project.api.component

import org.junit.Assert
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner

/**
 * @author dongfg
 * @date 2018/3/17
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@ActiveProfiles("dev")
@FixMethodOrder(MethodSorters.JVM)
class QuartzTest {

    class QuartzTestJob : Job {
        override fun execute(context: JobExecutionContext?) {
            println("tick...")
        }

    }

    @Autowired
    private lateinit var quartz: Quartz

    @Test
    @Throws(Exception::class)
    fun allInOne() {
        getJobKeys()
        submitJob()
        getJobKeys()
        exists()
        getTrigger()
        getTriggerState()
        triggerJob()
        pauseJob()
        resumeJob()
        removeJob()
    }

    @Test
    @Throws(Exception::class)
    fun submitJob() {
        quartz.submitJob(QuartzTestJob::class.java.simpleName, QuartzTestJob::class.java, cronExpression = "0/10 * * * * ?")
        Thread.sleep(1000 * 10)
    }

    @Test
    fun getJobKeys() {
        val jobKeys = quartz.getJobKeys()
        Assert.assertNotNull(jobKeys)
        Assert.assertTrue(jobKeys.isNotEmpty())
        print(jobKeys)
    }

    @Test
    fun getTrigger() {
        val trigger = quartz.getTrigger(QuartzTestJob::class.java.simpleName)
        println(trigger)
    }

    @Test
    fun getTriggerState() {
        val state = quartz.getTriggerState(QuartzTestJob::class.java.simpleName)
        println(state.name)
    }

    @Test
    fun exists() {
        Assert.assertTrue(quartz.exists(QuartzTestJob::class.java.simpleName))
    }

    @Test
    fun triggerJob() {
        Assert.assertTrue(quartz.triggerJob(QuartzTestJob::class.java.simpleName))
    }

    @Test
    fun pauseJob() {
        Assert.assertTrue(quartz.pauseJob(QuartzTestJob::class.java.simpleName))
    }

    @Test
    fun resumeJob() {
        Assert.assertTrue(quartz.resumeJob(QuartzTestJob::class.java.simpleName))
    }

    @Test
    fun removeJob() {
        Assert.assertTrue(quartz.removeJob(QuartzTestJob::class.java.simpleName))
    }
}