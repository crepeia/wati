#!/bin/bash

# File with translation tags
VAR='translationSheet.csv'

# Create pt-br sheet
awk -F',' ' NF {print $1"="$2}' $VAR > pt-br.txt
sed 's/\"//' pt-br.txt > messages_pt.properties
native2ascii messages_pt.properties > ~/wati/system/src/java/wati/utility/messages_pt.properties

# Create es sheet
awk -F',' ' NF {print $1"="$3}' $VAR > es.txt
sed 's/\"//' es.txt > messages_es.properties
native2ascii messages_es.properties > ~/wati/system/src/java/wati/utility/messages_es.properties

# Create en-us sheet
awk -F',' ' NF {print $1"="$4}' $VAR > en-us.txt
sed 's/\"//' en-us.txt > messages_en.properties
native2ascii messages_en.properties > ~/wati/system/src/java/wati/utility/messages_en.properties

# Create de sheet
awk -F',' ' NF {print $1"="$6}' $VAR > de.txt
sed 's/\"//' de.txt > messages_de.properties
native2ascii messages_de.properties > ~/wati/system/src/java/wati/utility/messages_de.properties

# Create ru sheet
awk -F',' ' NF {print $1"="$5}' $VAR > ru.txt
sed 's/\"//' ru.txt > messages_ru.properties
native2ascii messages_ru.properties > ~/wati/system/src/java/wati/utility/messages_ru.properties

# Remove temp files
rm ru.txt de.txt en-us.txt es.txt pt-br.txt
