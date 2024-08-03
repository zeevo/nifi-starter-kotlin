# nifi-starter-kotlin

Write your NiFi processors in Kotlin.

```bash
git clone https://github.com/zeevo/nifi-starter-kotlin.git
```

## Create a new Processor

```kotlin
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
```
