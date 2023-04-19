<root>
├── build.gradle.kts (or build.gradle)
├── settings.gradle.kts (or settings.gradle)
├── gradle.properties
├── gradlew
├── gradlew.bat
├── .gitignore
└── src
    ├── main
    │   ├── kotlin
    │   │   └── com
    │   │       └── yourcompany
    │   │           └── yourproject
    │   │               ├── application
    │   │               │   ├── dtos
    │   │               │   ├── mappers
    │   │               │   └── services
    │   │               ├── domain
    │   │               │   ├── entities
    │   │               │   ├── repositories
    │   │               │   └── usecases
    │   │               ├── infrastructure
    │   │               │   ├── config
    │   │               │   ├── persistence
    │   │               │   │   ├── entities
    │   │               │   │   ├── repositories
    │   │               │   │   └── datasource
    │   │               │   └── web
    │   │               │       ├── controllers
    │   │               │       └── filters
    │   │               └── yourproject.kt (main application file)
    │   └── resources
    │       ├── application.properties (or application.yml)
    │       └── static
    │           └── (static resources)
    └── test
        ├── kotlin
        │   └── com
        │       └── yourcompany
        │           └── yourproject
        │               ├── application
        │               │   ├── mappers
        │               │   └── services
        │               ├── domain
        │               │   ├── entities
        │               │   ├── repositories
        │               │   └── usecases
        │               └── infrastructure
        │                   └── persistence
        │                       └── repositories
        └── resources
            └── (test resources)
