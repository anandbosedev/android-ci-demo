# Demonstration of CI setup for Android projects

This is a demonstration of how to setup CI for Android projects. In this repository, we have a simple counter application built with Jetpack Compose. We aim to automate the build & instrumented testing on every push/merge to main branch.

## The TestApp

This is how the app looks like:

![](ss.png)

[A short video here](test_app.webm)

I have written 3 basic instrumented tests to check if:
1. The initial state is 0, just after the fresh start
2. The value is 1 after pressing "Increment" button, just after the fresh start
3. The value is -1 after pressing the "Decrement" button, just after the fresh start

## Instrumented tests with Gradle managed devices

Gradle makes us easier to quickly spin up an emulated device with the required specifications. In the `app/build.gradle.kts`, we provide the specification of our medium sized phone:

```kotlin
testOptions {
    managedDevices {
        localDevices {
            create("mediumPhoneApi34AospAtd") {
                device = "Medium Phone"
                apiLevel = 34
                systemImageSource = "aosp-atd"
            }
        }
    }
}
```

This gives us a Gradle task `mediumPhoneApi34AospAtdDebugAndroidTest` which builds the project, spins up the emulator instance with `aosp-atd` image (a light-weight image specifically for instrumentation testing), executes the test cases and generate reports.

## Test automation workflow

For automating build generation and test execution, we use a pre-built Android SDK toolchain container image [anandbose16/android-sdk](https://hub.docker.com/r/anandbose16/android-sdk) ([GitHub](https://github.com/anandbosedev/android-sdk)) to minimise the setup time and simplifying the configuration. The container image meets the requirements for basic Android development environment such as Android SDK toolchain, Gradle, OpenJDK, emulator and basic command-line utilities for post tasks.

The workflow is simple:
1. Use the container anandbose16/android-sdk:34 inside GitHub provided Ubuntu host
2. Checkout code
3. Perform build and execute tests with `mediumPhoneApi34AospAtdDebugAndroidTest` Gradle task
4. Upload the APK file and the test reports beautified by Gradle.

The implementation of this workflow is hosting platform specific, and you can refer the implementations here:

* GitHub Workflow: [Repo](https://github.com/anandbosedev/android-ci-demo) [.github/workflows/main.yml](.github/workflows/main.yml)
* GitLab CI: [Repo](https://gitlab.com/anandbose/android-ci-demo) [.gitlab-ci.yml](.gitlab-ci.yml) *(Instrumented testing is broken because of no KVM in GitLab CI runners)*
* Azure DevOps Pipelines: [Repo (awaiting Azure DevOps Parallelism from Microsoft)](https://dev.azure.com/anandbose/android-ci-demo) [azure-pipelines.yml](azure-pipelines.yml)
* BitBucket Pipelines: [Repo](https://bitbucket.org/anandbose/android-ci-demo) [bitbucket-pipelines.yml](bitbucket-pipelines.yml)
## Roadmap

I am working on this during my free times. Here are the list of things I plan to do:

1. Explore the ways to automate the container image builds, at least during vendor updates (Android SDK toolchain, Gradle, OpenJDK, emulator, base image security updates, etc)
2. Provide more container images for different Android versions
3. Create workflow configurations for GitLab, BitBucket, Azure DevOps etc.
4. Improve performance with persisting Gradle caches.

#### *Feel free to send comments, suggestions, issues and pull requests!*