# Starter verification

Checked on 18 November 2026:

- Apache Maven 3.9.11 `clean verify`: BUILD SUCCESS using JDK 27 with Java 17 release compilation. All 13 Java source files compiled. No automated test suite is included in the starter.
- Separate Swing smoke check: dashboard opens, all seven navigation buttons open their assigned JFrame, and each module Close button disposes its own window. Database configuration is not needed to launch.
- Missing database configuration produces the intended explanatory SQLException.
- `pom.xml` and all seven `.form` XML files parse; each `.form` has a corresponding `.java` file. NetBeans Design-mode loading itself has not been tested in an installed IDE.
- Git ignore check confirms local `config/database.properties` and `target/` are ignored, while form files and the configuration example remain trackable.
- SQL columns and foreign-key relationships were compared with the supplied ERD image (`database/erd-reference.png`). Exactly seven tables are declared. MySQL import and a live JDBC connection were not tested because a MySQL server was unavailable.

Build output and machine-local Maven files are excluded from the deliverable ZIP. Dependency versions are pinned in `pom.xml`. No GitHub repository has been created or pushed; the leader performs the documented first push using the group's chosen repository.
