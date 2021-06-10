![Maven metadata URL](https://img.shields.io/maven-metadata/v?color=blue&label=maven%20central&logo=gradle&metadataUrl=https%3A%2F%2Fs01.oss.sonatype.org%2Fservice%2Flocal%2Frepositories%2Freleases%2Fcontent%2Fcom%2Ftheonlytails%2Fblockmodels%2Fmaven-metadata.xml&style=for-the-badge)
![GitHub Workflow Status](https://img.shields.io/github/workflow/status/TheOnlyTails/blockmodels/Java%20CI%20with%20Gradle?label=gradle%20build&logo=github&style=for-the-badge)
![GitHub top language](https://img.shields.io/github/languages/top/TheOnlyTails/blockmodels?logo=kotlin&logoColor=white&style=for-the-badge)
![GitHub License](https://img.shields.io/github/license/theonlytails/blockmodels?style=for-the-badge&logo=key)

# BlockModels

A Kotlin DSL for creating block models in Minecraft Forge mods.

For documentation and usage instructions, please take a look at
the [wiki](https://github.com/TheOnlyTails/BlockModels/wiki).

Here's
the [`maven-metadata.xml`](https://s01.oss.sonatype.org/service/local/repositories/releases/content/com/theonlytails/blockmodels/maven-metadata.xml)
of this library.

## Installation

###### Don't forget to replace the VERSION key with the version in the top with the Maven Central badge at the top!

#### Gradle/Groovy

```groovy
repositories {
    mavenCentral()
}

dependencies {
    def blockModels = fg.deobf(project.dependencies.create(group: "com.theonlytails", name: "blockmodels", version: VERSION) {
	    transitive = false
    })
    
    implementation fg.deobf(blockModels)
}
```

#### Gradle/Kotlin

```kotlin
repositories {
    mavenCentral()
}

dependencies {
    val blockModels = project.dependencies.create(group = "com.theonlytails", name = "blockmodels", version = VERSION)
		.apply { isTransitive = false }
    
    implementation(project.the<DependencyManagementExtension>().deobf(blockModels))
}
```

The `isTransitive` property is added to make sure the library is imported correctly.
