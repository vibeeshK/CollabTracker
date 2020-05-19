@echo

REM This batch process is meant for running the ColbTrkClientUI

java -Dlog4j.configurationFile=${INSTALL_PATH}\mainJar64Bit\config\clientUIlog4j2.xml -cp ${INSTALL_PATH}\mainJar64Bit\ColbTrk-0.0.1-SNAPSHOT-jar-with-dependencies.jar colbTrk.ColbTrkClientUI

pause
