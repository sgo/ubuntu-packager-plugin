#!/bin/bash -ex

if [ -z "$1" ]; then
echo "Usage: debuild.sh path/to/build/dir"
  exit 1
fi

cd $1
exec debuild -us -uc -B