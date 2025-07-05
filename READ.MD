Run specific tags: mvn test -Dtags="@smoke and not @wip"
Parallel execution: mvn test -DthreadCount=4
Headless mode override: mvn test -Dheadless=false

mvn test -Denv=ipb
mvn clean test -Denv=pba
mvn test -Ppba

cmd.exe : mvn clean test -Pipb,smoke
PowerShell: mvn clean test "-Pipb,smoke"

mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="codegen demo.playwright.dev/todomvc"

This framework provides:

✅ Full BDD support with Cucumber

✅ Cross-browser testing (Chromium, Firefox, WebKit)

✅ Configurable execution modes (headless/headed)

✅ Advanced reporting with failure analysis

✅ Robust test data management

✅ Parallel execution capability

✅ Automatic screenshot capture

✅ Retry mechanism for flaky tests

✅ Environment-specific configuration

✅ Component-based page object design