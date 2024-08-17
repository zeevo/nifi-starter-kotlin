package org.example.starter.processor

import org.apache.nifi.util.TestRunners
import org.junit.jupiter.api.Test

class ExampleProcessorTest {
    private val testRunner = TestRunners.newTestRunner(ExampleProcessor::class.java)

    @Test
    fun testProcessor() {
        testRunner.enqueue("Hello world")

        testRunner.run()

        testRunner.assertAllFlowFilesTransferred("success")
    }
}
