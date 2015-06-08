#!/bin/bash -x

DIR="$(dirname $0)/.."

cd "$DIR"

mkdir -p out/classes.eclipse/

cd src/


find -type f -name *.java | while read sourcefile; do javac "$sourcefile" -d ../out/classes.eclipse/; done    
