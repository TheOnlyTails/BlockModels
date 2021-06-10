import com.vanniktech.maven.publish.MavenPublishPluginExtension
import com.vanniktech.maven.publish.SonatypeHost
import net.minecraftforge.gradle.common.util.ModConfig
import net.minecraftforge.gradle.common.util.RunConfig
import net.minecraftforge.gradle.userdev.UserDevExtension
import org.gradle.jvm.toolchain.JvmVendorSpec.ADOPTOPENJDK
import java.time.Instant.now
import java.time.format.DateTimeFormatter.ISO_INSTANT

// BuildScript
buildscript {
	repositories {
		maven(url = "https://maven.minecraftforge.net/")
		jcenter()
		mavenCentral()
	}

	dependencies {
		classpath(group = "net.minecraftforge.gradle", name = "ForgeGradle", version = "4.1.+") {
			isChanging = true
		}
		classpath(group = "com.vanniktech", name = "gradle-maven-publish-plugin", version = "latest.release")
	}
}

plugins {
	idea
	`java-library`
	kotlin("jvm") version "latest.release"
}
apply(plugin = "com.vanniktech.maven.publish")
apply(plugin = "net.minecraftforge.gradle")

// Config -> Minecraft
val forgeVersion: String by extra
val minecraftVersion: String by extra

@Suppress("PropertyName")
val VERSION_NAME: String by extra

@Suppress("PropertyName")
val GROUP: String by extra { "com.theonlytails" }

@Suppress("PropertyName")
val POM_ARTIFACT_ID: String by extra { "blockmodels" }

// JVM Info
println(
	"""
		Java: ${System.getProperty("java.version")} 
		JVM: ${System.getProperty("java.vm.version")} (${System.getProperty("java.vendor")}) 
		Arch: ${System.getProperty("os.arch")}
	""".trimIndent()
)

// Minecraft
configure<UserDevExtension> {
	mappings("official", minecraftVersion)

	// @Suppress("SpellCheckingInspection")
	// accessTransformer(file("src/test/resources/META-INF/accesstransformer.cfg"))

	runs(closureOf<NamedDomainObjectContainer<RunConfig>> {
		create("client") {
			workingDirectory(file("run"))

			taskName = "client"

			// Recommended logging data for a userdev environment
			property("forge.logging.markers" to "SCAN,REGISTRIES,REGISTRYDUMP")

			// Recommended logging level for the console
			property("forge.logging.console.level" to "debug")

			mods {
				create(POM_ARTIFACT_ID) {
					source(sourceSets["test"])
				}
			}
		}

		create("server") {
			workingDirectory(file("run"))

			taskName = "server"

			// Recommended logging data for a userdev environment
			property("forge.logging.markers" to "SCAN,REGISTRIES,REGISTRYDUMP")

			// Recommended logging level for the console
			property("forge.logging.console.level" to "debug")

			mods {
				create(POM_ARTIFACT_ID) {
					source(sourceSets["test"])
				}
			}
		}

		create("data") {
			workingDirectory(file("run"))

			taskName = "datagen"

			// Recommended logging data for a userdev environment
			property("forge.logging.markers" to "SCAN,REGISTRIES,REGISTRYDUMP")

			// Recommended logging level for the console
			property("forge.logging.console.level" to "debug")

			// Specify the mod id for data generation, where to output the resulting resource, and where to look for existing resources.
			args(
				"--mod",
				POM_ARTIFACT_ID,
				"--all",
				"--output",
				file("src/generated/resources/"),
				"--existing",
				file("src/test/resources/")
			)

			mods {
				create(POM_ARTIFACT_ID) {
					source(sourceSets["test"])
				}
			}
		}
	})
}

// Minecraft Dependency
// Note: Due to the way kotlin gradle works we need to define the minecraft dependency after we configure Minecraft
dependencies {
	"minecraft"(group = "net.minecraftforge", name = "forge", version = "$minecraftVersion-$forgeVersion")
	implementation(group = "thedarkcolour", name = "kotlinforforge", version = "latest.release")
	implementation(group = "io.github.microutils", name = "kotlin-logging-jvm", version = "latest.release")
}

repositories {
	maven {
		name = "kotlinforforge"
		url = uri("https://thedarkcolour.github.io/KotlinForForge/")
	}
}

// Setup
project.group = GROUP
project.version = VERSION_NAME
base.archivesBaseName = POM_ARTIFACT_ID

// Sets the toolchain to compile against OpenJDK 8
java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(8))
		vendor.set(ADOPTOPENJDK)
	}
}

// Finalize the jar by re-obfuscating
tasks.named<Jar>("jar") {
	// Manifest
	manifest {
		attributes(
			"Specification-Title" to "BlockModels",
			"Specification-Vendor" to "TheOnlyTails",
			"Specification-Version" to "1",
			"Implementation-Title" to "BlockModels",
			"Implementation-Version" to project.version,
			"Implementation-Vendor" to "TheOnlyTails",
			"Implementation-Timestamp" to ISO_INSTANT.format(now()),
			"FMLModType" to "LIBRARY"
		)
	}

	finalizedBy("reobfJar")
}

// Publishing to maven central
extensions.getByType<MavenPublishPluginExtension>().sonatypeHost = SonatypeHost.S01

fun RunConfig.mods(closure: NamedDomainObjectContainer<ModConfig>.() -> Unit) = mods(closureOf(closure))
fun RunConfig.property(property: Pair<String, String>) = property(property.first, property.second)