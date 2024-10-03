## Notes and Implementation Details

### Code Analysis Tools:

- Added Ktlint & Detekt for keeping the same style for the code within the whole team.
- Added pre-commit hook to run Ktlint and Detekt before code is pushed to remote branch

### Architecture pattern:

This project is using MVVM architecture with Clean Code guidelines. ViewModel exposes 2 flows, one
for updating ViewStates and one for one-time actions. Wherever there is a need for UseCase we create
one, otherwise we just use repository directly in our ViewModel. Repository component is gathering
the data from different data sources.

### Project structure

We are using single module structure with features divided into a separate folders which could be
easily converted to their own modules.

Every feature is divided into 4 folders(layers):

1. Data
   Here is the place where we are storing everything related to data. Our Data sources,
   repositories, retrofit API implementations, and remote/local models.
2. Domain
   In Domain, we have our business logic. Every UseCase is stored here, and domain models and
   mappers to map our data models to domain models.
3. Presentation
   Presentation is a location where we store our ViewModels, ViewStates, and other
   presentation-related components.
4. UI
   In UI we are having our Composable screens. If there would be a need for a UI component that is
   present only on one screen, we would store that also here.

### Added libraries:

- *Koin* -> for dependency injection.
- *Ktor* -> for networking.
- *Compose Multiplatform* -> toolkit UI for multiple platform targets.
- *Compose Navigation Component* -> for navigating in the app between the screens.
- *KVault* -> persistent storage for small data.
- *Inspektify* -> debug tool for inspecting network communication.
- *MockK* -> mocking library for testing purposes
- *BuildKonfig* -> supports different build env variables and different flavours for the project

### Tests

- Added Konsist linter to guard consistency in the project and to prevent bad usage of our code.
  There are some tests written for checking general rules of writing code. Additionally, we also
  have tests that check how are layers of our project structure depend on each other and where in
  our project structure we are storing architecture components like UseCase, Repository, etc. Those
  tests are located under konsistTest module.
- Added unit tests for the domain layer.

### Commit messages:

Commit messages are being done without any rules on how they need to be written. I am used to adding
a ticket number in front of the commit message whenever committing something. I found that way
really useful if there was a need to investigate with which ticket a part of the code changed or
written.
