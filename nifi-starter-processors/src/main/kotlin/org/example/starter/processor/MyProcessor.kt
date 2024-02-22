package org.example.starter.processor

import org.apache.nifi.annotation.documentation.CapabilityDescription
import org.apache.nifi.annotation.documentation.Tags
import org.apache.nifi.annotation.lifecycle.OnScheduled
import org.apache.nifi.components.PropertyDescriptor
import org.apache.nifi.flowfile.FlowFile
import org.apache.nifi.processor.AbstractProcessor
import org.apache.nifi.processor.ProcessContext
import org.apache.nifi.processor.ProcessSession
import org.apache.nifi.processor.ProcessorInitializationContext
import org.apache.nifi.processor.Relationship
import org.apache.nifi.processor.util.StandardValidators

@Tags("example")
@CapabilityDescription("Provide a description")
class MyProcessor : AbstractProcessor() {

    private var MY_PROPERTY: PropertyDescriptor =
        PropertyDescriptor.Builder()
            .name("MY_PROPERTY")
            .displayName("My property")
            .description("Example Property")
            .required(true)
            .addValidator(StandardValidators.NON_EMPTY_VALIDATOR)
            .build()

    private var MY_RELATIONSHIP: Relationship =
        Relationship.Builder()
            .name("MY_RELATIONSHIP")
            .description("Example relationship")
            .build()

    private var descriptors: List<PropertyDescriptor> = listOf(MY_PROPERTY)

    private var relationships: Set<Relationship> = setOf(MY_RELATIONSHIP)

    private var myProperty: String = ""

    override fun getRelationships(): Set<Relationship> {
        return this.relationships
    }

    override fun getSupportedPropertyDescriptors(): List<PropertyDescriptor> {
        return descriptors
    }

    override fun init(context: ProcessorInitializationContext) {
        descriptors = listOf(MY_PROPERTY)
        relationships = hashSetOf(MY_RELATIONSHIP)
    }

    @OnScheduled
    fun onScheduled(context: ProcessContext) {
        myProperty = context.getProperty(MY_PROPERTY).evaluateAttributeExpressions().toString()
    }

    override fun onTrigger(context: ProcessContext, session: ProcessSession) {
        val flowFile: FlowFile = session.get() ?: return

        session.write(flowFile) { o -> o.write(myProperty.toByteArray()) }

        session.transfer(flowFile, MY_RELATIONSHIP)
    }
}