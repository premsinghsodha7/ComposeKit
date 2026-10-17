# ComposeKit

> Reusable Jetpack Compose building blocks for clear, polished finance interfaces.

ComposeKit is an Android library module plus a runnable demo app. Its components are presentation-only: they do not send payments or persist financial data.

## Components

- `PrimaryActionButton` — full-width action with enabled and loading states.
- `BalanceSummaryCard` — balance, title, and supporting detail in a themed card.
- `AmountField` — currency-labelled decimal input with validation feedback.
- `TransactionSummaryRow` — compact transaction title, date, and signed amount.
- `ComposeKitTheme` and `ComposeKitColors` — a small reusable color foundation.

## Run the demo

Open the repository in Android Studio, select the `demo` run configuration, and run it on an emulator or device with API 24+. Or use `./gradlew :demo:installDebug` with a connected device.

The demo screen shows each component together, including amount validation and a loading-state example. All sample values are fictional.

## Use the library in another app

Include this repository as a Gradle project and add the module dependency:

```kotlin
dependencies {
    implementation(project(":composekit"))
}
```

The host app must enable Compose and include Compose Material 3. For example:

```kotlin
PrimaryActionButton(
    text = "Continue",
    onClick = { /* handle the action in the host app */ }
)
```

## Build and tests

- Kotlin 2.0.21, Compose, Material 3
- Android Gradle Plugin 8.7.3, Gradle 8.9, JDK 17
- minSdk 24, targetSdk 35
- `./gradlew testDebugUnitTest :demo:assembleDebug`
- GitHub Actions runs unit tests and assembles the demo app on pushes and pull requests.

## Scope

This is a portfolio component library, not a financial service. Components do not process payments, store customer data, or connect to a backend.

## License

MIT. See [LICENSE](LICENSE).
