@echo

REM This batch process is meant for running the ColbTrk Xtd Decker orchestrator

java -Dlog4j.configurationFile=${INSTALL_PATH}\mainJar64Bit\config\xtdDeckerProcOrchestlog4j2.xml -cp ${INSTALL_PATH}\mainJar64Bit\ColbTrkXtdSrvrComp-0.0.1-SNAPSHOT-jar-with-dependencies xtdSrvrComp.XtdDeckerProcOrchestrator
