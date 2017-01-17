@echo off

echo -------------------- AutoConfig --------------------

echo Git settting: assume unchanged files...

git update-index --assume-unchanged project.properties
git update-index --assume-unchanged .classpath
git update-index --assume-unchanged .project

echo ----------------------- End ------------------------

pause