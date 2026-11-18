# Group 4 - Laundry Services Management System

One shared Apache NetBeans Maven project for seven members. Java 17 is the source/target baseline. The dashboard and seven placeholder JFrame screens run without MySQL. CRUD, billing calculations and processing rules are next steps; the starter does not load or save records.

## Open and run in Apache NetBeans

1. Install a JDK 17 or newer and Apache NetBeans with Java and Maven support. Use the same JDK version across the group; JDK 17 is the project baseline. NetBeans itself may need a newer JDK depending on the version installed.
2. Extract the project ZIP, or clone your group's repository. Open the folder containing `pom.xml` using **File > Open Project**. Do not create another project around it.
3. Allow Maven to download dependencies on the first build (internet required). Set the project's Java platform to a compatible JDK if NetBeans prompts you.
4. Right-click the project > **Clean and Build**, then **Run** (F6). If asked for the main class, select `com.group4.laundry.Main`.
5. The dashboard opens seven module windows. Closing a module returns you to the dashboard; closing the dashboard exits the application.

For a terminal with Maven 3.9+ and a JDK on PATH, from the project root:

```sh
mvn clean verify
mvn exec:java
```

NetBeans bundles Maven; a separate Maven installation is only necessary for terminal commands. Use `mvn exec:java` for the complete runtime classpath. The ordinary Maven JAR is not packaged as a standalone double-click application.

## Seven-member ownership

| Member | Module / branch | Form | Tables |
| --- | --- | --- | --- |
| 1 | `feature/customers` | `CustomerForm` | `customers` |
| 2 | `feature/services` | `ServiceForm` | `services` |
| 3 | `feature/add-ons` | `AddOnForm` | `add_ons` |
| 4 | `feature/orders` | `OrderForm` | `orders`, `order_details` |
| 5 | `feature/billing` | `BillingForm` | `order_details`, `order_detail_add_ons` |
| 6 | `feature/processing` | `ProcessingForm` | `orders` |
| 7 | `feature/policies-dashboard` | `ShopPolicyForm`, `MainDashboard` | `shop_policies`, dashboard integration |

Replace member numbers with your names as a group. Members 4/5 must agree on order-detail handling; members 6/7 must agree on storage policy and pickup rules. Member 7 coordinates shared navigation, and everyone reviews integration.

## Project layout and JFrame editing

```text
pom.xml
config/database.properties.example
database/laundry_database.sql
src/main/java/com/group4/laundry/
  Main.java
  config/DatabaseConnection.java
  ui/MainDashboard.java
  ui/*Form.java + *Form.form
  model/package-info.java
  dao/package-info.java
  service/package-info.java
```

Open your module's `*Form.java` and switch to **Design** to edit the JFrame with NetBeans GUI Builder. Each form uses a simple BorderLayout with a title, placeholder area and Close button. Replace the placeholder area with your controls. Commit the `.java` and matching `.form` together. Use the GUI Builder for generated sections; put event logic outside them. `MainDashboard` uses hand-written Swing and is edited in Source only.

Place SQL operations in `dao`, shared records/classes in `model`, and business rules/transactions in `service`. Use prepared statements and try-with-resources. Keep slow database operations off the Swing event thread (for example, use SwingWorker). Orders with details and add-ons should be saved in a single transaction when implemented. Use BigDecimal for money.

## Database setup (optional for the dashboard)

The SQL reproduces the seven tables, columns, data types and FK links from the supplied Group 4 ERD image:
`customers`, `orders`, `shop_policies`, `services`, `order_details`, `add_ons`, `order_detail_add_ons`.
There are no added tables or columns, including no payments table.

The ERD does not define nullability, defaults, auto-increment behavior or delete rules. The starter uses auto-increment INT primary keys, required foreign keys, nullable business fields and restrictive FK deletes/updates. Review these implementation assumptions together before implementing CRUD. The unusual names `free_days_of_ready` and `fee_per_day_if_ready` are preserved from the diagram. Status values, fee formulas, policy defaults and rounding rules still need a group agreement.

1. Start MySQL 8.0 or newer. In MySQL Workbench, open `database/laundry_database.sql` and execute it against a development server with permission to create the `laundry_db` database and its tables. Use a new, empty database. The script contains no DROP statements and intentionally fails on existing tables rather than silently accepting a conflicting schema.
2. Alternatively, open the MySQL command-line client and run `SOURCE /absolute/path/to/database/laundry_database.sql;` (use forward slashes for Windows paths in this command).
3. Create a local application database user with permissions appropriate to development. Each member configures their own credentials. Do not use real customer records as test data.
4. Copy `config/database.properties.example` to `config/database.properties`. Fill in your local URL, user and password. This local file is ignored by Git, and is read relative to the project root. Java properties files use escaping: double a literal backslash (`\\`) in a password. Environment variables avoid that file-format escaping.
5. Alternatively set `LAUNDRY_DB_URL`, `LAUNDRY_DB_USER` and `LAUNDRY_DB_PASSWORD` in your environment before starting NetBeans/the app. Each environment variable overrides its corresponding local property. An explicitly empty password is supported; no password or username is hardcoded.

Future DAOs can open a connection with:

```java
try (java.sql.Connection connection =
        com.group4.laundry.config.DatabaseConnection.open()) {
    // Use a PreparedStatement and close it and its ResultSet here.
}
```

The MySQL driver is declared in `pom.xml`; do not add downloaded driver JARs manually. The app does not connect at startup. `DatabaseConnection.open()` reports missing configuration when called. Avoid displaying/logging raw driver exceptions because they can include connection details.

Each local database has separate records. GitHub shares source and SQL structure, not live data. To share identical starter records, agree on a separate synthetic seed script later. A shared database server can share live development records if the group chooses that approach.

## First GitHub upload (leader)

Create an empty GitHub repository named `group4-laundry-management-system`. Leave the initial README, license and .gitignore options unchecked because these files already exist locally. From this project folder (the one containing `pom.xml`):

```sh
git init -b main
git add .
git status
git commit -m "Create Group 4 NetBeans Swing starter"
git remote add origin https://github.com/YOUR-OWNER/group4-laundry-management-system.git
git push -u origin main
```

Replace `YOUR-OWNER` with your account/organization. Sign in using GitHub's supported authentication when prompted; do not embed a token in the remote URL. Before committing, inspect `git status` and confirm `config/database.properties` is absent. The project is delivered without a pre-created Git history so the group chooses its own author and repository.

Invite the other six members as collaborators using the repository's **Settings > Collaborators** (or the organization's access controls). Set `main` to require pull-request reviews if available for your repository/account.

## Other members: clone and start a branch

```sh
git clone https://github.com/YOUR-OWNER/group4-laundry-management-system.git
cd group4-laundry-management-system
git switch -c feature/customers
```

Use your assigned branch from the table, then open the cloned folder in NetBeans. Do not create another unrelated NetBeans project or Git repository.

For each change (example for Member 1):

```sh
git add src/main/java/com/group4/laundry/ui/CustomerForm.java src/main/java/com/group4/laundry/ui/CustomerForm.form
git commit -m "Build customer form"
git push -u origin feature/customers
```

Also stage any DAO, model or service files you actually changed. Open a pull request from the feature branch to `main`. Have another member review it; build and try the application before merging. Shared `pom.xml`, SQL, connection configuration and dashboard changes need coordination. Keep ERD changes out of module work until the group approves them.

After a merge, with a clean working tree:

```sh
git switch main
git pull --ff-only origin main
git switch feature/customers
git merge main
```

Resolve conflicts together, then rebuild. Avoid concurrent edits to the same form. Never overwrite another member's work or force-push `main`.

## Initial verification checklist

- Build the project and launch the dashboard.
- Open all seven module screens and verify each Close button works.
- Open each module in NetBeans Design; save `.java` and `.form` together.
- Import SQL into an empty local MySQL database and confirm exactly seven tables.
- Keep credentials and `target/` out of Git.

See `VERIFICATION.md` for checks performed on this delivered starter.

## Dependency references

Versions are pinned for repeatable builds. Official references: [Maven Compiler Plugin releases](https://github.com/apache/maven-compiler-plugin/releases), [Exec Maven Plugin releases](https://github.com/mojohaus/exec-maven-plugin/releases), [MySQL Connector/J release notes](https://dev.mysql.com/doc/relnotes/connector-j/en/).
