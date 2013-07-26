#!/bin/bash
if [ $# -ne 2 ]; then
  echo "Usage: <Project dir> <Deploy Dir>"
  exit 1
fi

DW_DIR=$1
DEPLOY_DIR=$2

cd $DW_DIR
cp dist/designwizard-1.3.jar $DEPLOY_DIR

ant javadoc

zip -r designwizard-1.3-javadoc.zip doc/
mv designwizard-1.3-javadoc.zip $DEPLOY_DIR

zip -r designwizard-1.3-src.zip src/
mv designwizard-1.3-src.zip $DEPLOY_DIR