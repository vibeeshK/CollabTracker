@echo

REM this batch process is meant for running the ColbTrkArchiveInactiveERLs

java -Dlog4j.configurationFile=${INSTALL_PATH}\mainJar64Bit\config\archiveInactiveERLslog4j2.xml -cp ${INSTALL_PATH}\mainJar64Bit\ColbTrk-0.0.1-SNAPSHOT-jar-with-dependencies.jar colbTrk.ArchiveInactiveERLs