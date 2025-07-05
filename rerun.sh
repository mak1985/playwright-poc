#!/bin/bash

echo "Checking for failed scenarios..."
if [ -s target/failed_scenarios.txt ]; then
    echo "🔁 Retrying failed scenarios..."
    mvn test -Dtest=org.jpmc.awm.tcoe.runners.RetryRunner
else
    echo "✅ No failed scenarios to retry."
fi
