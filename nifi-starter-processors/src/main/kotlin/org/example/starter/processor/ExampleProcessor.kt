package org.example.starter.processor

import org.apache.nifi.annotation.documentation.CapabilityDescription
import org.apache.nifi.annotation.documentation.Tags
import org.apache.nifi.components.PropertyDescriptor
import org.apache.nifi.flowfile.FlowFile
import org.apache.nifi.processor.AbstractProcessor
import org.apache.nifi.processor.ProcessContext
import org.apache.nifi.processor.ProcessSession
import org.apache.nifi.processor.Relationship

@Tags("starter")
@CapabilityDescription("Starter processor")
class ExampleProcessor : AbstractProcessor() {
    val success: Relationship = Relationship
        .Builder()
        .name("success")
        .description("success")
        .build()

    val failure: Relationship = Relationship
        .Builder()
        .name("failure")
        .description("failure")
        .build()

    override fun getRelationships(): Set<Relationship> {
        return setOf(success, failure)
    }

    override fun getSupportedPropertyDescriptors(): List<PropertyDescriptor> {
        return listOf()
    }

    override fun onTrigger(context: ProcessContext, session: ProcessSession) {
        val flowFile: FlowFile = session.get() ?: return

        session.transfer(flowFile, success)
    }
}