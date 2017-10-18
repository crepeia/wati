#!/bin/bash
# This script downloads all translation files and store them in the proper project folder. You need a crowdin API code in order to use this script.
# Author: H. Gomide
# License: GNU, of course.
echo "Please insert your Crowdin api code here: "
read $api
wget https://api.crowdin.com/api/project/livewithouttobacco/download/all.zip?key=77ee7c1bf983defaab46c5c02ce2854a$api -O translations.zip
mkdir translations
unzip -j translations.zip -d translations
cp translations/messages_pt-BR.properties ../../system/src/java/wati/utility/messages_pt.properties
cp translations/messages_de-DE.properties ../../system/src/java/wati/utility/messages_de.properties
cp translations/messages_ru-RU.properties ../../system/src/java/wati/utility/messages_ru.properties
cp translations/messages_it-IT.properties ../../system/src/java/wati/utility/messages_it.properties
cp translations/messages_es-ES.properties ../../system/src/java/wati/utility/messages_es.properties
rm -rf translations
rm translations.zip


