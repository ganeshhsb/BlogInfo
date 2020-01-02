package com.markata.ganesh_hs

import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import java.util.concurrent.TimeUnit
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import java.util.concurrent.Executor

/**
 * A class that changes RxJava schedulers to Immediate and Test schedulers for immediate actions in
 * tests and waiting timeouts for testing RxJava timeout in host java tests.
 */
class RxImmediateSchedulerRule : TestRule {

    /**
     * Get test scheduler for testing RxJava timeout.
     *
     * @return [TestScheduler] object for testing RxJava timeout.
     */
    val testScheduler: TestScheduler
        get() = TEST_SCHEDULER

    val immediateSchedulerRule: Scheduler
        get() = IMMEDIATE_SCHEDULER

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
                RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
                RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
                RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

                try {
                    base.evaluate()
                } finally {
                    RxJavaPlugins.reset()
                    RxAndroidPlugins.reset()
                }
            }
        }
    }

    companion object {

         val TEST_SCHEDULER = TestScheduler()
         val IMMEDIATE_SCHEDULER = object : Scheduler() {
            override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
                // Changing delay to 0 prevents StackOverflowErrors when scheduling with a delay.
                return super.scheduleDirect(run, 0, unit)
            }

            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() }, false)
            }
        }
    }
}