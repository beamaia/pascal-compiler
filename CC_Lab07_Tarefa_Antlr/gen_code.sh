#!/bin/bash

YEAR=$(pwd | grep -o '20..-.')
ROOT=/home/zambon/Teaching/$YEAR/CC/labs
DATA=$ROOT/tests
IN=$DATA/in
OUT=out07

ANTLR_PATH=$ROOT/tools/antlr-4.9.2-complete.jar
CLASS_PATH_OPTION="-cp .:$ANTLR_PATH"

mkdir -p $OUT
for infile in `ls $IN/c*.ezl`; do
    base=$(basename $infile)
    outfile=$OUT/${base/.ezl/.tm}
    echo
    echo Compiling $base
    java $CLASS_PATH_OPTION:bin Main $infile > $outfile
done
