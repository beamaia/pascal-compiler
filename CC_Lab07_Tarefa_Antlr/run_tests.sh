#!/bin/bash

YEAR=$(pwd | grep -o '20..-.')
DATA=/home/zambon/Teaching/$YEAR/CC/labs/tests
OUT=out07

EXE=./tmsim

for infile in `ls $OUT/c*.tm`; do
    base=$(basename $infile)
    echo
    echo Running $base
    $EXE < $infile
done
