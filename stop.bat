@echo off
chcp 65001 > nul
echo Остановка проекта Carpet Study Program...
docker compose down
echo Проект успешно остановлен.
pause