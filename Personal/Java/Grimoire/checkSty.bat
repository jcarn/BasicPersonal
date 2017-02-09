@ECHO OFF >nul
ECHO %1
ECHO %2
GOTO checkSpecific
:checkSpecific
    java -jar checkstyle-6.2.2.jar -c cs1331-checkstyle.xml %1
    GOTO Javadoc

:Javadoc
IF "%2" == "JD" (
    java -jar checkstyle-6.2.2.jar -j %1
    javadoc %1 -d javadoc >nul
)
@ECHO ON