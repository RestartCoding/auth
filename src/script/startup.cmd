::ECHO OFF
javaw -server -XX:NewSize=128M -XX:MaxNewSize=128M -XX:SurvivorRatio=8 -Xms512M -Xmx512M -jar auth.jar