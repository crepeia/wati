#!/usr/bin/env python

# This script replaces a string inside a document with another string. Please, configure set up before running it.
import os, glob

os.chdir("/home/hp-gomide/replaceStr")

for file in glob.glob("*.xhtml"):
    f=open(file, 'r')
    filedata = f.read()
    f.close()
    newdata = filedata.replace("lang=\"en\"","lang=\"pt,en,es,de,ru\" xml:lang=\"pt,en,es,de,ru\"")
    fa = open(file, 'w')
    fa.write(newdata)
    fa.close()