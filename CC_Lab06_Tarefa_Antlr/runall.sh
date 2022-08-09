#!/bin/bash

YEAR=$(pwd | grep -o '20..-.')
# Ajeite essa variável como feito no Makefile
ROOT=/home/zambon/Teaching/$YEAR/CC/labs
DATA=$ROOT/tests
IN=$DATA/in

ANTLR_PATH=$ROOT/tools/antlr-4.9.2-complete.jar
CLASS_PATH_OPTION="-cp .:$ANTLR_PATH"

for infile in `ls $IN/c*.ezl`; do
    base=$(basename $infile)
    outfile=$OUT/${base/.ezl/.out}
    echo
    echo Running $base
    java $CLASS_PATH_OPTION:bin Main $infile
done
