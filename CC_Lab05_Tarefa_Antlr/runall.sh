#!/bin/bash

YEAR=$(pwd | grep -o '20..-.')
# Ajeite essa variável como feito no Makefile
ROOT=/home/zambon/Teaching/$YEAR/CC/labs
DATA=$ROOT/tests
IN=$DATA/in
OUT=./out05

ANTLR_PATH=$ROOT/tools/antlr-4.9.2-complete.jar
CLASS_PATH_OPTION="-cp .:$ANTLR_PATH"

rm -rf $OUT
mkdir $OUT
for infile in `ls $IN/c*.ezl`; do
    base=$(basename $infile)
    outfile=$OUT/${base/.ezl/.out}
    dotfile=$OUT/${base/.ezl/.dot}
    pdffile=$OUT/${base/.ezl/.pdf}
    echo Running $base
    java $CLASS_PATH_OPTION:bin checker/Main $infile 1> $outfile 2> $dotfile
    dot -Tpdf $dotfile -o $pdffile
done
