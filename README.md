# ComposeKit

> A small, reusable Jetpack Compose component library for polished Android finance interfaces.

ComposeKit collects focused UI building blocks for apps that need clear money entry and confident primary actions. The first release provides a branded action button, a balance summary card, and an amount field with a semantic label. Components are deliberately presentation-only: they do not send payments or store financial data.

## Components

- `PrimaryActionButton` — full-width action with enabled and loading states.
- `BalanceSummaryCard` — accessible title, amount, and supporting detail.
- `AmountField` — decimal amount input with explicit currency label.

## Add the library

Open the repository in Android Studio, then include `:composekit` in your Android project's settings and add it as a dependency:

```kotlin
dependencies {
    implementation(project(":composekit"))
}
```

The host app must enable Compose and include the Compose Material 3 dependencies. The library targets Android API 24+ and uses JDK 17.

## Example

```kotlin
PrimaryActionButton(
    text = "Continue",
    onClick = { /* handle in the host app */ }
)
```

## Scope

This is an early portfolio starter. Planned work includes Compose previews, UI tests, typography/color tokens, dark theme variants, and usage examples. No real financial service integration is included.

## License

MIT. See [LICENSE](LICENSE).
