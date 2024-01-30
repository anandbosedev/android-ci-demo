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

The ideal workflow:
1. Use the container image `anandbose16/android-sdk:34`
2. Checkout code
3. Generate build with `:app:assembleDebug` task
4. Execute lint with `:app:lintDebug` task
5. Execute unit tests with `:app:testDebugUnitTest` task
6. Execute instrumented tests with `:app:mediumPhoneApi34AospAtdDebugAndroidTest` task
7. Upload the APK file and the test reports to artifacts storage.

> Note: The implementation of the workflow is different on each platform, and platform implied limitations will apply.

| Platform | Repo | Configuration | Supports Build | Supports Lint | Supports Unit Tests | Support Instrumented Tests |
|----------|------|---------------|----------------|---------------|---------------------|----------------------------|
| GitHub | [Repo](https://github.com/anandbosedev/android-ci-demo) | [.github/workflows/main.yml](.github/workflows/main.yml) | ✅ | ✅ | ✅ | ✅ |
| GitLab | [Repo](https://gitlab.com/anandbose/android-ci-demo) | [.gitlab-ci.yml](.gitlab-ci.yml) | ✅ | ✅ | ✅ | ⛔<sup>[1]</sup> |
| BitBucket | [Repo](https://bitbucket.org/anandbose/android-ci-demo) | [bitbucket-pipelines.yml](bitbucket-pipelines.yml) | ✅ | ✅ | ✅ | ⛔<sup>[2]</sup> |
| Azure DevOps | [Repo](https://dev.azure.com/anandbose/android-ci-demo) | [azure-pipelines.yml](azure-pipelines.yml) | ✅ | ✅ | ✅ | ⛔<sup>[3]</sup> |

> <sup>[1][2]</sup> *Running emulators in GitLab and BitBucket pipelines are not supported due to lack of [KVM](https://developer.android.com/studio/run/emulator-acceleration#vm-linux) hypervisor.*<br>
<sup>[3]</sup> *Running emulators in Azure is not supported due to lack of [KVM](https://developer.android.com/studio/run/emulator-acceleration#vm-linux) hypervisor support. However, Azure provides a task [AppCenterTest@1](https://learn.microsoft.com/en-us/azure/devops/pipelines/tasks/reference/app-center-test-v1?view=azure-pipelines) requires paid subscription in [VS AppCenter](https://appcenter.ms/).*

## Roadmap

I am working on this during my free times. Here are the list of things I plan to do:

1. Explore the ways to automate the container image builds, at least during vendor updates (Android SDK toolchain, Gradle, OpenJDK, emulator, base image security updates, etc)
2. Provide more container images for different Android versions
3. Create workflow configurations for GitLab, BitBucket, Azure DevOps etc.
4. Improve performance with persisting Gradle caches.

#### *Feel free to send comments, suggestions, issues and pull requests!*