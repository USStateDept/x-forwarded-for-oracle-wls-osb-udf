#!/bin/bash -x

DIR="$(dirname $0)/.."

cd "$DIR"

cd out/classes.eclipse

jar cvf ../osb-x-forwarded-for.jar .

cd ..

cp -vf ../osb-x-forwarded-for.properties .
cp -vf ../osb-x-forwarded-for.xml .

tar czvf osb-x-forwarded-for.tgz osb-x-forwarded-for.jar osb-x-forwarded-for.properties osb-x-forwarded-for.xml
